package jaykye.superherosighting.dao;

import jaykye.superherosighting.model.Hero;
import jaykye.superherosighting.model.Location;

import java.util.List;

public interface LocationDao {
    Location getLocationById(int id);
    List<Location> getAllLocations();
    Location addLocation(Location location);
    void updateLocation(Location location);
    void deleteLocationById(int id);

    List<Location> getAllLocationsForHero(Hero hero);
}
