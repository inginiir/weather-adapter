package kalita.projects.weatheradapter.route;

import kalita.projects.weatheradapter.model.MessageA;
import kalita.projects.weatheradapter.model.MessageB;
import kalita.projects.weatheradapter.service.WeatherAddingService;
import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.ws.rs.core.MediaType;

@Component
public class MyAdapterRouteBuilder extends RouteBuilder {

    @Value("${server.port}")
    String serverPort;

    @Value("${serviceA.api.path}")
    String contextPath;

    private WeatherAddingService weatherAddingService;

    public MyAdapterRouteBuilder(WeatherAddingService weatherAddingService) {
        this.weatherAddingService = weatherAddingService;
    }

    @Override
    public void configure() throws Exception {

        configureRestService();

        rest("/api/").description("Test REST ServiceA")
                .id("routeA")
                .post("/add-weather")
                .produces(MediaType.APPLICATION_JSON)
                .consumes(MediaType.APPLICATION_JSON)
                .bindingMode(RestBindingMode.auto)
                .type(MessageA.class)
                .enableCORS(true)
                .to("direct:remoteServiceA");

        from("direct:remoteServiceA")
                .routeId("routeB")
                .tracing()
                .process(exchange -> {
                    MessageA messageA = (MessageA) exchange.getIn().getBody();
                    MessageB messageB = weatherAddingService.addWeatherDataToMessage(messageA);
                    exchange.getIn().setBody(messageB);
                })
                .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(201));
    }

    private void configureRestService() {
        CamelContext context = new DefaultCamelContext();
        restConfiguration().contextPath(contextPath) //
                .port(serverPort)
                .enableCORS(true)
                .apiContextPath("/api-doc")
                .apiProperty("api.title", "Test REST API")
                .apiProperty("api.version", "v1")
                .apiProperty("cors", "true")
                .apiContextRouteId("doc-api")
                .component("servlet")
                .bindingMode(RestBindingMode.json)
                .dataFormatProperty("prettyPrint", "true");

    }
}
