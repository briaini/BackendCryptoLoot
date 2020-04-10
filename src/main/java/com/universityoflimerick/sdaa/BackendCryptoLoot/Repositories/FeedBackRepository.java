package com.universityoflimerick.sdaa.BackendCryptoLoot.Repositories;

import com.universityoflimerick.sdaa.BackendCryptoLoot.Models.Feedback;
import org.springframework.data.repository.CrudRepository;

public interface FeedBackRepository extends CrudRepository<Feedback, Integer> {
}
