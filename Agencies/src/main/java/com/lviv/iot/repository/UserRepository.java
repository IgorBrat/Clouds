package com.lviv.iot.repository;

import com.lviv.iot.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    @Procedure("UserParamInsert")
    User addUserWithProcedure(String phone, String email);
}
