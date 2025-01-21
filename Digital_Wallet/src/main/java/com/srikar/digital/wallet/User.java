package com.srikar.digital.wallet;

import com.srikar.digital.wallet.util.IdGenerator;

public class User {
    private final String id;
    private final String name;
    private final String email;
    private final Wallet wallet;

    public User(String name, String email, Wallet wallet) {
        this.name = name;
        this.email = email;
        this.wallet = wallet;
        this.id = IdGenerator.generateUserId();
    }
    public String getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public String getEmail() {
        return email;
    }
    public Wallet getWallet() {
        return wallet;
    }
}
