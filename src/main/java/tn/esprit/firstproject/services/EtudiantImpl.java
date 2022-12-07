package tn.esprit.firstproject.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import tn.esprit.firstproject.entities.Contrat;
import tn.esprit.firstproject.entities.Departement;
import tn.esprit.firstproject.entities.Equipe;
import tn.esprit.firstproject.entities.Etudiant;
import tn.esprit.firstproject.repositories.IContratRepository;
import tn.esprit.firstproject.repositories.IDepartementRepository;
import tn.esprit.firstproject.repositories.IEquipeRepository;
import tn.esprit.firstproject.repositories.IEtudiantRepository;

import java.util.List;
@RequiredArgsConstructor
@Service
@Slf4j
public class EtudiantImpl implements IEtudiantService{
    @Autowired
    private final IEtudiantRepository etudiantRepository ;
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
@Scheduled(cron="*/60 * * * * *")
void test() {
        log.info("hello");
}
    @Override
    public Etudiant addEtudiant(Etudiant etudiant) {

        return etudiantRepository.save(etudiant) ;
    }

    @Override
    public Etudiant updateEtudiant(Etudiant e) {
        return etudiantRepository.save(e) ;
    }

    @Override
    public Etudiant retrieveEtudiant(Integer idEtudiant) {
        return etudiantRepository.findById(idEtudiant).orElse(null);
    }

    @Override
    public void removeEtudiant(Integer idEtudiant) {
        etudiantRepository.deleteById(idEtudiant) ;
    }

    @Override
    public List<Etudiant> findEtudiantByDepartement(String nomDep) {
        return etudiantRepository.findEtudiantByDepartement(nomDep);
    }

    @Override
    public Etudiant getEtudiantById(Integer idEtudiant) {
        return etudiantRepository.findById(idEtudiant).orElse(null);
    }
   public Etudiant addAndAssignEtudiantToEquipeAndContract(Etudiant e, Integer idContrat, Integer
            idEquipe)
   {

       Contrat ce =  contratServices.retrieveContrat(idContrat);
       Equipe eq = equipeServices.retrieveEquipe(idEquipe);
       //ce.setEtudiant(e);
       //contratRepository.save(ce);
       ce.setEtudiant(e);
       contratServices.updateContrat(ce);
       eq.getEtudiants().add(e);
       //e.getEquipes().add(eq);
       //e.getContrats().add(ce);

       equipeServices.updateEquipe(eq);
       etudiantRepository.save(e) ;
return e;
   }



    @Override
    public List<Etudiant> getEtudiantsByDepartement(Integer idDep) {

        Departement dep = departementRepository.findById(idDep).orElse(null) ;

        return etudiantRepository.getEtudiantsByDepartement(dep.getIdDepart());
    }
}
