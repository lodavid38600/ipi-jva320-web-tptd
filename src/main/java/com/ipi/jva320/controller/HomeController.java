package com.ipi.jva320.controller;

import com.ipi.jva320.exception.SalarieException;
import com.ipi.jva320.model.SalarieAideADomicile;
import com.ipi.jva320.service.SalarieAideADomicileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class HomeController {

    @Autowired
    private SalarieAideADomicileService salarieAideADomicileService;


    @GetMapping(value = "/")
    public String home(ModelMap model) {

        model.put("salarieCount", salarieAideADomicileService.countSalaries());
        return "home";
    }
    @PostMapping("/salaries/{id}/delete")
    public String deleteSalarie(@PathVariable("id") Long id) {
        try {
            // Logique de suppression du salarié avec l'identifiant donné
            salarieAideADomicileService.deleteSalarieAideADomicile(id);
        } catch (SalarieException e) {
            // Gérer l'exception ici (par exemple, afficher un message d'erreur ou effectuer une autre action appropriée)
            e.printStackTrace();
        }

        // Redirection vers une page appropriée après la suppression
        return "redirect:/salaries";
    }

    @GetMapping(value = "/salaries/{id}")
    public String salarie(ModelMap model, @PathVariable Long id) {
        model.put("salarie", salarieAideADomicileService.getSalarie(id));
        return "detail_Salarie";
    }

    @GetMapping(value = "/salaries/aide/new")
    public String newSalarie(ModelMap model) {
        return "detail_Salarie";
    }

    @PostMapping(value = "/salaries/save")
    public String createSalarie(SalarieAideADomicile salarie) throws SalarieException {
        salarieAideADomicileService.creerSalarieAideADomicile(salarie);
        return "redirect:/salaries/" + salarie.getId();
    }

    @PostMapping(value = "/salaries/{id}")
    public String updateSalarie(SalarieAideADomicile salarie) throws SalarieException {
        salarieAideADomicileService.updateSalarieAideADomicile(salarie);
        return "redirect:/salaries/" + salarie.getId();
    }

    @GetMapping("/salaries")
    public String getSalaries(@RequestParam(value = "page", defaultValue = "0") int page,
                              @RequestParam(value = "size", defaultValue = "10") int size,
                              @RequestParam(value = "sortDirection", defaultValue = "ASC") String sortDirection,
                              @RequestParam(value = "sortProperty", defaultValue = "nom") String sortProperty,
                              ModelMap model) {
        try {
            // Appeler la méthode de service avec les paramètres de pagination et de tri
            Page<SalarieAideADomicile> salariesPage = salarieAideADomicileService.getSalariesPage(page, size, sortDirection, sortProperty);

            // Récupérer la liste des salariés de la page courante
            List<SalarieAideADomicile> salaries = salariesPage.getContent();

            // Ajouter la liste à la model pour l'affichage
            model.put("salaries", salaries);

            // Ajouter les informations de pagination à la model pour l'affichage des boutons
            model.put("currentPage", salariesPage.getNumber());
            model.put("totalPages", salariesPage.getTotalPages());

            return "list";
        } catch (Exception e) {
            // Gérer les exceptions
            e.printStackTrace();
            return "error";
        }
    }

    @GetMapping("/salariés")
    public String getSalaries(@RequestParam("nom") String nom, ModelMap model) {
        try {
            // Décoder le paramètre "nom" à partir de l'URL encodée en UTF-8
            String decodedNom = URLDecoder.decode(nom, StandardCharsets.UTF_8.toString());
            System.out.println(decodedNom);

            // Récupérer la liste complète des salariés depuis le service
            List<SalarieAideADomicile> salaries = salarieAideADomicileService.getSalaries();

            // Filtrer la liste des salariés en fonction du nom recherché
            List<SalarieAideADomicile> salariesFiltres = salaries.stream()
                    .filter(salarie -> salarie.getNom().equalsIgnoreCase(decodedNom))
                    .collect(Collectors.toList());

            // Ajouter la liste filtrée à la model pour l'affichage
            model.put("salaries", salariesFiltres);

            return "list";
        } catch (UnsupportedEncodingException e) {
            // Gérer l'exception d'encodage incorrect
            e.printStackTrace();
            // Retourner une vue d'erreur appropriée
            return "error";
        }
    }
}
