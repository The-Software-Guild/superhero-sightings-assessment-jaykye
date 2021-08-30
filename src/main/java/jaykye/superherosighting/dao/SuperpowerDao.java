package jaykye.superherosighting.dao;

import jaykye.superherosighting.model.Hero;
import jaykye.superherosighting.model.Superpower;

import java.util.List;

public interface SuperpowerDao {
    Superpower getSuperpowerById(int id);
    List<Superpower> getAllSuperpowers();
    Superpower addSuperpower(Superpower superpower);
    void updateSuperpower(Superpower superpower);
    void deleteSuperpowerById(int id);
}
