package com.mcbans.mcbans.commands;

import com.mcbans.mcbans.MCBansPlugin;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

/**
 *
 * @author rakiru
 */
public class MCBansCommand implements CommandExecutor {

	private final MCBansPlugin MCBans;

	public MCBansCommand(MCBansPlugin plugin) {
		this.MCBans = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] split) {
		if (!sender.hasPermission("mcbans.ban")) {
			sender.sendMessage(MCBans.lang.getFormat("permissionDenied"));
			return true;
		}

		//Ban ban = new Ban();
		//ban.start();
		return true;
	}
}
