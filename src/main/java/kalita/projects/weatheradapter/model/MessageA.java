package kalita.projects.weatheradapter.model;

public class MessageA {

    private String message;
    private Language language;
    private Coordinates coordinates;

    public MessageA() {
    }

    public MessageA(String message, Language language, Coordinates coordinates) {
        this.message = message;
        this.language = language;
        this.coordinates = coordinates;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    @Override
    public String toString() {
        return "MessageA: {" +
                "\"msg\": \"" + message + '\"' +
                ", \"lng\": \"" + language + '\"' +
                ", \"coordinates\": " + coordinates +
                '}';
    }
}
