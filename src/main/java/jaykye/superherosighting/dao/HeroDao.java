package jaykye.superherosighting.dao;

import jaykye.superherosighting.model.Hero;
import jaykye.superherosighting.model.Location;
import jaykye.superherosighting.model.Organization;

import java.util.List;

public interface HeroDao {
    Hero getHeroById(int id);
    List<Hero> getAllHeroes();
    Hero addHero(Hero hero);
    void updateHero(Hero hero);
    void deleteHeroById(int id);
    List<Hero> getMembersForOrganization(Organization organization);
    List<Hero> getAllHeroSightedAtLocation(Location location);
}
