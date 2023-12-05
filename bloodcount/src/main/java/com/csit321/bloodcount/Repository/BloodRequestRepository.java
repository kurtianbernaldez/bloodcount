package com.csit321.bloodcount.Repository;

import com.csit321.bloodcount.Entity.BloodRequestEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BloodRequestRepository extends JpaRepository<BloodRequestEntity, Integer> {
}
