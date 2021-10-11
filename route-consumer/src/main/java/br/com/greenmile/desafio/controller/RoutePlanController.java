package br.com.greenmile.desafio.controller;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.greenmile.desafio.entity.dto.RoutePlanDto;
import br.com.greenmile.desafio.entity.enums.CoordinateDeliveryStatusEnum;
import br.com.greenmile.desafio.entity.form.RouteForm;
import br.com.greenmile.desafio.exception.RoutePlanNotFoundException;
import br.com.greenmile.desafio.service.RoutePlanService;

@RestController
@RequestMapping("/Route")
public class RoutePlanController {
	
	
	private final RoutePlanService routePlanService;

	public RoutePlanController(RoutePlanService routePlanService){
		this.routePlanService = routePlanService;
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<RoutePlanDto> retrieveRoutePlan(@PathVariable Long id) throws RoutePlanNotFoundException{
		return ResponseEntity.ok(new RoutePlanDto(routePlanService.findRoutePlanById(id)));
	}

	@PostMapping
	public ResponseEntity<Long> newRoutePlan(@Valid @RequestBody RouteForm routeForm){
		return ResponseEntity.ok(this.routePlanService.save(routeForm).getRoutePlanId());
	}

	@PutMapping
	public ResponseEntity<RoutePlanDto> updateRoutePlan(@Valid @RequestBody RouteForm routeForm) throws RoutePlanNotFoundException{
		return ResponseEntity.ok(new RoutePlanDto(routePlanService.update(routeForm)));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteRoute(@PathVariable Long id) throws RoutePlanNotFoundException{
		routePlanService.deleteRoute(id);
		return ResponseEntity.ok("");
	}

	@GetMapping("/LongerStops")
	public ResponseEntity<List<RoutePlanDto>> longerStop() throws RoutePlanNotFoundException{
		return ResponseEntity.ok(routePlanService.longerStops().stream()
															   .map(RoutePlanDto::new)
															   .collect(Collectors.toList()));
	}

	@GetMapping("/DeliveryStatus")
	public ResponseEntity<List<RoutePlanDto>> findByFilters() throws RoutePlanNotFoundException{
		return ResponseEntity.ok(routePlanService.findRoutePlanByDeliveryStatus(CoordinateDeliveryStatusEnum.ANSER)
																				 .stream()
																				 .map(RoutePlanDto::new)
																				 .collect(Collectors.toList()));
	}

	@GetMapping
	public ResponseEntity<List<RoutePlanDto>> findBySoecification(RouteForm form) throws RoutePlanNotFoundException{
		return ResponseEntity.ok(routePlanService.findRoutesBySpecification(form)
												 .stream()
												 .map(item -> {
													 RoutePlanDto dto = new RoutePlanDto();
													 dto.setData(item.getDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
													 dto.setDescription(item.getRoutePlanDescription());
													 dto.setId(item.getRoutePlanId());
													 dto.setStatus(item.getStatus());
													 return dto;
												 })
												 .collect(Collectors.toList()));
	}
	
}
