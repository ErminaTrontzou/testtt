package gr.thegoodsideofe1.tourguide.services;

import gr.thegoodsideofe1.tourguide.entities.LocationImage;
import gr.thegoodsideofe1.tourguide.repositories.LocationImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class LocationImageService {
    @Autowired
    private LocationImageRepository locationImageRepository;
    public List<LocationImage> listAllLocationImages(){
        return locationImageRepository.findAll();
    }
}
