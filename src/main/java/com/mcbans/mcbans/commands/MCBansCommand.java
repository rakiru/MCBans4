package com.mcbans.mcbans.commands;

import com.mcbans.mcbans.MCBansPlugin;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

/**
 *
 * @author rakiru
 */
public class MCBansCommand implements CommandExecutor {

	private final ChatColor COLOUR = ChatColor.GOLD;
	private final MCBansPlugin plugin;

	public MCBansCommand(MCBansPlugin plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (!sender.hasPermission("mcbans.mcbans")) {
			sender.sendMessage(plugin.lang.getFormat("permissionDenied"));
			return true;
		}

		//Show usage if empty command
		if (args.length == 0) {
			return false;
		}

		if (args[0].equalsIgnoreCase("version")) {
			sender.sendMessage("MCBans version " + plugin.getDescription().getVersion());
			sender.sendMessage("BUILD: " + MCBansPlugin.BUILD_NUMBER);
			sender.sendMessage("GIT REV: " + MCBansPlugin.GIT_REVISION);
		} else if (args[0].equalsIgnoreCase("online")) {
			plugin.setOnline(true);
		} else if (args[0].equalsIgnoreCase("offline")) {
			plugin.setOnline(false);
		} else if (args[0].equalsIgnoreCase("status")) {
			sender.sendMessage(COLOUR + "MCBans is currently running in " + (plugin.isOnline() ? ChatColor.GREEN + "online" : ChatColor.RED + "offline") + COLOUR + " mode.");
		} else if (args[0].equalsIgnoreCase("reload")) {
			plugin.reloadConfig();
			plugin.lang.reload();
		} else if (args[0].equalsIgnoreCase("magic")) {
			sender.sendMessage(ChatColor.MAGIC + "Indeed.");
		}

		//Ban ban = new Ban();
		//ban.start();
		return true;
	}
}
