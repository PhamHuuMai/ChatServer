package mta.is.maiph.websocket.entrypointchat;

import java.util.concurrent.ConcurrentLinkedQueue;
import mta.is.maiph.dto.connection.Message;

/**
 *
 * @author MaiPH
 */
public class SendMessageEntryPoint {

    private static ConcurrentLinkedQueue<Message> pool = new ConcurrentLinkedQueue<>();

    private static SendMessageEntryPoint instance;

    private SendMessageEntryPoint() {
    }

    public static SendMessageEntryPoint instance() {
        return instance == null ? new SendMessageEntryPoint() : instance;
    }

    public static void add(Message msg) {
        pool.add(msg);
    }

    public static Message pool(Message msg) {
        return pool.poll();
    }
}
