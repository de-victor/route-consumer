package br.com.greenmile.desafio.entity.model;

import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "tbl_coord_in_delivery")
public class CoordinateInDelivery {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_coordinate_in_delivery")
	private Long coordinateDeliveryId;
	
	@Column(name = "lat")
	private Double lat;
	
	@Column(name = "lgn")
	private Double lgn;
	
	@Column(name = "dt_in_delivery")
	private Instant instant;
	
	@ManyToOne
	@JoinColumn(name = "id_coordinate_delivery", referencedColumnName = "id_coordinate_delivery")
	private CoordinateDelivery coordinateDelivery;
	
	
	public CoordinateInDelivery(Double lat, Double lgn, Instant instant, CoordinateDelivery coordinateDelivery) {
		this.lat = lat;
		this.lgn = lgn;
		this.instant = instant;
		this.coordinateDelivery = coordinateDelivery;
	}

}
