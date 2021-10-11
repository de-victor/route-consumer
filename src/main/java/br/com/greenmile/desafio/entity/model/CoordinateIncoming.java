package br.com.greenmile.desafio.entity.model;

import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "tbl_coordinate")
public class CoordinateIncoming {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_coordinate")
	private Long coordinateId;
	
	@Column(name = "lat")
	private Double lat;
	
	@Column(name = "lgn")
	private Double lng;
	
	@ManyToOne
	private RoutePlan routePlan;
	
	@Column(name = "dt_recived")
	private Instant instant;
	
	public CoordinateIncoming(Double lat, Double lng, RoutePlan routePlan, Instant instant) {
		this.routePlan = routePlan;
		this.lat = lat;
		this.lng = lng;
		this.instant = instant;
	}
	
	
	

}
