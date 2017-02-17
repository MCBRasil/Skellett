package com.gmail.thelimeglass;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.black_ixx.playerpoints.PlayerPoints;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarFlag;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;
import org.tyrannyofheaven.bukkit.zPermissions.ZPermissionsService;

import com.gmail.thelimeglass.BossBars.CondBarHasFlag;
import com.gmail.thelimeglass.BossBars.EffBarAddFlag;
import com.gmail.thelimeglass.BossBars.EffBarAddPlayer;
import com.gmail.thelimeglass.BossBars.EffBarHideAndShow;
import com.gmail.thelimeglass.BossBars.EffBarRemoveAllPlayers;
import com.gmail.thelimeglass.BossBars.EffBarRemoveFlag;
import com.gmail.thelimeglass.BossBars.EffBarRemovePlayer;
import com.gmail.thelimeglass.BossBars.ExprBarColour;
import com.gmail.thelimeglass.BossBars.ExprBarFlags;
import com.gmail.thelimeglass.BossBars.ExprBarPlayers;
import com.gmail.thelimeglass.BossBars.ExprBarProgress;
import com.gmail.thelimeglass.BossBars.ExprBarStyle;
import com.gmail.thelimeglass.BossBars.ExprBarTitle;
import com.gmail.thelimeglass.BossBars.ExprBarVisible;
import com.gmail.thelimeglass.BossBars.ExprNewBossBar;
import com.gmail.thelimeglass.Conditions.CondClientTimeRelative;
import com.gmail.thelimeglass.Conditions.CondFileExists;
import com.gmail.thelimeglass.Conditions.CondIsWhitelisted;
import com.gmail.thelimeglass.Conditions.CondLineOfSight;
import com.gmail.thelimeglass.Effects.EffFirework;
import com.gmail.thelimeglass.Expressions.ExprIsCollidable;
import com.gmail.thelimeglass.Expressions.ExprYaml;
import com.gmail.thelimeglass.Feudal.ExprFeudalKingdomDescription;
import com.gmail.thelimeglass.Feudal.ExprFeudalKingdomFighters;
import com.gmail.thelimeglass.Feudal.ExprFeudalKingdomHome;
import com.gmail.thelimeglass.Feudal.ExprFeudalLocationKingdom;
import com.gmail.thelimeglass.Feudal.ExprFeudalLocationKingdomName;
import com.gmail.thelimeglass.Feudal.ExprFeudalMessage;
import com.gmail.thelimeglass.Feudal.ExprFeudalPlayerKingdom;
import com.gmail.thelimeglass.Feudal.ExprFeudalPlayerKingdomName;
import com.gmail.thelimeglass.Maps.SkellettMapRenderer;
import com.gmail.thelimeglass.MySQL.EffMySQLConnect;
import com.gmail.thelimeglass.MySQL.EffMySQLDisconnect;
import com.gmail.thelimeglass.MySQL.EffMySQLUpdate;
import com.gmail.thelimeglass.MySQL.ExprMySQLDatabase;
import com.gmail.thelimeglass.MySQL.ExprMySQLHost;
import com.gmail.thelimeglass.MySQL.ExprMySQLPassword;
import com.gmail.thelimeglass.MySQL.ExprMySQLPort;
import com.gmail.thelimeglass.MySQL.ExprMySQLQuery;
import com.gmail.thelimeglass.MySQL.ExprMySQLQueryBoolean;
import com.gmail.thelimeglass.MySQL.ExprMySQLQueryInteger;
import com.gmail.thelimeglass.MySQL.ExprMySQLQueryNumber;
import com.gmail.thelimeglass.MySQL.ExprMySQLQueryObject;
import com.gmail.thelimeglass.MySQL.ExprMySQLQueryString;
import com.gmail.thelimeglass.MySQL.ExprMySQLUsername;
import com.gmail.thelimeglass.OITB.CondOITBHasCooldown;
import com.gmail.thelimeglass.OITB.EffOITBAddCoins;
import com.gmail.thelimeglass.OITB.EffOITBRemoveCoins;
import com.gmail.thelimeglass.OITB.EffOITBSetCoins;
import com.gmail.thelimeglass.OITB.EffOITBSetCooldown;
import com.gmail.thelimeglass.OITB.ExprOITBGetChallengeWins;
import com.gmail.thelimeglass.OITB.ExprOITBGetCoins;
import com.gmail.thelimeglass.OITB.ExprOITBGetDeaths;
import com.gmail.thelimeglass.OITB.ExprOITBGetHighestZombiesWave;
import com.gmail.thelimeglass.OITB.ExprOITBGetHits;
import com.gmail.thelimeglass.OITB.ExprOITBGetKills;
import com.gmail.thelimeglass.OITB.ExprOITBGetModifier;
import com.gmail.thelimeglass.OITB.ExprOITBGetPlayTime;
import com.gmail.thelimeglass.OITB.ExprOITBGetPlayerExp;
import com.gmail.thelimeglass.OITB.ExprOITBGetPlayerRank;
import com.gmail.thelimeglass.OITB.ExprOITBGetShotsfired;
import com.gmail.thelimeglass.OITB.ExprOITBGetTopPlayers;
import com.gmail.thelimeglass.OITB.ExprOITBGetTopPlayersWithScore;
import com.gmail.thelimeglass.OITB.ExprOITBGetTopScores;
import com.gmail.thelimeglass.OITB.ExprOITBGetTournamentWins;
import com.gmail.thelimeglass.OITB.ExprOITBGetZombieKills;
import com.gmail.thelimeglass.ProtocolSupport.ExprBlockRemapperItemType;
import com.gmail.thelimeglass.ProtocolSupport.ExprItemRemapperID;
import com.gmail.thelimeglass.ProtocolSupport.ExprItemRemapperItemType;
import com.gmail.thelimeglass.ProtocolSupport.ExprProtocolVersion;
import com.gmail.thelimeglass.Scoreboards.CondObjectiveExists;
import com.gmail.thelimeglass.Scoreboards.CondObjectiveIsModifiable;
import com.gmail.thelimeglass.Scoreboards.CondTeamHasEntry;
import com.gmail.thelimeglass.Scoreboards.EffDeleteScoreboard;
import com.gmail.thelimeglass.Scoreboards.EffRegisterObjective;
import com.gmail.thelimeglass.Scoreboards.EffRegisterTeam;
import com.gmail.thelimeglass.Scoreboards.EffResetEntryScores;
import com.gmail.thelimeglass.Scoreboards.EffScoreboardClearSlot;
import com.gmail.thelimeglass.Scoreboards.EffTeamAddEntry;
import com.gmail.thelimeglass.Scoreboards.EffTeamRemoveEntry;
import com.gmail.thelimeglass.Scoreboards.EffUnregisterObjective;
import com.gmail.thelimeglass.Scoreboards.EffUnregisterTeam;
import com.gmail.thelimeglass.Scoreboards.ExprEntries;
import com.gmail.thelimeglass.Scoreboards.ExprGetEntryScores;
import com.gmail.thelimeglass.Scoreboards.ExprGetEntryTeam;
import com.gmail.thelimeglass.Scoreboards.ExprGetObjective;
import com.gmail.thelimeglass.Scoreboards.ExprGetScoreboard;
import com.gmail.thelimeglass.Scoreboards.ExprGetTeam;
import com.gmail.thelimeglass.Scoreboards.ExprNewScoreboard;
import com.gmail.thelimeglass.Scoreboards.ExprObjectiveCriteria;
import com.gmail.thelimeglass.Scoreboards.ExprObjectiveDisplayName;
import com.gmail.thelimeglass.Scoreboards.ExprObjectiveDisplaySlot;
import com.gmail.thelimeglass.Scoreboards.ExprObjectiveGetScore;
import com.gmail.thelimeglass.Scoreboards.ExprObjectiveName;
import com.gmail.thelimeglass.Scoreboards.ExprObjectives;
import com.gmail.thelimeglass.Scoreboards.ExprObjectivesByCriteria;
import com.gmail.thelimeglass.Scoreboards.ExprPlayerScoreboard;
import com.gmail.thelimeglass.Scoreboards.ExprScore;
import com.gmail.thelimeglass.Scoreboards.ExprScoreEntry;
import com.gmail.thelimeglass.Scoreboards.ExprScoreObjective;
import com.gmail.thelimeglass.Scoreboards.ExprTeamDisplayName;
import com.gmail.thelimeglass.Scoreboards.ExprTeamEntries;
import com.gmail.thelimeglass.Scoreboards.ExprTeamFriendlyFire;
import com.gmail.thelimeglass.Scoreboards.ExprTeamFriendlyInvisibles;
import com.gmail.thelimeglass.Scoreboards.ExprTeamName;
import com.gmail.thelimeglass.Scoreboards.ExprTeamOptions;
import com.gmail.thelimeglass.Scoreboards.ExprTeamPrefix;
import com.gmail.thelimeglass.Scoreboards.ExprTeamSize;
import com.gmail.thelimeglass.Scoreboards.ExprTeamSuffix;
import com.gmail.thelimeglass.Scoreboards.ExprTeams;
import com.gmail.thelimeglass.Stylishboards.StyleManager;
import com.gmail.thelimeglass.Utils.AntiDependency;
import com.gmail.thelimeglass.Utils.AntiDependencyEnum;
import com.gmail.thelimeglass.Utils.Config;
import com.gmail.thelimeglass.Utils.Dependency;
import com.gmail.thelimeglass.Utils.DetectVersion;
import com.gmail.thelimeglass.Utils.Disabled;
import com.gmail.thelimeglass.Utils.EnumClassInfo;
import com.gmail.thelimeglass.Utils.FullConfig;
import com.gmail.thelimeglass.Utils.MainConfig;
import com.gmail.thelimeglass.Utils.PropertyType;
import com.gmail.thelimeglass.Utils.RegisterEnum;
import com.gmail.thelimeglass.Utils.RegisterSimpleEnum;
import com.gmail.thelimeglass.Utils.SkellettProxy;
import com.gmail.thelimeglass.Utils.Syntax;
import com.gmail.thelimeglass.Utils.TypeClassInfo;
import com.gmail.thelimeglass.Utils.Version;
import com.sainttx.holograms.api.HologramManager;
import com.sainttx.holograms.api.HologramPlugin;
import ch.njol.skript.Skript;
import ch.njol.skript.aliases.ItemType;
import ch.njol.skript.lang.Condition;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.util.SimpleEvent;
import ch.njol.skript.registrations.Classes;
import ch.njol.skript.util.Timespan;
import nl.lolmewn.stats.api.StatsAPI;
import nl.lolmewn.stats.bukkit.api.event.StatsHolderUpdateEvent;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.api.remapper.BlockRemapperControl.MaterialAndData;
import us.forseth11.feudal.kingdoms.Kingdom;

public class Skellett extends JavaPlugin {
	
	public static final HashMap<String, Scoreboard> skellettBoards = new HashMap<>();
	public static Skellett instance;
	public static Plugin plugin;
	public static String prefix = "&8[&aSkellett&8] &e";
	public FileConfiguration config = getConfig();
	private File ceFile;
	private FileConfiguration ceData;
	public static File spFile;
	public static FileConfiguration spData;
	private File mysqlFile;
	public static FileConfiguration mysqlData;
	private File syntaxFile;
	private static FileConfiguration syntaxData;
	public static File syntaxToggleFile;
	public static FileConfiguration syntaxToggleData;
	public static ZPermissionsService zPermissions;
	public static HologramManager hologramManager;
	public static StatsAPI statsAPI;
	public static PlayerPoints playerPoints;
	
	public void onEnable(){
		instance = this;
		plugin = this;
		getCommand("skellett").setExecutor(new Command());
		getServer().getPluginManager().registerEvents(new Command(), this);
		if (!Objects.equals(getDescription().getVersion(), config.getString("version"))) {
			File f = new File(getDataFolder(), "config.yml");
			if (f.exists()) {
				f.delete();
			}
			File f2 = new File(getDataFolder(), "SyntaxToggles.yml");
			if (f2.exists()) {
				f2.delete();
			}
			Bukkit.getConsoleSender().sendMessage(cc(prefix + "&6New update found! Updating files..."));
			saveDefaultConfig();
		}
		try {
			if (!getDataFolder().exists()) {
				getDataFolder().mkdirs();
			}
			File file = new File(getDataFolder(), "config.yml");
			ceFile = new File(getDataFolder(), "CustomEvents.yml");
			spFile = new File(getDataFolder(), "SkellettProxy.yml");
			mysqlFile = new File(getDataFolder(), "MySQL.yml");
			syntaxFile = new File(getDataFolder(), "Syntax.yml");
			syntaxToggleFile = new File(getDataFolder(), "SyntaxToggles.yml");
			if (!file.exists()) {
				Bukkit.getConsoleSender().sendMessage(cc(prefix + "&cconfig.yml not found, generating a new config!"));
				saveDefaultConfig();
			}
			if (!ceFile.exists()) {
				ceFile.getParentFile().mkdirs();
				Bukkit.getConsoleSender().sendMessage(cc(prefix + "&cCustomEvents.yml not found, generating a new config!"));
				saveResource("CustomEvents.yml", false);
			}
			ceData = new YamlConfiguration();
			try {
				ceData.load(ceFile);
		 	} catch (IOException e) {
		 		e.printStackTrace();
		 	}
			if (!spFile.exists()) {
				spFile.getParentFile().mkdirs();
				Bukkit.getConsoleSender().sendMessage(cc(prefix + "&cSkellettProxy.yml not found, generating a new file!"));
				saveResource("SkellettProxy.yml", false);
			}
			spData = new YamlConfiguration();
			try {
				spData.load(spFile);
		 	} catch (IOException e) {
		 		e.printStackTrace();
		 	}
			if (!mysqlFile.exists()) {
				mysqlFile.getParentFile().mkdirs();
				Bukkit.getConsoleSender().sendMessage(cc(prefix + "&cMySQL.yml not found, generating a new file!"));
				saveResource("MySQL.yml", false);
			}
			mysqlData = new YamlConfiguration();
			try {
				mysqlData.load(mysqlFile);
		 	} catch (IOException e) {
		 		e.printStackTrace();
		 	}
			if (!syntaxFile.exists()) {
				syntaxFile.getParentFile().mkdirs();
				Bukkit.getConsoleSender().sendMessage(cc(prefix + "&cSyntax.yml not found, generating a new file!"));
				saveResource("Syntax.yml", false);
			}
			syntaxData = new YamlConfiguration();
			try {
				syntaxData.load(syntaxFile);
		 	} catch (IOException e) {
		 		e.printStackTrace();
		 	}
			if (!syntaxToggleFile.exists()) {
				syntaxToggleFile.getParentFile().mkdirs();
				Bukkit.getConsoleSender().sendMessage(cc(prefix + "&cSyntaxToggles.yml not found, generating a new file!"));
				saveResource("SyntaxToggles.yml", false);
			}
			syntaxToggleData = new YamlConfiguration();
			try {
				syntaxToggleData.load(syntaxToggleFile);
		 	} catch (IOException e) {
		 		e.printStackTrace();
		 	}
		} catch (Exception error) {
			error.printStackTrace();
		}
		Skript.registerAddon(this);
		Register.events();
		Register.types();
		if (syntaxToggleData.getBoolean("Syntax.Expressions.CollidableState")) {
			Skript.registerExpression(ExprIsCollidable.class,Boolean.class,ExpressionType.SIMPLE, "collid(e|able) [state] [of] %entity%");
		}
		if (syntaxToggleData.getBoolean("Syntax.Conditions.LineOfSight")) {
			Skript.registerCondition(CondLineOfSight.class, "%entity% [can] (see|visibly see|line of sight) [can see] %entity%");
		}
		if (syntaxToggleData.getBoolean("Syntax.Conditions.ClientTime")) {
			Skript.registerCondition(CondClientTimeRelative.class, "[skellett] [client] relative time of %player% [is] [%-boolean%] [relative] [to] [server]");
		}
		if (syntaxToggleData.getBoolean("Syntax.Conditions.Whitelist")) {
			Skript.registerCondition(CondIsWhitelisted.class, "[server] whitelist[ed] [state]");
		}
		if (config.getBoolean("PluginHooks.zPermissions")) {
			try {
				zPermissions = Bukkit.getServicesManager().load(ZPermissionsService.class);
			}
			catch (NoClassDefFoundError e) {}
		}
		if (config.getBoolean("PluginHooks.PlayerPoints")) {
			Plugin plugin = getServer().getPluginManager().getPlugin("PlayerPoints");
			playerPoints = PlayerPoints.class.cast(plugin);
		}
		if (config.getBoolean("PluginHooks.StatsAPI")) {
			try {
				@SuppressWarnings("unchecked")
				RegisteredServiceProvider<StatsAPI> stats = (RegisteredServiceProvider<StatsAPI>) Bukkit.getServicesManager().getRegistrations(nl.lolmewn.stats.api.StatsAPI.class);
				statsAPI = stats.getProvider();
				Skript.registerEvent("[on] stats[[ ]api] update:", SimpleEvent.class, StatsHolderUpdateEvent.class, "[on] stats[[ ]api] update");
			}
			catch (NoClassDefFoundError e) {}
		}
		if (config.getBoolean("PluginHooks.Holograms")) {
			hologramManager = JavaPlugin.getPlugin(HologramPlugin.class).getHologramManager();
		}
		if (syntaxToggleData.getBoolean("Main.Bossbars")) {
			if (!Bukkit.getServer().getVersion().contains("MC: 1.6") && !Bukkit.getServer().getVersion().contains("MC: 1.7") && !Bukkit.getServer().getVersion().contains("MC: 1.8")) {
				Skript.registerExpression(ExprNewBossBar.class, BossBar.class, ExpressionType.SIMPLE, "[skellett] [create] [a] new [boss[ ]]bar [with flag %-barflag%]");
				Skript.registerEffect(EffBarAddPlayer.class, "[skellett] add %player% to [the] [boss[ ]]bar %bossbar%");
				Skript.registerExpression(ExprBarVisible.class, Boolean.class, ExpressionType.SIMPLE, "[the] [skellett] visib(le|ility) [(for|of)] [boss[ ]]bar %bossbar%", "[skellett] %bossbar%'s [[boss][ ]bar] visib(le|ility)");
				Skript.registerEffect(EffBarRemovePlayer.class, "[skellett] remove %player% from [the] [boss[ ]]bar %bossbar%");
				Skript.registerExpression(ExprBarColour.class, BarColor.class, ExpressionType.SIMPLE, "[the] [skellett] colo[u]r of [boss[ ]]bar %bossbar%", "[skellett] %bossbar%'s [[boss][ ]bar] colo[u]r");
				Skript.registerExpression(ExprBarStyle.class, BarStyle.class, ExpressionType.SIMPLE, "[the] [skellett] style of [boss[ ]]bar %bossbar%", "[skellett] %bossbar%'s [[boss][ ]bar] style");
				Skript.registerExpression(ExprBarPlayers.class, Player.class, ExpressionType.SIMPLE, "[skellett] [(the|all)] [of] [the] player[[']s] [(in|of)] [the] [boss[ ]]bar %bossbar%", "[skellett] %bossbar%'s player[[']s]");
				Skript.registerExpression(ExprBarProgress.class, Number.class, ExpressionType.SIMPLE, "[the] [skellett] progress of [boss[ ]]bar %bossbar%", "[skellett] %bossbar%'s [[boss][ ]bar] progress");
				Skript.registerExpression(ExprBarTitle.class, String.class, ExpressionType.SIMPLE, "[the] [skellett] (title|name|header|string) of [boss[ ]]bar %bossbar%", "[skellett] %bossbar%'s [boss[ ]]bar (title|name|header|string)");
				Skript.registerCondition(CondBarHasFlag.class, "[skellett] [boss[ ]][bar] %bossbar% (1�(ha(s|ve)|contain[s])|2�(do[es](n't| not) have| do[es](n't| not) contain)) [(the|a)] [boss[ ]][bar] [flag] %barflag%");
				Skript.registerEffect(EffBarRemoveAllPlayers.class, "[skellett] remove [(the|all)] [of] [the] player[[']s] [(in|of)] [the] [boss[ ]]bar %bossbar%");
				Skript.registerEffect(EffBarRemoveFlag.class, "[skellett] remove [boss[ ]][bar] [flag] %barflag% from [the] [boss[ ]][bar] %bossbar%");
				Skript.registerEffect(EffBarAddFlag.class, "[skellett] add [boss[ ]][bar] [flag] %barflag% to [the] [boss[ ]][bar] %bossbar%");
				Skript.registerEffect(EffBarHideAndShow.class, "[skellett] (1�hide|2�show) [boss[ ]]bar %bossbar%");
				Skript.registerExpression(ExprBarFlags.class, BarFlag.class, ExpressionType.SIMPLE, "[skellett] [(the|all)] [of] [the] flag[[']s] [(in|of)] [the] [boss[ ]]bar %bossbar%", "[skellett] %bossbar%'s flag[[']s]");
			}
		}
		if (syntaxToggleData.getBoolean("Main.Scoreboards")) {
			Skript.registerExpression(ExprGetScoreboard.class, Scoreboard.class, ExpressionType.SIMPLE, "[get] (score[ ][board]|[skellett[ ]]board)) [(with|named)] [(name|id)] %string%");
			Skript.registerExpression(ExprNewScoreboard.class, Scoreboard.class, ExpressionType.SIMPLE, "[create] [a] new (score[ ][board]|[skellett[ ]]board) [(with|named)] [(name|id)] %string%");
			Skript.registerExpression(ExprPlayerScoreboard.class, Scoreboard.class, ExpressionType.SIMPLE, "(score[ ][board]|[skellett[ ]]board) of [player] %player%", "%player%'s (score[ ][board]|[skellett[ ]]board)");
			Skript.registerEffect(EffDeleteScoreboard.class, "(delete|clear|remove) (score[ ][board]|[skellett[ ]]board)) [(with|named)] [(name|id)] %string%");
			Skript.registerExpression(ExprGetObjective.class, Objective.class, ExpressionType.SIMPLE, "[the] (score[ ][board]|[skellett[ ]]board) objective %string% [[(in|from)] (score[ ][board]|[skellett[ ]]board) [%-scoreboard%]]");
			Skript.registerExpression(ExprObjectiveCriteria.class, String.class, ExpressionType.SIMPLE, "[the] (score[ ][board]|[skellett[ ]]board) objective criteria [of] %objective%", "[the] (score[ ][board]|[skellett[ ]]board) %objective%'s objective criteria");
			Skript.registerEffect(EffRegisterObjective.class, "register [new] (score[ ][board]|[skellett[ ]]board) objective %string% with [criteria] %string% [[(in|from)] %-scoreboard%]", "register [new] objective %string% with [criteria] %string% [(in|from)] (score[ ][board]|[skellett[ ]]board) [%-scoreboard%]");
			Skript.registerCondition(CondObjectiveExists.class, "objective %string% [[(in|from)] (score[ ][board]|[skellett[ ]]board) [%-scoreboard%]] (1�(is set|[does] exist[s])|2�(is(n't| not) set|does(n't| not) exist[s]))");
			Skript.registerExpression(ExprObjectives.class, Objective.class, ExpressionType.SIMPLE, "[(the|all)] [of] [the] [(score[ ][board]|board)[[']s]] objectives [[(in|from)] (score[ ][board]|[skellett[ ]]board) [%-scoreboard%]]");
			Skript.registerExpression(ExprObjectivesByCriteria.class, Objective.class, ExpressionType.SIMPLE, "[(the|all)] [of] [the] (score[ ][board]|board)[[']s] objectives (by|with) [criteria] %string% [[(in|from)] (score[ ][board]|[skellett[ ]]board) [%-scoreboard%]]");
			Skript.registerEffect(EffUnregisterObjective.class, "unregister (score[ ][board]|[skellett[ ]]board) objective %objective%");
			Skript.registerExpression(ExprObjectiveDisplayName.class, String.class, ExpressionType.SIMPLE, "[the] (score[ ][board]|[skellett[ ]]board) objective display name [(for|from|of)] %objective%", "[the] (score[ ][board]|[skellett[ ]]board) %objective%['s] objective['s] display name", "[the] (score[ ][board]|[skellett[ ]]board) objective %objective%['s] display name");
			Skript.registerExpression(ExprObjectiveName.class, String.class, ExpressionType.SIMPLE, "[the] (score[ ][board]|[skellett[ ]]board) objective name [(for|from|of)] %objective%", "[the] (score[ ][board]|[skellett[ ]]board) %objective%['s] objective['s] name", "[the] (score[ ][board]|[skellett[ ]]board) objective %objective%['s] name");
			Skript.registerExpression(ExprObjectiveDisplaySlot.class, String.class, ExpressionType.SIMPLE, "[the] (score[ ][board]|[skellett[ ]]board) objective [display] slot [(for|from|of)] %objective%", "[the] (score[ ][board]|[skellett[ ]]board) %objective%['s] objective['s] [display] slot", "[the] (score[ ][board]|[skellett[ ]]board) objective %objective%['s] [display] slot");
			Skript.registerCondition(CondObjectiveIsModifiable.class, "[the] (score[ ][board]|[skellett[ ]]board) objective %objective% (1�is modifiable|2�is(n't| not) modifiable)");
			Skript.registerExpression(ExprObjectiveGetScore.class, Score.class, ExpressionType.SIMPLE, "[the] (score[ ][board]|[skellett[ ]]board) [objective] %objective%['s] score [(for|from|of)] [entry] %string%", "[the] (score[ ][board]|[skellett[ ]]board) %objective%['s] [objective['s]] score [(for|from|of)] [entry] %string%");
			Skript.registerEffect(EffScoreboardClearSlot.class, "clear (score[ ][board]|board) [display] slot %string% [[(in|from)] (score[ ][board]|[skellett[ ]]board) [%-scoreboard%]]");
			Skript.registerExpression(ExprEntries.class, String.class, ExpressionType.SIMPLE, "[(the|all)] [of] [the] (score[ ][board]|board)[[']s] entr(ies|y[[']s]) [[(in|from)] (score[ ][board]|[skellett[ ]]board) [%-scoreboard%]]");
			Skript.registerExpression(ExprGetEntryTeam.class, Team.class, ExpressionType.SIMPLE, "[the] (score[ ][board]|board) team of [entry] %string% [[(in|from)] (score[ ][board]|[skellett[ ]]board) [%-scoreboard%]]", "[the] (score[ ][board]|board) [entry] %string%'s team [[(in|from)] (score[ ][board]|[skellett[ ]]board) [%-scoreboard%]]");
			Skript.registerExpression(ExprGetEntryScores.class, Score.class, ExpressionType.SIMPLE, "[(the|all)] [of] [the] (score[ ][board]|[skellett[ ]]board) scores of [entry] %string% [[(in|from)] (score[ ][board]|[skellett[ ]]board) [%-scoreboard%]]", "[(the|all)] [of] [the] (score[ ][board]|[skellett[ ]]board) [entry] %string%'s scores [[(in|from)] (score[ ][board]|[skellett[ ]]board) [%-scoreboard%]]");
			Skript.registerEffect(EffResetEntryScores.class, "reset [(the|all)] [of] [the] (score[ ][board]|[skellett[ ]]board) scores of [entry] %string% [[(in|from)] (score[ ][board]|[skellett[ ]]board) [%-scoreboard%]]", "reset [(the|all)] [of] [the] (score[ ][board]|[skellett[ ]]board) [entry] %string%'s scores [[(in|from)] (score[ ][board]|[skellett[ ]]board) [%-scoreboard%]]", "(score[ ][board]|[skellett[ ]]board) reset [(the|all)] [of] [the] scores of [entry] %string% [[(in|from)] (score[ ][board]|[skellett[ ]]board) [%-scoreboard%]]");
			Skript.registerEffect(EffRegisterTeam.class, "register [a] [new] (score[ ][board]|[skellett[ ]]board) team %string% [[(in|from)] (score[ ][board]|[skellett[ ]]board) [%-scoreboard%]]");
			Skript.registerExpression(ExprTeams.class, Team.class, ExpressionType.SIMPLE, "[(the|all)] [of] [the] teams [[(in|from)] (score[ ][board]|[skellett[ ]]board) [%-scoreboard%]]");
			Skript.registerExpression(ExprGetTeam.class, Team.class, ExpressionType.SIMPLE, "[the] (score[ ][board]|[skellett[ ]]board) %string% team [[(in|from)] (score[ ][board]|[skellett[ ]]board) [%-scoreboard%]]", "(score[ ][board]|[skellett[ ]]board) [get] team %string% [[(in|from)] (score[ ][board]|[skellett[ ]]board) [%-scoreboard%]]");
			Skript.registerExpression(ExprScoreEntry.class, String.class, ExpressionType.SIMPLE, "[the] (score[ ][board]|[skellett[ ]]board) [get] entry [(for|from|of)] score %score%", "(score[ ][board]|[skellett[ ]]board) %score%'s score entry");
			Skript.registerExpression(ExprScoreObjective.class, Objective.class, ExpressionType.SIMPLE, "[the] (score[ ][board]|[skellett[ ]]board) objective [(for|from|of)] score %score%", "[the] (score[ ][board]|[skellett[ ]]board) %score%'s scores objective");
			Skript.registerExpression(ExprScore.class, Number.class, ExpressionType.SIMPLE, "[the] (score[ ][board]|[skellett[ ]]board) (score|number|slot) [(for|from|of)] %score%", "(score[ ][board]|[skellett[ ]]board) %score%'s (score|number|slot)");
			Skript.registerEffect(EffTeamAddEntry.class, "(score[ ][board]|[skellett[ ]]board) add [the] entry [(from|of)] %string% to [the] [team] %team%");
			Skript.registerExpression(ExprTeamFriendlyFire.class, Boolean.class, ExpressionType.SIMPLE, "[the] [(score[ ][board]|[skellett[ ]]board)] friendly [fire] state [(for|of)] [team] %team%");
			Skript.registerExpression(ExprTeamFriendlyInvisibles.class, Boolean.class, ExpressionType.SIMPLE, "[the] (score[ ][board]|[skellett[ ]]board) [friendly] invisible[s] [state] [(for|of)] [team] %team%");
			Skript.registerExpression(ExprTeamDisplayName.class, String.class, ExpressionType.SIMPLE, "[the] [(score[ ][board]|[skellett[ ]]board)] team display name [(for|from|of)] %team%");
			Skript.registerExpression(ExprTeamEntries.class, String.class, ExpressionType.SIMPLE, "[(the|all)] [of] [the] (score[ ][board]|[skellett[ ]]board)[[']s] entr(ies|y[[']s]) (in|within) [the] [team] %team%");
			Skript.registerExpression(ExprTeamName.class, String.class, ExpressionType.SIMPLE, "[the] (score[ ][board]|[skellett[ ]]board)) [team] name [(for|of)] [team] %team%");
			if (!Bukkit.getServer().getVersion().contains("MC: 1.6") && !Bukkit.getServer().getVersion().contains("MC: 1.7") && !Bukkit.getServer().getVersion().contains("MC: 1.8")) {
				Skript.registerExpression(ExprTeamOptions.class, Team.OptionStatus.class, ExpressionType.SIMPLE, "[the] (score[ ][board]|[skellett[ ]]board) [team] option[s] [status] %teamoption% [(for|of)] [the] [team] %team%");
			}
			Skript.registerExpression(ExprTeamPrefix.class, String.class, ExpressionType.SIMPLE, "[(score[ ][board]|[skellett[ ]]board)] [team] prefix [(for|of)] [team] %team%");
			Skript.registerExpression(ExprTeamSuffix.class, String.class, ExpressionType.SIMPLE, "[(score[ ][board]|[skellett[ ]]board)] [team] suffix [(for|of)] [team] %team%");
			Skript.registerExpression(ExprTeamSize.class, Number.class, ExpressionType.SIMPLE, "[(score[ ][board]|[skellett[ ]]board)] team size [(for|of)] [team] %team%");
			Skript.registerCondition(CondTeamHasEntry.class, "[the] (score[ ][board]|[skellett[ ]]board) (1�(ha(s|ve)|contain[s])|2�(do[es](n't| not) have| do[es](n't| not) contain)) [the] [entry] %string% [(in|within)] the [team] %team%");
			Skript.registerEffect(EffTeamRemoveEntry.class, "[(score[ ][board]|[skellett[ ]]board)] remove [the] entry [(from|of)] %string% from [the] [team] %team%");
			Skript.registerEffect(EffUnregisterTeam.class, "unregister [the] (score[ ][board]|[skellett[ ]]board) team %team%");
		}
		if (config.getBoolean("PluginHooks.OITB")) {
			if (Bukkit.getPluginManager().getPlugin("OneInTheBattle") != null) {
				Skript.registerEffect(EffOITBAddCoins.class, "[OITB] (add|give) %integer% coin[s] to %player%");
				Skript.registerEffect(EffOITBRemoveCoins.class, "[OITB] (remove|take|subtract) %integer% coin[s] from %player%");
				Skript.registerEffect(EffOITBSetCoins.class, "[OITB] [set] [player] coins of %player% to %integer%");
				Skript.registerEffect(EffOITBSetCooldown.class, "[OITB] [set] %player% cool[ ]down of [ability] %string% to %integer%");
				Skript.registerExpression(ExprOITBGetChallengeWins.class,Integer.class,ExpressionType.SIMPLE, "[OITB] [get] Challenge[s][ ](win[s]|won) of %player%");
				Skript.registerExpression(ExprOITBGetCoins.class,Integer.class,ExpressionType.SIMPLE, "[OITB] [get] coins of %player%");
				Skript.registerExpression(ExprOITBGetDeaths.class,Integer.class,ExpressionType.SIMPLE, "[OITB] [get] deaths of %player%");
				Skript.registerExpression(ExprOITBGetHighestZombiesWave.class,Integer.class,ExpressionType.SIMPLE, "[OITB] [get] [Player]['][s][ ]High[est][ ][Zombie][s][ ]Wave of %player%");
				Skript.registerExpression(ExprOITBGetHits.class,Integer.class,ExpressionType.SIMPLE, "[OITB] [get] hits of %player%");
				Skript.registerExpression(ExprOITBGetKills.class,Integer.class,ExpressionType.SIMPLE, "[OITB] [get] kills of %player%");
				Skript.registerExpression(ExprOITBGetModifier.class,Integer.class,ExpressionType.SIMPLE, "[OITB] [get] modifier of %player%");
				Skript.registerExpression(ExprOITBGetPlayerExp.class,Integer.class,ExpressionType.SIMPLE, "[OITB] [get] [Player][ ]E[x]p[eri[(e|a)]nce] of %player%");
				Skript.registerExpression(ExprOITBGetPlayerRank.class,Integer.class,ExpressionType.SIMPLE, "[OITB] [get] [Player][ ]rank of %player%");
				Skript.registerExpression(ExprOITBGetPlayTime.class,String.class,ExpressionType.SIMPLE, "[OITB] [get] play[er][ ]time of %player%");
				Skript.registerExpression(ExprOITBGetShotsfired.class,Integer.class,ExpressionType.SIMPLE, "[OITB] [get] Shots[ ]fired of %player%");
				Skript.registerExpression(ExprOITBGetTopPlayers.class,String.class,ExpressionType.SIMPLE, "[(the|all)] [of] [the] top %integer% players of [the] [OITB] [stat][istic] %StatType%");
				Skript.registerExpression(ExprOITBGetTopPlayersWithScore.class,String.class,ExpressionType.SIMPLE, "[(the|all)] [of] [the] top %integer% player[s] with score[s] of [the] [OITB] [stat][istic] %StatType%");
				Skript.registerExpression(ExprOITBGetTopScores.class,Integer.class,ExpressionType.SIMPLE, "[(the|all)] [of] [the] top %integer% scores of [the] [OITB] [stat][istic] %StatType%");
				Skript.registerExpression(ExprOITBGetTournamentWins.class,Integer.class,ExpressionType.SIMPLE, "[OITB] [get] Tournament[s][ ](win[s]|won) of %player%");
				Skript.registerExpression(ExprOITBGetZombieKills.class,Integer.class,ExpressionType.SIMPLE, "[OITB] [get] zombie[ ]kills of %player%");
				Skript.registerCondition(CondOITBHasCooldown.class, "[OITB] %player% has cool[ ]down [on] [ability] %string%");
			}
		}
		if (config.getBoolean("PluginHooks.ProtocolSupport")) {
			if (Bukkit.getPluginManager().getPlugin("ProtocolSupport") != null) {
				Skript.registerExpression(ExprBlockRemapperItemType.class, MaterialAndData.class, ExpressionType.SIMPLE, "[protocol[ ]support] remap[ped] block [of] %itemtype%(:| (with|and) data )%number% (for|of) [protocol] version %protocolversion%");
				Skript.registerExpression(ExprItemRemapperItemType.class, ItemType.class, ExpressionType.SIMPLE, "[protocol[ ]support] remap[ped] item [of] %itemtype% (for|of) [protocol] version %protocolversion%");
				Skript.registerExpression(ExprItemRemapperID.class, Number.class, ExpressionType.SIMPLE, "[protocol[ ]support] remap[ped] item [of] [ID] %number% (for|of) [protocol] version %protocolversion%");
				Skript.registerExpression(ExprProtocolVersion.class, ProtocolVersion.class, ExpressionType.SIMPLE, "[skellett] protocol[ ][support] version of %player%", "[skellett] %player%'s protocol[ ][support] version");
			}
		}
		if (config.getBoolean("PluginHooks.Feudal")) {
			if (Bukkit.getPluginManager().getPlugin("Feudal").isEnabled()) {
				String feudalprefix = config.getString("FeudalSyntaxPrefix");
				if (feudalprefix == null) {
					feudalprefix = "feudal [kingdom]";
				}
				Skript.registerExpression(ExprFeudalPlayerKingdomName.class, String.class, ExpressionType.SIMPLE, feudalprefix + " name of %player%", "%player%'s feudal [kingdom] name");
				Skript.registerExpression(ExprFeudalLocationKingdomName.class, String.class, ExpressionType.SIMPLE, feudalprefix + " name at [location] %location%");
				Skript.registerExpression(ExprFeudalPlayerKingdom.class, Kingdom.class, ExpressionType.SIMPLE, feudalprefix + " of %player%", "%player%'s feudal [kingdom]");
				Skript.registerExpression(ExprFeudalLocationKingdom.class, Kingdom.class, ExpressionType.SIMPLE, feudalprefix + " at [location] %location%");
				Skript.registerExpression(ExprFeudalMessage.class, String.class, ExpressionType.SIMPLE, feudalprefix + " (config|files|messages) [message] %string%");
				Skript.registerExpression(ExprFeudalKingdomDescription.class, String.class, ExpressionType.SIMPLE, feudalprefix + " description of %kingdom%", "%kingdom%'s feudal [kingdom] description");
				Skript.registerExpression(ExprFeudalKingdomHome.class, Location.class, ExpressionType.SIMPLE, feudalprefix + " home of %kingdom%", "%kingdom%'s feudal [kingdom] home");
				Skript.registerExpression(ExprFeudalKingdomFighters.class, String.class, ExpressionType.SIMPLE, "(the|all)] [of] [the]" + feudalprefix + "fighter[[']s] of %kingdom%", "[(the|all)] [of] [the] %kingdom%'s" + feudalprefix + "fighter[[']s]", "[(the|all)] [of] [the] fighter[[']s] of " + feudalprefix + "%kingdom%");
			}
		}
		if (mysqlData.getBoolean("MySQL")) {
			Skript.registerEffect(EffMySQLConnect.class, "[skellett] connect [to] mysql");
			Skript.registerEffect(EffMySQLDisconnect.class, "[skellett] disconnect [from] mysql");
			Skript.registerEffect(EffMySQLUpdate.class, "[skellett] mysql update %string%");
			Skript.registerExpression(ExprMySQLHost.class, String.class, ExpressionType.SIMPLE, "[skellett] mysql['s] host");
			Skript.registerExpression(ExprMySQLUsername.class, String.class, ExpressionType.SIMPLE, "[skellett] mysql['s] username");
			Skript.registerExpression(ExprMySQLPassword.class, String.class, ExpressionType.SIMPLE, "[skellett] mysql['s] password");
			Skript.registerExpression(ExprMySQLPort.class, Number.class, ExpressionType.SIMPLE, "[skellett] mysql['s] port");
			Skript.registerExpression(ExprMySQLDatabase.class, String.class, ExpressionType.SIMPLE, "[skellett] mysql['s] database");
			Skript.registerExpression(ExprMySQLQuery.class, ResultSet.class, ExpressionType.SIMPLE, "[skellett] mysql result of query %string%");
			Skript.registerExpression(ExprMySQLQueryString.class, String.class, ExpressionType.SIMPLE, "[skellett] mysql string[s] %string% (in|from|of) %resultset%");
			Skript.registerExpression(ExprMySQLQueryInteger.class, Number.class, ExpressionType.SIMPLE, "[skellett] mysql integer[s] %string% (in|from|of) %resultset%");
			Skript.registerExpression(ExprMySQLQueryNumber.class, Number.class, ExpressionType.SIMPLE, "[skellett] mysql (number|float)[s] %string% (in|from|of) %resultset%");
			Skript.registerExpression(ExprMySQLQueryBoolean.class, Boolean.class, ExpressionType.SIMPLE, "[skellett] mysql boolean[s] %string% (in|from|of) %resultset%");
			Skript.registerExpression(ExprMySQLQueryObject.class, Object.class, ExpressionType.SIMPLE, "[skellett] mysql object[s] %string% (in|from|of) %resultset%");
		}
		if (ceData.getBoolean("CustomEvents")) {
			for(int i = 1; i <= ceData.getInt("CustomEventSetup.NumberOfEvents"); i++) {
				try {
					@SuppressWarnings("unchecked")
					Class<? extends Event> classType = ((Class<? extends Event>) Class.forName(ceData.getString("CustomEventSetup." + i + ".Event")));
					Skript.registerEvent(ceData.getString("CustomEventSetup." + i + ".Syntax"), SimpleEvent.class, classType, "[skellett] " + ceData.getString("CustomEventSetup." + i + ".Syntax"));
					Register.addEvent(classType);
					Bukkit.getConsoleSender().sendMessage(cc("&aRegistered custom event: &5" + ceData.getString("CustomEventSetup." + i + ".Syntax")));
				} catch (ClassNotFoundException e1) {
					e1.printStackTrace();
				}
			}
		}
		if (getServer().getPluginManager().getPlugin("SkQuery") == null) {
			if (syntaxToggleData.getBoolean("Main.Yaml")) {
				Skript.registerExpression(ExprYaml.class, Object.class, ExpressionType.SIMPLE, "[skellett] (file|y[a]ml) [file] (1�value|2�node[s]|3�node[s with] keys|4�list) %string% (in|at|from) [file] %string%");
			}
		}
		Skript.registerCondition(CondFileExists.class, "[skellett] [file] exist(s|ence) [(at|of)] %string% [is %-boolean%]");
		Skript.registerEffect(EffFirework.class, "[skellett] (launch|deploy) [%-strings%] firework[s] at %locations% [with] (duration|timed|time) %number% [colo[u]r[ed] (%-strings%|%-color%)]");
		Skript.registerEvent("[on] entity sho[o]t:", SimpleEvent.class, EntityShootBowEvent.class, "[on] entity sho[o]t");
		try {
			Metrics metrics = new Metrics(this);
			metrics.start();
			if (config.getBoolean("debug")) {
				Bukkit.getConsoleSender().sendMessage(cc(prefix + "&aMetrics registered!"));
			}
		} catch (IOException e) {
			if (config.getBoolean("debug")) {
				Bukkit.getConsoleSender().sendMessage(cc(prefix + "&cMetrics failed to register"));
			}
		}
		try {
			register();
		} catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | IOException | InstantiationException e2) {
			e2.printStackTrace();
		}
		if (spData.get("Host") == null) {
			spData.set("Host", "localhost");
		}
		if (spData.get("Disconnect") == null) {
			spData.set("Disconnect", true);
		}
		if (spData.get("Post") == null) {
			spData.set("Port", 7332);
		}
		if (spData.get("Events") == null) {
			spData.set("Events", false);
		}
		if (spData.get("EventPort") == null) {
			spData.set("EventPort", 7331);
		}
		try {
			spData.save(spFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (spData.getBoolean("SkellettProxy", false)) {
			Sockets.run();
		}
		int version = Integer.parseInt(getDescription().getVersion().replaceAll("[^0-9]", ""));
		int update = 0;
		String updateString = null;
		if (config.getBoolean("VersionChecker", true)) {
			try {
				HttpURLConnection con = (HttpURLConnection) new URL("http://www.spigotmc.org/api/general.php").openConnection();
				con.setDoOutput(true);
				con.setRequestMethod("POST");
				con.getOutputStream()
					.write(("key=98BE0FE67F88AB82B4C197FAF1DC3B69206EFDCC4D3B80FC83A00037510B99B4&resource=34361")
					.getBytes("UTF-8"));
				updateString = new BufferedReader(new InputStreamReader(con.getInputStream())).readLine();
				update =  Integer.parseInt(updateString.replaceAll("[^0-9]", ""));
				if (version < update) {
					Bukkit.getConsoleSender().sendMessage(cc(prefix + "&eThere is a new update for Skellett! Version: " + updateString));
				} else if (version > update) {
					Bukkit.getConsoleSender().sendMessage(cc(prefix + "&eRunning a beta version of Skellett! Use with caution"));
				} else if (!getDescription().getVersion().equals(updateString)) {
					Bukkit.getConsoleSender().sendMessage(cc(prefix + "&eRunning a Skellett Snapshot/Fix version!"));			
				}
			} catch (Exception ex) {
				Bukkit.getConsoleSender().sendMessage(cc(prefix + "&cFailed to check for an update on spigot"));
			}
		}
		if (!config.getBoolean("DisableRegisteredInfo", false)) {
			Bukkit.getConsoleSender().sendMessage(cc(prefix + "&aHas been enabled!"));
		}
	}
	public static String cc(String text) {
		return ChatColor.translateAlternateColorCodes('&', text);
	}
	@SuppressWarnings("deprecation")
	public static int getTicks(Timespan time) {
		if (Skript.methodExists(Timespan.class, "getTicks_i")) {
			Number tick = time.getTicks_i();
			return tick.intValue();
		} else {
			return time.getTicks();
		}
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void register() throws IOException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
		
		int effN = 0;
		int condN = 0;
		int exprN = 0;
		
		Set<Class<?>> classes = new HashSet<>();		
		Method method = JavaPlugin.class.getDeclaredMethod("getFile");
		
		method.setAccessible(true);
		File file = (File) method.invoke(this);
		try {
			JarFile jar = new JarFile(file);
			for (Enumeration<JarEntry> entry = jar.entries(); entry.hasMoreElements();) {
				String name = entry.nextElement().getName().replace("/", ".");
				if (name.startsWith("com.gmail.thelimeglass") && name.endsWith(".class")) {
					try {
						classes.add(Class.forName(name.substring(0, name.length() - 6)));
					} catch (ClassNotFoundException error) {
						error.printStackTrace();
					} catch (NoClassDefFoundError | ExceptionInInitializerError e) {}
				}
			}
			jar.close();
		} catch(Exception error) {
			error.printStackTrace();
		}
		if (classes != null) {
			run: for (Class c : classes) {
				String statement = "Effects";
				if (Condition.class.isAssignableFrom(c)) {
					statement = "Conditions";
				} else if (Expression.class.isAssignableFrom(c)) {
					statement = "Expressions";
				}
				for (Annotation a : c.getAnnotations()) {
					if (a instanceof Disabled) {
						continue run;
					}
					if (a instanceof SkellettProxy) {
						if (!spData.getBoolean("SkellettProxy", false)) {
							continue run;
						}
					}
					if (a instanceof Dependency) {
						List<String> plugins = new ArrayList<String>(Arrays.asList(((Dependency) c.getAnnotation(Dependency.class)).value()));
						for (String pl : plugins) {
							if (Bukkit.getPluginManager().getPlugin(pl) == null) {
								continue run;
							}
						}
					}
					if (a instanceof AntiDependency) {
						List<String> plugins = new ArrayList<String>(Arrays.asList(((AntiDependency) c.getAnnotation(AntiDependency.class)).value()));
						for (String pl : plugins) {
							if (Bukkit.getPluginManager().getPlugin(pl) != null) {
								continue run;
							}
						}
					}
					if (a instanceof DetectVersion) {
						if (Bukkit.getVersion().contains("1.8") && !c.getName().contains("1_8")){
							continue run;
						} else if ((Bukkit.getVersion().contains("1.9") && Bukkit.getVersion().contains("R0.1")) && !(c.getName().contains("1_9") && c.getName().contains("R1"))){
							continue run;
						} else if ((Bukkit.getVersion().contains("1.9") && Bukkit.getVersion().contains("R0.2")) && !(c.getName().contains("1_9") && c.getName().contains("R2"))){
							continue run;
						} else if (Bukkit.getVersion().contains("1.10") && !c.getName().contains("1_10")){
							continue run;
						} else if (Bukkit.getVersion().contains("1.11") && !c.getName().contains("1_11")){
							continue run;
						}
					}
					if (a instanceof Version) {
						String[] versions = {"1.8R3", "1.9R1", "1.9R2", "1.10" , "1.11", "1.11.2"};
						Integer server = null;
						Integer serverTag = null;
						for (int i = 0; i < versions.length; i++) {
							if (versions[i].contains("R")) {
								String[] subVersion = versions[i].split("R");
								if (Bukkit.getVersion().contains(subVersion[0]) && Bukkit.getVersion().contains("R0." + subVersion[1])) {
									server = i;
								}
							} else if (Bukkit.getVersion().contains(versions[i])) {
								server = i;
							}
							if (versions[i].equals(((Version) c.getAnnotation(Version.class)).value())) {
								serverTag = i;
							}
						}
						if (serverTag == null || server == null || serverTag > server) {
							if (!config.getBoolean("DisableCompatableWarning") && c.isAnnotationPresent(Config.class)) {
								Bukkit.getConsoleSender().sendMessage(cc(prefix + ((Config) c.getAnnotation(Config.class)).value() + " syntax is for " + versions[serverTag] + "+ spigot versions!"));
							}
							continue run;
						}
					}
					if (a instanceof Config) {
						FileConfiguration data = syntaxToggleData;
						String node = ((Config) c.getAnnotation(Config.class)).value();
						if (c.isAnnotationPresent(MainConfig.class)) {
							data = config;
						}
						if (c.isAnnotationPresent(SkellettProxy.class)) {
							data = spData;
						}
						if (!c.isAnnotationPresent(FullConfig.class)) {
							node = "Syntax." + statement + "." + node;
						}
						if (data.getBoolean(node) == false) {
							if (config.getBoolean("NotRegisteredSyntax", false)) {
								Bukkit.getConsoleSender().sendMessage(cc(prefix + node.toString() + " didn't register!"));
							}
							continue run;
						}
					}
					if (a instanceof RegisterSimpleEnum) {
						Class clazz = ((RegisterSimpleEnum) c.getAnnotation(RegisterSimpleEnum.class)).ExprClass();
						String type = ((RegisterSimpleEnum) c.getAnnotation(RegisterSimpleEnum.class)).value();
						TypeClassInfo.create(clazz, type).register();
						if (config.getBoolean("debug")) {
							Bukkit.getConsoleSender().sendMessage(cc(prefix + "&eRegistered simple type " + type));
						}
					} else if (a instanceof RegisterEnum) {
						Boolean register = true;
						if (c.isAnnotationPresent(AntiDependencyEnum.class)) {
							List<String> plugins = new ArrayList<String>(Arrays.asList(((AntiDependencyEnum) c.getAnnotation(AntiDependencyEnum.class)).value()));
							for (String pl : plugins) {
								if (Bukkit.getPluginManager().getPlugin(pl) != null) {
									register = false;
								}
							}
						}
						if (register) {
							Class clazz = ((RegisterEnum) c.getAnnotation(RegisterEnum.class)).ExprClass();
							String type = ((RegisterEnum) c.getAnnotation(RegisterEnum.class)).value();
							if (clazz.equals(String.class)) {
								if (Classes.getExactClassInfo(((Expression) c.newInstance()).getReturnType()) == null) {
									EnumClassInfo.create(((Expression) c.newInstance()).getReturnType(), type).register();
								}
							} else {
								EnumClassInfo.create(clazz, type).register();
							}
							if (config.getBoolean("debug")) {
								Bukkit.getConsoleSender().sendMessage(cc(prefix + "&eRegistered type " + type));
							}
						}
					}
				}
				if (c.isAnnotationPresent(Syntax.class)) {
					String node = null;
					if (c.isAnnotationPresent(Config.class)) {
						node = ((Config) c.getAnnotation(Config.class)).value();
					}
					String[] syntax = ((Syntax) c.getAnnotation(Syntax.class)).value();
					if (node != null) {
						if (syntaxData.get(node) == null) {
							List<String> list = new ArrayList<String>(Arrays.asList(syntax));
							if (list.size() <= 1) {
								list.add(null);
							}
							syntaxData.set(node, list);
						} if (syntaxData.get(node) != null) {
							if (syntaxData.getList(node) == null) {
								syntaxData.getList(node).add(null);
							}
							List<Object> list = (List<Object>) syntaxData.getList(node);
							for (String s : syntax) {
								if (!syntaxData.getList(node).contains(s)) {
									list.add(s);
								}
								list.remove(null);
							}
						}
						try {
							syntaxData.save(syntaxFile);
						} catch (IOException e1) {
							e1.printStackTrace();
						}
					}
					FileConfiguration data = syntaxToggleData;
					if (c.isAnnotationPresent(MainConfig.class)) {
						data = config;
					}
					if (c.isAnnotationPresent(SkellettProxy.class)) {
						data = spData;
					}
					if (!c.isAnnotationPresent(FullConfig.class)) {
						if (node != null) {
							node = "Syntax." + statement + "." + node;
						}
					}
					if (ch.njol.skript.lang.Effect.class.isAssignableFrom(c)) {
						if (config.getBoolean("debug")) {
							if (node.equals(config.getString("syntaxDebug"))) {
								Bukkit.getConsoleSender().sendMessage(cc(prefix + "&cRegistering effect " + node));
							} else {
								Bukkit.getConsoleSender().sendMessage(cc(prefix + "&5Registering effect " + node));
							}
						}
						if (node != null) {
							if (data.getBoolean(node)) {
								Skript.registerEffect(c, syntax);
								effN++;
							}
						} else {
							Skript.registerEffect(c, syntax);
							effN++;
							/*if (syntaxData.getList(node) != null) {
								strings.addAll((Collection<? extends String>) syntaxData.get(node));
								Skript.registerEffect(c, strings.toArray(new String[strings.size()]));
							} else {
								Skript.registerEffect(c, syntaxData.getString(node));
							}*/
						}
					} else if (Condition.class.isAssignableFrom(c)) {
						if (config.getBoolean("debug")) {
							if (node.equals(config.getString("syntaxDebug"))) {
								Bukkit.getConsoleSender().sendMessage(cc(prefix + "&cRegistering condition " + node));
							} else {
								Bukkit.getConsoleSender().sendMessage(cc(prefix + "&5Registering condition " + node));
							}
						}
						if (node != null) {
							if (data.getBoolean(node)) {
								Skript.registerCondition(c, syntax);
								condN++;
							}
						} else {
							Skript.registerCondition(c, syntax);
							condN++;
						}
					} else if (Expression.class.isAssignableFrom(c)) {
						if (config.getBoolean("debug")) {
							if (node.equals(config.getString("syntaxDebug"))) {
								Bukkit.getConsoleSender().sendMessage(cc(prefix + "&cRegistering expression " + node));
							} else {
								Bukkit.getConsoleSender().sendMessage(cc(prefix + "&5Registering expression " + node));
							}
						}
						if (c.isAnnotationPresent(PropertyType.class)) {
							if (node != null) {
								if (data.getBoolean(node)) {
									try {
										Skript.registerExpression(c, ((Expression) c.newInstance()).getReturnType(), ((PropertyType) c.getAnnotation(PropertyType.class)).value(), syntax);
										exprN++;
									} catch (IllegalAccessException e) {
										Bukkit.getConsoleSender().sendMessage(Skellett.cc(Skellett.prefix + "&cFailed to register expression " + c.getCanonicalName()));
										e.printStackTrace();
									} catch (InstantiationException e) {
										Bukkit.getConsoleSender().sendMessage(Skellett.cc(Skellett.prefix + "&cFailed to register expression " + c.getCanonicalName()));
										e.printStackTrace();
									}
								}
							} else {
								try {
									Skript.registerExpression(c, ((Expression) c.newInstance()).getReturnType(), ((PropertyType) c.getAnnotation(PropertyType.class)).value(), syntax);
									exprN++;
								} catch (IllegalAccessException e) {
									Bukkit.getConsoleSender().sendMessage(Skellett.cc(Skellett.prefix + "&cFailed to register expression " + c.getCanonicalName()));
									e.printStackTrace();
								} catch (InstantiationException e) {
									Bukkit.getConsoleSender().sendMessage(Skellett.cc(Skellett.prefix + "&cFailed to register expression " + c.getCanonicalName()));
									e.printStackTrace();
								}
							}
						}
					}
				}
			}
		}
		if (!config.getBoolean("DisableRegisteredInfo", false)) {
			Bukkit.getConsoleSender().sendMessage(Skellett.cc(Skellett.prefix + "Registered &a" + effN + " &eEffects, &a" + condN + "&e Conditions, &a" + exprN + "&e Expressions and &a" + Register.getClasses().size() + "&e Events"));
		}
	}
	public void onDisable(){
		StyleManager.dump();
		SkellettMapRenderer.dump();
		Register.dump();
	}
}
/*
Needs fixing:

#This is disabled
[a] new euler angle

angle of body (from|on) armo[u]r stand %entity%
armo[u]r stand %entity%['s] body angle

New Stuff:

TODO: Add checker to the item version display in the GUI
TODO: OpenMusicGUI webclient
TODO: PlaceholderAPI hook
TODO: Look at FactionUUID for Facsk
TODO: MiniturePets hook as requested by MrTrev
TODO: Make the bungeecord uuid accept offline player strings aswell

Fixed:

	Add page with string not working

Effects:
	
	update [the] (inventory|menu|gui) %inventory%
	
	open book %itemstack% to %player%
	
Expressions:
	
	[the] hitbox length of %entity%
	%entity%'s hitbox length
	[the] length of %entity%'s hitbox
	
	[the] hitbox width of %entity%
	%entity%'s hitbox width
	[the] width of %entity%'s hitbox
	
Conditions:

	[player] %player% (1�is|2�is(n't| not)) viewing [the] credits
	
	[entity] %entity% (1�is|2�is(n't| not)) in water

Spawners:

	Expressions:
	
		#Changers: set, add and remove (Integer)
		delay (of|from) spawner [at] %block%
		%block%'s spawn[er] delay
		spawn[er] delay (of|from) %block%
		
		#Changers: set (String)
		(entity|mob|creature) [type] (of|from) spawner [at] %block%
		%block%'s spawn[er] (entity|mob|creature) [type]
		
	Effects:
	
		(make|force) spawner [at] %block% to spawn [[a[n]] entity]
		
	Events:
	
		[on] spawner spawn:
			
			#Get the spawner used to spawn the entity
			event-block
			
			#Get entity spawned
			event-entity

Eggwars:

	Conditions:
	
		egg[ ]wars player %ewplayer% (1�is|2�is(n't| not)) invincible
		
		egg[ ]wars player %ewplayer% (1�is|2�is(n't| not)) in a[n] arena
		
		egg[ ]wars player %ewplayer% (1�is|2�is(n't| not)) dead

	Expressions:
	
		#Changers: set (EwKit)
		[the] egg[ ]wars kit of player %ewplayer%
		%player%'s egg[ ]war[s] kit
	
		#Changers: set (InvPlayer)
		[the] egg[ ]wars inv[entory] of player %ewplayer%
		%player%'s egg[ ]war[s] inv[entory]
		
		#Changers: set (Integer)
		[the] egg[ ]wars target team of player %ewplayer%
		%player%'s egg[ ]war[s] target team
		
		[the] egg[ ]wars [(player|game)] data of player %ewplayer%
		%player%'s egg[ ]war[s] [(player|game)] data
	
		[the] egg[ ]wars team [(from|of)] %player%
		%player%'s egg[ ]war[s] team
		
		#Info returns a player from the eggwars player
		[the] player [(from|of)] egg[ ]wars player %player%
		
		[the] egg[ ]wars player [(from|of)] %player%
		%player%'s egg[ ]war[s] player
	
		#Changers: set (Arena)
		[the] egg[ ]wars arena of player %ewplayer%
		%player%'s egg[ ]war[s] arena

*/