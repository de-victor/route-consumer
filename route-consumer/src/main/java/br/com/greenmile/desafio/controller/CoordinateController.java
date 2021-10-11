package br.com.greenmile.desafio.controller;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.greenmile.desafio.entity.form.CoordinateForm;
import br.com.greenmile.desafio.exception.RoutePlanNotFoundException;
import br.com.greenmile.desafio.service.CoordinateService;

@RestController
@RequestMapping("/ReceiveCoordinate")
public class CoordinateController {
	
	private final CoordinateService coordinateService;
	
	
	public CoordinateController(CoordinateService coordinateService) {
		this.coordinateService = coordinateService;
	}
	
	
	@PostMapping
	public ResponseEntity<String> receiveCoordinate(@Valid @RequestBody CoordinateForm coordinateForm) throws RoutePlanNotFoundException {
		coordinateService.incomingCoordinate(coordinateForm);
		return ResponseEntity.ok("");
	}
	

}
