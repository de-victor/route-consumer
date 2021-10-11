package br.com.greenmile.desafio.entity.dto;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import br.com.greenmile.desafio.entity.enums.RoutePlanStatusEnum;
import br.com.greenmile.desafio.entity.model.RoutePlan;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonInclude(Include.NON_NULL)
public class RoutePlanDto {
	private Long id;
	private String description;
	private RoutePlanStatusEnum status;
	private List<CoordinateDeliveryDto> stops;
	private String data;
	
	public RoutePlanDto(RoutePlan routePlan) {
		this.id = routePlan.getRoutePlanId();
		this.description = routePlan.getRoutePlanDescription();
		this.status = routePlan.getStatus();
		this.data = routePlan.getDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
		
		this.stops = routePlan.getPlannedStops().stream()
												  .map(CoordinateDeliveryDto::new)
												  .collect(Collectors.toList());
		
	}
	
}
