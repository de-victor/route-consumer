package br.com.greenmile.desafio.entity.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import br.com.greenmile.desafio.entity.enums.CoordinateDeliveryStatusEnum;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "tbl_coordinate_delivery")
public class CoordinateDelivery {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_coordinate_delivery")
	private Long coordinateDeliveryId;
	
	@Column(name = "lat")
	private Double lat;
	
	@Column(name = "lgn")
	private Double lgn;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "delivery_status")
	private CoordinateDeliveryStatusEnum status;
	
	@Column(name = "description")
	private String description;
	
	@Column(name = "delivery_radius")
	private Double deliveryRadius;

	private String address;
	
	@Transient
	private Long usedTimeInDeliver;
	
	@ManyToOne
	@JoinColumn(name = "id_route_plan", referencedColumnName = "id_route_plan")
	private RoutePlan routePlan;
	
	@OneToMany(mappedBy = "coordinateDelivery")
	private List<CoordinateInDelivery> coordinateInDeliveryList;

	public CoordinateDelivery(Double lat, Double lgn, String description, Double deliveryRadius, String address, CoordinateDeliveryStatusEnum status){
		this.lat = lat;
		this.lgn = lgn;
		this.description = description;
		this.deliveryRadius = deliveryRadius;
		this.address = address;
		this.status = status;
	}

}
