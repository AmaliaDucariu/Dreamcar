package com.amalia.dreamcar.services;

import com.amalia.dreamcar.UtilizatoriRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UtilizatoriService {

    @Autowired
    private UtilizatoriRepository repository;
}
