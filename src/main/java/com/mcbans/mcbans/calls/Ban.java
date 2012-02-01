package com.mcbans.mcbans.calls;

import com.mcbans.mcbans.BanType;
import com.mcbans.mcbans.MCBansPlugin;
import com.mcbans.mcbans.api.BanEvent;
import com.mcbans.mcbans.utils.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.entity.Player;
import org.json.JSONException;
import org.json.JSONObject;
import org.bukkit.Bukkit;

/**
 *
 * @author Sean
 */
public class Ban implements Runnable {

	private String playerName;
	private final MCBansPlugin plugin;
	private final String adminName;
	private String reason;
	private BanType banType;
	private String playerIP;
	private String time;
	private String timeUnit;
	private int handlersLeft;
	public final long timeAdded;

	public Ban(MCBansPlugin plugin, String playerName, String adminName, String reason, BanType banType, String playerIP, String time, String timeUnit, int handlers) {
		this.plugin = plugin;
		this.playerName = playerName;
		this.adminName = adminName;
		this.reason = reason;
		this.handlersLeft = handlers;
		this.banType = banType;
		this.playerIP = playerIP;
		this.time = time;
		this.timeUnit = timeUnit;
		timeAdded = System.currentTimeMillis();
	}

	public synchronized void appendReason(String reasonEnd) {
		reason += ' ' + reasonEnd;
	}

	public synchronized void finishHandler() {
		handlersLeft--;
	}

	public void run() {
		//Allow max of 3 seconds (default) for handlers to do their stuff before continuing
		if (handlersLeft > 0 || timeAdded - System.currentTimeMillis() < plugin.config.getFlagHandlerTimeout()) {
			try {
				Thread.sleep(50);
			} catch (InterruptedException ex) {
				Logger.getLogger(Ban.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
		ban();
		//TODO: Check if the player is online and kick them if they are, in case they got back on during the callback
		//Call the event here so that everything else is done-and-dusted, in case we need to pass more information to it
		Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new EventFirer(adminName, playerName, playerIP, reason, time, timeUnit, banType));
	}

	private void ban() {
		HashMap<String, String> urlItems = new HashMap<String, String>();
		JSONHandler webHandler = new JSONHandler(plugin);
		urlItems.put("player", playerName);
		urlItems.put("playerip", playerIP);
		urlItems.put("reason", reason);
		urlItems.put("admin", adminName);
		urlItems.put("exec", banType.getAPICode());
		HashMap<String, String> result = webHandler.mainRequest(urlItems);
		try {
			//An error happened
			if (!result.containsKey("result")) {
				//TODO: We should probably handle this...
			}
			//Several possible response codes
			if (result.get("result").equals("y")) {
				//Success!
			} else if (result.get("result").equals("e")) {
				//Error :(
			} else if (result.get("result").equals("w")) {
				//Warning
			} else if (result.get("result").equals("s")) {
				//Server group
			} else if (result.get("result").equals("a")) {
				//Already banned
			}
			plugin.log.action(adminName + " attempted to ban " + playerName + '[' + playerIP + " with type " + banType.toString() + " for reason " + reason);
		} catch (NullPointerException ex) {
			plugin.log.debug(ex.toString());
		}
	}
}
