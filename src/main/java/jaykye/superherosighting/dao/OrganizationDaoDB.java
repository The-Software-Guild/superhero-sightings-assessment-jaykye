package jaykye.superherosighting.dao;

import jaykye.superherosighting.model.Hero;
import jaykye.superherosighting.model.Organization;
import jaykye.superherosighting.model.Organization;
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

public class OrganizationDaoDB implements OrganizationDao {

    @Autowired
    JdbcTemplate jdbc;

    @Override
    public Organization getOrganizationById(int id) {
        try {
            final String GET_ORGANIZATION_BY_ID = "SELECT * FROM organization WHERE organizationId = ?";
            Organization organization = jdbc.queryForObject(GET_ORGANIZATION_BY_ID, new OrganizationMapper(), id);
            List<Hero> members = getMembersForOrganization(organization);
            organization.setMembers(members);
            return organization;
        } catch(DataAccessException ex) {
            return null;
        }
    }

    // ############################### Foreign key object 가져오는 함수들 ###############################

    private List<Hero> getMembersForOrganization(Organization organization) {
        final String GET_ALL_MEMBERS = "SELECT DISTINCT h.* from hero h join hero_organization ho on h.heroId = ho.heroId where ho.organizationId = ?";
        return jdbc.query(GET_ALL_MEMBERS, new HeroDaoDB.HeroMapper(), organization.getId());
    }

    // #################################################################################################


    @Override
    public List<Organization> getAllOrganizations() {
        final String GET_ALL_ORGANIZATIONS = "SELECT * FROM organization";
        return jdbc.query(GET_ALL_ORGANIZATIONS, new OrganizationMapper());
    }

    @Override
    public Organization addOrganization(Organization organization) {
        final String INSERT_ORGANIZATION = "INSERT INTO organization(organizationName, description, organizationAddress) " +
                "VALUES(?, ?, ?)";
        jdbc.update(INSERT_ORGANIZATION,
                organization.getName(),
                organization.getDescription(),
                organization.getAddress()
        );

        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        organization.setId(newId);
        return organization;
    }

    @Override
    public void updateOrganization(Organization organization) {
        final String UPDATE_ORGANIZATION = "UPDATE organization SET organizationName = ?" +
                "WHERE organizationId = ?";
        jdbc.update(UPDATE_ORGANIZATION,
                organization.getName(),
                organization.getId());
    }

    @Override
    public void deleteOrganizationById(int id) {
        final String DELETE_HERO_ORGANIZATION = "DELETE ho.* " +
                "from hero_organization ho " +
                "join hero h " +
                "on ho.heroId = h.heroId " +
                "join organization s " +
                "on ho.organizationId = s.organizationId " +
                "where s.organizationId = ?";

        jdbc.update(DELETE_HERO_ORGANIZATION, id);

//        final String DELETE_HERO = "DELETE from hero h join hero_organization ho on h.heroId = ho.heroId where ho.organizationId = ?";
//        jdbc.update(DELETE_HERO, id);

        final String DELETE_ORGANIZATION = "DELETE from organization where organizationId = ?";
        jdbc.update(DELETE_ORGANIZATION, id);
    }


    @Override
    public List<Organization> getOrganizationByHero(Hero hero) {
        return hero.getOrganizations();
    }

    public static final class OrganizationMapper implements RowMapper<Organization> {

        @Override
        public Organization mapRow(ResultSet rs, int index) throws SQLException {
            Organization organization = new Organization();
            organization.setId(rs.getInt("organizationId"));
            organization.setName(rs.getString("organizationName"));
            organization.setDescription(rs.getString("description"));
            organization.setAddress(rs.getString("organizationAddress"));
            return organization;
        }
    }
}
