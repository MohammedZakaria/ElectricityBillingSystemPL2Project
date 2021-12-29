module org.pl.electricitybillingsystempl2project {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires vavr;
    requires com.fasterxml.jackson.databind;
    requires org.apache.commons.lang3;
    requires java.desktop;

    opens org.pl.electricitybillingsystempl2project;
    opens org.pl.electricitybillingsystempl2project.Controllers;

    opens org.pl.electricitybillingsystempl2project.entities;
    exports org.pl.electricitybillingsystempl2project.entities;
}