package kalita.projects.weatheradapter.service;

import kalita.projects.weatheradapter.model.Coordinates;
import kalita.projects.weatheradapter.model.Language;
import kalita.projects.weatheradapter.model.MessageA;
import kalita.projects.weatheradapter.model.MessageB;
import kalita.projects.weatheradapter.model.weather_api.OpenWeatherApi;
import kalita.projects.weatheradapter.service.exceptions.WeatherServiceNotAvailableException;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class WeatherAddingServiceTest {

    private RestTemplate restTemplate = mock(RestTemplate.class);
    private WeatherAddingService weatherAddingService = new WeatherAddingService(restTemplate);

    @Test
    void successfulMessageCreate() throws WeatherServiceNotAvailableException {
        OpenWeatherApi openWeatherApi = new OpenWeatherApi();
        openWeatherApi.setCurrentTemperature(25);
        MessageA messageA = new MessageA("Hello", Language.RU, new Coordinates("55.42","61.04"));
        String uri = "http://api.openweathermap.org/data/2.5/weather?lat=55.42&lon=61.04&lang=ru&units=metric&appid=null";
        Mockito.doReturn(openWeatherApi)
                .when(restTemplate).getForObject(uri, OpenWeatherApi.class);
        MessageB createdMessageB = weatherAddingService.addWeatherDataToMessage(messageA);
        MessageB expectedMessageB = new MessageB("Hello", new Date(), 25);
        assertEquals(expectedMessageB, createdMessageB);
    }

    @Test
    void shouldIgnoreMessageWithEnLanguage() throws WeatherServiceNotAvailableException {
        MessageA messageA = new MessageA("Hello", Language.EN, new Coordinates("55.42","61.04"));
        MessageB createdMessageB = weatherAddingService.addWeatherDataToMessage(messageA);
        assertNull(createdMessageB);
    }

    @Test
    void shouldIgnoreEmptyMessage() throws WeatherServiceNotAvailableException {
        MessageA messageA = new MessageA("", Language.RU, new Coordinates("55.42","61.04"));
        MessageB createdMessageB = weatherAddingService.addWeatherDataToMessage(messageA);
        assertNull(createdMessageB);
    }

    @Test
    void shouldThrowExceptionWhenWeatherServiceIsUnavailable() {
        MessageA messageA = new MessageA("Hello", Language.RU, new Coordinates("55.42","61.04"));
        String uri = "http://api.openweathermap.org/data/2.5/weather?lat=55.42&lon=61.04&lang=ru&units=metric&appid=null";
        Mockito.doReturn(null)
                .when(restTemplate).getForObject(uri, OpenWeatherApi.class);

        assertThrows(WeatherServiceNotAvailableException.class, () -> weatherAddingService.addWeatherDataToMessage(messageA));
    }
}