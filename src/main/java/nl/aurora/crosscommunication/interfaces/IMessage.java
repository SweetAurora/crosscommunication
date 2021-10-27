package nl.aurora.crosscommunication.interfaces;

import io.netty.buffer.ByteBuf;

/**
 * Implement this interface for each message you wish to define.
 *
 * @author cpw
 */
public interface IMessage {
    /**
     * Convert from the supplied buffer into your specific message type
     *
     * @param byteBuf -
     */
    void toBytes(ByteBuf byteBuf);

    /**
     * Deconstruct your message into the supplied byte buffer
     *
     * @param byteBuf -
     */
    void fromBytes(ByteBuf byteBuf);
}