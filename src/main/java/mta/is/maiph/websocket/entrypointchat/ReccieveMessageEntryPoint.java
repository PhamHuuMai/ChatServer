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

    private static ConcurrentLinkedQueue<Message> pool = new ConcurrentLinkedQueue<>();

    public static void add(Message msg) {
        pool.add(msg);
    }

    public static Message pool() {
        return pool.poll();
    }
}
