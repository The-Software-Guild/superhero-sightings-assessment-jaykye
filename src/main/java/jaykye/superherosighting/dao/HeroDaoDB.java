package jaykye.superherosighting.dao;

import jaykye.superherosighting.model.Hero;
import jaykye.superherosighting.model.Location;
import jaykye.superherosighting.model.Organization;
import jaykye.superherosighting.model.Superpower;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class HeroDaoDB implements HeroDao {
    @Autowired
    JdbcTemplate jdbc;

    /**
     * 기본적으로 모든 데이터는 object로 다룬다.
     * Foreign key 에 해당하는 object는 따로 함수를 만들어서 obj 형태로 가져온다.
     */
    @Override
    public Hero getHeroById(int id) {
        try {
            final String GET_HERO_BY_ID = "SELECT * FROM hero WHERE heroId = ?";
            Hero hero = jdbc.queryForObject(GET_HERO_BY_ID, new HeroMapper(), id);
            hero.setSuperpower(getSuperpowerForHero(id));
            hero.setOrganizations(getOrganizationsForHero(id));
            return hero;
        } catch(DataAccessException ex) {
            return null;
        }
    }

    // ############################### Foreign key object 가져오는 함수들 ###############################
    private Superpower getSuperpowerForHero(int heroId){
        final String SELECT_SUPERPOWER_FOR_HERO = "SELECT s.* from hero h join superpower s " +
                "ON h.superpowerId = s.superpowerId " +
                "WHERE h.heroId = ?";
        return jdbc.queryForObject(SELECT_SUPERPOWER_FOR_HERO, new SuperpowerDaoDB.SuperpowerMapper(), heroId);
    }

    private List<Organization> getOrganizationsForHero(int heroId){
        final String SELECT_ORGANIZATION_FOR_HERO = "SELECT o.* from hero h " +
                "join hero_organization ho " +
                "on h.heroId = ho.heroId " +
                "join organization o " +
                "on ho.organizationId = o.organizationId " +
                "where h.heroId = ?";
        return jdbc.query(SELECT_ORGANIZATION_FOR_HERO, new OrganizationDaoDB.OrganizationMapper(), heroId);
    }
    // #################################################################################################

    /**
     * 딱히 특별한 것은 없음. 대신에 모든 course object 를 가져온 뒤에 iterate 하면서 foreign key obj 가져오는 함수를
     * 적용 하는 작업을 따로 독립 함수로 만들어서 사용. -- 내생각엔 좀 불필요한 듯.
     */
    @Override
    public List<Hero> getAllHeroes() {
        final String GET_ALL_HEROES = "SELECT * FROM hero";
        List<Hero> heroes = jdbc.query(GET_ALL_HEROES, new HeroMapper());
        for (Hero hero : heroes){
            hero.setSuperpower(getSuperpowerForHero(hero.getId()));
            hero.setOrganizations(getOrganizationsForHero(hero.getId()));
        }
        return heroes;
    }

    /**
     * Hero가 Primary key 인 hero 부터 추가 한 후, hero_organization relationship table에도 여기서 추가한다.
     * -- let course manage the relationship.
     * student 에서는 이 작업 안하나? -- 안한다.
     * object 형태로 있는 foreign object 는 id로 변환해서 넣어준다.
     * @param hero
     * @return
     */
    @Override
    @Transactional
    public Hero addHero(Hero hero) {
        final String INSERT_HERO = "INSERT INTO hero(heroName, description, superpowerId) " +
                "VALUES(?,?,?)";
        jdbc.update(INSERT_HERO,
                hero.getName(),
                hero.getDescription(),
                hero.getSuperpower().getId()
        );

        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        hero.setId(newId);
        // 여기서 bridge table 처리한다.
        insertHeroOrganization(hero);
        return hero;
    }

    // Handle Many to many bridge table.
    private void insertHeroOrganization(Hero hero){
        final String INSERT_HERO_ORGANIZATION = "INSERT into hero_organization(heroId, organizationId) VALUES(?, ?)";
        for (Organization organization : hero.getOrganizations()) {
            jdbc.update(INSERT_HERO_ORGANIZATION, hero.getId(), organization.getId());
        }
    }

    /**
     * Update도 add와 마찬가지로 bridge 테이블도 여기서 manage 한다.
     * Hero 데이터의 경우, id를 유지하고 싶으므로 UPDATE 키워드를 사용하지만, bridge 테이블은 autoincrement id 를 사용하지 않아서
     * 그냥 지워버리고 새로 add해도 무방하다.
     * @param hero
     */
    @Override
    public void updateHero(Hero hero) {
        final String UPDATE_HERO = "UPDATE hero SET heroName = ?, description = ?, " +
                "superpowerId = ? WHERE heroId = ?";
        jdbc.update(UPDATE_HERO,
                hero.getName(),
                hero.getDescription(),
                hero.getSuperpower().getId(),
                hero.getId());
        final String DELETE_HERO_ORGANIZATION = "DELETE FROM hero_organization where heroId = ?";
        jdbc.update(DELETE_HERO_ORGANIZATION, hero.getId());
        insertHeroOrganization(hero);
    }

    /**
     * 삭제는 add의 역순이다. Foreign key부터 없앤다. Add, update와 차이점은, 여기서 delete할 때 bridge table에 끼치는 영향은
     * organization에서 지우는 것과는 다른 영향이다. 그래서 각각에서 모두 bridge table entry를 delete 한다.
     * @param id
     */
    @Override
    @Transactional
    public void deleteHeroById(int id) {

        final String DELETE_HERO_ORGANIZATION = "DELETE FROM hero_organization "
                + "WHERE heroId = ?";
        jdbc.update(DELETE_HERO_ORGANIZATION, id);

        final String DELETE_SIGHTING = "DELETE FROM sighting "
                + "WHERE heroId = ?";
        jdbc.update(DELETE_SIGHTING, id);

        final String DELETE_HERO = "DELETE FROM hero WHERE heroId = ?";
        jdbc.update(DELETE_HERO, id);
    }

    @Override
    public List<Hero> getAllHeroSightedAtLocation(Location location) {
        final String GET_ALL_HEROES_SIGHTED_AT_LOCATION = "SELECT DISTINCT h.* " +
                "from hero h " +
                "join sighting st " +
                "on h.heroId = st.heroId " +
                "where st.locationId = ?";
        List<Hero> heroes = jdbc.query(GET_ALL_HEROES_SIGHTED_AT_LOCATION, new HeroMapper(), location.getId());
        for (Hero hero : heroes){
            hero.setSuperpower(getSuperpowerForHero(hero.getId()));
            hero.setOrganizations(getOrganizationsForHero(hero.getId()));
        }
        return heroes;
    }

    @Override
    public List<Hero> getMembersForOrganization(Organization organization) {
        final String GET_ALL_MEMBERS = "SELECT DISTINCT h.* from hero h join hero_organization ho on h.heroId = ho.heroId where ho.organizationId = ?";
        return jdbc.query(GET_ALL_MEMBERS, new HeroMapper(), organization.getId());
    }


    public static final class HeroMapper implements RowMapper<Hero> {
        /**
         * Do not map foreign keys here.
         * @param rs
         * @param index
         * @return
         * @throws SQLException
         */
        @Override
        public Hero mapRow(ResultSet rs, int index) throws SQLException {
            Hero hero = new Hero();
            hero.setId(rs.getInt("heroId"));
            hero.setName(rs.getString("heroName"));
            hero.setDescription(rs.getString("description"));
            return hero;
        }
    }




}
