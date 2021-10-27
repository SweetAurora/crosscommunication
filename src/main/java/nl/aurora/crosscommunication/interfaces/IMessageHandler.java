package nl.aurora.crosscommunication.interfaces;

import org.bukkit.entity.Player;

/**
 * @author Aurora
 * @since 10/27/2021
 */
public interface IMessageHandler<P extends IMessage> {
    void handle(Player sender, P packet);
}