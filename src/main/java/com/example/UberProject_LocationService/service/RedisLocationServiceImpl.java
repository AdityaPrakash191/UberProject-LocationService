package com.example.UberProject_LocationService.service;

import com.example.UberProject_LocationService.dtos.DriverLocationDto;
import org.springframework.data.geo.*;
import org.springframework.data.redis.connection.RedisGeoCommands;
import org.springframework.data.redis.core.GeoOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RedisLocationServiceImpl implements LocationService{

    public StringRedisTemplate stringRedisTemplate;

    public static final String DRIVER_GEO_OPS_KEY="drivers";

    public static final Double SEARCH_RADIUS = 500.0;

    public RedisLocationServiceImpl (StringRedisTemplate stringRedisTemplate){
        this.stringRedisTemplate = stringRedisTemplate;
    }

    @Override
    public Boolean saveDriverLocation(String driverId, Double latitude, Double longitude) {
        GeoOperations<String,String> geoOps = stringRedisTemplate.opsForGeo();
        geoOps.add(DRIVER_GEO_OPS_KEY,
                new RedisGeoCommands.GeoLocation<>(
                        driverId,
                        new Point(
                                latitude,
                                longitude
                        )
                ));
        return true;

    }

    @Override
    public List<DriverLocationDto> getNearByDrivers(Double latitude, Double longitude) {
        GeoOperations<String,String> geoOps = stringRedisTemplate.opsForGeo();
        Distance radius = new Distance(SEARCH_RADIUS, Metrics.KILOMETERS);
        Circle within = new Circle(new Point(latitude , longitude) , radius);

        GeoResults<RedisGeoCommands.GeoLocation<String>> results = geoOps.radius(DRIVER_GEO_OPS_KEY , within);
        List<DriverLocationDto> drivers = new ArrayList<>();
        for(GeoResult<RedisGeoCommands.GeoLocation<String>> result : results){
            Point point = geoOps.position(DRIVER_GEO_OPS_KEY,result.getContent().getName()).get(0);
           DriverLocationDto dto = DriverLocationDto.builder()
                   .driverId(result.getContent().getName())
                   .latitude(point.getX())
                   .longitude(point.getY())
                                    .build();
           drivers.add(dto);
        }
        return drivers;
    }
}
