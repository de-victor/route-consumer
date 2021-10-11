package br.com.greenmile.desafio.entity.model;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import br.com.greenmile.desafio.entity.enums.RoutePlanStatusEnum;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity(name = "tbl_route_plan")
public class RoutePlan {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_route_plan")
	private Long routePlanId;
	
	@Column(name = "description")
	private String routePlanDescription;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "route_status")
	private RoutePlanStatusEnum status;
	
	@Column(name = "in_place_count")
	private Integer inPlaceCount;

	private LocalDate date;
	
	@OneToMany(mappedBy = "routePlan")
	private List<CoordinateDelivery> plannedStops;

	public RoutePlan(String routePlanDescription, RoutePlanStatusEnum status, LocalDate date, List<CoordinateDelivery> plannedStops){
		this.routePlanDescription = routePlanDescription;
		this.status = status;
		this.date = date;
		this.plannedStops = plannedStops;
	}

}
