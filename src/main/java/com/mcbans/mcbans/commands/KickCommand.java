package com.mcbans.mcbans.commands;

import com.mcbans.mcbans.MCBansAPI;
import com.mcbans.mcbans.MCBansPlugin;
import java.util.List;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 *
 * @author rakiru
 */
public class KickCommand implements CommandExecutor {

	private final MCBansPlugin plugin;

	public KickCommand(MCBansPlugin plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (!sender.hasPermission("mcbans.kick")) {
			sender.sendMessage(plugin.lang.getFormat("permissionDenied"));
			return true;
		}

		String adminName = sender.getName();
		String kickReason;
		String playerName;

		switch (args.length) {
			case 0:
				//No arguments - show command usage
				return false;
			case 1:
				//One argument - player to be kicked
				playerName = args[0];
				kickReason = plugin.config.getDefaultKickReason();
				break;
			default:
				//More than one argument - player to be kicked and reason
				playerName = args[0];
				StringBuilder sb = new StringBuilder(args[1]);
				for (int i = 2; i < args.length; i++) {
					sb.append(' ');
					sb.append(args[i]);
				}
				kickReason = sb.toString();
				break;
		}

		Player player;
		List<Player> playerMatches = plugin.getServer().matchPlayer(playerName);

		switch (playerMatches.size()) {
			case 0:
				//No matching players - no-one to kick
				sender.sendMessage(plugin.lang.getFormat("kickMessageNoPlayer", playerName, adminName, kickReason));
				return true;
			case 1:
				//One matching player - what we want
				player = playerMatches.get(0);
				//May have been partial name match - get the full name
				playerName = player.getName();
				break;
			default:
				//More than one matching player - don't want to kick the wrong person
				sender.sendMessage(plugin.lang.getFormat("multiplePlayerMatches", playerName));
				return true;
		}

		//Kick player
		MCBansAPI.getInstance().kickPlayer(playerName, kickReason, adminName);
		//Tell everyone that the player has been kicked
		plugin.getServer().broadcastMessage(plugin.lang.getFormat("kickMessageSuccess", playerName, adminName, kickReason));

		return true;
	}
}
