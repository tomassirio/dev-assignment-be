package com.transferz.repository;

import com.transferz.dao.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface FlightRepository extends JpaRepository<Flight, UUID> {
    @Query("SELECT f FROM Flight f WHERE (SELECT COUNT(p) FROM Passenger p WHERE p.flight = f) <= :maxPassengers")
    Optional<Flight> findAvailableFlight(@Param("maxPassengers") long maxPassengers);
}