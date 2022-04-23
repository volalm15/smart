package com.vollify.smart.device.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class Location {
    @DecimalMin(value = "-90", message = "Latitute could not be lesser than -90")
    @DecimalMax(value = "90", message = "Latitute could not be greater than 90")
    @NotNull
    Double latitude;

    @DecimalMin(value = "-180", message = "Longitude could not be lesser than -90")
    @DecimalMax(value = "180", message = "Longitude could not be greater than 90")
    @NotNull
    Double longitude;

}
