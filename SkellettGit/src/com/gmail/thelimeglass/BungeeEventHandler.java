package com.gmail.thelimeglass;

import java.net.InetAddress;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import com.gmail.thelimeglass.SkellettProxy.Events.EvtBungeecordConnect;
import com.gmail.thelimeglass.SkellettProxy.Events.EvtBungeecordDisconnect;

import ch.njol.skript.lang.Effect;

public class BungeeEventHandler {
	
	public static Object handleEvent(BungeeEventPacket packet, InetAddress address) {
		switch (packet.getType()) {
		case PINGSERVER:
			break;
		case PLAYERCHAT:
			/*@SuppressWarnings("unchecked")
			ArrayList<Object> data = (ArrayList<Object>) packet.getObject();
			String msg = (String) data.get(1);
			Player reciever = null;
			if (data.get(2) != null) {
				reciever = Bukkit.getPlayer((UUID) data.get(2));
			}
			EvtBungeecordPlayerChat chatEvent = new EvtBungeecordPlayerChat(player, msg, reciever);
			Bukkit.getPluginManager().callEvent(chatEvent);
			if (chatEvent.isCancelled()) {
				return true;
			}*/
			break;
		case PLAYERDISCONNECT:
			OfflinePlayer playerDisconnect = Bukkit.getOfflinePlayer((UUID) packet.getObject());
			if (playerDisconnect != null) {
				Bukkit.getPluginManager().callEvent(new EvtBungeecordDisconnect(playerDisconnect));
			}
			break;
		case PLAYERLOGIN:
			OfflinePlayer playerLogin = Bukkit.getOfflinePlayer((UUID) packet.getObject());
			if (playerLogin != null) {
				Bukkit.getPluginManager().callEvent(new EvtBungeecordConnect(playerLogin));
			}
			break;
		case EVALUATE:
			String evaluate = (String) packet.getObject();
			Effect e = Effect.parse(evaluate, null);
			e.run(null);
			break;
		}
		return null;
	}
}
	