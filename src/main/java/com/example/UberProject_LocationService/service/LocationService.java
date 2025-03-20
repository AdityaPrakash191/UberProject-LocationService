package com.example.UberProject_LocationService.service;

import com.example.UberProject_LocationService.dtos.DriverLocationDto;

import java.util.List;

public interface LocationService {

    Boolean saveDriverLocation(String driverId , Double latitude , Double longitude);

    List<DriverLocationDto> getNearByDrivers(Double latitude , Double Longitude);


}
