package com.brodzik.adrian.simplefilesynchronizer;

import com.brodzik.adrian.simplefilesynchronizer.handler.ConfigurationHandler;
import com.brodzik.adrian.simplefilesynchronizer.reference.Constants;
import com.brodzik.adrian.simplefilesynchronizer.ui.dashboard.DashboardView;
import com.brodzik.adrian.simplefilesynchronizer.ui.dashboard.DashboardViewModel;
import de.saxsys.mvvmfx.FluentViewLoader;
import de.saxsys.mvvmfx.ViewTuple;
import it.sauronsoftware.junique.AlreadyLockedException;
import it.sauronsoftware.junique.JUnique;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;

public class App extends Application {
    public static Stage primaryStage;

    public static void main(String[] args) {
        boolean alreadyRunning;

        try {
            JUnique.acquireLock(Constants.APP_ID, message -> {
                if (message.equals(Constants.MESSAGE_SHOW)) {
                    Platform.runLater(() -> App.primaryStage.show());
                }

                return "";
            });
            alreadyRunning = false;
        } catch (AlreadyLockedException e) {
            alreadyRunning = true;
        }

        if (alreadyRunning) {
            System.out.println("Application already running.");
            JUnique.sendMessage(Constants.APP_ID, Constants.MESSAGE_SHOW);
            System.exit(0);
        }

        Application.launch(args);
    }

    @Override
    public void start(Stage stage) {
        App.primaryStage = stage;

        ConfigurationHandler.INSTANCE.load();

        ViewTuple<DashboardView, DashboardViewModel> about = FluentViewLoader.fxmlView(DashboardView.class).load();
        stage.setWidth(ConfigurationHandler.INSTANCE.width);
        stage.setHeight(ConfigurationHandler.INSTANCE.height);
        stage.setScene(new Scene(about.getView()));
        stage.setTitle(Constants.DASHBOARD_TITLE);
        stage.show();

        stage.getIcons().add(new Image(getClass().getResourceAsStream("/icon.png")));

        Platform.setImplicitExit(false);

        SwingUtilities.invokeLater(this::addTrayIcon);
    }

    @Override
    public void stop() throws Exception {
        ConfigurationHandler.INSTANCE.width = App.primaryStage.getWidth();
        ConfigurationHandler.INSTANCE.height = App.primaryStage.getHeight();
        ConfigurationHandler.INSTANCE.save();
        super.stop();
    }

    private void addTrayIcon() {
        try {
            PopupMenu popup = new PopupMenu();
            TrayIcon trayIcon = new TrayIcon(ImageIO.read(getClass().getResourceAsStream("/icon.png")));
            trayIcon.setImageAutoSize(true);
            SystemTray tray = SystemTray.getSystemTray();

            CheckboxMenuItem runOnStartupItem = new CheckboxMenuItem("Run on startup");
            runOnStartupItem.setState(ConfigurationHandler.INSTANCE.runOnStartup);
            runOnStartupItem.addItemListener(itemEvent -> ConfigurationHandler.INSTANCE.runOnStartup = runOnStartupItem.getState());

            MenuItem exitItem = new MenuItem("Exit");
            exitItem.addActionListener(actionEvent -> {
                Platform.exit();
                tray.remove(trayIcon);
            });

            trayIcon.addActionListener(actionEvent -> Platform.runLater(() -> App.primaryStage.show()));
            trayIcon.setToolTip(Constants.APP_NAME);

            App.primaryStage.setOnCloseRequest(windowEvent -> {
                if (Platform.isImplicitExit()) {
                    tray.remove(trayIcon);
                }
            });

            popup.add(runOnStartupItem);
            popup.add(exitItem);

            trayIcon.setPopupMenu(popup);

            tray.add(trayIcon);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
