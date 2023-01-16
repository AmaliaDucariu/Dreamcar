package com.amalia.dreamcar;

import com.amalia.dreamcar.email.EmailService;
import com.amalia.dreamcar.utils.Utils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import java.util.*;
import java.util.concurrent.TimeUnit;

@SpringBootApplication
public class MySqlApp {
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(MySqlApp.class, args);

        SolicitariRepository solicitariRepository = context.getBean(SolicitariRepository.class);
        OferteRepository oferteRepository = context.getBean(OferteRepository.class);
        UtilizatoriRepository utilizatoriRepository = context.getBean(UtilizatoriRepository.class);
        EmailService emailService = context.getBean(EmailService.class);
        Utils utils = context.getBean(Utils.class);

        Runnable checkExpDate = () -> {
            try {
                while (true) {
                    // get all solicitari
                    Iterable<Solicitari> allSolicitari = solicitariRepository.findAll();
                    List<Solicitari> solicitariExp = new ArrayList<>();

                    // TODAY For general use
                    //Date today = new Date();

                    // find all solicitari care au expirat
                    allSolicitari.forEach(solicitare -> {
                        // TODAY For testing Purposes
                        Date today = new Date(solicitare.getDataLansare().getTime() + solicitare.getTimeout() * (1000 * 60 * 60 * 24));

                        Date expDate = new Date(solicitare.getDataLansare().getTime() + solicitare.getTimeout() * (1000 * 60 * 60 * 24));

                        if (expDate.compareTo(today) == 0) {
                            solicitariExp.add(solicitare);
                        }
                    });

                    if (!solicitariExp.isEmpty()) {
                        solicitariExp.forEach(solicitare -> {
                            // find all oferte corespunzatoare solicitarii
                            final List<Oferte> oferte = oferteRepository.getAllBySolicitare(solicitare.getSolicitare());
                            final Oferte ofertaCastigatoare = oferte.stream().filter(oferta -> Float.compare(oferta.getNrBucati(), solicitare.getNrBucati()) == 0).min(Comparator.comparingDouble(Oferte::getPret)).orElse(null);

                            // find user email pentru instiintare
                            if (ofertaCastigatoare != null) {
                                Utilizatori utilizator = utilizatoriRepository.findByUsername(ofertaCastigatoare.getUsername());
                                emailService.sendSimpleMessage(utilizator.getEmail(), "Winner!", "Congratulations");
                            }

                            // delete solicitare si ofertele corespondente
                            utils.deleteSolicitareAndOferte(solicitare.getSolicitare());
                        });
                    }

                    TimeUnit.MINUTES.sleep(15);
                }
            } catch (InterruptedException err) {
                err.printStackTrace();
            }
        };

        Thread thread = new Thread(checkExpDate);
        thread.start();
    }
}
