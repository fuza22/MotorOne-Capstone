package it.epicode.Capstone.service;

import it.epicode.Capstone.exception.NotFoundException;
import it.epicode.Capstone.model.entities.Drivers;
import it.epicode.Capstone.model.entities.User;
import it.epicode.Capstone.model.request.DriversRequest;
import it.epicode.Capstone.repository.DriversRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class DriversService {

    @Autowired
    private DriversRepository driversRepository;

    @Autowired
    private UserService userService;



    public Page<Drivers> getAll(Pageable pageable){

        return driversRepository.findAll(pageable);

    }

    public Drivers getDriverById(int id) throws NotFoundException {
        return driversRepository.findById(id).orElseThrow(()->new NotFoundException("Pilota con id = " + id + " non trovato"));
    }


    public Drivers saveDriver(DriversRequest driversRequest){
        Drivers d = new Drivers();
        d.setDriver_number(driversRequest.getDriver_number());
        d.setCountry_code(driversRequest.getCountry_code());
        d.setBroadcast_name(driversRequest.getBroadcast_name());
        d.setFirst_name(driversRequest.getFirst_name());
        d.setLast_name(driversRequest.getLast_name());
        d.setFull_name(driversRequest.getFull_name());
        d.setMeeting_key(driversRequest.getMeeting_key());
        d.setName_acronym(driversRequest.getName_acronym());
        d.setTeam_name(driversRequest.getTeam_name());

        return driversRepository.save(d);
    }

    public Drivers updateDriver(int id, DriversRequest driversRequest) throws NotFoundException {
        Drivers d = new Drivers();
        d.setDriver_number(driversRequest.getDriver_number());
        d.setCountry_code(driversRequest.getCountry_code());
        d.setBroadcast_name(driversRequest.getBroadcast_name());
        d.setFirst_name(driversRequest.getFirst_name());
        d.setLast_name(driversRequest.getLast_name());
        d.setFull_name(driversRequest.getFull_name());
        d.setMeeting_key(driversRequest.getMeeting_key());
        d.setName_acronym(driversRequest.getName_acronym());
        d.setTeam_name(driversRequest.getTeam_name());


        return driversRepository.save(d);
    }

    public void deleteDriverById(int id) throws NotFoundException {
        Drivers d = getDriverById(id);
        driversRepository.delete(d);
    }


}
