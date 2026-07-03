package com.photographer.platform.photographer.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PhotographerResponse {

    private Long id;

    private Long userId;

    private String fullName;

    private String email;

    private String phone;

    private String studioName;

    private String bio;

    private Integer experience;

    private String city;

    private String state;

    private String country;

    private Double startingPrice;

    private String profileImage;

    private boolean verified;

}