package com.universityoflimerick.sdaa.BackendCryptoLoot.Models;

import javax.persistence.*;

@Entity
@Table(name = "bankaccount")
public class BankAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Integer id;
    Integer availablefunds;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private UserProfile user;

    public BankAccount() {
    }

    public boolean hasSufficientFunds(float amount){
        return availablefunds >= amount;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAvailablefunds() {
        return availablefunds;
    }

    public void setAvailablefunds(Integer availablefunds) {
        this.availablefunds = availablefunds;
    }

    public int getUser() {
        return user.getId();
    }

    public void setUser(UserProfile user) {
        this.user = user;
    }
}