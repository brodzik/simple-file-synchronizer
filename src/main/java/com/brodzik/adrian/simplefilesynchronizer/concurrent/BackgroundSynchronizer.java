package com.brodzik.adrian.simplefilesynchronizer.concurrent;

import com.brodzik.adrian.simplefilesynchronizer.handler.EntryHandler;
import com.brodzik.adrian.simplefilesynchronizer.handler.SyncHandler;
import com.brodzik.adrian.simplefilesynchronizer.reference.Constants;

import java.util.Date;
import java.util.concurrent.Semaphore;

public class BackgroundSynchronizer implements Runnable {
    private Semaphore mutex = new Semaphore(1);
    private boolean active = true;

    @Override
    public void run() {
        while (true) {
            try {
                mutex.acquire();
                if (!active) {
                    System.out.println("Background synchronizer shutting down.");
                    return;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
                return;
            } finally {
                mutex.release();
            }

            long now = new Date().getTime();
            EntryHandler.INSTANCE.getEntries().forEach(entry -> {
                if (entry.isEnabled() && entry.getFrequency() > 0 && now > entry.getLastSync().getTime() + entry.getFrequency() * 1000) {
                    SyncHandler.INSTANCE.sync(entry);
                }
            });

            try {
                Thread.sleep(Constants.SYNC_DELAY_SECONDS * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
                return;
            }
        }
    }

    public void stop() {
        try {
            mutex.acquire();
            active = false;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            mutex.release();
        }
    }
}
