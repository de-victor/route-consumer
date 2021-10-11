package br.com.greenmile.desafio.service;

import org.springframework.stereotype.Service;

import br.com.greenmile.desafio.entity.form.CoordinateForm;
import br.com.greenmile.desafio.entity.model.CoordinateIncoming;
import br.com.greenmile.desafio.exception.RoutePlanNotFoundException;
import br.com.greenmile.desafio.repository.CoordinateIncomingRepository;
import br.com.greenmile.desafio.repository.RoutePlanRepository;
import lombok.extern.java.Log;

@Log
@Service
public class CoordinateService {
	
	
	private final CoordinateIncomingRepository coordinateIncomingRepository;
	private final RoutePlanService routePlanService;
	private final RoutePlanRepository routePlanRepository;
	
	
	public CoordinateService(CoordinateIncomingRepository coordinateIncomingRepository, 
							 RoutePlanService routePlanService,
							 RoutePlanRepository routePlanRepository) {
		
		this.coordinateIncomingRepository = coordinateIncomingRepository;
		this.routePlanService = routePlanService;
		this.routePlanRepository = routePlanRepository;
	}
	
	public void incomingCoordinate(CoordinateForm coordinateForm) throws RoutePlanNotFoundException {
		CoordinateIncoming coordinateIncoming = coordinateIncomingRepository.save(coordinateForm.convert(routePlanRepository));
		log.info(coordinateForm.toString());
		
		routePlanService.processRoutePlanFromCoordinateIncoming(coordinateIncoming);
		
	}

	
}
