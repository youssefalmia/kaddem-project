package tn.esprit.firstproject.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.firstproject.entities.DetailEquipe;

@Repository
public interface IDetailEquipeRepository extends CrudRepository<DetailEquipe,Integer> {
}
