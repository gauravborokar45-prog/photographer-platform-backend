package com.photographer.platform.photographer.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdatePhotographerRequest {

    @NotBlank(message = "Studio name is required")
    private String studioName;

    private String bio;

    @NotNull(message = "Experience is required")
    @Min(value = 0)
    private Integer experience;

    @NotBlank(message = "City is required")
    private String city;

    @NotBlank(message = "State is required")
    private String state;

    @NotBlank(message = "Country is required")
    private String country;

    @NotNull(message = "Starting price is required")
    @Min(value = 0)
    private Double startingPrice;

}