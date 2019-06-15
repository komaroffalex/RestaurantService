package com.rservice.service.geolocation;

import com.google.maps.*;
import com.google.maps.DistanceMatrixApi;
import com.google.maps.DistanceMatrixApiRequest;
import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.errors.ApiException;
import com.google.maps.model.DistanceMatrix;
import com.google.maps.model.GeocodingResult;
import com.google.maps.model.LatLng;
import com.google.maps.model.TravelMode;
import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;

import java.io.IOException;

public class GeoLocationService {

    private static final String restaurantOrigin = "Chicago";
    private static final String APPLICATION_NAME = "rservice";
    private static final String API_KEY = "AIzaSyB71ESw0km7xM_6UkJGWzBMNBBO4FzC6XI";    // get your own key on Google API Console

    public GeoLocationService() {
    }

    public String getDistanceTo(String destination) {
        try {
            String distance = getDriveDist(restaurantOrigin, destination);
            return distance;
        }
        catch (ApiException | InterruptedException | IOException e) {
            return (e.toString());
        }
    }

    //given two addresses, calculates the driving distance
    public static String getDriveDist(String addrOne, String addrTwo) throws ApiException, InterruptedException, IOException {

        //set up key
        GeoApiContext distCalcer = new GeoApiContext.Builder()
                .apiKey(API_KEY)
                .build();

        DistanceMatrixApiRequest req = DistanceMatrixApi.newRequest(distCalcer);
        DistanceMatrix result = req.origins(addrOne)
                .destinations(addrTwo)
                .mode(TravelMode.DRIVING)
                .avoid(DirectionsApi.RouteRestriction.TOLLS)
                .language("en-US")
                .await();

        String distApart = result.rows[0].elements[0].distance.humanReadable;

        return distApart;
    }
}
