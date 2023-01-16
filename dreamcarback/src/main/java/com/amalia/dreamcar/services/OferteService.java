package com.amalia.dreamcar.services;

import com.amalia.dreamcar.OferteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OferteService {

    @Autowired
    private OferteRepository repository;
}
