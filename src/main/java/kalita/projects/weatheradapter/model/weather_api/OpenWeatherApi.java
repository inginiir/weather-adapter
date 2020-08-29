package kalita.projects.weatheradapter.model.weather_api;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.stereotype.Component;

@Component
@JsonIgnoreProperties(ignoreUnknown = true)
public class OpenWeatherApi implements WeatherApi {

    @JsonAlias("main")
    private WeatherParametersForOpenWeather mainWeatherParameters;
    private Integer currentTemperature;

    public WeatherParametersForOpenWeather getMainWeatherParameters() {
        return mainWeatherParameters;
    }

    public void setMainWeatherParameters(WeatherParametersForOpenWeather mainWeatherParameters) {
        this.mainWeatherParameters = mainWeatherParameters;
    }

    @Override
    public Integer getTemperature() {
        return currentTemperature;
    }

    public void setCurrentTemperature(Integer currentTemperature) {
        this.currentTemperature = currentTemperature;
    }

    public void fillParameters() {
        if (mainWeatherParameters != null) {
            this.setCurrentTemperature(mainWeatherParameters.getCurrentTemperature().intValue());
        }
    }

}
