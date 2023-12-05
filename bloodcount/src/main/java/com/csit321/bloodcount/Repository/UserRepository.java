    package com.csit321.bloodcount.Repository;

    import com.csit321.bloodcount.Entity.UserEntity;
    import org.springframework.data.jpa.repository.JpaRepository;
    import org.springframework.data.jpa.repository.Query;

    import java.util.List;

    public interface UserRepository extends JpaRepository<UserEntity, Integer> {
        UserEntity saveAndFlush(UserEntity entity);
    }
