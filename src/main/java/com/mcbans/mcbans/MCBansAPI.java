package com.mcbans.mcbans;

import com.mcbans.mcbans.api.MCBansPluginManager;
import com.mcbans.mcbans.utils.*;
import com.mcbans.mcbans.calls.*;
import java.util.HashMap;
import org.bukkit.entity.Player;

//TODO: Move this into the api package

/**
 *
 * @author Sean (rakiru)
 */
public class MCBansAPI {

	public static final int VERSION_MAJOR = 1; //Increments when breakages may occur due to changes
	public static final int VERSION_MINOR = 1; //Increments when a change has been made, resets to 1 when major version is changed
	public static final String VALID_PLAYER_NAME_REGEX = "^\\w{2,16}$";
	private static MCBansAPI instance;
	private MCBansPlugin plugin;
	private MCBansPluginManager pluginManager;
	private Config config;
	private HashMap<String, Boolean> isStaffList = new HashMap<String, Boolean>();

	public MCBansAPI(MCBansPlugin plugin) {
		this.plugin = plugin;
		this.config = plugin.config;
		this.pluginManager = pluginManager;
	}

	/**
	 * Must be called before any other method to do some start-up magic
	 *
	 * @param plugin Instance of the MCBans plugin
	 */
	public static void initialise(MCBansPlugin plugin) {
		MCBansAPI.instance = new MCBansAPI(plugin);
	}

	/**
	 * Get the instance of MCBansAPI
	 *
	 * @return The instance of MCBansAPI
	 */
	public static MCBansAPI getInstance() {
		return instance;
	}

	/**
	 * Ban a player using the MCBans system
	 *
	 * @param playerName Full name of the player to be banned (no partial names)
	 * @param playerIP String representation of the player's IP address
	 * @param type Type of ban: "g" = global, "l" = local
	 * @param reason Reason for the ban
	 * @param adminName Name to be attached as player who placed the ban
	 * @param duration Number of time units to temporarily ban player for
	 * @param measure Type of time units to temporarily ban for
	 */
	private void banPlayer(String playerName, String playerIP, BanType type, String reason, String adminName, String duration, String measure) {
		Ban ban = new Ban(plugin, playerName, adminName, reason, type, playerIP, duration, measure, 0);
		if (type == BanType.GLOBAL_BAN) {
			kickPlayer(playerName, "You have been globally banned. Check MCBans.com", adminName);
		} else {
			kickPlayer(playerName, reason, adminName);
		}
	}

	/**
	 * Ban a player using the MCBans system
	 *
	 * @param playerName Full name of the player to be banned (no partial names)
	 * @param playerIP String representation of the player's IP address
	 * @param type Type of ban: "g" = global, "l" = local
	 * @param reason Reason for the ban
	 * @param adminName Name to be attached as player who placed the ban
	 */
	private void banPlayer(String playerName, String playerIP, BanType type, String reason, String adminName) {
		banPlayer(playerName, playerIP, type, reason, adminName, "", "");
	}

	/**
	 * Ban a player globally using the MCBans system
	 *
	 * @param playerName Full name of the player to be banned (no partial names)
	 * @param playerIP String representation of the player's IP address
	 * @param reason Reason for the ban
	 * @param adminName Name to be attached as player who placed the ban
	 */
	public void globalBanPlayer(String playerName, String playerIP, String reason, String adminName) {
		banPlayer(playerName, playerIP, BanType.GLOBAL_BAN, reason, adminName);
	}

	/**
	 * Ban a player globally using the MCBans system
	 *
	 * @param playerName Full name of the player to be banned (no partial names)
	 * @param reason Reason for the ban
	 * @param adminName Name to be attached as player who placed the ban
	 */
	public void globalBanPlayer(String playerName, String reason, String adminName) {
		globalBanPlayer(playerName, getPlayerIP(playerName), reason, adminName);
	}

	/**
	 * Ban a player globally using the MCBans system as console
	 *
	 * @param playerName Full name of the player to be banned (no partial names)
	 * @param reason Reason for the ban
	 */
	public void globalBanPlayer(String playerName, String reason) {
		globalBanPlayer(playerName, reason, "console");
	}

	/**
	 * Ban a player locally using the MCBans system
	 *
	 * @param playerName Full name of the player to be banned (no partial names)
	 * @param playerIP String representation of the player's IP address
	 * @param reason Reason for the ban
	 * @param adminName Name to be attached as player who placed the ban
	 */
	public void localBanPlayer(String playerName, String playerIP, String reason, String adminName) {
		banPlayer(playerName, playerIP, BanType.LOCAL_BAN, reason, adminName);
	}

	/**
	 * Ban a player locally using the MCBans system
	 *
	 * @param playerName Full name of the player to be banned (no partial names)
	 * @param reason Reason for the ban
	 * @param adminName Name to be attached as player who placed the ban
	 */
	public void localBanPlayer(String playerName, String reason, String adminName) {
		localBanPlayer(playerName, getPlayerIP(playerName), reason, adminName);
	}

	/**
	 * Ban a player locally using the MCBans system as console
	 *
	 * @param playerName Full name of the player to be banned (no partial names)
	 * @param reason Reason for the ban
	 */
	public void localBanPlayer(String playerName, String reason) {
		localBanPlayer(playerName, reason, "console");
	}

	/**
	 * Ban a player locally using the MCBans system for the default ban reason as console
	 *
	 * @param playerName Full name of the player to be banned (no partial names)
	 */
	public void localBanPlayer(String playerName) {
		localBanPlayer(playerName, config.getDefaultLocalBanReason());
	}

	/**
	 * Ban a player for a specific time using the MCBans system
	 *
	 * @param playerName Full name of the player to be banned (no partial names)
	 * @param playerIP String representation of the player's IP address
	 * @param time Number of units the ban should last
	 * @param units Units of time to measure in: "m" = minutes, "h" = hours, "d" = days
	 * @param reason Reason for the ban
	 * @param adminName Name to be attached as player who placed the ban
	 */
	public void tempBanPlayer(String playerName, String playerIP, float time, String units, String reason, String adminName) {
		banPlayer(playerName, playerIP, BanType.TEMP_BAN, reason, adminName);
	}

	/**
	 * Ban a player for a specific time using the MCBans system
	 *
	 * @param playerName Full name of the player to be banned (no partial names)
	 * @param time Number of units the ban should last
	 * @param units Units of time to measure in: "m" = minutes, "h" = hours, "d" = days
	 * @param reason Reason for the ban
	 * @param adminName Name to be attached as player who placed the ban
	 */
	public void tempBanPlayer(String playerName, float time, String units, String reason, String adminName) {
		tempBanPlayer(playerName, getPlayerIP(playerName), time, units, reason, adminName);
	}

	/**
	 * Ban a player for a specific time using the MCBans system
	 *
	 * @param playerName Full name of the player to be banned (no partial names)
	 * @param playerIP String representation of the player's IP address
	 * @param time Number of units the ban should last
	 * @param units Units of time to measure in: "m" = minutes, "h" = hours, "d" = days
	 * @param adminName Name to be attached as player who placed the ban
	 */
	public void tempBanPlayer(String playerName, String playerIP, float time, String units, String adminName) {
		tempBanPlayer(playerName, playerIP, time, units, config.getDefaultTempBanReason(), adminName);
	}

	/**
	 * Ban a player for a specific time using the MCBans system for the default ban reason
	 *
	 * @param playerName Full name of the player to be banned (no partial names)
	 * @param time Number of units the ban should last
	 * @param units Units of time to measure in: "m" = minutes, "h" = hours, "d" = days
	 * @param adminName Name to be attached as player who placed the ban
	 */
	public void tempBanPlayer(String playerName, float time, String units, String adminName) {
		tempBanPlayer(playerName, time, units, config.getDefaultTempBanReason(), adminName);
	}

	/**
	 * Unban a player using the MCBans system
	 *
	 * @param playerName Full name of the player to be banned (no partial names)
	 * @param adminName Name to be attached as player who placed the ban
	 */
	public void unbanPlayer(String playerName, String adminName) {
		banPlayer(playerName, "", BanType.UNBAN, "", adminName);
	}

	/**
	 * Unban a player using the MCBans system as console
	 *
	 * @param playerName Full name of the player to be banned (no partial names)
	 */
	public void unbanPlayer(String playerName) {
		unbanPlayer(playerName, "console");
	}

	/**
	 * Kick a player using the MCBans system (the MCBans plugin will log the kick)
	 *
	 * @param playerName Full name of the player to be banned (no partial names)
	 * @param reason Reason for the kick
	 * @param adminName Name to be attached as player who kicked the player
	 */
	public void kickPlayer(String playerName, String reason, String adminName) {
		Player player = plugin.getServer().getPlayerExact(playerName);
		if (player != null) {
			player.kickPlayer(reason);
		}
		plugin.log.action(adminName + " kicked " + playerName + '[' + player.getAddress().toString() + "] for reason " + reason);
	}

	/**
	 * Kick a player using the MCBans system for the default kick reason (the MCBans plugin will log the kick)
	 *
	 * @param playerName Full name of the player to be banned (no partial names)
	 * @param adminName Name to be attached as player who kicked the player
	 */
	public void kickPlayer(String playerName, String adminName) {
		kickPlayer(playerName, config.getDefaultKickReason(), adminName);
	}

	/**
	 * Kick a player using the MCBans system for the default kick reason  as console(the MCBans plugin will log the kick)
	 *
	 * @param playerName Full name of the player to be banned (no partial names)
	 */
	public void kickPlayer(String playerName) {
		kickPlayer(playerName, "console");
	}

	/**
	 * Checks if a given player is a member of the MCBans team
	 *
	 * @param playerName Full name of the player to be checked
	 * @return Whether or not the player is a member of staff
	 */
	public boolean isMCBansStaff(String playerName) {
		if (isStaffList.containsKey(playerName)) {
			return isStaffList.get(playerName);
		} else {
			//TODO: Call MCBans server and check
			return false;
		}
	}

	public MCBansPluginManager getPluginManager() {
		return pluginManager;
	}

	protected void setMCBansStaff(String playerName, boolean isStaff) {
		isStaffList.put(playerName, isStaff);
	}

	////////////////////////////
	/////// Util methods ///////
	////////////////////////////
	private String getPlayerIP(String playerName) {
		Player player = plugin.getServer().getPlayerExact(playerName);
		if (player != null) {
			return player.getAddress().getAddress().getHostAddress();
		} else {
			return "";
		}
	}
}
