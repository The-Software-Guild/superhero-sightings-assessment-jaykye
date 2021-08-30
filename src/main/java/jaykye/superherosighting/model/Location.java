package jaykye.superherosighting.model;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Objects;

public class Location {
    private int id;
    @NotBlank(message = "Location name must not be empty.")
    @Size(max = 45, message = "Location name must be less than 45 characters.")
    private String name;

    @NotBlank(message = "Description must not be empty.")
    @Size(max = 255, message = "Description must be less than 255 characters.")
    private String description;

    @NotBlank(message = "Address must not be empty.")
    @Size(max = 45, message = "Address must be less than 45 characters")
    private String address;

    private double latitude;
    private double longitude;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Location location = (Location) o;
        return id == location.id && Double.compare(location.latitude, latitude) == 0 && Double.compare(location.longitude, longitude) == 0 && Objects.equals(name, location.name) && Objects.equals(description, location.description) && Objects.equals(address, location.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, address, latitude, longitude);
    }
}
