package com.universityoflimerick.sdaa.BackendCryptoLoot.Repositories;

import com.universityoflimerick.sdaa.BackendCryptoLoot.Models.BankAccount;
import org.springframework.data.repository.CrudRepository;

public interface BankApiRepository extends CrudRepository<BankAccount, Integer> {
}
