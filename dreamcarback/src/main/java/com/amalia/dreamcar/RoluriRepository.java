package com.amalia.dreamcar;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoluriRepository extends CrudRepository<Roluri, Long> {

    long deleteByRol(Long rol);

}
