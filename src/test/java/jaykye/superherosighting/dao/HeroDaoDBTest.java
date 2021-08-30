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
class HeroDaoDBTest {

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
    }

    @Test
    public void testAddAndGetHeroById(){
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


        Hero fromDao = heroDao.getHeroById(hero.getId());

        assertEquals(fromDao, hero);
    }

    @Test
    public void testGetAllHeroes(){
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

        Superpower superpower2 = new Superpower();
        superpower2.setName("Admin");
        superpower2 = superpowerDao.addSuperpower(superpower2);

        Organization organization2 = new Organization();
        organization2.setName("Jay company");
        organization2.setDescription("This is Jay's company");
        organization2.setAddress("Home");
        organization2 = organizationDao.addOrganization(organization2);
        List<Organization> organizations2 = new ArrayList<Organization>();
        organizations2.add(organization2);

        Hero hero2 = new Hero();
        hero2.setName("Jay");
        hero2.setDescription("This is me.");
        hero2.setSuperpower(superpower2);
        hero2.setOrganizations(organizations2);
        hero2 = heroDao.addHero(hero2);

        List<Hero> heroes = heroDao.getAllHeroes();
        assertEquals(2, heroes.size());

        assertTrue(heroes.contains(hero));
        assertTrue(heroes.contains(hero2));
    }

    @Test
    public void testUpdateHero(){
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

        Hero fromDao = heroDao.getHeroById(hero.getId());
        assertEquals(hero, fromDao);

        // New DTO -- only modify name and Organization

        hero.setName("New name");
        Organization organization2 = new Organization();
        organization2.setName("Jay company 2");
        organization2.setDescription("This is Jay's company 2");
        organization2.setAddress("Home 2");
        organization2 = organizationDao.addOrganization(organization2);

        organizations.add(organization2);
        hero.setOrganizations(organizations);

        assertNotEquals(hero, fromDao);

        heroDao.updateHero(hero);

        fromDao = heroDao.getHeroById(hero.getId());
        assertEquals(hero, fromDao);
    }

    @Test
    public void testDeleteHeroById() {
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

        heroDao.deleteHeroById(hero.getId());
        Hero fromDao = heroDao.getHeroById(hero.getId());

        assertNull(fromDao);
    }

    @Test
    public void testGetAllHeroSightedAtLocation() {
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

        List<Hero> heroesSightedAtLocation = heroDao.getAllHeroSightedAtLocation(location);
        assertTrue(heroesSightedAtLocation.contains(hero));
    }
}