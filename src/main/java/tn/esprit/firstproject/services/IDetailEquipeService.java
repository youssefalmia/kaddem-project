package tn.esprit.firstproject.services;

import org.springframework.stereotype.Service;
import tn.esprit.firstproject.entities.DetailEquipe;

@Service
public interface IDetailEquipeService {
    DetailEquipe addDetailEquipe(DetailEquipe detailEquipe) ;

    DetailEquipe updateDetailEquipe(DetailEquipe detailEquipe);

    void removeDetailEquipe(Integer idDetailEquipe) ;
}
