package org.pl.electricitybillingsystempl2project.entities;

public class Billing extends BaseEntity {
    int reading_id;
    int billing;
    int tarrif;

    public int getReading_id() {
        return reading_id;
    }

    public void setReading_id(int reading_id) {
        this.reading_id = reading_id;
    }

    public int getBilling() {
        return billing;
    }

    public void setBilling(int billing) {
        this.billing = billing;
    }

    public int getTarrif() {
        return tarrif;
    }

    public void setTarrif(int tarrif) {
        this.tarrif = tarrif;
    }
}
