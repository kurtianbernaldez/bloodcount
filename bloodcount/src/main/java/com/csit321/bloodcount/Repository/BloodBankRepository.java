package com.csit321.bloodcount.Repository;

import com.csit321.bloodcount.Entity.BloodBankEntity;
import com.csit321.bloodcount.Entity.BloodRequestEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BloodBankRepository extends JpaRepository<BloodBankEntity, Integer> {
}
