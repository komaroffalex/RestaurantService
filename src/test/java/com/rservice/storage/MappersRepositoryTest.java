package com.rservice.storage;

import org.junit.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Savepoint;

import static org.junit.Assert.*;

public class MappersRepositoryTest {


    private MappersRepository repository = new MappersRepository();
    private static Connection connection;
    private Savepoint savepoint;

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
    public void logInUser() {
        assertNotNull(repository.logInUser("admin", "1111"));
    }

    @Test
    public void findUserById() {
        assertNotNull(repository.findUserById(1));
        assertNotNull(repository.findUserById(2));
    }

    @Test
    public void findTableById() {
        assertNotNull(repository.findTableById(1));
    }

    @Test
    public void getAllFood() {
        assertTrue(repository.getAllFood().size() > 0);
    }

    @Test
    public void getAllOrders() {
        assertTrue(repository.getAllOrders().size() > 0);
    }

    @Test
    public void getAllReservations() {
        assertTrue(repository.getAllReservations().size() > 0);
    }

    @Test
    public void getAllTables() {
        assertTrue(repository.getAllTables().size() > 0);
    }

    @Test
    public void getAllUsers() {
        assertTrue(repository.getAllUsers().size() > 0);
    }
}