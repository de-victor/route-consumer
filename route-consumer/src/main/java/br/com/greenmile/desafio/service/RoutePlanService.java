package br.com.greenmile.desafio.service;

import java.time.Instant;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import br.com.greenmile.desafio.entity.enums.CoordinateDeliveryStatusEnum;
import br.com.greenmile.desafio.entity.enums.RoutePlanStatusEnum;
import br.com.greenmile.desafio.entity.enums.SearchOperation;
import br.com.greenmile.desafio.entity.form.CoodirnateDeliveryForm;
import br.com.greenmile.desafio.entity.form.RouteForm;
import br.com.greenmile.desafio.entity.model.CoordinateDelivery;
import br.com.greenmile.desafio.entity.model.CoordinateDeliveryEvent;
import br.com.greenmile.desafio.entity.model.CoordinateInDelivery;
import br.com.greenmile.desafio.entity.model.CoordinateIncoming;
import br.com.greenmile.desafio.entity.model.RoutePlan;
import br.com.greenmile.desafio.entity.model.RoutePlanEvent;
import br.com.greenmile.desafio.entity.others.SearchElement;
import br.com.greenmile.desafio.exception.RoutePlanNotFoundException;
import br.com.greenmile.desafio.repository.CoordinateDeliveryEventRepository;
import br.com.greenmile.desafio.repository.CoordinateDeliveryRepository;
import br.com.greenmile.desafio.repository.CoordinateInDeliveryRepository;
import br.com.greenmile.desafio.repository.RoutePlanEventRepository;
import br.com.greenmile.desafio.repository.RoutePlanRepository;
import br.com.greenmile.desafio.specification.RoutePlanSpecification;
import br.com.greenmile.desafio.util.SpatialUtil;
import lombok.extern.java.Log;


@Log
@Service
public class RoutePlanService {
	
	
	private final RoutePlanRepository routePlanRepository;
	private final CoordinateInDeliveryRepository coordinateInDeliveryRepository;
	private final CoordinateDeliveryRepository coordinateDeliveryRepository;
	private final CoordinateDeliveryEventRepository coordinateDeliveryEventRepository;
	private final RoutePlanEventRepository routePlanEventRepository;
	private final SpatialUtil spatialUtil;
	private final Long inRadiusLocationQt = Long.valueOf(2);
	
	
	public RoutePlanService(RoutePlanRepository routePlanRepository, 
							SpatialUtil spatialUtil, 
							CoordinateInDeliveryRepository coordinateInDeliveryRepository, 
							CoordinateDeliveryRepository coordinateDeliveryRepository,
							CoordinateDeliveryEventRepository coordinateDeliveryEventRepository,
							RoutePlanEventRepository routePlanEventRepository) {
		
		this.routePlanRepository = routePlanRepository;
		this.spatialUtil = spatialUtil;
		this.coordinateInDeliveryRepository = coordinateInDeliveryRepository;
		this.coordinateDeliveryRepository = coordinateDeliveryRepository;
		this.coordinateDeliveryEventRepository = coordinateDeliveryEventRepository;
		this.routePlanEventRepository = routePlanEventRepository;
	}

	public RoutePlan save(RouteForm routeForm){
		RoutePlan route = routeForm.convert();
		route = this.routePlanRepository.save(route);
		Long routePlanId = route.getRoutePlanId();

		route.getPlannedStops().stream().forEach(stop ->{
			stop.setRoutePlan(new RoutePlan());
			stop.getRoutePlan().setRoutePlanId(routePlanId);
			coordinateDeliveryRepository.save(stop);
		});
		return route;
	}

	public void deleteRoute(Long id) throws RoutePlanNotFoundException{
		Optional<RoutePlan> opRoutePlan = routePlanRepository.findById(id);
		if(opRoutePlan.isPresent()){
			opRoutePlan.get().getPlannedStops().forEach(stop ->{
				coordinateDeliveryRepository.deleteById(stop.getCoordinateDeliveryId());
			});
			routePlanRepository.deleteById(id);
		}
		else{
			throw new RoutePlanNotFoundException("id: "+id+" não encontrado em rota");
		}
	}

	public RoutePlan update(RouteForm routeForm) throws RoutePlanNotFoundException{
		Optional<RoutePlan> opRoutePlan = routePlanRepository.findById(routeForm.getId());
		if(opRoutePlan.isPresent()){
			RoutePlan route = routeForm.convert();
			routePlanRepository.save(route);
			List<CoodirnateDeliveryForm> removalStops = routeForm.getStops().stream().filter(item -> item.getRemove().equals(true)).collect(Collectors.toList());
			if(removalStops.size() > 0){
				removalStops.forEach(stop ->{
					coordinateDeliveryRepository.deleteById(stop.getId());
				});
			}
			List<CoodirnateDeliveryForm> updateStops = routeForm.getStops().stream().filter(item -> item.getRemove().equals(false)).collect(Collectors.toList());
			if(updateStops.size() > 0){
				updateStops.forEach(stop ->{
					CoordinateDelivery coordD = stop.convert();
					coordD.setRoutePlan(route);
					coordinateDeliveryRepository.save(coordD);
				});
			}
			return routePlanRepository.save(route);
		}
		else{
			throw new RoutePlanNotFoundException("id: "+routeForm.getId()+" não encontrado em rotas");
		}
	}
	
	public RoutePlan findRoutePlanById(Long id) throws RoutePlanNotFoundException {

		Optional<RoutePlan> routePlanOp = routePlanRepository.findById(id);
		
		if(routePlanOp.isPresent()) {
			return routePlanOp.get();
		}
		
		throw new RoutePlanNotFoundException("Nenhum plano de rota encontrado");
		
	}

	public List<RoutePlan> findRoutePlanByDeliveryStatus(CoordinateDeliveryStatusEnum deliveryStatus) throws RoutePlanNotFoundException {

		List<RoutePlan> list = routePlanRepository.findDistinctByPlannedStops_Status(deliveryStatus);
		
		list.forEach(route ->{
			List<CoordinateDelivery> ansers = route.getPlannedStops().stream().filter(obj -> obj.getStatus().equals(CoordinateDeliveryStatusEnum.ANSER)).collect(Collectors.toList());
			route.setPlannedStops(ansers);
		});
		
		if(!list.isEmpty()) {
			return list;
		}
		
		throw new RoutePlanNotFoundException("nada encontrado com o status informado");
		
	}

	public List<RoutePlan> findRoutesBySpecification(RouteForm form){
		RoutePlanSpecification specification = validateSpecification(form);
		List<RoutePlan> list = routePlanRepository.findAll(specification);
		return list;
	}
	
	private RoutePlanSpecification validateSpecification(RouteForm form) {
		RoutePlanSpecification specification = new RoutePlanSpecification();
		
		if(form.getId() != null){
			specification.addFilter(new SearchElement("routePlanId", form.getId(), SearchOperation.EQUAL));
		}
		if(form.getStopStatus() != null){
			specification.addFilter(new SearchElement("plannedStops.status", form.getStopStatus(), SearchOperation.EQUAL));
		}
		if(form.getStatusRoute() != null){
			specification.addFilter(new SearchElement("status", form.getStatusRoute(), SearchOperation.EQUAL));
		}
		
		return specification;
	}

	public List<RoutePlan> longerStops() throws RoutePlanNotFoundException{
		List<RoutePlan> list = routePlanRepository.findAll();
		if(list.isEmpty()) {
			throw new RoutePlanNotFoundException("Nenhuma entrega finalizada");
		}
		
		list.forEach(route ->{
			route.getPlannedStops().stream()
								   .filter(stop -> stop.getStatus().equals(CoordinateDeliveryStatusEnum.ANSER))
								   .forEach(stop ->{
										List<CoordinateInDelivery> orderedList = stop.getCoordinateInDeliveryList().stream()
																												   .sorted(Comparator.comparingLong(obj -> obj.getInstant().getEpochSecond()))
																												   .collect(Collectors.toList());
										CoordinateInDelivery start = orderedList.get(0);
										CoordinateInDelivery last = orderedList.get(orderedList.size()-1);
										Long timePassed = last.getInstant().getEpochSecond() - start.getInstant().getEpochSecond();
										stop.setUsedTimeInDeliver(timePassed);
										
									});
		});
		list.forEach(route ->{
			List<CoordinateDelivery> orderByUsedTime = route.getPlannedStops().stream().filter(stop -> stop.getStatus().equals(CoordinateDeliveryStatusEnum.ANSER))
																					   .sorted(Comparator.comparing(CoordinateDelivery::getUsedTimeInDeliver).reversed())
																					   .collect(Collectors.toList());
			if(!orderByUsedTime.isEmpty()){
				route.setPlannedStops(Arrays.asList(orderByUsedTime.get(0)));
			}
			else{
				route.setPlannedStops(orderByUsedTime);
			}
		});
		
		return list;
	}

	public void processRoutePlanFromCoordinateIncoming(CoordinateIncoming coordinateIncoming) {
		RoutePlan routePlan = routePlanRepository.findByRoutePlanIdAndStatusIn(coordinateIncoming.getRoutePlan().getRoutePlanId(), Arrays.asList(RoutePlanStatusEnum.NOT_STARTED, RoutePlanStatusEnum.STARTED));
		
		if(routePlan != null) {
			if(routePlan.getStatus().equals(RoutePlanStatusEnum.NOT_STARTED)) {
				routePlan.setStatus(RoutePlanStatusEnum.STARTED);
				routePlanRepository.save(routePlan);
				eventEntryRoutePlan(coordinateIncoming, RoutePlanStatusEnum.STARTED, routePlan);
			}
			
			if(routePlan.getStatus().equals(RoutePlanStatusEnum.STARTED)) {
				routePlan.getPlannedStops().stream().forEach(stop ->{
					Double distance = spatialUtil.haversineFormula(coordinateIncoming.getLat(), coordinateIncoming.getLng(), stop.getLat(), stop.getLgn())*1000;
					
					if(distance <= stop.getDeliveryRadius() && !stop.getStatus().equals(CoordinateDeliveryStatusEnum.ANSER)) {
						
						CoordinateInDelivery coordinateInDelivery = coordinateInDeliveryRepository.save(new CoordinateInDelivery(coordinateIncoming.getLat(), coordinateIncoming.getLng(), Instant.now(), stop));
						
						if(stop.getStatus().equals(CoordinateDeliveryStatusEnum.NOT_ANSWER)) {
							
							Long countOfCoordenatesInDelivery = coordinateInDeliveryRepository.countByCoordinateDelivery_coordinateDeliveryId(coordinateInDelivery.getCoordinateDelivery().getCoordinateDeliveryId());
							
							if(countOfCoordenatesInDelivery >= this.inRadiusLocationQt) {
								stop.setStatus(CoordinateDeliveryStatusEnum.IN_PROGRESS);
								coordinateDeliveryRepository.save(stop);
								eventEntryCoordinateDelivery(coordinateIncoming, CoordinateDeliveryStatusEnum.IN_PROGRESS, stop);
							}
						}
					}
					else if(distance > stop.getDeliveryRadius() && stop.getStatus().equals(CoordinateDeliveryStatusEnum.IN_PROGRESS)) {
						stop.setStatus(CoordinateDeliveryStatusEnum.ANSER);
						coordinateDeliveryRepository.save(stop);
						eventEntryCoordinateDelivery(coordinateIncoming, CoordinateDeliveryStatusEnum.ANSER, stop);
					}
					
					log.info(String.format("%s distancia(M): %f status: %s", stop.getDescription(), distance, stop.getStatus()));
					
				});
				int size = routePlan.getPlannedStops().stream()
										   			  .filter(stop -> stop.getStatus().equals(CoordinateDeliveryStatusEnum.NOT_ANSWER) || stop.getStatus().equals(CoordinateDeliveryStatusEnum.IN_PROGRESS))
										   			  .collect(Collectors.toList())
										   			  .size();
				if(size == 0) {
					routePlan.setStatus(RoutePlanStatusEnum.DONE);
					routePlanRepository.save(routePlan);
					eventEntryRoutePlan(coordinateIncoming, RoutePlanStatusEnum.DONE, routePlan);
				}
			}
		}
	}

	private void eventEntryRoutePlan(CoordinateIncoming coordinateIncoming, RoutePlanStatusEnum status, RoutePlan routePlan){
		routePlanEventRepository.save(new RoutePlanEvent(Instant.now(), 
														 status, 
														 routePlan, 
														 coordinateIncoming.getLat(), 
														 coordinateIncoming.getLng()));
	}

	private void eventEntryCoordinateDelivery(CoordinateIncoming coordinateIncoming, CoordinateDeliveryStatusEnum status, CoordinateDelivery stop){
		coordinateDeliveryEventRepository.save(new CoordinateDeliveryEvent(Instant.now(), 
																		   coordinateIncoming.getLat(), 
																		   coordinateIncoming.getLng(), 
																		   status, 
																		   stop));
	}

}
