package com.brodzik.adrian.simplefilesynchronizer;

import com.brodzik.adrian.simplefilesynchronizer.concurrent.BackgroundSynchronizer;
import com.brodzik.adrian.simplefilesynchronizer.handler.ConfigurationHandler;
import com.brodzik.adrian.simplefilesynchronizer.handler.EntryHandler;
import com.brodzik.adrian.simplefilesynchronizer.handler.SyncHandler;
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
    public static boolean hasTray;

    private BackgroundSynchronizer backgroundSynchronizer;

    private SystemTray tray;
    private TrayIcon trayIcon;

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

        if (Constants.APP_DIR.toFile().mkdirs()) {
            System.out.println("Created app directory: " + Constants.APP_DIR.toString());
        }

        ConfigurationHandler.INSTANCE.load();
        EntryHandler.INSTANCE.load();
        SyncHandler.INSTANCE.load();

        if (SystemTray.isSupported()) {
            Platform.setImplicitExit(false);
            SwingUtilities.invokeLater(this::addTrayIcon);
            App.hasTray = true;
        } else {
            System.out.println("SystemTray is not supported.");
            App.hasTray = false;
        }

        stage.widthProperty().addListener(observable -> ConfigurationHandler.INSTANCE.layout.setWidth(stage.getWidth()));
        stage.heightProperty().addListener(observable -> ConfigurationHandler.INSTANCE.layout.setHeight(stage.getHeight()));

        backgroundSynchronizer = new BackgroundSynchronizer();
        new Thread(backgroundSynchronizer).start();

        ViewTuple<DashboardView, DashboardViewModel> about = FluentViewLoader.fxmlView(DashboardView.class).load();
        stage.setWidth(ConfigurationHandler.INSTANCE.layout.getWidth());
        stage.setHeight(ConfigurationHandler.INSTANCE.layout.getHeight());
        stage.setScene(new Scene(about.getView()));
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/icon.png")));
        stage.setTitle(Constants.DASHBOARD_TITLE);
        stage.show();
    }

    @Override
    public void stop() throws Exception {
        backgroundSynchronizer.stop();

        ConfigurationHandler.INSTANCE.save();
        EntryHandler.INSTANCE.save();
        SyncHandler.INSTANCE.save();

        if (App.hasTray) {
            SwingUtilities.invokeLater(() -> tray.remove(trayIcon));
        }

        super.stop();
    }

    private void addTrayIcon() {
        try {
            PopupMenu popup = new PopupMenu();
            trayIcon = new TrayIcon(ImageIO.read(getClass().getResourceAsStream("/icon.png")));
            trayIcon.setImageAutoSize(true);
            tray = SystemTray.getSystemTray();

            MenuItem exitItem = new MenuItem("Exit");
            exitItem.addActionListener(actionEvent -> Platform.exit());

            trayIcon.addActionListener(actionEvent -> Platform.runLater(() -> App.primaryStage.show()));
            trayIcon.setToolTip(Constants.APP_NAME);

            popup.add(exitItem);

            trayIcon.setPopupMenu(popup);

            tray.add(trayIcon);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
