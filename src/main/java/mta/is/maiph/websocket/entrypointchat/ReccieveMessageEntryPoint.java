/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mta.is.maiph.websocket.entrypointchat;

import java.util.concurrent.ConcurrentLinkedQueue;
import mta.is.maiph.dto.connection.Message;

/**
 *
 * @author MaiPH
 */
public class ReccieveMessageEntryPoint {

    private ConcurrentLinkedQueue<Message> pool = new ConcurrentLinkedQueue<>();

    private static ReccieveMessageEntryPoint instance;

    private ReccieveMessageEntryPoint() {
    }

    public static ReccieveMessageEntryPoint instance() {
        return instance == null ? new ReccieveMessageEntryPoint() : instance;
    }

    public void add(Message msg) {
        pool.add(msg);
    }

    public Message pool() {
        return pool.poll();
    }

    public boolean isEmpty() {
        return pool.isEmpty();
    }
}
