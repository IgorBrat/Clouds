package com.lviv.iot.repository;

import com.lviv.iot.domain.EquipmentShop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EquipmentShopRepository extends JpaRepository<EquipmentShop, Integer> {
}
