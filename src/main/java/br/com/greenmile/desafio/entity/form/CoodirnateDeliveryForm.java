package br.com.greenmile.desafio.entity.form;

import java.time.Instant;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import br.com.greenmile.desafio.entity.enums.CoordinateDeliveryStatusEnum;
import br.com.greenmile.desafio.entity.model.CoordinateDelivery;
import lombok.Data;

@Data
public class CoodirnateDeliveryForm {
    private Long id;
    @NotNull
    @Min(value = 1, message = "Latitude não pode ser inferior a 1")
    private Double lat;
    @NotNull
    @Min(value = 1, message = "Longitude não pode ser inferior a 1")
    private Double lgn;
    @NotNull
    private String description;
    @NotNull
    private Double radius;
    @NotNull
    private String address;

    private String routeName;

    private Long routeId;

    private CoordinateDeliveryStatusEnum status;

    private Instant searchTime;

    private Boolean remove = false;

    public CoordinateDelivery convert(){
        CoordinateDelivery coordinateDelivery = new CoordinateDelivery(lat, lgn, description, radius, address, CoordinateDeliveryStatusEnum.NOT_ANSWER);
        if(this.id != null){
            coordinateDelivery.setCoordinateDeliveryId(this.id);
        }
        return coordinateDelivery;
    }
}
