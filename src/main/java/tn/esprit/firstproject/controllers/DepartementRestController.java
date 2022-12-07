package tn.esprit.firstproject.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.*;
import tn.esprit.firstproject.entities.Departement;
import tn.esprit.firstproject.entities.Universite;
import tn.esprit.firstproject.services.IDepartementService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/dep")
public class DepartementRestController {
    private final IDepartementService iDepartementService;
    @GetMapping("/get/{id-dep}")

    public Departement getById(@PathVariable("id-dep") Integer id){
        return iDepartementService.retrieveDepartement(id);
    }
    @PostMapping("/add")
    public Departement addDepart(@RequestBody Departement dep){
        return  iDepartementService.addDepartement(dep);
    }
    @GetMapping("/all")
    @ResponseBody
    public List<Departement> getDepart() {
        return iDepartementService.retrieveAllDepartements();
    }
    @PutMapping("/update")
    public Departement Departementupdate(@RequestBody Departement departement){
        return iDepartementService.updateDepartement(departement);
    }

    @PutMapping("/etudiantToDep/{idDep}/{idEt}")
    public Departement assignEtudiantToDepartement(@PathVariable("idDep") Integer idDep, @PathVariable("idEt") Integer idEt){
        return iDepartementService.affecterEtudiant(idDep,idEt);

    }

}
