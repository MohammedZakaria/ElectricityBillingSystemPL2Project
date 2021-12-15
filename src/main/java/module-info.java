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

    opens org.pl.electricitybillingsystempl2project to javafx.fxml;
    exports org.pl.electricitybillingsystempl2project;
}