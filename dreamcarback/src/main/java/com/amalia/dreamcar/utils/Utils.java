package com.amalia.dreamcar.utils;

import com.amalia.dreamcar.OferteRepository;
import com.amalia.dreamcar.Solicitari;
import com.amalia.dreamcar.SolicitariRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class Utils {
    @Autowired
    private OferteRepository oferteRepository;

    @Autowired
    private SolicitariRepository solicitariRepository;

    @Transactional
    public void deleteSolicitareAndOferte(Long solicitare) {
        oferteRepository.deleteAllBySolicitare(solicitare);
        solicitariRepository.deleteBySolicitare(solicitare);
    };

}
