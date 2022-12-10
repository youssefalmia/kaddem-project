package tn.esprit.firstproject.services;

import lombok.extern.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.*;
import org.springframework.stereotype.Service;
import tn.esprit.firstproject.entities.*;
import tn.esprit.firstproject.enums.*;
import tn.esprit.firstproject.repositories.IEquipeRepository;

import java.util.*;
import java.util.concurrent.*;

@Service
@Slf4j
public class EquipeImpl implements IEquipeService {
    @Autowired
    IEquipeRepository equipeRepository;

    @Override
    public List<Equipe> retrieveAllEquipes() {
        return (List<Equipe>) equipeRepository.findAll();
    }

    @Override
    public Equipe addEquipe(Equipe equipe) {

        return equipeRepository.save(equipe);
    }

    @Override
    public Equipe updateEquipe(Equipe e) {
        return equipeRepository.save(e);
    }

    @Override
    public Equipe retrieveEquipe(Integer idEquipe) {
        return equipeRepository.findById(idEquipe).get();
    }

    public Boolean isStudentContractMoreThanOneYear(Etudiant etudiant){
        // test si les étudiant de l'équipes ( comparer la date début du contract avec la date actuelle)
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        Date today1 = new Date();

        for (Contrat contrat : etudiant.getContrats()) {
            long debCont = contrat.getDateFinContrat().getTime();
            long timeDiff = Math.abs(debCont - today1.getTime());
            long daysDiff = TimeUnit.DAYS.convert(timeDiff, TimeUnit.MILLISECONDS);
            if(daysDiff >= 365){
                return true;
            }
        }
        return false;
    }
    @Scheduled(cron = "0 0 13 * * *")
    public void faireEvoluerEquipes(){
        // Todo page 22
        List<Equipe> equipes = retrieveAllEquipes();

        for (Equipe equipe : equipes) {
            if(equipe.getEtudiants().size() >= 3 && (equipe.getNiveau() == Niveau.JUNIOR || equipe.getNiveau() == Niveau.SENIOR) ){
                if(equipe.getEtudiants().stream().allMatch(this::isStudentContractMoreThanOneYear))
                {
                  log.info("l'equipes a 3 membres ou plus ayant dépassé 1 an avec contrat");
                  if(equipe.getNiveau() == Niveau.JUNIOR){
                      equipe.setNiveau(Niveau.SENIOR);
                  } else if (equipe.getNiveau()== Niveau.SENIOR) {
                      equipe.setNiveau(Niveau.EXPERT);
                  }
                }
            }else {
                log.info("l'equipe"+equipe.getNomEquipe()+" ne respecte pas la condition");
            }
        }

    }
}
