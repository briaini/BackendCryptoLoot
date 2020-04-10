package com.universityoflimerick.sdaa.BackendCryptoLoot.Models;

import javax.persistence.*;

@Entity
@Table(name = "userprofiledetails")
public class UserProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Integer id;
    String sub;
    String name;
    @OneToOne(mappedBy = "user", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    private Feedback feedback;
    @OneToOne(mappedBy = "user", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    private BankAccount bankAccount;

    public UserProfile() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSub() {
        return sub;
    }

    public void setSub(String sub) {
        this.sub = sub;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "UserProfile{" +
                "id=" + id +
                ", sub='" + sub + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}