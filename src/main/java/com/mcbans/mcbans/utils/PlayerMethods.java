/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mcbans.mcbans.utils;

import com.mcbans.mcbans.MCBansPlugin;
import java.util.ArrayList;
import java.util.List;
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
			player = (Player)scheduler.callSyncMethod(plugin, new PlayerGetterExact(playerName)).get();
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

	public static List<String> getPlayerNames(MCBansPlugin plugin) throws InterruptedException {
		ArrayList<String> players = new ArrayList();
		BukkitScheduler scheduler = Bukkit.getScheduler();
		try {
			players = (ArrayList)scheduler.callSyncMethod(plugin, new PlayerNamesGetter()).get();
		} catch (ExecutionException e) {
			plugin.log.debug(e.toString());
		}
		return players;
	}

	public static void broadcast(MCBansPlugin plugin, String message) {
		BukkitScheduler scheduler = Bukkit.getScheduler();
		scheduler.scheduleSyncDelayedTask(plugin, new Broadcast(message));
	}

	public static void broadcast(MCBansPlugin plugin, String message, String permissionNode) {
		BukkitScheduler scheduler = Bukkit.getScheduler();
		scheduler.scheduleSyncDelayedTask(plugin, new Broadcast(message, permissionNode));
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

	public static class PlayerNamesGetter implements Callable {

		public PlayerNamesGetter() {
		}

		public Object call() {
			return Bukkit.getOnlinePlayers();
		}
	}

	public static class Broadcast implements Runnable {

		String message;
		String permissionNode;
		ArrayList<String> lines;

		public Broadcast(String message) {
			this.message = message;
			this.permissionNode = "";
			this.lines = null;
		}

		public Broadcast(String message, String permissionNode) {
			this.message = message;
			this.permissionNode = permissionNode;
			this.lines = null;
		}

		private Broadcast(ArrayList<String> lines) {
			this.lines = lines;
			this.permissionNode = "";
			this.message = "";
		}

		public void run() {
			if (lines == null) {
				Bukkit.broadcastMessage(message);
			} else if (!permissionNode.equalsIgnoreCase(permissionNode)) {
				for (Player player : Bukkit.getOnlinePlayers()) {
					if (player.hasPermission(permissionNode)) {
						player.sendMessage(message);
					}
				}
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
