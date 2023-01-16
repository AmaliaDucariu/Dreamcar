package com.amalia.dreamcar.services;

import com.amalia.dreamcar.SolicitariRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SolicitariService {

    @Autowired
    private SolicitariRepository repository;
}
