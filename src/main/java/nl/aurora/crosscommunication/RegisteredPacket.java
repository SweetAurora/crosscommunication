package nl.aurora.crosscommunication;

import nl.aurora.crosscommunication.interfaces.IMessage;
import nl.aurora.crosscommunication.interfaces.IMessageHandler;

/**
 * @author Aurora
 * @since 10/27/2021
 */
public final class RegisteredPacket {
    private final int id;
    private final Class<IMessage> message;
    private final Class<IMessageHandler<?>> handler;

    public RegisteredPacket(int id, Class<IMessage> message, Class<IMessageHandler<?>> handler) {
        this.id = id;
        this.message = message;
        this.handler = handler;
    }

    public int getId() {
        return id;
    }

    public Class<IMessage> getMessage() {
        return message;
    }

    public Class<IMessageHandler<?>> getHandler() {
        return handler;
    }
}