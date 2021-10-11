package br.com.greenmile.desafio.entity.form;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import br.com.greenmile.desafio.entity.enums.CoordinateDeliveryStatusEnum;
import br.com.greenmile.desafio.entity.enums.RoutePlanStatusEnum;
import br.com.greenmile.desafio.entity.model.RoutePlan;
import lombok.Data;

@Data
public class RouteForm {

    private Long id;

    @NotNull
    private String description;
    
    @NotNull
    private LocalDate date;
    
    @Valid
    @NotEmpty(message = "necessário informar pelo menos uma parada")
    private List<CoodirnateDeliveryForm> stops;

    private CoordinateDeliveryStatusEnum stopStatus;

    private RoutePlanStatusEnum statusRoute;

    private Instant searchTime;

    private String stopDesc;

    public RoutePlan convert(){
        RoutePlan routePlan = new RoutePlan(description, 
                             RoutePlanStatusEnum.NOT_STARTED, 
                             date, 
                             stops.stream()
                                  .map(CoodirnateDeliveryForm::convert)
                                  .collect(Collectors.toList()));
        if(this.id != null){
            routePlan.setRoutePlanId(this.id);
        }
        return routePlan;
    }
    
}
