package tn.esprit.firstproject.services;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.firstproject.entities.Contrat;
import tn.esprit.firstproject.entities.Etudiant;
import tn.esprit.firstproject.enums.Specialite;
import tn.esprit.firstproject.repositories.IContratRepository;
import tn.esprit.firstproject.repositories.IEtudiantRepository;

import java.util.*;

@Service
@RequiredArgsConstructor
public class ContratImpl implements  IContratService {
    @Autowired
   private final IContratRepository contratRepository ;
    @Autowired
   private final IEtudiantRepository etudiantRepository ;

    @Override
    public List<Contrat> retrieveAllContrats() {
        return (List<Contrat>) contratRepository.findAll();
    }

    @Override
    public Contrat updateContrat(Contrat ce) {
        return contratRepository.save(ce) ;
    }

    @Override
    public Contrat addContrat(Contrat ce) {
        return contratRepository.save(ce) ;
    }

    @Override
    public Contrat retrieveContrat(Integer idContrat) {
        return contratRepository.findById(idContrat).orElse(null) ;
    }

    @Override
    public void removeContrat(Integer idContrat) {
        contratRepository.deleteById(idContrat);
    }

    @Override
    public Integer nbContratsValides(Date startDate, Date endDate) {
        // todo page 20
        return contratRepository.nbContratsValides(startDate,endDate);
    }

    @Override
    public Contrat affectContratToEtudiant(Contrat ce, String nomE, String prenomE) throws Exception {
        Etudiant e = etudiantRepository.findEtudiantByPrenomEtNom(prenomE,nomE).get(0);

        Set<Contrat> contratSet = new HashSet<>();
        contratSet.add(ce);
        e.setContrats(contratSet);

        // TODO PAGE 16
        // Add a method to get how much contract the student have
        if(contratRepository.nbContratsEtudiant(e.getIdEtudiant()) >= 5){
            throw new Exception("Must have less than 5 contracts");
        }


        ce.setEtudiant(e);
        contratRepository.save(ce);
        etudiantRepository.save(e);
        return ce ;
    }
    @Override
    public float getChiffreAffaireEntreDeuxDate(Date startDate, Date endDate) {
        // TODO page 19
        List<Contrat> listContrat=contratRepository.contratBetween2dates(startDate,endDate);
        System.out.println(listContrat);
        float chiffre=0;
        for( Contrat c:listContrat){
            if(c.getSpecialite().equals(Specialite.IA)){
                chiffre=chiffre+300;
            }
            else if (c.getSpecialite().equals(Specialite.RESEAUX)){
                chiffre=chiffre+350;
            }
            else if(c.getSpecialite().equals(Specialite.CLOUD)){
                chiffre=chiffre+400;
            }
            else if (c.getSpecialite().equals(Specialite. SECURITE)){
                chiffre=chiffre+450;
            }
        }
        return chiffre;
    }

    @Override
    public List<Contrat> contratBetween2dates(Date startDate, Date endDate) {
        return  contratRepository.contratBetween2dates(startDate,endDate);
    }
}
