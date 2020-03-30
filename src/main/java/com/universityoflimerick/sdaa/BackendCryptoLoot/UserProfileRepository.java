package com.universityoflimerick.sdaa.BackendCryptoLoot;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserProfileRepository extends CrudRepository<UserProfile, Integer> {


//    @Query(value = "SELECT name FROM `userprofiledetails` WHERE sub = :sub",
//            nativeQuery = true)
//    String getUserProfileBySub(@Param("sub") String sub);


    @Query(value = "SELECT * FROM `userprofiledetails` WHERE sub = :sub",
            nativeQuery = true)
    Optional<UserProfile> findBySub(@Param("sub") String sub);
}
