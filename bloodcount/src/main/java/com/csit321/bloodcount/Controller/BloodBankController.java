package com.csit321.bloodcount.Controller;

import com.csit321.bloodcount.Entity.BloodBankEntity;
import com.csit321.bloodcount.Service.BloodBankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bloodbank")
public class BloodBankController {
    @Autowired
    private BloodBankService bloodBankService;

    @PostMapping("/insertBloodBank")
    public BloodBankEntity insertBloodBank(@RequestBody BloodBankEntity bloodBank) {
        return bloodBankService.insertBloodBank(bloodBank);
    }

    @GetMapping("/getAllBloodBanks")
    public List<BloodBankEntity> getAllBloodBanks() {
        return bloodBankService.getAllBloodBanks();
    }

    @PutMapping("/updateBloodBank")
    public BloodBankEntity updateBloodBank(@RequestParam int bloodBankId, @RequestBody BloodBankEntity newBloodBankDetails) {
        return bloodBankService.updateBloodBank(bloodBankId, newBloodBankDetails);
    }

    @DeleteMapping("/deleteBloodBank/{bloodBankId}")
    public String deleteBloodBank(@PathVariable int bloodBankId) {
        return bloodBankService.deleteBloodBank(bloodBankId);
    }
}
