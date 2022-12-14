package tn.esprit.firstproject.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import tn.esprit.firstproject.enums.Option;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Getter
@Setter
public class Etudiant implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idEtudiant ;
    private String prenom ;
    private String nom ;
    @Enumerated(EnumType.STRING)
    private Option optionEtudiant ;

    @ManyToOne(cascade = CascadeType.REMOVE)
    Departement departement;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "etudiant")
    private Set<Contrat> contrats ;

    @JsonIgnore
    @ManyToMany(cascade = CascadeType.REMOVE)
    private Set<Equipe> equipes ;
}
