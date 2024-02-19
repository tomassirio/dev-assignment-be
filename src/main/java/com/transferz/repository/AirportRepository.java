package com.transferz.repository;

import com.transferz.dao.Airport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AirportRepository extends JpaRepository<Airport, UUID> {
    Optional<Airport> findByCode(String code);

}