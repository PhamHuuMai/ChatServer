/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mta.is.maiph.worker;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 * @author MaiPH
 */
public class BackgroundThread {

    private final int SIZE_POOL = 10;
    private static BackgroundThread instance;
    private final ExecutorService executor = Executors.newFixedThreadPool(SIZE_POOL);

    private BackgroundThread() {
    }

    public static BackgroundThread instance() {
        instance = instance == null ? new BackgroundThread() : instance;
        return instance;
    }

    public void execute(Runnable task) {
        executor.execute(task);
    }
}
