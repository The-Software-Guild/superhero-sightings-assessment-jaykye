package jaykye.superherosighting.dao;

import jaykye.superherosighting.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SightingDaoDBTest {
    
    @Autowired
    HeroDao heroDao;

    @Autowired
    SuperpowerDao superpowerDao;

    @Autowired
    OrganizationDao organizationDao;

    @Autowired
    LocationDao locationDao;

    @Autowired
    SightingDao sightingDao;

    @BeforeEach
    public void setUp() {
        List<Superpower> superpowers = superpowerDao.getAllSuperpowers();
        for(Superpower superpower : superpowers) {
            superpowerDao.deleteSuperpowerById(superpower.getId());
        }

        List<Hero> heroes = heroDao.getAllHeroes();
        for(Hero hero : heroes) {
            heroDao.deleteHeroById(hero.getId());
        }

        List<Organization> organizations = organizationDao.getAllOrganizations();
        for(Organization organization : organizations) {
            organizationDao.deleteOrganizationById(organization.getId());
        }

        List<Sighting> sightings = sightingDao.getAllSightings();
        for(Sighting sighting : sightings) {
            sightingDao.deleteSightingById(sighting.getId());
        }

        List<Location> locations = locationDao.getAllLocations();
        for(Location location : locations) {
            locationDao.deleteLocationById(location.getId());
        }
    }



    @Test
    public void testAddAndGetSightingById() {
        Superpower superpower = new Superpower();
        superpower.setName("Admin");
        superpower = superpowerDao.addSuperpower(superpower);

        Organization organization = new Organization();
        organization.setName("Jay company");
        organization.setDescription("This is Jay's company");
        organization.setAddress("Home");
        organization = organizationDao.addOrganization(organization);
        List<Organization> organizations = new ArrayList<Organization>();
        organizations.add(organization);

        Hero hero = new Hero();
        hero.setName("Jay");
        hero.setDescription("This is me.");
        hero.setSuperpower(superpower);
        hero.setOrganizations(organizations);
        hero = heroDao.addHero(hero);

        Location location = new Location();
        location.setName("Montreal");
        location.setDescription("This is my city");
        location.setAddress("Montreal, Quebec, Canada");
        location.setLatitude(11.11);
        location.setLongitude(-11.11);
        locationDao.addLocation(location);

        Sighting sighting = new Sighting();
        sighting.setDate(LocalDate.now());
        sighting.setLocation(location);
        sighting.setHero(hero);
        sightingDao.addSighting(sighting);

        Sighting fromDao = sightingDao.getSightingById(sighting.getId());

        assertEquals(fromDao, sighting);
    }

    @Test
    public void testGetAllSightings() {
        Superpower superpower = new Superpower();
        superpower.setName("Admin");
        superpower = superpowerDao.addSuperpower(superpower);

        Organization organization = new Organization();
        organization.setName("Jay company");
        organization.setDescription("This is Jay's company");
        organization.setAddress("Home");
        organization = organizationDao.addOrganization(organization);
        List<Organization> organizations = new ArrayList<Organization>();
        organizations.add(organization);

        Hero hero = new Hero();
        hero.setName("Jay");
        hero.setDescription("This is me.");
        hero.setSuperpower(superpower);
        hero.setOrganizations(organizations);
        hero = heroDao.addHero(hero);

        Location location = new Location();
        location.setName("Montreal");
        location.setDescription("This is my city");
        location.setAddress("Montreal, Quebec, Canada");
        location.setLatitude(11.11);
        location.setLongitude(-11.11);
        locationDao.addLocation(location);

        Sighting sighting = new Sighting();
        sighting.setDate(LocalDate.now());
        sighting.setLocation(location);
        sighting.setHero(hero);
        sightingDao.addSighting(sighting);

        Superpower superpower1 = new Superpower();
        superpower1.setName("Admin");
        superpower1 = superpowerDao.addSuperpower(superpower1);

        Organization organization1 = new Organization();
        organization1.setName("Jay company");
        organization1.setDescription("This is Jay's company");
        organization1.setAddress("Home");
        organization1 = organizationDao.addOrganization(organization1);
        List<Organization> organizations1 = new ArrayList<Organization>();
        organizations1.add(organization1);

        Hero hero1 = new Hero();
        hero1.setName("Jay");
        hero1.setDescription("This is me.");
        hero1.setSuperpower(superpower1);
        hero1.setOrganizations(organizations1);
        hero1 = heroDao.addHero(hero1);

        Location location1 = new Location();
        location1.setName("Montreal");
        location1.setDescription("This is my city");
        location1.setAddress("There, Quebec, Canada");
        location1.setLatitude(11.11);
        location1.setLongitude(-11.11);
        locationDao.addLocation(location1);

        Sighting sighting1 = new Sighting();
        sighting1.setDate(LocalDate.now());
        sighting1.setLocation(location1);
        sighting1.setHero(hero1);
        sightingDao.addSighting(sighting1);

        List<Sighting> sightings = sightingDao.getAllSightings();

        assertEquals(2, sightings.size());
        assertTrue(sightings.contains(sighting));
        assertTrue(sightings.contains(sighting1));
    }

    @Test
    public void testUpdateSighting() {
        Superpower superpower = new Superpower();
        superpower.setName("Admin");
        superpower = superpowerDao.addSuperpower(superpower);

        Organization organization = new Organization();
        organization.setName("Jay company");
        organization.setDescription("This is Jay's company");
        organization.setAddress("Home");
        organization = organizationDao.addOrganization(organization);
        List<Organization> organizations = new ArrayList<Organization>();
        organizations.add(organization);

        Hero hero = new Hero();
        hero.setName("Jay");
        hero.setDescription("This is me.");
        hero.setSuperpower(superpower);
        hero.setOrganizations(organizations);
        hero = heroDao.addHero(hero);

        Location location = new Location();
        location.setName("Montreal");
        location.setDescription("This is my city");
        location.setAddress("Montreal, Quebec, Canada");
        location.setLatitude(11.11);
        location.setLongitude(-11.11);
        locationDao.addLocation(location);

        Sighting sighting = new Sighting();
        sighting.setDate(LocalDate.now());
        sighting.setLocation(location);
        sighting.setHero(hero);
        sightingDao.addSighting(sighting);

        Sighting fromDao = sightingDao.getSightingById(sighting.getId());
        assertEquals(sighting, fromDao);

        sighting.setDate(LocalDate.of(2020,1,1));
        sightingDao.updateSighting(sighting);
        assertNotEquals(fromDao, sighting);

        fromDao = sightingDao.getSightingById(sighting.getId());
        assertEquals(sighting, fromDao);
    }

    @Test
    public void testDeleteSightingById() {
        Superpower superpower = new Superpower();
        superpower.setName("Admin");
        superpower = superpowerDao.addSuperpower(superpower);

        Organization organization = new Organization();
        organization.setName("Jay company");
        organization.setDescription("This is Jay's company");
        organization.setAddress("Home");
        organization = organizationDao.addOrganization(organization);
        List<Organization> organizations = new ArrayList<Organization>();
        organizations.add(organization);

        Hero hero = new Hero();
        hero.setName("Jay");
        hero.setDescription("This is me.");
        hero.setSuperpower(superpower);
        hero.setOrganizations(organizations);
        hero = heroDao.addHero(hero);

        Location location = new Location();
        location.setName("Montreal");
        location.setDescription("This is my city");
        location.setAddress("Montreal, Quebec, Canada");
        location.setLatitude(11.11);
        location.setLongitude(-11.11);
        locationDao.addLocation(location);

        Sighting sighting = new Sighting();
        sighting.setDate(LocalDate.now());
        sighting.setLocation(location);
        sighting.setHero(hero);
        sightingDao.addSighting(sighting);

        Sighting fromDao = sightingDao.getSightingById(sighting.getId());
        assertEquals(sighting, fromDao);

        sightingDao.deleteSightingById(sighting.getId());

        fromDao = sightingDao.getSightingById(sighting.getId());
        assertNull(fromDao);
    }
}