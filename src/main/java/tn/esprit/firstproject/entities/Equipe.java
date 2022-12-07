package tn.esprit.firstproject.entities;

import lombok.Getter;
import lombok.Setter;
import tn.esprit.firstproject.enums.Niveau;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Getter
@Setter
public class Equipe  implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idEquipe ;
    private String nomEquipe ;

    @Enumerated(EnumType.STRING)
    private Niveau niveau ;

    @ManyToMany(mappedBy = "equipes", cascade = CascadeType.ALL)
    private Set<Etudiant> etudiants ;

    @OneToOne
    private DetailEquipe detailEquipe ;
}
