package jaykye.superherosighting.model;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Objects;

public class Hero {
    private int id;  // This is auto generated from server.
    @NotBlank(message = "Name must not be empty.")
    @Size(max = 45, message="Name must be less than 45 characters.")
    private String name;
    @Size(max = 255, message="Description must be less than 255 characters.")
    private String description;
    private Superpower superpower;  // will attach a selector. - don't need to validate.
    private List<Organization> organizations;


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

    public List<Organization> getOrganizations() {
        return organizations;
    }


    public void setDescription(String description) {
        this.description = description;
    }

    public Superpower getSuperpower() {
        return superpower;
    }

    public void setSuperpower(Superpower superpower) {
        this.superpower = superpower;
    }

    public void setOrganizations(List<Organization> organizations) {
        this.organizations = organizations;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Hero hero = (Hero) o;
        return id == hero.id && Objects.equals(name, hero.name) && Objects.equals(description, hero.description) && Objects.equals(superpower, hero.superpower) && Objects.equals(organizations, hero.organizations);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, superpower, organizations);
    }
}
