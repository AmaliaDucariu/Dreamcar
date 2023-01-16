package com.amalia.dreamcar;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OferteRepository extends CrudRepository<Oferte, Long> {

    long deleteByIdOferta(Long idOferta);

    long deleteAllBySolicitare(Long solicitare);

    List<Oferte> getAllBySolicitare(long solicitare);
}
