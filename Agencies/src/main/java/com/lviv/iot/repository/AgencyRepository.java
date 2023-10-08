package com.lviv.iot.repository;

import com.lviv.iot.domain.Agency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.stereotype.Repository;

@Repository
public interface AgencyRepository extends JpaRepository<Agency, Integer> {
    @Procedure("CreateTablesWithCursor")
    void createTablesWithCursor();
}
