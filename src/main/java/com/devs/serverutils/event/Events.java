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

        PlayerTeam team = (PlayerTeam) player.getTeam();

        BlockPos pos = player.blockPosition();
        BlockPos before = player.getPersistentData().contains(ServerUtils.MODID + "xb") ? BlockPos.of(player.getPersistentData().getLong(ServerUtils.MODID + "xb")) : null;

        int afkTick = player.getPersistentData().contains("afkxt") ? player.getPersistentData().getInt("afkxt") : 0;

        if (before == null) {
            player.getPersistentData().putLong(ServerUtils.MODID + "xb", pos.asLong());
            player.getPersistentData().putInt("afkxt", afkTick >= 6000 ? 6000 : ++afkTick);
            return;
        }
        if (pos.equals(before)) {
            player.getPersistentData().putInt("afkxt", afkTick >= 6000 ? 6000 : ++afkTick);
        } else {
            player.getPersistentData().putLong(ServerUtils.MODID + "xb", pos.asLong());
            player.getPersistentData().putInt("afkxt", 0);
            if (team != null && team.getName().equals("afk")) {
                changePlayerTeam(server, player.getPersistentData().getString("teamxt"), player.getScoreboardName());
            }
        }
        if (++afkTick >= 6000) {
            player.getPersistentData().putString("teamxt", team == null || team.getName().equals("afk") ? "snt" : team.getName());
            changePlayerTeam(server, "afk", player.getScoreboardName());
            server.getPlayerList().broadcastSystemMessage(Component.literal(player.getDisplayName().getString() + " is now afk"), true);
        }
    }

    private static void changePlayerTeam(MinecraftServer server, String team, String playername) {
        Scoreboard scoreboard = server.getScoreboard();

        if (team.equals("snt")) {
            PlayerTeam team1 = scoreboard.getPlayerTeam("afk");
            scoreboard.removePlayerFromTeam(playername, team1);
            return;
        }

        PlayerTeam team1 = scoreboard.getPlayerTeam(team);
        scoreboard.addPlayerToTeam(playername, team1);
    }

//    @SubscribeEvent
//    public static void onPlayerTickEvent(LivingEvent.LivingTickEvent event) {
//
//    }
}
