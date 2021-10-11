package br.com.greenmile.desafio.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.greenmile.desafio.entity.model.CoordinateIncoming;

@Repository
public interface CoordinateIncomingRepository extends CrudRepository<CoordinateIncoming, Long>{

}
