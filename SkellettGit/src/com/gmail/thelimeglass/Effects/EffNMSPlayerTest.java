package com.gmail.thelimeglass.Effects;

import javax.annotation.Nullable;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import com.gmail.thelimeglass.Utils.Disabled;
import com.gmail.thelimeglass.Utils.Syntax;
import com.gmail.thelimeglass.versionControl.ClientBorderManager;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;

@Syntax("skellett player nms of %player%")
@Disabled
public class EffNMSPlayerTest extends Effect {
	
	private Expression<Player> player;
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] e, int matchedPattern, Kleenean isDelayed, ParseResult parser) {
		player = (Expression<Player>) e[0];
		return true;
	}
	@Override
	public String toString(@Nullable Event paramEvent, boolean paramBoolean) {
		return "skellett player nms of %player%";
	}
	@Override
	protected void execute(Event e) {
		Bukkit.getLogger().info(ClientBorderManager.getWarningBlocks(player.getSingle(e)) + "");
		ClientBorderManager.setWarningBlocks(player.getSingle(e), 2);
		Bukkit.getLogger().info(ClientBorderManager.getWarningBlocks(player.getSingle(e)) + "");
		//ClientBorderManager.setSize(player.getSingle(e), 5);
	}
}