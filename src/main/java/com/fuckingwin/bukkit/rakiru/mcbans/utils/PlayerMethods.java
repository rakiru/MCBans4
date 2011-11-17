/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fuckingwin.bukkit.rakiru.mcbans.utils;

import com.fuckingwin.bukkit.rakiru.mcbans.MCBansPlugin;
import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitScheduler;

/**
 *
 * @author Sean
 */
public class PlayerMethods {

	public static void kickPlayer(MCBansPlugin plugin, Player player, String kickReason) {
		BukkitScheduler scheduler = Bukkit.getScheduler();
		scheduler.scheduleSyncDelayedTask(plugin, new PlayerKicker(player, kickReason));
	}

	public static Player getPlayer(MCBansPlugin plugin, String playerName) throws InterruptedException {
		Player player;
		BukkitScheduler scheduler = Bukkit.getScheduler();
		try {
			player = (Player)scheduler.callSyncMethod(plugin, new PlayerGetter(playerName)).get();
		} catch (ExecutionException e) {
			plugin.log.debug(e.toString());
			player = null;
		}
		return player;
	}

	public static Player getPlayerExact(MCBansPlugin plugin, String playerName) throws InterruptedException {
		Player player;
		BukkitScheduler scheduler = Bukkit.getScheduler();
		try {
			player = (Player)scheduler.callSyncMethod(plugin, new PlayerGetter(playerName)).get();
		} catch (ExecutionException e) {
			plugin.log.debug(e.toString());
			player = null;
		}
		return player;
	}

	public static String getPlayerName(MCBansPlugin plugin, Player player) throws InterruptedException {
		String playerName;
		BukkitScheduler scheduler = Bukkit.getScheduler();
		try {
			playerName = (String)scheduler.callSyncMethod(plugin, new PlayerNameGetter(player)).get();
		} catch (ExecutionException e) {
			plugin.log.debug(e.toString());
			playerName = "";
		}
		return playerName;
	}

	public static void broadcast(MCBansPlugin plugin, String message) {
		BukkitScheduler scheduler = Bukkit.getScheduler();
		scheduler.scheduleSyncDelayedTask(plugin, new Broadcast(message));
	}

	public static void broadcast(MCBansPlugin plugin, ArrayList<String> lines) {
		BukkitScheduler scheduler = Bukkit.getScheduler();
		scheduler.scheduleSyncDelayedTask(plugin, new Broadcast(lines));
	}

	public static void message(MCBansPlugin plugin, CommandSender sender, String message) {
		BukkitScheduler scheduler = Bukkit.getScheduler();
		scheduler.scheduleSyncDelayedTask(plugin, new Message(sender, message));
	}
//Change player to commandsender
	public static void message(MCBansPlugin plugin, CommandSender sender, ArrayList<String> lines) {
		BukkitScheduler scheduler = Bukkit.getScheduler();
		scheduler.scheduleSyncDelayedTask(plugin, new Message(sender, lines));
	}

	public static class PlayerKicker implements Runnable {

		Player player;
		String kickReason;

		public PlayerKicker(Player player, String kickReason) {
			this.player = player;
			this.kickReason = kickReason;
		}

		public void run() {
			player.kickPlayer(kickReason);
		}

	}

	public static class PlayerGetter implements Callable {

		String playerName;

		public PlayerGetter(String playerName) {
			this.playerName = playerName;
		}

		public Object call() {
			return Bukkit.getPlayer(playerName);
		}
	}

	public static class PlayerGetterExact implements Callable {

		String playerName;

		public PlayerGetterExact(String playerName) {
			this.playerName = playerName;
		}

		public Object call() {
			return Bukkit.getPlayerExact(playerName);
		}
	}

	public static class PlayerNameGetter implements Callable {

		Player player;

		public PlayerNameGetter(Player player) {
			this.player = player;
		}

		public Object call() {
			return player.getName();
		}
	}

	public static class Broadcast implements Runnable {

		String message;
		ArrayList<String> lines;

		public Broadcast(String message) {
			this.message = message;
			this.lines = null;
		}

		private Broadcast(ArrayList<String> lines) {
			this.lines = lines;
			this.message = "";
		}

		public void run() {
			if (lines == null) {
				Bukkit.broadcastMessage(message);
			} else {
				for (String line : lines) {
					Bukkit.broadcastMessage(line);
				}
			}
		}

	}

	public static class Message implements Runnable {

		String message;
		ArrayList<String> lines;
		CommandSender sender;

		public Message(CommandSender sender, String message) {
			this.sender = sender;
			this.message = message;
			this.lines = null;
		}

		private Message(CommandSender sender, ArrayList<String> lines) {
			this.sender = sender;
			this.lines = lines;
			this.message = "";
		}

		public void run() {
			if (lines == null) {
				sender.sendMessage(message);
			} else {
				for (String line : lines) {
					sender.sendMessage(line);
				}
			}
		}

	}
}
