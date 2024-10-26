module com.example.pcdlab1_scarebnii {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.pcdlab1_scarebnii to javafx.fxml;
    exports com.example.pcdlab1_scarebnii;
}