package tn.esprit.firstproject.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Getter
@Setter
public class Departement  implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idDepart ;
    private String nomDepart ;

    //@JsonIgnore
    @ManyToOne
    @Getter(AccessLevel.NONE)
    private Universite universite;

    public Universite getUniversite(){
        if (universite != null){
            Universite uni= universite;
            uni.setDepartements(null);
            return uni;
        }
        return new Universite();
    }

    @JsonIgnore
    @OneToMany(cascade = CascadeType.MERGE, mappedBy = "departement")
    private Set<Etudiant> etudiants ;
}
