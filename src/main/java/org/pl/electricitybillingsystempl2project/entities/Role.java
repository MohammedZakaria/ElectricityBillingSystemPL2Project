package org.pl.electricitybillingsystempl2project.entities;

public class Role extends BaseEntity {
    int authorities;

    public int getAuthorities() {
        return authorities;
    }

    public void setAuthorities(int authorities) {
        this.authorities = authorities;
    }
}

