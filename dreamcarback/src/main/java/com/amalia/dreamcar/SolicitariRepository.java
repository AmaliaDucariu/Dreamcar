package com.amalia.dreamcar;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SolicitariRepository extends CrudRepository<Solicitari, Long> {
    long deleteBySolicitare(Long solicitare);
}
