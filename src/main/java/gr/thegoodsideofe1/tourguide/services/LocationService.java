package gr.thegoodsideofe1.tourguide.services;


import gr.thegoodsideofe1.tourguide.entities.Location;
import gr.thegoodsideofe1.tourguide.repositories.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class LocationService {
    @Autowired
    private LocationRepository locationRepository;
    public List<Location> listAllLocations(){
        return locationRepository.findAll();
    }
}
