package tn.esprit.firstproject.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.*;
import org.springframework.stereotype.Service;
import tn.esprit.firstproject.entities.Contrat;
import tn.esprit.firstproject.entities.Etudiant;
import tn.esprit.firstproject.enums.Specialite;
import tn.esprit.firstproject.repositories.IContratRepository;
import tn.esprit.firstproject.repositories.IEtudiantRepository;

import java.text.*;
import java.time.*;
import java.util.*;
import java.util.concurrent.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class ContratImpl implements IContratService {
    @Autowired
    private final IContratRepository contratRepository;
    @Autowired
    private final IEtudiantRepository etudiantRepository;

    @Override
    public List<Contrat> retrieveAllContrats() {
        return (List<Contrat>) contratRepository.findAll();
    }

    @Override
    public Contrat updateContrat(Contrat ce) {
        return contratRepository.save(ce);
    }

    @Override
    public Contrat addContrat(Contrat ce) {
        return contratRepository.save(ce);
    }

    @Override
    public Contrat retrieveContrat(Integer idContrat) {
        return contratRepository.findById(idContrat).orElse(null);
    }

    @Override
    public void removeContrat(Integer idContrat) {
        contratRepository.deleteById(idContrat);
    }

    @Override
    public Integer nbContratsValides(Date startDate, Date endDate) {
        // todo page 20
        return contratRepository.nbContratsValides(startDate, endDate);
    }

    @Override
    public Contrat affectContratToEtudiant(Contrat ce, String nomE, String prenomE) throws Exception {
        Etudiant e = etudiantRepository.findEtudiantByPrenomEtNom(prenomE, nomE).get(0);

        Set<Contrat> contratSet = new HashSet<>();
        contratSet.add(ce);
        e.setContrats(contratSet);

        // TODO PAGE 16
        // Add a method to get how much contract the student have
        if (contratRepository.nbContratsEtudiant(e.getIdEtudiant()) >= 5) {
            throw new Exception("Must have less than 5 contracts");
        }

        ce.setEtudiant(e);
        contratRepository.save(ce);
        etudiantRepository.save(e);
        return ce;
    }

    @Override
    public float getChiffreAffaireEntreDeuxDate(Date startDate, Date endDate) {
        // TODO page 19
        List<Contrat> listContrat = contratRepository.contratBetween2dates(startDate, endDate);
        System.out.println(listContrat);
        float chiffre = 0;
        for (Contrat c : listContrat) {
            if (c.getSpecialite().equals(Specialite.IA)) {
                chiffre = chiffre + 300;
            } else if (c.getSpecialite().equals(Specialite.RESEAUX)) {
                chiffre = chiffre + 350;
            } else if (c.getSpecialite().equals(Specialite.CLOUD)) {
                chiffre = chiffre + 400;
            } else if (c.getSpecialite().equals(Specialite.SECURITE)) {
                chiffre = chiffre + 450;
            }
        }
        return chiffre;
    }

    @Override
    public List<Contrat> contratBetween2dates(Date startDate, Date endDate) {
        return contratRepository.contratBetween2dates(startDate, endDate);
    }

    /**
     *
     */
    @Override
    //@Scheduled(cron = "*/60 * * * * *")
    @Scheduled(cron = "0 0 */13 * * *")
    public void retrieveAndUpdateStatusContrat() {
    // todo page 21
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());

        Date today1 = new Date();

        List<Contrat> contratList = retrieveAllContrats();
        for (Contrat contrat : contratList) {
            long finCont = contrat.getDateFinContrat().getTime();

            long timeDiff = Math.abs(finCont - today1.getTime());

            long daysDiff = TimeUnit.DAYS.convert(timeDiff, TimeUnit.MILLISECONDS);
            if( daysDiff <= 15 ){
                log.warn("Le contract :"+contrat.getIdContrat()+"associer à la date de fin: "
                        +contrat.getDateFinContrat()+" ayant la spécialité "+contrat.getSpecialite()
                        +" pour l'étudiant: "+contrat.getEtudiant().getPrenom()+" "+contrat.getEtudiant().getPrenom()
                        +" vas expiré dans: "+daysDiff);
                if(daysDiff == 0){
                    contrat.setArchive(true);
                    updateContrat(contrat);
                }
            }
            log.info("days between today and contract avec mont" + contrat.getMontantContrat() + " est " + daysDiff);
        }

    }
}
