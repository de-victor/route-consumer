package br.com.greenmile.desafio.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import br.com.greenmile.desafio.entity.enums.CoordinateDeliveryStatusEnum;
import br.com.greenmile.desafio.entity.enums.RoutePlanStatusEnum;
import br.com.greenmile.desafio.entity.model.RoutePlan;

@Repository
public interface RoutePlanRepository extends JpaRepository<RoutePlan, Long>, JpaSpecificationExecutor<RoutePlan> {
	
	List<RoutePlan> findDistinctByPlannedStops_Status(CoordinateDeliveryStatusEnum plannedStopStatus);

	RoutePlan findByRoutePlanIdAndStatusIn(Long routePlanId, List<RoutePlanStatusEnum> status);


}
