package com.example.UberProject_LocationService.controller;

import com.example.UberProject_LocationService.dtos.DriverLocationDto;
import com.example.UberProject_LocationService.dtos.GetNearByDriverRequestDto;
import com.example.UberProject_LocationService.dtos.SaveDriverLocationRequestDto;
import com.example.UberProject_LocationService.service.LocationService;
import org.springframework.data.geo.*;
import org.springframework.data.redis.connection.RedisGeoCommands;
import org.springframework.data.redis.core.GeoOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/location")
public class LocationController {

    private final LocationService locationService;

    public LocationController(LocationService locationService){
        this.locationService = locationService;
    }


    @PostMapping("/drivers")
    public ResponseEntity<?> saveDriverLocation(@RequestBody SaveDriverLocationRequestDto saveDriverLocationRequestDto){
      try{
          Boolean response = locationService.saveDriverLocation(saveDriverLocationRequestDto.getDriverId(),saveDriverLocationRequestDto.getLatitude(),saveDriverLocationRequestDto.getLongitude());
          return new ResponseEntity<>(response, HttpStatus.CREATED);
      }catch(Exception e){
          System.out.println(e.getMessage());
          return new ResponseEntity<>(false,HttpStatus.INTERNAL_SERVER_ERROR);
      }
    }

    @PostMapping("nearby/drivers")
    public ResponseEntity<List<DriverLocationDto>> getNearByDrivers(@RequestBody GetNearByDriverRequestDto getNearByDriverRequestDto){

        try{
            List<DriverLocationDto> drivers = locationService.getNearByDrivers(getNearByDriverRequestDto.getLatitude(), getNearByDriverRequestDto.getLongitude());
            return new ResponseEntity<>(drivers, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(new ArrayList<>(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
