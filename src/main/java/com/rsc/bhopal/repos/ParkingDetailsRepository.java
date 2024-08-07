package com.rsc.bhopal.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rsc.bhopal.entity.ParkingDetails;

@Repository
public interface ParkingDetailsRepository extends JpaRepository<ParkingDetails, Long> {

}
