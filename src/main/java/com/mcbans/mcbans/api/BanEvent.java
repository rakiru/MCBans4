package com.mcbans.mcbans.api;

import com.mcbans.mcbans.BanType;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.command.CommandSender;

/**
 * Thrown whenever a player is banned or unbanned via the MCBans system
 * @author Sean
 */
public class BanEvent extends Event {

	private static final HandlerList handlers = new HandlerList();
	private String banAdmin;
	private String bannedPlayer;
	private String bannedPlayerIP;
	private String banReason;
	private String duration;
	private String measure;
	private BanType banType;

	public BanEvent(String banAdmin, String bannedPlayer, String playerIP, String banReason, String duration, String measure, BanType banType) {
		this.banAdmin = banAdmin;
		this.bannedPlayer = bannedPlayer;
		this.bannedPlayerIP = playerIP;
		this.banReason = banReason;
		this.duration = duration;
		this.measure = measure;
		this.banType = banType;
	}

	@Override
	public HandlerList getHandlers() {
		return handlers;
	}

	public static HandlerList getHandlerList() {
		return handlers;
	}

	/**
	 * @return the ban issuer's name
	 */
	public String getBanAdmin() {
		return banAdmin;
	}

	/**
	 * @return the banned player's name
	 */
	public String getBannedPlayer() {
		return bannedPlayer;
	}

	/**
	 * @return the banned player's IP
	 */
	public String getBannedPlayerIP() {
		return bannedPlayerIP;
	}

	/**
	 * @return the ban reason
	 */
	public String getBanReason() {
		return banReason;
	}

	/**
	 * @return the time length of a temp ban in measures
	 */
	public String getTimeDuration() {
		return duration;
	}

	/**
	 * @return the time unit of a temp ban
	 */
	public String getTimeMeasure() {
		return measure;
	}

	/**
	 * @return the type of ban
	 */
	public BanType getBanType() {
		return banType;
	}
}
