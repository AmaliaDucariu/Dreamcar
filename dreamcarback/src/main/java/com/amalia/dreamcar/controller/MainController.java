package com.amalia.dreamcar.controller;

import com.amalia.dreamcar.*;
import com.amalia.dreamcar.email.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Controller
public class MainController {

    @Autowired
    private EmailService emailService;

    @Autowired
    private UtilizatoriRepository utilizatoriRepository;

    @Autowired
    private OferteRepository oferteRepository;

    @Autowired
    private SolicitariRepository solicitariRepository;

    @Autowired
    private RoluriRepository roluriRepository;

    // Get all entries

    @GetMapping(path="/allUtilizatori")
    public @ResponseBody Iterable<Utilizatori> getAllUtilizatori() {
        return utilizatoriRepository.findAll();
    }

    @GetMapping(path="/allOferte")
    public @ResponseBody Iterable<Oferte> getAllOferte() {
        return oferteRepository.findAll();
    }

    @GetMapping(path="/allSolicitari")
    public @ResponseBody Iterable<Solicitari> getAllSolicitari() {
        return solicitariRepository.findAll();
    }

    @GetMapping(path="/allRoluri")
    public @ResponseBody Iterable<Roluri> getAllRoluri() {
        return roluriRepository.findAll();
    }

    // Delete entry by Id

    @DeleteMapping(path="/allUtilizatori/{username}")
    @Transactional
    public @ResponseBody HttpStatus deleteByUsername(@PathVariable("username") String username) {
        Long result = utilizatoriRepository.deleteByUsername(username);

        return result == 1 ? HttpStatus.OK : HttpStatus.BAD_REQUEST;
    }

    @DeleteMapping(path="/allOferte/{idOferta}")
    @Transactional
    public @ResponseBody HttpStatus deleteById(@PathVariable("idOferta") Long idOferta) {
        Long result = oferteRepository.deleteByIdOferta(idOferta);

        return result == 1 ? HttpStatus.OK : HttpStatus.BAD_REQUEST;
    }

    @DeleteMapping(path="/allSolicitari/{solicitare}")
    @Transactional
    public @ResponseBody HttpStatus deleteBySolicitare(@PathVariable("solicitare") Long solicitare) {
        List<Oferte> oferteCorespondente = oferteRepository.getAllBySolicitare(solicitare);

        oferteCorespondente.forEach(oferta -> {
            final Utilizatori utilizator = utilizatoriRepository.findByUsername(oferta.getUsername());
            emailService.sendSimpleMessage(utilizator.getEmail(), "Request Deleted", "The Request for which you proposed an offer was deleted.");
        });

        long result = solicitariRepository.deleteBySolicitare(solicitare);
        long oferte = oferteRepository.deleteAllBySolicitare(solicitare);

        return (result == 1 && oferte == 1) ? HttpStatus.OK : HttpStatus.BAD_REQUEST;
    }

    @DeleteMapping(path="/allRoluri/{rol}")
    @Transactional
    public @ResponseBody HttpStatus deleteByRol(@PathVariable("rol") Long rol) {
        Long result = roluriRepository.deleteByRol(rol);

        return result == 1 ? HttpStatus.OK : HttpStatus.BAD_REQUEST;
    }

    // Create entries

    @PostMapping(path="/allUtilizatori")
    @Transactional
    public @ResponseBody HttpStatus createUtilizator(@RequestBody Utilizatori utilizator) {
        utilizatoriRepository.save(utilizator);
        return HttpStatus.CREATED;
    }

    @PostMapping(path="/allOferte")
    @Transactional
    public @ResponseBody HttpStatus createOferta(@RequestBody Oferte oferta) {
        Solicitari solicitare = solicitariRepository.findById(oferta.getSolicitare()).orElse(null);

        if (solicitare != null && oferta.getPret() < solicitare.getPretTinta() && Objects.equals(oferta.getNrBucati(), solicitare.getNrBucati())) {
            Utilizatori utilizator = utilizatoriRepository.findByUsername(oferta.getUsername());

            emailService.sendSimpleMessage(utilizator.getEmail(), "Winner", "Congratulations!");

            solicitariRepository.deleteBySolicitare(solicitare.getSolicitare());
            oferteRepository.deleteAllBySolicitare(solicitare.getSolicitare());

            return HttpStatus.CREATED;
        } else {
            oferteRepository.save(oferta);
            return HttpStatus.CREATED;
        }

    }

    @PostMapping(path="/allSolicitari")
    @Transactional
    public @ResponseBody HttpStatus createSolicitare(@RequestBody Solicitari solicitare) {
        solicitariRepository.save(solicitare);
        return HttpStatus.CREATED;
    }

    @PostMapping(path="/allRoluri")
    @Transactional
    public @ResponseBody HttpStatus createRol(@RequestBody Roluri rol) {
        roluriRepository.save(rol);
        return HttpStatus.CREATED;
    }

    // Update entries
    @Transactional
    @PutMapping(path="/allOferte/{idOferta}")
    public @ResponseBody HttpStatus updateOferta(@PathVariable Long idOferta,@RequestBody Oferte oferta) {
        Oferte updateOferta = oferteRepository.findById(idOferta).orElse(null);

        if (updateOferta != null) {
            Solicitari solicitare = solicitariRepository.findById(oferta.getSolicitare()).orElse(null);

            if (solicitare != null && oferta.getPret() < solicitare.getPretTinta() && Objects.equals(oferta.getNrBucati(), solicitare.getNrBucati())) {
                Utilizatori utilizator = utilizatoriRepository.findByUsername(oferta.getUsername());

                emailService.sendSimpleMessage(utilizator.getEmail(), "Winner", "Congratulations!");
                oferteRepository.deleteAllBySolicitare(solicitare.getSolicitare());
                solicitariRepository.deleteBySolicitare(solicitare.getSolicitare());

                return HttpStatus.OK;
            } else {
                updateOferta.setNrBucati(oferta.getNrBucati());
                updateOferta.setPret(oferta.getPret());

                oferteRepository.save(updateOferta);

                return HttpStatus.OK;
            }
        }

        return HttpStatus.BAD_REQUEST;
    }

    @PutMapping(path="/allSolicitari/{solicitare}")
    public @ResponseBody HttpStatus updateLinie(@PathVariable Long solicitare,@RequestBody Solicitari solicitareNew) {
        Solicitari updateSolicitare = solicitariRepository.findById(solicitare).orElse(null);

        if (updateSolicitare != null) {
            updateSolicitare.setPretTinta(solicitareNew.getPretTinta());
            updateSolicitare.setTimeout(solicitareNew.getTimeout());
            updateSolicitare.setTimeoutUom(solicitareNew.getTimeoutUom());
            updateSolicitare.setDescriere(solicitareNew.getDescriere());

            solicitariRepository.save(updateSolicitare);

            return HttpStatus.OK;
        }

        return HttpStatus.BAD_REQUEST;
    }
}
