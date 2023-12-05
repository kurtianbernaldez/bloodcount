package com.csit321.bloodcount.Repository;

import com.csit321.bloodcount.Entity.HospitalEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HospitalRepository extends JpaRepository<HospitalEntity, Integer> {
}
