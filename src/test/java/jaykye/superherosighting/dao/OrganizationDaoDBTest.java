package jaykye.superherosighting.dao;

import jaykye.superherosighting.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.internal.matchers.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class OrganizationDaoDBTest {
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
    public void testAddAndGetOrganizationById() {
        Organization organization = new Organization();
        organization.setName("Jay company");
        organization.setDescription("This is Jay's company");
        organization.setAddress("Home");
        organization = organizationDao.addOrganization(organization);

        Organization fromDao = organizationDao.getOrganizationById(organization.getId());

        assertEquals(fromDao, organization);
    }

    @Test
    public void testGetAllOrganizations() {
        Organization organization = new Organization();
        organization.setName("Jay company");
        organization.setDescription("This is Jay's company");
        organization.setAddress("Home");
        organization = organizationDao.addOrganization(organization);

        Organization organization2 = new Organization();
        organization2.setName("Jay company");
        organization2.setDescription("This is Jay's company");
        organization2.setAddress("Home");
        organization2 = organizationDao.addOrganization(organization2);

        List<Organization> organizations = organizationDao.getAllOrganizations();

        assertEquals(2, organizations.size());
        assertTrue(organizations.contains(organization));
        assertTrue(organizations.contains(organization2));
    }

    @Test
    public void testUpdateOrganization() {
        Organization organization = new Organization();
        organization.setName("Jay company");
        organization.setDescription("This is Jay's company");
        organization.setAddress("Home");
        organization = organizationDao.addOrganization(organization);

        Organization fromDao = organizationDao.getOrganizationById(organization.getId());
        assertEquals(organization, fromDao);

        organization.setName("Someone's company");
        organizationDao.updateOrganization(organization);
        assertNotEquals(fromDao, organization);

        fromDao = organizationDao.getOrganizationById(organization.getId());
        assertEquals(organization, fromDao);
    }
    
    @Test
    public void testDeleteOrganizationById() {
        Organization organization = new Organization();
        organization.setName("Admin");
        organization = organizationDao.addOrganization(organization);

        Organization fromDao = organizationDao.getOrganizationById(organization.getId());
        assertEquals(organization, fromDao);

        organizationDao.deleteOrganizationById(organization.getId());

        fromDao = organizationDao.getOrganizationById(organization.getId());
        assertNull(fromDao);
    }

}