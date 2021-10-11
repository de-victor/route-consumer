package br.com.greenmile.desafio.entity.form;

import java.time.Instant;
import java.util.Optional;

import javax.validation.constraints.NotNull;

import br.com.greenmile.desafio.entity.model.CoordinateIncoming;
import br.com.greenmile.desafio.entity.model.RoutePlan;
import br.com.greenmile.desafio.exception.RoutePlanNotFoundException;
import br.com.greenmile.desafio.repository.RoutePlanRepository;
import lombok.Data;

@Data
public class CoordinateForm {

	private Long id;
	
	@NotNull
	private Double lat;
	
	@NotNull
	private Double lng;
	
	@NotNull	
	private Long routeId;
	
	@NotNull
	private Instant instant;
	
	
	public CoordinateIncoming convert(RoutePlanRepository routePlanRepository) throws RoutePlanNotFoundException {
		Optional<RoutePlan> route = routePlanRepository.findById(this.routeId);
		if(route.isPresent()) {
			return new CoordinateIncoming(lat, lng, route.get(), instant);
		}
		
		throw new RoutePlanNotFoundException("id do veiculo informado não existe");
		
		
	}

}
