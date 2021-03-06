package ro.db.api.em;

import org.junit.Before;
import org.junit.Test;
import ro.db.appl.domain.Department;
import ro.db.appl.domain.Location;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * Developer: Viorelt
 * <p>
 **/

public class EntityManagerTest {

    private EntityManager<Location> entityManager;

    @Before
    public void setUp() {
        entityManager=new EntityManagerImpl<>(Location.class);
    }

    @Test
    public void testFindById() {
        Location location=entityManager.findById(1000L);
        assertEquals(Long.valueOf(1000),location.getId());
    }

    @Test
    public void testGetNextIdVal() {
        Long nextId=entityManager.getNextIdVal(EntityUtils.getTableName(Location.class),
                EntityUtils.getPkColumn(Location.class).getDbColumnName());
        assertTrue(nextId!=null&&nextId>0);
    }

    @Test
    public void testInsert() {
        Location location=new Location();
        location.setStateProvince("test_state_province");
        location.setCity("test_city");
        location.setPostalCode("tp_code");
        location.setStreetAddress("test_street_address");
        Location dbLocation=entityManager.insert(location);
        assertTrue(dbLocation.getId()!=null&&dbLocation.getId()>0);
    }


    @Test
    public void testFindAll() {
        List<Department> all= new EntityManagerImpl<>(Department.class).findAll();
        assertTrue(all.size()>0);
    }

    @Test
    public void testUpdate() {
        Location location=entityManager.findById(1000L);
        String streetAddress = location.getStreetAddress() + "_test";
        location.setStreetAddress(streetAddress);
        entityManager.update(location);
        Location locationDb=entityManager.findById(1000L);
        assertEquals(streetAddress,locationDb.getStreetAddress());

    }


    @Test
    public void testDelete() {
        Location location=entityManager.findById(1000L);
        entityManager.delete(location);

        Location locationDb=entityManager.findById(1000L);
        assertNull(locationDb);

        location.setId(null);
        locationDb=entityManager.insert(location);

        assertNotNull(locationDb.getId());

    }


    @Test
    public void testFindByParms() {
        Map<String,Object> param=new HashMap<>();
        param.put("location_id",1100L);
        param.put("postal_code","10934");
        List<Location> locations=entityManager.findByParams(param);
        assertTrue(locations.size()==1);
    }
}