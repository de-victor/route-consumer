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

import br.com.greenmile.desafio.entity.enums.RoutePlanStatusEnum;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tbl_route_event")
@Data
@NoArgsConstructor
public class RoutePlanEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_route_event")
    private Long routeEventeId;

    @Column(name = "time_event")
    private Instant timechange;

    @Enumerated(EnumType.STRING)
    private RoutePlanStatusEnum stauts;

    private Double lat;
    private Double lgn;

    @ManyToOne
    @JoinColumn(name = "id_route_plan", referencedColumnName = "id_route_plan")
    private RoutePlan routePlan;

    public RoutePlanEvent(Instant timechange, RoutePlanStatusEnum stauts, RoutePlan routePlan, Double lat, Double lgn){
        this.timechange = timechange;
        this.stauts = stauts;
        this.routePlan = routePlan;
        this.lat = lat;
        this.lgn = lgn;
    }
    
}
