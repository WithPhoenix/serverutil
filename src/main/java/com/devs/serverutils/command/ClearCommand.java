package com.devs.serverutils.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.tree.LiteralCommandNode;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

import java.util.Collection;

public class ClearCommand {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        LiteralCommandNode<CommandSourceStack> literalCommandNode = dispatcher.register(Commands.literal("resetBalanceOf").requires((p) -> p.hasPermission(4))
                .then(Commands.argument("target", EntityArgument.players())
                        .executes((c) -> reset(c.getSource(), EntityArgument.getPlayers(c, "target")))));
    }

    private static int reset(CommandSourceStack stack, Collection<ServerPlayer> targets) {
        for (ServerPlayer p : targets) {
            p.getPersistentData().putLong("balance", 0);
        }
        stack.sendSuccess(Component.literal("reset was success full"), true);
        return 1;
    }
}
