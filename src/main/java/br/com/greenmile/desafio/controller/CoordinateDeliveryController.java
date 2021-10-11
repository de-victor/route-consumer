package br.com.greenmile.desafio.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.greenmile.desafio.entity.dto.CoordinateDeliveryDto;
import br.com.greenmile.desafio.entity.form.CoodirnateDeliveryForm;
import br.com.greenmile.desafio.service.CoordinateDeliveryService;

@RestController
@RequestMapping("/DeliveryStops")
public class CoordinateDeliveryController {

	private final CoordinateDeliveryService coordinateDeliveryService;
	
	public CoordinateDeliveryController(CoordinateDeliveryService coordinateDeliveryService) {
		this.coordinateDeliveryService = coordinateDeliveryService;
	}
	
	@GetMapping("/all")
	public ResponseEntity<List<CoordinateDeliveryDto>> listOfCompletedStops(){
		return ResponseEntity.ok(coordinateDeliveryService.listOfCompletedStops()
														  .stream()
														  .map(CoordinateDeliveryDto::new)
														  .collect(Collectors.toList()));
	}

	@GetMapping
	public ResponseEntity<List<CoordinateDeliveryDto>> findByFilters(CoodirnateDeliveryForm coodirnateDeliveryForm){
		return ResponseEntity.ok(coordinateDeliveryService.findBySpecification(coodirnateDeliveryForm)
														  .stream()
														  .map(CoordinateDeliveryDto::new)
														  .collect(Collectors.toList()));
	}
	
	
}
