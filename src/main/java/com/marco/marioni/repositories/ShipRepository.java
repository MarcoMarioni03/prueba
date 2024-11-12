package com.marco.marioni.repositories;

import com.marco.marioni.domain.entities.ShipEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShipRepository extends JpaRepository<ShipEntity, Integer> {
}
