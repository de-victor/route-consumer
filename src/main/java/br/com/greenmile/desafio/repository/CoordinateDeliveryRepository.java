package br.com.greenmile.desafio.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.greenmile.desafio.entity.enums.CoordinateDeliveryStatusEnum;
import br.com.greenmile.desafio.entity.model.CoordinateDelivery;

@Repository
public interface CoordinateDeliveryRepository extends CrudRepository<CoordinateDelivery, Long>, JpaSpecificationExecutor<CoordinateDelivery>{
	
	List<CoordinateDelivery> findByStatus(CoordinateDeliveryStatusEnum status);

}
