package eu.aimless.f1predictor.repository.model;

public class Circuit {

    private String circuitId;
    private String circuitName;
    private String url;
    private Location Location;

    public Circuit(String circuitId, String circuitName, String url, Location Location) {
        this.circuitId = circuitId;
        this.circuitName = circuitName;
        this.url = url;
        this.Location = Location;
    }

    public String getCircuitId() {
        return circuitId;
    }

    public void setCircuitId(String circuitId) {
        this.circuitId = circuitId;
    }

    public String getCircuitName() {
        return circuitName;
    }

    public void setCircuitName(String circuitName) {
        this.circuitName = circuitName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public eu.aimless.f1predictor.repository.model.Location getLocation() {
        return Location;
    }

    public void setLocation(eu.aimless.f1predictor.repository.model.Location location) {
        Location = location;
    }
}
