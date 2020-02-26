/**
 * Created as part of Osmosis 2020.
 */
package com.mindtree.minto.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mindtree.minto.model.Booking;

/**
 * 
 * BookingRepository.java Created On: Feb 24, 2020 Created By: M1026329
 */
public interface BookingRepository extends JpaRepository<Booking, Integer> {

	List<Booking> findAllByDateOfFirstSegment(Date dateOfFirstSegment);

}
