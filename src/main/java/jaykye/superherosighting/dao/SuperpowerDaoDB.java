package jaykye.superherosighting.dao;

import jaykye.superherosighting.model.Hero;
import jaykye.superherosighting.model.Superpower;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
@Repository

public class SuperpowerDaoDB implements SuperpowerDao {

    @Autowired
    JdbcTemplate jdbc;
    
    @Override
    public Superpower getSuperpowerById(int id) {
        try {
            final String GET_SUPERPOWER_BY_ID = "SELECT * FROM superpower WHERE superpowerId = ?";
            return jdbc.queryForObject(GET_SUPERPOWER_BY_ID, new SuperpowerMapper(), id);
        } catch(DataAccessException ex) {
            return null;
        }    
    }

    @Override
    public List<Superpower> getAllSuperpowers() {
        final String GET_ALL_SUPERPOWERS = "SELECT * FROM superpower";
        return jdbc.query(GET_ALL_SUPERPOWERS, new SuperpowerMapper());
    }

    @Override
    public Superpower addSuperpower(Superpower superpower) {
        final String INSERT_SUPERPOWER = "INSERT INTO superpower(superpowerName) " +
                "VALUES(?)";
        jdbc.update(INSERT_SUPERPOWER,
                superpower.getName()
        );

        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        superpower.setId(newId);
        return superpower;  
    }

    @Override
    public void updateSuperpower(Superpower superpower) {
        final String UPDATE_SUPERPOWER = "UPDATE superpower SET superpowerName = ?" +
                "WHERE superpowerId = ?";
        jdbc.update(UPDATE_SUPERPOWER,
                superpower.getName(),
                superpower.getId());
    }

    @Override
    public void deleteSuperpowerById(int id) {
        final String DELETE_HERO_ORGANIZATION = "DELETE ho.* from hero_organization ho join hero h " +
                "on ho.heroId = h.heroId " +
                "join superpower s " +
                "on h.superpowerId = s.superpowerId " +
                "where s.superpowerId = ?";

        jdbc.update(DELETE_HERO_ORGANIZATION, id);

        final String DELETE_SIGHTING = "delete st.* from sighting st " +
                "join hero h " +
                "on st.heroId = h.heroId " +
                "where h.superpowerId = ?";
        jdbc.update(DELETE_SIGHTING, id);

        final String DELETE_HERO = "DELETE from hero where superpowerId = ?";
        jdbc.update(DELETE_HERO, id);

        final String DELETE_SUPERPOWER = "DELETE from superpower where superpowerId = ?";
        jdbc.update(DELETE_SUPERPOWER, id);
    }

    public static final class SuperpowerMapper implements RowMapper<Superpower> {

        @Override
        public Superpower mapRow(ResultSet rs, int index) throws SQLException {
            Superpower superpower = new Superpower();
            superpower.setId(rs.getInt("superpowerId"));
            superpower.setName(rs.getString("superpowerName"));
            return superpower;
        }
    }
}
