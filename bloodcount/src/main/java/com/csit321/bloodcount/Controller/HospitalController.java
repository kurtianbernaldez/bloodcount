package com.csit321.bloodcount.Controller;

import com.csit321.bloodcount.Entity.HospitalEntity;
import com.csit321.bloodcount.Service.HospitalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/hospital")
public class HospitalController {

    @Autowired
    private HospitalService hospitalService;

    @PostMapping("/insertHospital")
    public HospitalEntity insertHospital(@RequestBody HospitalEntity hospital) {
        return hospitalService.insertHospital(hospital);
    }

    @GetMapping("/getAllHospitals")
    public List<HospitalEntity> getAllHospitals() {
        return hospitalService.getAllHospitals();
    }

    @PutMapping("/updateHospital")
    public HospitalEntity updateHospital(@RequestParam int hospitalId, @RequestBody HospitalEntity newHospitalDetails) {
        return hospitalService.updateHospital(hospitalId, newHospitalDetails);
    }

    @DeleteMapping("/deleteHospital/{hospitalId}")
    public String deleteHospital(@PathVariable int hospitalId) {
        return hospitalService.deleteHospital(hospitalId);
    }
}
