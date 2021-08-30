package jaykye.superherosighting.model;

import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.Objects;

public class Sighting {
    private int id;
    @NotBlank(message = "Date must not be blank")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;
    Hero hero;
    Location location;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Hero getHero() {
        return hero;
    }

    public void setHero(Hero hero) {
        this.hero = hero;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Sighting sighting = (Sighting) o;
        return id == sighting.id && Objects.equals(date, sighting.date) && Objects.equals(hero, sighting.hero) && Objects.equals(location, sighting.location);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, date, hero, location);
    }
}
