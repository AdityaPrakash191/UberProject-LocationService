package com.example.UberProject_LocationService.dtos;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class DriverLocationDto {

    private String driverId;
    private Double latitude;
    private Double longitude;

}
