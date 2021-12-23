/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.pl.electricitybillingsystempl2project.entities;

/**
 * @author a
 */
public class Customer extends User {
    int phone;
    double meterid;
    int contract;
    String photo;

    public Customer(int phone, double meterid, int contract, String photo, String authorities, String name, String email, int password) {
        super(authorities, name, email, password);
        this.phone = phone;
        this.meterid = meterid;
        this.contract = contract;
        this.photo = photo;
    }

    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

    public double getMeterid() {
        return meterid;
    }

    public void setMeterid(double meterid) {
        this.meterid = meterid;
    }

    public int getContract() {
        return contract;
    }

    public void setContract(int contract) {
        this.contract = contract;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

}
