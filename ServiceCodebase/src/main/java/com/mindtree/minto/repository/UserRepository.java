/**
 * Created as part of Osmosis 2020.
 */
package com.mindtree.minto.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mindtree.minto.model.User;

/**
 * 
 * UserRepository.java Created On: Feb 22, 2020 Created By: M1026329
 */
public interface UserRepository extends JpaRepository<User, Integer> {

    @Query(value = "select u.email_id, u.wallet_id, u.user_role, u.face_id, u.first_name, u.last_name, u.date_of_birth, u.contact_no from USER_INFORMATION u where u.USER_PASS =:pswrd and u.EMAIL_ID = :email", nativeQuery = true)
    List<Object> checkLogin(@Param("pswrd") String pwd, @Param("email") String email);
    
    @Query(value = "select u.email_id, u.wallet_id, u.user_role, u.face_id from USER_INFORMATION u where u.EMAIL_ID = :email", nativeQuery = true)
    List<Object> fetchFaceID(@Param("email") String email);
    
    @Query(value = "select u.wallet_id from USER_INFORMATION u where u.EMAIL_ID = :email", nativeQuery = true)
    Optional<String> getWalletID(@Param("email") String email);
    
    @Query(value = "select u.wallet_id from USER_INFORMATION u where u.LAST_NAME = :name", nativeQuery = true)
    Optional<String> getWalletIDByName(@Param("name") String email);
    
    @Query(value = "select ti.TRAVEL_INFO from TRAVEL_INFORMATION ti, USER_INFORMATION u where u.EMAIL_ID = :email and u.USER_ID = ti.USER_ID" , nativeQuery = true)
    List<byte[]> getTravelInfos(@Param("email") String email);
    
    @Query("from USER_INFORMATION u where u.email = :email")
    List<User> getUsers(@Param("email") String email);
    @Query("from USER_INFORMATION u where u.email = :email and u.pswrd =:pswrd")
    List<User> getUsers(@Param("email") String email, @Param("pswrd") String pwd);
}
