module com.language {
    requires javafx.controls;
    requires javafx.fxml;
    requires junit;
    requires json.simple;
    requires jlayer;
    requires software.amazon.awssdk.core;
    requires software.amazon.awssdk.services.polly;
    requires software.amazon.awssdk.regions;
    requires software.amazon.awssdk.utils;
    requires org.slf4j;
    requires org.slf4j.simple;
    requires software.amazon.awssdk.awscore;
    requires software.amazon.eventstream;
    requires javafx.graphics;

    opens com.language to javafx.fxml;
    exports com.language;

    opens com.narriation to javafx.fxml;
    exports com.narriation;

    opens com.model to javafx.fxml;
    exports com.model;

    opens com.controllers to javafx.fxml;
    exports com.controllers;
}