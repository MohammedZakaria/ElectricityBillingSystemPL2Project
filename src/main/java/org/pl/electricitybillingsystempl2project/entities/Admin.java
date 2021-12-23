package org.pl.electricitybillingsystempl2project.entities;

public class Admin extends BaseEntity {
    private int phone;
    private String photo;

    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

}
