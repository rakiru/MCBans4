/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fuckingwin.bukkit.rakiru.mcbans.calls;

import com.fuckingwin.bukkit.rakiru.mcbans.MCBansPlugin;
import org.bukkit.entity.Player;

/**
 *
 * @author Sean
 */
public class BaseCall implements Runnable {
	protected final Player player;
	protected final String playerName;
	protected final MCBansPlugin plugin;

	public BaseCall(MCBansPlugin plugin, Player player) {
		this.plugin = plugin;
		this.player = player;
		this.playerName = "";
	}

	public BaseCall(MCBansPlugin plugin, String playerName) {
		this.plugin = plugin;
		this.playerName = playerName;
		this.player = null;
	}

	public void run() {
		// Do your stuff here
	}

}
