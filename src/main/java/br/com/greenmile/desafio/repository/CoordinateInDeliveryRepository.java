package br.com.greenmile.desafio.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.greenmile.desafio.entity.model.CoordinateInDelivery;

@Repository
public interface CoordinateInDeliveryRepository extends CrudRepository<CoordinateInDelivery, Long> {
	
	Long countByCoordinateDelivery_coordinateDeliveryId(Long id);

}
