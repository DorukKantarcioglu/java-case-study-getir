package com.getir.readingisgood.response;

public class CustomerResponse {
    private final Long id;

    private final String email;

    private final String name;

    private final String address;

    public CustomerResponse(Long id, String email, String name, String address) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.address = address;
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }
}
