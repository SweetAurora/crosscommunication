package nl.aurora.crosscommunication;

import nl.aurora.crosscommunication.channels.InitializedChannel;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Aurora
 * @since 10/27/2021
 */
public final class CrossCommunication extends JavaPlugin {
    private static final Map<String, InitializedChannel> REGISTERED_CHANNELS = new HashMap<>();
    private static boolean started = false;

    @Override
    public void onEnable() {
        CrossCommunication.started = true;
    }

    /**
     * This method is used to register a new channel.
     *
     * @param plugin owner of the specified channel.
     * @param channelName listening channel name
     * @return null in case of error or an {@link InitializedChannel} object.
     */
    public static InitializedChannel registerChannel(Plugin plugin, String channelName) {
        if (!started) {
            plugin.getLogger().severe("Tried registering a channel before the server finished starting up.");
            return null;
        }

        if (REGISTERED_CHANNELS.containsKey(channelName)) {
            plugin.getLogger().severe("Tried double-registering a channel that has already been registered by the following plugin: " +
                    REGISTERED_CHANNELS.get(channelName).getPlugin().getName() + ".\nReturning the already existing channel.");
            return REGISTERED_CHANNELS.get(channelName);
        }
        InitializedChannel initializedChannel = new InitializedChannel(plugin, channelName);
        REGISTERED_CHANNELS.put(channelName, initializedChannel);
        return initializedChannel;
    }
}