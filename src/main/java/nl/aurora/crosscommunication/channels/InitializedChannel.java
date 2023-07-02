package nl.aurora.crosscommunication.channels;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import nl.aurora.crosscommunication.RegisteredPacket;
import nl.aurora.crosscommunication.channels.listeners.PacketListener;
import nl.aurora.crosscommunication.interfaces.IMessage;
import nl.aurora.crosscommunication.interfaces.IMessageHandler;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Aurora
 * @since 10/27/2021
 */
public final class InitializedChannel {
    private final Map<Integer, RegisteredPacket> registeredPacketMap;
    private final Map<Class<IMessage>, Integer> messageToIdMap;
    private final Plugin plugin;
    private final String channelName;

    public InitializedChannel(Plugin plugin, String channel) {
        this.plugin = plugin;
        this.channelName = channel;
        this.registeredPacketMap = new HashMap<>();
        this.messageToIdMap = new HashMap<>();
        Bukkit.getServer().getMessenger().registerIncomingPluginChannel(plugin, channel, new PacketListener(this));
        Bukkit.getServer().getMessenger().registerOutgoingPluginChannel(plugin, channel);
    }

    /**
     * Method used to send packets to a player.
     *
     * @param player target user to send the specified packet to.
     * @param message the packet to send to the user.
     */
    public void sendPacket(Player player, IMessage message) {
        if (!messageToIdMap.containsKey(message.getClass())) {
            plugin.getLogger().severe("Tried sending unregistered packet " + message.getClass().getSimpleName() + ".");
            return;
        }
        ByteBuf byteBuf = Unpooled.buffer();
        byteBuf.writeByte(messageToIdMap.get(message.getClass()));
        message.toBytes(byteBuf);
        player.sendPluginMessage(plugin, channelName, byteBuf.array());
    }

    /**
     * Register a new packet on this specified channel.
     *
     * @param id packet ID, never duplicate.
     * @param message Packet class
     * @param handler Packet handler class
     */
    public void registerPacket(int id, Class<IMessage> message, Class<IMessageHandler<?>> handler) {
        this.messageToIdMap.put(message, id);
        this.registeredPacketMap.put(id, new RegisteredPacket(id, message, handler));
    }

    public Map<Integer, RegisteredPacket> getRegisteredPacketMap() {
        return registeredPacketMap;
    }

    public Plugin getPlugin() {
        return plugin;
    }

    public String getChannelName() {
        return channelName;
    }
}
