package com.mcbans.mcbans.calls;

import com.mcbans.mcbans.BanType;
import com.mcbans.mcbans.api.BanEvent;
import org.bukkit.Bukkit;

/**
 *
 * @author Sean
 */
class EventFirer implements Runnable {

	String adminName;
	String playerName;
	String playerIP;
	String reason;
	String time;
	String timeUnit;
	BanType banType;

	public EventFirer(String adminName, String playerName, String playerIP, String reason, String time, String timeUnit, BanType banType) {
		this.adminName = adminName;
		this.playerName = playerName;
		this.playerIP = playerIP;
		this.reason = reason;
		this.time = time;
		this.timeUnit = timeUnit;
		this.banType = banType;
	}

	public void run() {
		Bukkit.getServer().getPluginManager().callEvent(new BanEvent(adminName, playerName, playerIP, reason, time, timeUnit, banType));
	}

}
