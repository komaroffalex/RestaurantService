package com.rservice.businesslogic.entities.users;

import com.rservice.Util;
import com.rservice.businesslogic.Status;
import com.rservice.businesslogic.entities.orders.Food;
import com.rservice.businesslogic.entities.orders.Order;
import com.rservice.businesslogic.exceptions.RServiceAppException;
import com.rservice.storage.DataGateway;
import com.rservice.storage.MappersRepository;
import com.rservice.storage.Repository;
import org.junit.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

public class WorkerTest {

    private static Repository repository = new MappersRepository();
    private Client client = new Client(2, "test client", "1", "Bill Gates");
    private Worker worker = new Worker(3, "test worker", "1", "Joe Devil", 0);
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
    public void finishOrder() {
        Food foodToOrder = repository.findFoodById(2);
        List<Food> orderFood = new ArrayList<Food>();
        orderFood.add(foodToOrder);
        Date delTime = Util.getDateFromFormattedString("2018-07-20 15:38:00");
        String address = "Toronto";
        Order newOrder = client.placeOrder(repository, delTime, orderFood, address);
        try {
            Order confirmedOrder = worker.finishOrder(repository, newOrder);
            assertEquals(confirmedOrder.getTime(), delTime);
            assertEquals(confirmedOrder.getAddress(), address);
            assertEquals(confirmedOrder.getFood(),orderFood);
            assertEquals(confirmedOrder.getStatus(), Status.READY);
        }
        catch (RServiceAppException e){
            e.printStackTrace();
        }
    }

    @Test
    public void denyOrder() {
        Food foodToOrder = repository.findFoodById(2);
        List<Food> orderFood = new ArrayList<Food>();
        orderFood.add(foodToOrder);
        Date delTime = Util.getDateFromFormattedString("2018-07-20 15:38:00");
        String address = "Toronto";
        Order newOrder = client.placeOrder(repository, delTime, orderFood, address);
        try {
            Order confirmedOrder = worker.declineOrder(repository, newOrder);
            assertEquals(confirmedOrder.getTime(), delTime);
            assertEquals(confirmedOrder.getAddress(), address);
            assertEquals(confirmedOrder.getFood(),orderFood);
            assertEquals(confirmedOrder.getStatus(), Status.DENIED);
        }
        catch (RServiceAppException e){
            e.printStackTrace();
        }
    }

}