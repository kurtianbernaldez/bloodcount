    package com.csit321.bloodcount.Repository;

    import com.csit321.bloodcount.Entity.DonorEntity;
    import org.springframework.data.jpa.repository.JpaRepository;

    public interface DonorRepository extends JpaRepository<DonorEntity, Integer> {
    }
