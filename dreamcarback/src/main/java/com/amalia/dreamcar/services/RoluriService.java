package com.amalia.dreamcar.services;

import com.amalia.dreamcar.RoluriRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoluriService {

    @Autowired
    private RoluriRepository repository;
}
