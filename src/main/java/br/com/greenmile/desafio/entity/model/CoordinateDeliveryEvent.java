package br.com.greenmile.desafio.entity.model;

import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import br.com.greenmile.desafio.entity.enums.CoordinateDeliveryStatusEnum;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "tbl_coord_delivery_event")
public class CoordinateDeliveryEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_coordinate_delivery_event")
    private Long coordinateDeliveryEventId;

    private Double lat;
    
    private Double lgn;

    @Enumerated(EnumType.STRING)
    private CoordinateDeliveryStatusEnum status;

    @Column(name = "time_event")
    private Instant timechange;

    @ManyToOne
    @JoinColumn(name = "id_coordinate_delivery", referencedColumnName = "id_coordinate_delivery")
    private CoordinateDelivery coordinateDelivery;

    public CoordinateDeliveryEvent(Instant timechange, Double lat, Double lgn, CoordinateDeliveryStatusEnum status, CoordinateDelivery coordinateDelivery){
        this.timechange = timechange;
        this.lat = lat;
        this.lgn = lgn;
        this. status = status;
        this.coordinateDelivery = coordinateDelivery;
    }
    
}
