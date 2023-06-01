package com.ipi.jva320.service;

import com.ipi.jva320.model.SalarieAideADomicile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

/**
 * Ajoute des données de test si vide au démarrage
 */
@Component
public class DataInitService implements CommandLineRunner {

    @Autowired
    private SalarieAideADomicileService salarieAideADomicileService;

    @Override
    public void run(String... strings) throws Exception {
        if (this.salarieAideADomicileService.countSalaries() != 0) {
            return;
        }

        SalarieAideADomicile s1 = this.salarieAideADomicileService.creerSalarieAideADomicile(
                new SalarieAideADomicile("Jean", LocalDate.now(), LocalDate.now(),
                20, 0,
                80, 5, 1));
        SalarieAideADomicile s2 = this.salarieAideADomicileService.creerSalarieAideADomicile(
                new SalarieAideADomicile("Jeanne", LocalDate.parse("2022-12-05"), LocalDate.parse("2022-12-05"),
                        20, 0,
                        80, 10, 1));
        SalarieAideADomicile s3 = this.salarieAideADomicileService.creerSalarieAideADomicile(
                new SalarieAideADomicile("Jeannot", LocalDate.parse("2022-12-05"), LocalDate.parse("2022-12-05"),
                        20, 0,
                        80, 10, 1));
        SalarieAideADomicile s4 = this.salarieAideADomicileService.creerSalarieAideADomicile(
                new SalarieAideADomicile("Test", LocalDate.parse("2022-12-05"), LocalDate.parse("2022-12-05"),
                        20, 0,
                        80, 10, 1));
    }
}
