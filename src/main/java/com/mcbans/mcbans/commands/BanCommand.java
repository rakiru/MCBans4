package com.mcbans.mcbans.commands;

import com.mcbans.mcbans.BanType;
import com.mcbans.mcbans.MCBansAPI;
import com.mcbans.mcbans.MCBansPlugin;
import com.mcbans.mcbans.api.FlagHandler;
import com.mcbans.mcbans.api.MCBansPluginManager;
import com.mcbans.mcbans.calls.Ban;
import java.util.ArrayList;
import java.util.regex.Pattern;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 *
 * @author rakiru
 */
public class BanCommand implements CommandExecutor {

	private final MCBansPlugin plugin;
	private final MCBansPluginManager manager;
	private final Pattern validUsername = Pattern.compile(MCBansAPI.VALID_PLAYER_NAME_REGEX);

	public BanCommand(MCBansPlugin plugin) {
		this.plugin = plugin;
		this.manager = MCBansAPI.getInstance().getPluginManager();
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (!sender.hasPermission("mcbans.ban")) {
			sender.sendMessage(plugin.lang.getFormat("permissionDenied"));
			return true;
		}

		String adminName = sender.getName();
		String banReason = null;
		String playerName = null;
		String flagString = null;
		String playerIP = "";
		String time = null;
		String timeUnit = null;
		BanType banType = BanType.LOCAL_BAN;
		ArrayList<FlagHandler> customFlags = new ArrayList<FlagHandler>();

		if (args.length == 0) {
			//No arguments - show command usage
			return false;
		}

		switch (args.length) {
			case 0:
				//No arguments - show command usage
				return false;
			case 1:
				//One argument - player to be banned (only valid case)
				if (args[0].startsWith("-")) {
					//Flags used but not other info (player name, etc) - show command usage
					return false;
				} else {
					playerName = args[0];
					banReason = plugin.config.getDefaultLocalBanReason();
				}
				break;
			default:
				//More than one argument - player to be banned and reason, or player to be banned and flags
				if (args[0].startsWith("-")) {
					//Flags used - parse them and second arg must be player to be banned
					flagString = args[0].substring(1);
					char[] flagChars = flagString.toCharArray();
					for (char flag : flagChars) {
						BanType f = BanType.getByChar(flag);
						if (f == null) {
							FlagHandler h = manager.getHandler(flag);
							if (h == null) {
								//TODO: Message player - invalid flag entered
							} else {
								customFlags.add(h);
							}
						} else {
							banType = f;
						}
					}
					playerName = args[1];
					int reasonStart;
					if (banType == BanType.TEMP_BAN && args.length >= 4) {
						//TODO: Should either add a default time in config or error here if no time entered
						time = args[2];
						timeUnit = args[3];
						reasonStart = 4;
					} else {
						reasonStart = 2;
					}
					if (args.length > reasonStart) {
						StringBuilder sb = new StringBuilder(args[reasonStart]);
						for (int i = reasonStart; i < args.length; i++) {
							sb.append(' ');
							sb.append(args[i]);
						}
						banReason = sb.toString();
					}
				} else {
					//Player name and ban reason - local ban player
					playerName = args[0];
					StringBuilder sb = new StringBuilder(args[1]);
					for (int i = 2; i < args.length; i++) {
						sb.append(' ');
						sb.append(args[i]);
					}
					banReason = sb.toString();
				}
				break;
		}

		//Check flag permissions
		if (!plugin.checkPermission(sender, banType.getPermission())) {
			//TODO: Message player
			plugin.log.debug("Use of ban type flag without permission", false);
			return true;
		}
		//Check for global ban without reason
		if (banType == BanType.GLOBAL_BAN && banReason == null) {
			//TODO: Message player
			plugin.log.debug("Global ban without reason", false);
		}

		Player player = plugin.getServer().getPlayerExact(playerName);
		if (player != null) {
			playerIP = player.getAddress().toString();
		}

		plugin.log.debug("Banning...", false);
		Ban ban = new Ban(plugin, playerName, adminName, banReason, banType, playerIP, time, timeUnit, customFlags.size());
		ban.run();
		//Run custom flag handlers
		for (FlagHandler h : customFlags) {
			h.run(playerName, adminName, banReason, flagString, args);
		}

		return true;
	}
}
