package com.brodzik.adrian.simplefilesynchronizer.ui;

import com.brodzik.adrian.simplefilesynchronizer.util.Checksum;
import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import javax.inject.Inject;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

public class MainView implements FxmlView<MainViewModel> {
    @FXML
    private Button buttonTest;

    @Inject
    private Stage primaryStage;

    @InjectViewModel
    private MainViewModel viewModel;

    public void initialize() {
        buttonTest.setOnAction(actionEvent -> {
            System.out.println("Click!");

            DirectoryChooser directoryChooser = new DirectoryChooser();
            File selectedDirectory = directoryChooser.showDialog(primaryStage);

            if (selectedDirectory == null) {
                return;
            }

            System.out.println(selectedDirectory.getAbsolutePath());

            try {
                Files.walk(Paths.get(selectedDirectory.getAbsolutePath()))
                        .filter(Files::isRegularFile)
                        .forEach(path -> {
                            try {
                                File file = new File(path.toString());
                                System.out.println(String.format("%s, %s", path, Checksum.getSHA256(file)));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        });
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
