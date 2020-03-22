/**
 * Created as part of Osmosis 2020.
 */
package com.mindtree.minto.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mindtree.minto.model.ExpenseInfo;

/**
 * 
 * UserRepository.java Created On: Feb 22, 2020 Created By: M1026329
 */
public interface ExpenseInfoRepository extends JpaRepository<ExpenseInfo, Integer> {

}
