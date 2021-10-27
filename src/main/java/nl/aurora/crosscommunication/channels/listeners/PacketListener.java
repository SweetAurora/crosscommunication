package nl.aurora.crosscommunication.channels.listeners;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import nl.aurora.crosscommunication.RegisteredPacket;
import nl.aurora.crosscommunication.channels.InitializedChannel;
import nl.aurora.crosscommunication.interfaces.IMessage;
import nl.aurora.crosscommunication.interfaces.IMessageHandler;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.logging.Level;

/**
 * @author Aurora
 * @since 10/27/2021
 */
public final class PacketListener implements PluginMessageListener {
    private static final Method HANDLE_METHOD;

    static {
        Method tempField = null;
        try {
            tempField = IMessageHandler.class.getMethod("handle", Player.class, IMessage.class);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        HANDLE_METHOD = tempField;
    }

    private final InitializedChannel channel;

    public PacketListener(InitializedChannel channel) {
        this.channel = channel;
    }

    @Override
    public void onPluginMessageReceived(String channelName, Player player, byte[] bytes) {
        ByteBuf byteBuf = Unpooled.wrappedBuffer(bytes);
        int packetId = byteBuf.readByte();
        if (!channel.getRegisteredPacketMap().containsKey(packetId)) {
            channel.getPlugin().getLogger().severe("User " + player.getName() + " tried calling non-existent packet! (ID: " + packetId + ")");
            return;
        }

        RegisteredPacket registeredPacket = channel.getRegisteredPacketMap().get(packetId);
        try {
            IMessage message = registeredPacket.getMessage().getConstructor().newInstance();
            message.fromBytes(byteBuf);
            IMessageHandler<?> messageHandler = registeredPacket.getHandler().getConstructor().newInstance();
            HANDLE_METHOD.invoke(messageHandler, player, message);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            channel.getPlugin().getLogger().log(Level.SEVERE, e, () -> "An error occurred while attempting to handle packet "
                    + registeredPacket.getMessage().getSimpleName() + " (ID: " + packetId + ").");
        }
    }
}