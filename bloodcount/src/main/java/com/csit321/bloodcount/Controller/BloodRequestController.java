package com.csit321.bloodcount.Controller;

import com.csit321.bloodcount.Entity.BloodRequestEntity;
import com.csit321.bloodcount.Service.BloodRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bloodrequest")
public class BloodRequestController {
    @Autowired
    private BloodRequestService bloodRequestService;

    @PostMapping("/insertBloodRequest")
    public BloodRequestEntity insertBloodRequest(@RequestBody BloodRequestEntity bloodRequest) {
        return bloodRequestService.insertBloodRequest(bloodRequest);
    }

    @GetMapping("/getAllBloodRequests")
    public List<BloodRequestEntity> getAllBloodRequests() {
        return bloodRequestService.getAllBloodRequests();
    }

    @PutMapping("/updateBloodRequest/{requestId}")
    public BloodRequestEntity updateBloodRequest(@PathVariable int requestId, @RequestBody BloodRequestEntity newRequestDetails) {
        return bloodRequestService.updateBloodRequest(requestId, newRequestDetails);
    }

    @DeleteMapping("/deleteBloodRequest/{requestId}")
    public String deleteBloodRequest(@PathVariable int requestId) {
        return bloodRequestService.deleteBloodRequest(requestId);
    }
}
