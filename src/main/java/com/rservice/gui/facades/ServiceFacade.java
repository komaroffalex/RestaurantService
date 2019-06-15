package com.rservice.gui.facades;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.rservice.Main;
import com.rservice.businesslogic.entities.orders.Order;
import com.rservice.businesslogic.entities.users.Administrator;
import com.rservice.businesslogic.exceptions.NotFoundException;
import com.rservice.businesslogic.exceptions.RServiceAppException;
import com.rservice.service.geolocation.GeoLocationService;
import com.rservice.storage.MappersRepository;

import java.util.List;

public class ServiceFacade {

    private int loggedInUserId;
    private MappersRepository repository;
    private Facade facade = Main.FACADE;

    public ServiceFacade() {
        this.repository = new MappersRepository();
    }

    // Http Server
    public String getAllOrdersAsJson() throws NotFoundException {
        List<Order> books = repository.getAllOrders();
        if (books == null) throw new NotFoundException("All orders not found.");
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(books);
    }

    public String searchGoogleMapDistance(String destination) {
        return new GeoLocationService().getDistanceTo(destination);
    }

    public void setUserId (int userId) {
        loggedInUserId = userId;
    }
}
