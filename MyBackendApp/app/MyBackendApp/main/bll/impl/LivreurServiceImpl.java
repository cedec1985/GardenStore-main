package app.MyBackendApp.main.bll.impl;

import app.MyBackendApp.main.dal.LivreurRepository;
import app.MyBackendApp.main.domain.entities.Livreur;
import jakarta.transaction.Transactional;
import java.util.List;
//import app.MyBackendApp.main.bll.services.LivreurService;
import org.springframework.stereotype.Service;


@Service
@Transactional
public class LivreurServiceImpl implements LivreurService {

    private final LivreurRepository livreurRepository;

    public LivreurServiceImpl(LivreurRepository livreurRepository) {
        this.livreurRepository = livreurRepository;
    }
    @Override
    public Livreur getOne(Long id) {
        return livreurRepository.findById(id).orElseThrow(()->new RuntimeException("aucun livreur trouvé avec cet ID"));
    }
    @Override
    public List<Livreur> getAll(Livreur livreur) {
        return livreurRepository.findAll(livreur);
    }
    @Override
    public Livreur update(Long id, Livreur livreur) {
        livreur.setId(id);
        return livreurRepository.save(livreur);
    }
    @Override
    public boolean delete(Long id) {
        Livreur livreur = getOne(id);
        livreurRepository.delete(livreur);
        return true;
    }
    @Override
    public Livreur add(Livreur livreur) {
        return livreurRepository.save(livreur);
    }
}
