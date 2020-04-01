package com.universityoflimerick.sdaa.BackendCryptoLoot.Repositories;

import com.universityoflimerick.sdaa.BackendCryptoLoot.Models.UserProfile;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserProfileRepository extends CrudRepository<UserProfile, Integer> {

    @Query(value = "SELECT * FROM `userprofiledetails` WHERE sub = :sub",
            nativeQuery = true)
    Optional<UserProfile> findBySub(@Param("sub") String sub);
}
