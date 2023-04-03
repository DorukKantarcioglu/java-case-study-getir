package com.getir.readingisgood.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class AddCustomerRequest {
    @NotBlank(message = "Email cannot be null or blank.")
    @Email(message = "Email has to be a valid address.")
    private String email;

    @NotBlank(message = "Name cannot be null or blank.")
    private String name;

    @NotBlank(message = "Address cannot be null or blank.")
    private String address;

    public AddCustomerRequest() {}

    public AddCustomerRequest(String email, String name, String address) {
        this.email = email;
        this.name = name;
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
