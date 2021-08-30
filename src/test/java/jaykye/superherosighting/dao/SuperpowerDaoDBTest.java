package jaykye.superherosighting.dao;

import jaykye.superherosighting.model.Hero;
import jaykye.superherosighting.model.Superpower;
import net.bytebuddy.implementation.bind.annotation.Super;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest

class SuperpowerDaoDBTest {

    @Autowired
    HeroDao heroDao;

    @Autowired
    SuperpowerDao superpowerDao;

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

    }

    @Test
    public void testAddAndGetById(){
        Superpower superpower = new Superpower();
        superpower.setName("Admin");
        superpower = superpowerDao.addSuperpower(superpower);

        Superpower fromDao = superpowerDao.getSuperpowerById(superpower.getId());
        assertEquals(fromDao, superpower);
    }

    @Test
    public void testGetAllSuperpower(){
        Superpower superpower1 = new Superpower();
        superpower1.setName("Admin");
        superpower1 = superpowerDao.addSuperpower(superpower1);

        Superpower superpower2 = new Superpower();
        superpower2.setName("User");
        superpower2 = superpowerDao.addSuperpower(superpower2);

        List<Superpower> superpowers = superpowerDao.getAllSuperpowers();

        assertEquals(2, superpowers.size());
        assertTrue(superpowers.contains(superpower1));
        assertTrue(superpowers.contains(superpower2));
    }


    @Test
    public void testUpdateSuperpower() {
        Superpower superpower = new Superpower();
        superpower.setName("Admin");
        superpower = superpowerDao.addSuperpower(superpower);

        Superpower fromDao = superpowerDao.getSuperpowerById(superpower.getId());
        assertEquals(superpower, fromDao);

        superpower.setName("User");
        superpowerDao.updateSuperpower(superpower);

        assertNotEquals(superpower, fromDao);

        fromDao = superpowerDao.getSuperpowerById(superpower.getId());

        assertEquals(superpower, fromDao);
    }

    @Test
    public void testDeleteSuperpowerById() {
        Superpower superpower = new Superpower();
        superpower.setName("Admin");
        superpower = superpowerDao.addSuperpower(superpower);

        Superpower fromDao = superpowerDao.getSuperpowerById(superpower.getId());
        assertEquals(superpower, fromDao);

        superpowerDao.deleteSuperpowerById(superpower.getId());

        fromDao = superpowerDao.getSuperpowerById(superpower.getId());
        assertNull(fromDao);
    }
}