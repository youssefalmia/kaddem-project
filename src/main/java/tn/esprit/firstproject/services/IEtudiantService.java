package tn.esprit.firstproject.services;

import org.springframework.stereotype.Service;
import tn.esprit.firstproject.entities.Departement;
import tn.esprit.firstproject.entities.Equipe;
import tn.esprit.firstproject.entities.Etudiant;

import java.util.List;
@Service
public interface IEtudiantService {
    List<Etudiant> retrieveAllEtudiants() ;
    Etudiant addEtudiant(Etudiant etudiant) ;

    Etudiant updateEtudiant(Etudiant e) ;
    Etudiant retrieveEtudiant(Integer idEtudiant) ;
    void removeEtudiant(Integer idEtudiant) ;
    List<Etudiant> findEtudiantByDepartement(String nomDep);

    Etudiant getEtudiantById(Integer idEtudiant);

    List<Equipe> getTeamsByStudent(Integer id);

    Etudiant assignToTeams(Integer idE, List<Integer> equipesIds);

    Etudiant addAndAssignEtudiantToEquipeAndContract(Etudiant e, Integer idContrat, Integer
            idEquipe);


    List<Etudiant> getEtudiantsByDepartement(Integer idDep);
}
