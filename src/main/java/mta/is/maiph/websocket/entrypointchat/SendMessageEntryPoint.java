package mta.is.maiph.websocket.entrypointchat;

import java.util.concurrent.ConcurrentLinkedQueue;
import mta.is.maiph.dto.connection.Message;

/**
 *
 * @author MaiPH
 */
public class SendMessageEntryPoint {

    private static ConcurrentLinkedQueue<Message> pool = new ConcurrentLinkedQueue<>();

    public static void add(Message msg) {
        pool.add(msg);
    }

    public static Message pool(Message msg) {
        return pool.poll();
    }
}
