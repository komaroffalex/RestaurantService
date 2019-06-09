package com.rservice.service.googledistance;

import com.rservice.service.geolocation.GeoLocationService;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertTrue;

public class GeoLocationTest {

    private GeoLocationService geoLocationService = new GeoLocationService();

    @Test
    public void getSampleDistance() {
        String destination = "Chicago";
        String distance = geoLocationService.getDistanceTo(destination);
        distance = distance.replaceAll("\\D+","");
        int distanceInt = Integer.parseInt(distance);
        assertTrue(distanceInt > 0);
    }
}
