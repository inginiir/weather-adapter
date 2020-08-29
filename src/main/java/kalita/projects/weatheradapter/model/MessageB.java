package kalita.projects.weatheradapter.model;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public class MessageB {

    private String text;
    private Date createdDate;
    private Integer currentTemperature;

    public MessageB() {
    }

    public MessageB(String text, Date createdDate, Integer currentTemperature) {
        this.text = text;
        this.createdDate = createdDate;
        this.currentTemperature = currentTemperature;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Integer getCurrentTemperature() {
        return currentTemperature;
    }

    public void setCurrentTemperature(Integer currentTemperature) {
        this.currentTemperature = currentTemperature;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MessageB messageB = (MessageB) o;
        return Objects.equals(text, messageB.text) &&
                Objects.equals(currentTemperature, messageB.currentTemperature);
    }

    @Override
    public int hashCode() {
        return Objects.hash(text, createdDate, currentTemperature);
    }

    @Override
    public String toString() {
        return "MessageB: {" +
                "\"txt\": \"" + text + '\"' +
                ", \"createdDt\": \"" + new SimpleDateFormat("yyyy-MM-dd'T'h:m:ss'Z'").format(createdDate) + '\"' +
                ", \"currentTemp\": \"" + currentTemperature + '\"' +
                '}';
    }
}
