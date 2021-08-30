package jaykye.superherosighting.dao;

import jaykye.superherosighting.model.Sighting;

import java.time.LocalDate;
import java.util.List;

public interface SightingDao {
    Sighting getSightingById(int id);
    List<Sighting> getAllSightings();
    Sighting addSighting(Sighting sighting);
    void updateSighting(Sighting sighting);
    void deleteSightingById(int id);

    List<Sighting> getSightingsByDate(LocalDate date);
    List<Sighting> getLast10Sightings();
}
