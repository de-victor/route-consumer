package br.com.greenmile.desafio.entity.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import br.com.greenmile.desafio.entity.enums.CoordinateDeliveryStatusEnum;
import br.com.greenmile.desafio.entity.model.CoordinateDelivery;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonInclude(Include.NON_NULL)
public class CoordinateDeliveryDto {
	private Long id;
	private Double lat;
	private Double lgn;
	private String description;
	private CoordinateDeliveryStatusEnum status;
	private RoutePlanDto routePlanDto;
	private Long usedTimeInSeconds;
	private Double radius;
	private String address;
	
	public CoordinateDeliveryDto(CoordinateDelivery coordinateDelivery) {
		this.id = coordinateDelivery.getCoordinateDeliveryId();
		this.lat = coordinateDelivery.getLat();
		this.lgn = coordinateDelivery.getLgn();
		this.status = coordinateDelivery.getStatus();
		this.description = coordinateDelivery.getDescription();
		this.radius = coordinateDelivery.getDeliveryRadius();
		this.address = coordinateDelivery.getAddress();
		if(coordinateDelivery.getUsedTimeInDeliver() != null) {
			this.usedTimeInSeconds = coordinateDelivery.getUsedTimeInDeliver(); 
		}
	}

}
