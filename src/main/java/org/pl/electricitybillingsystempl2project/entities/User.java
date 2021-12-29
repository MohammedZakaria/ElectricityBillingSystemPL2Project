/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.pl.electricitybillingsystempl2project.entities;



/**
 * @author a
 */
public class User extends BaseEntity {
    protected String name;
    protected String phone;
    protected String email;
    protected String password;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        if (phone.matches("[0-9]+"))
            this.phone = phone;
        else throw new RuntimeException("invalid phone number");
    }
}
