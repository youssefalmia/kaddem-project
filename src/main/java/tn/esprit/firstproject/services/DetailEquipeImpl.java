package tn.esprit.firstproject.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.firstproject.entities.DetailEquipe;
import tn.esprit.firstproject.repositories.IDetailEquipeRepository;

@Service
public class DetailEquipeImpl implements IDetailEquipeService{
    @Autowired
    IDetailEquipeRepository detailEquipeRepository ;
    @Override
    public DetailEquipe addDetailEquipe(DetailEquipe detailEquipe) {
        return detailEquipeRepository.save(detailEquipe);
    }

    @Override
    public DetailEquipe updateDetailEquipe(DetailEquipe detailEquipe) {return detailEquipeRepository.save(detailEquipe);}

    @Override
    public void removeDetailEquipe(Integer idDetailEquipe) {
        detailEquipeRepository.deleteById(idDetailEquipe);
    }
}
