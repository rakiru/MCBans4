package com.mcbans.mcbans.calls;

import com.mcbans.mcbans.MCBansPlugin;
import com.mcbans.mcbans.utils.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.entity.Player;

/**
 *
 * @author Sean
 */
public class Ban implements Runnable {

	protected String playerName;
	protected final MCBansPlugin plugin;
	protected final String adminName;
	protected final String kickReason;

	public Ban(MCBansPlugin plugin, String playerName, String adminName, String kickReason) {
		this.plugin = plugin;
		this.playerName = playerName;
		this.adminName = adminName;
		this.kickReason = kickReason;
	}

	public void run() {
		Player player;
		try {
			player = PlayerMethods.getPlayer(plugin, playerName);
			playerName = PlayerMethods.getPlayerName(plugin, player);
			PlayerMethods.kickPlayer(plugin, player, plugin.lang.getFormat("kickMessagePlayer", playerName, adminName, kickReason));
			PlayerMethods.broadcast(plugin, plugin.lang.getFormat("kickMessageSuccess", playerName, adminName, kickReason));
		} catch (InterruptedException e) {
			plugin.log.debug(e.toString());
		}
	}
}
