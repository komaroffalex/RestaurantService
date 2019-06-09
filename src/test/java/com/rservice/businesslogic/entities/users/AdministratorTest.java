package com.rservice.businesslogic.entities.users;

import com.rservice.businesslogic.entities.orders.Food;
import com.rservice.storage.DataGateway;
import com.rservice.storage.MappersRepository;
import com.rservice.storage.Repository;
import org.apache.commons.codec.digest.DigestUtils;
import org.junit.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Savepoint;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;

public class AdministratorTest {

    private static Repository repository = new MappersRepository();
    private Administrator administrator = new Administrator(-1, "test admin", "1", "admin");
    private static Connection connection;
    private Savepoint savepoint;
    private Food newFood;

    @BeforeClass
    public static void setUpClass() throws SQLException {
        connection = DataGateway.getInstance().getDataSource().getConnection();
        connection.setAutoCommit(false);
    }

    @AfterClass
    public static void tearDownClass() {

    }

    @Before
    public void setUp() throws Exception {
        savepoint = connection.setSavepoint();
    }

    @After
    public void tearDown() throws Exception {
        connection.rollback(savepoint);
    }

    @Test
    public void addNewFood() {
        String foodName = "soup";
        float foodCost = 22.10f;
        newFood = administrator.addNewFood(repository, foodName, foodCost);
        assertEquals(newFood.getName(), foodName);
        assertEquals(newFood.getCost(), foodCost, 0.1);
    }

    @Test
    public void addNewUser() {
        String login = "test user", password = "test password", name = "test name", role = "Client";
        User user = administrator.addNewUser(repository, login, password, name, role);
        assertEquals(user.getLogin(), login);
        assertEquals(user.getPassword(), DigestUtils.sha1Hex(password));
        assertEquals(user.getName(), name);
        assertEquals(user.getRole().getRoleName(), role);
    }
}