package com.example.UberProject_LocationService.dtos;


import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class GetNearByDriverRequestDto {

    private Double latitude;
    private Double longitude;

}
