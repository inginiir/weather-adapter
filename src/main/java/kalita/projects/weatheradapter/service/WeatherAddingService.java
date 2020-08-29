package kalita.projects.weatheradapter.service;

import kalita.projects.weatheradapter.model.Language;
import kalita.projects.weatheradapter.model.MessageA;
import kalita.projects.weatheradapter.model.MessageB;
import kalita.projects.weatheradapter.model.weather_api.OpenWeatherApi;
import kalita.projects.weatheradapter.model.weather_api.WeatherApi;
import kalita.projects.weatheradapter.service.exceptions.WeatherServiceNotAvailableException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Date;

@Service
public class WeatherAddingService {

    @Value("${openWeatherApiKey}")
    private String apiKey;

    private RestTemplate restTemplate;

    public WeatherAddingService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public MessageB addWeatherDataToMessage(MessageA messageA) throws WeatherServiceNotAvailableException {
        if (messageA != null && messageA.getLanguage().equals(Language.RU) && !messageA.getMessage().isEmpty()) {
            WeatherApi weatherApi = getWeatherApi(messageA);
            if (weatherApi != null) {
                weatherApi.fillParameters();
                MessageB messageB = new MessageB();
                messageB.setText(messageA.getMessage());
                messageB.setCreatedDate(new Date());
                messageB.setCurrentTemperature(weatherApi.getTemperature());
                return messageB;
            } else {
                throw new WeatherServiceNotAvailableException();
            }
        }
        return null;
    }

    private WeatherApi getWeatherApi(MessageA messageA) {
        String latitude = messageA.getCoordinates().getLatitude();
        String longitude = messageA.getCoordinates().getLongitude();
        if (!latitude.isEmpty() && !longitude.isEmpty()) {
            String uri = String.format("http://api.openweathermap.org/data/2.5/weather?" +
                            "lat=%s&" +
                            "lon=%s&" +
                            "lang=ru&" +
                            "units=metric&" +
                            "appid=%s",
                    latitude,
                    longitude,
                    apiKey);
            return restTemplate.getForObject(uri, OpenWeatherApi.class);
        } else {
            return null;
        }
    }
}
