package com.csit321.bloodcount.Controller;

import com.csit321.bloodcount.Entity.DonorEntity;
import com.csit321.bloodcount.Service.DonorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/donor")
public class DonorController {
    @Autowired
    private DonorService donorService;

    @PostMapping("/insertDonor")
    public DonorEntity insertDonor(@RequestBody DonorEntity donor) {
        return donorService.insertDonor(donor);
    }


    @GetMapping("/getAllDonors")
    public List<DonorEntity> getAllDonors(){
        return donorService.getAllDonors();
    }

    @PutMapping("/updateDonor")
    public DonorEntity updateDonor(@RequestParam int donorId, @RequestBody DonorEntity newDonorDetails){
        return donorService.updateDonor(donorId, newDonorDetails);
    }

    @DeleteMapping("/deleteDonor/{donorId}")
    public String deleteDonor(@PathVariable int donorId){
        return donorService.deleteDonor(donorId);
    }
}

