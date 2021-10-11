package br.com.greenmile.desafio.service;

import java.util.List;

import org.springframework.stereotype.Service;

import br.com.greenmile.desafio.entity.enums.CoordinateDeliveryStatusEnum;
import br.com.greenmile.desafio.entity.enums.SearchOperation;
import br.com.greenmile.desafio.entity.form.CoodirnateDeliveryForm;
import br.com.greenmile.desafio.entity.model.CoordinateDelivery;
import br.com.greenmile.desafio.entity.others.SearchElement;
import br.com.greenmile.desafio.exception.CoordinateDeliveryNotFoundException;
import br.com.greenmile.desafio.repository.CoordinateDeliveryRepository;
import br.com.greenmile.desafio.specification.CoordinateDeliverySpecification;

@Service
public class CoordinateDeliveryService {

	private final CoordinateDeliveryRepository coordinateDeliveryRepository;
	
	public CoordinateDeliveryService(CoordinateDeliveryRepository coordinateDeliveryRepository) {
		this.coordinateDeliveryRepository = coordinateDeliveryRepository;
	}
	
	
	
	public List<CoordinateDelivery> listOfCompletedStops(){
		List<CoordinateDelivery> list = coordinateDeliveryRepository.findByStatus(CoordinateDeliveryStatusEnum.ANSER);
		
		if(list.isEmpty()) {
			throw new CoordinateDeliveryNotFoundException("Nenhuma parada ainda foi atendida");
		}
		
		return list;
	}

	public List<CoordinateDelivery> findBySpecification(CoodirnateDeliveryForm coodirnateDeliveryForm){
		List<CoordinateDelivery> list = coordinateDeliveryRepository.findAll(validateSpecification(coodirnateDeliveryForm));
		if(list.isEmpty()){
			throw new CoordinateDeliveryNotFoundException("Não foram encontrada paradas com base o filtro informado");
		}
		return list;
	}



	private CoordinateDeliverySpecification validateSpecification(CoodirnateDeliveryForm form) {
		CoordinateDeliverySpecification specification = new CoordinateDeliverySpecification();

		if(form.getId() != null){
			specification.addFilter(new SearchElement("coordinateDeliveryId", form.getId(), SearchOperation.EQUAL));
		}
		if(form.getRouteId() != null){
			specification.addFilter(new SearchElement("routePlan.routePlanId", form.getRouteId(), SearchOperation.EQUAL));
		}
		if(form.getRouteName() != null){
			specification.addFilter(new SearchElement("routePlan.routePlanDescription", form.getRouteId(), SearchOperation.MATCH));
		}
		if(form.getStatus() != null){
			specification.addFilter(new SearchElement("status", form.getStatus(), SearchOperation.EQUAL));
		}

		return specification;
	}
	
}
