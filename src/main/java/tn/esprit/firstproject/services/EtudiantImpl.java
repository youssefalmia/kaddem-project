package tn.esprit.firstproject.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.firstproject.entities.Contrat;
import tn.esprit.firstproject.entities.Departement;
import tn.esprit.firstproject.entities.Equipe;
import tn.esprit.firstproject.entities.Etudiant;
import tn.esprit.firstproject.repositories.IContratRepository;
import tn.esprit.firstproject.repositories.IDepartementRepository;
import tn.esprit.firstproject.repositories.IEquipeRepository;
import tn.esprit.firstproject.repositories.IEtudiantRepository;

import java.util.*;

@RequiredArgsConstructor
@Service
@Slf4j
public class EtudiantImpl implements IEtudiantService {
    @Autowired
    private final IEtudiantRepository etudiantRepository;
    @Autowired
    private final IContratRepository contratRepository;
    @Autowired
    private final IEquipeRepository equipeRepository;

    private final ContratImpl contratServices;
    private final EquipeImpl equipeServices;
    @Autowired
    private final IDepartementRepository departementRepository;

    @Override
    public List<Etudiant> retrieveAllEtudiants() {
        log.debug("test");
        return (List<Etudiant>) etudiantRepository.findAll();
    }

//    @Scheduled(cron = "*/60 * * * * *")
//    void test() {
//        log.info("hello");
//    }

    @Override
    public Etudiant addEtudiant(Etudiant etudiant) {

        return etudiantRepository.save(etudiant);
    }

    @Override
    public Etudiant updateEtudiant(Etudiant e) {
        return etudiantRepository.save(e);
    }

    @Override
    public Etudiant retrieveEtudiant(Integer idEtudiant) {
        return etudiantRepository.findById(idEtudiant).orElse(null);
    }

    @Override
    public void removeEtudiant(Integer idEtudiant) {
        etudiantRepository.deleteById(idEtudiant);
    }

    @Override
    public List<Etudiant> findEtudiantByDepartement(String nomDep) {
        return etudiantRepository.findEtudiantByDepartement(nomDep);
    }

    @Override
    public Etudiant getEtudiantById(Integer idEtudiant) {
        return etudiantRepository.findById(idEtudiant).orElse(null);
    }


    @Override
    public List<Equipe> getTeamsByStudent(Integer id){
        return etudiantRepository.retrieveEquipeByEtudiant(id);
    }

    @Override
    public Etudiant assignToTeams(Integer idE, List<Integer> equipesIds){

        Etudiant e= this.getEtudiantById(idE);

        Set<Equipe> equipeSet = new HashSet<>();
        Set<Etudiant> etudiantSet = new HashSet<>();
        etudiantSet.add(e);
        for (Integer id: equipesIds){
            Equipe equipe= equipeServices.retrieveEquipe(id);
            equipe.setEtudiants(etudiantSet);
            equipeSet.add(equipe);
            equipeServices.updateEquipe(equipe);
        }
        e.setEquipes(equipeSet);

        etudiantRepository.save(e);
        return e;
    }

    public Etudiant addAndAssignEtudiantToEquipeAndContract(Etudiant e, Integer idContrat, Integer idEquipe) {

        Contrat ce = contratServices.retrieveContrat(idContrat);
        Equipe eq = equipeServices.retrieveEquipe(idEquipe);

        //Make an equie and contract set
        Set<Equipe> equipeSet = new HashSet<>();
        equipeSet.add(eq);

        Set<Contrat> contratSet = new HashSet<>();
        contratSet.add(ce);

        // Update the etudiant using its setters
        e.setEquipes(equipeSet);
        e.setContrats(contratSet);

        // Update the Contract
        ce.setEtudiant(e);

        Set<Etudiant> etudiantSet = new HashSet<>();
        etudiantSet.add(e);
        // update the equipe
        eq.setEtudiants(etudiantSet);

        // Start by saving the Etudiant
        etudiantRepository.save(e);
        contratServices.updateContrat(ce);
        equipeServices.updateEquipe(eq);
        return e;
    }


    @Override
    public List<Etudiant> getEtudiantsByDepartement(Integer idDep) {

        Departement dep = departementRepository.findById(idDep).orElse(null);

        return etudiantRepository.getEtudiantsByDepartement(dep.getIdDepart());
    }
}
