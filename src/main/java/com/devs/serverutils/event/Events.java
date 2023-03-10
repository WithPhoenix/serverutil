package com.devs.serverutils.event;


import com.devs.serverutils.ServerUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.scores.PlayerTeam;
import net.minecraft.world.scores.Scoreboard;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = ServerUtils.MODID)
public class Events {

    @SubscribeEvent
    public static void onPlayerCloneEvent(PlayerEvent.Clone event) {
        event.getEntity().getPersistentData().putLong("balance", event.getOriginal().getPersistentData().getLong("balance"));
        event.getEntity().getPersistentData().putString("teamxt", event.getOriginal().getTeam() == null ? "snt" : event.getOriginal().getTeam().getName());
    }

    @SubscribeEvent
    public static void onPlayerTickEvent(TickEvent.PlayerTickEvent event) {
        if (event.side.isClient()) return;
        Player player = event.player;
        MinecraftServer server = player.getServer();

        PlayerTeam team = server.getScoreboard().getPlayersTeam(player.getScoreboardName());

        BlockPos pos = player.blockPosition();
        BlockPos before = player.getPersistentData().contains(ServerUtils.MODID + "xb") ? BlockPos.of(player.getPersistentData().getLong(ServerUtils.MODID + "xb")) : null;

        int afkTick = player.getPersistentData().contains("afkxt") ? player.getPersistentData().getInt("afkxt") : 0;

        if (pos.equals(before)) {
            player.getPersistentData().putInt("afkxt", afkTick >= 6000 ? 6000 : ++afkTick);
        } else {
            if (team != null && !team.getName().equals("afk")) {
                player.getPersistentData().putString("teamxt", team.getName());
            }
            player.getPersistentData().putLong(ServerUtils.MODID + "xb", pos.asLong());
            player.getPersistentData().putInt("afkxt", 0);
            if (team != null && team.getName().equals("afk")) {
                changePlayerTeam(server, player.getPersistentData().getString("teamxt"), player.getScoreboardName());
            }
        }

        if (++afkTick >= 6000) {
            changePlayerTeam(server, "afk", player.getScoreboardName());
        }
    }

    private static void changePlayerTeam(MinecraftServer server, String team, String playername) {
        Scoreboard scoreboard = server.getScoreboard();

        if (team.equals("")) {
            PlayerTeam team1 = scoreboard.getPlayerTeam("afk");
            scoreboard.removePlayerFromTeam(playername, team1);
            return;
        }

        PlayerTeam team1 = scoreboard.getPlayerTeam(team);
        scoreboard.addPlayerToTeam(playername, team1);
    }
}
