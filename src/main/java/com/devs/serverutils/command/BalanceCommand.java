package com.devs.serverutils.command;

import com.devs.serverutils.service.WorldSaveData;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.tree.LiteralCommandNode;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

public class BalanceCommand {

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        LiteralCommandNode<CommandSourceStack> literalCommandNode = dispatcher.register(Commands.literal("balance").executes((c) -> info(c.getSource())));
        LiteralCommandNode<CommandSourceStack> literalCommandNode1 = dispatcher.register(Commands.literal("kontostand").executes((c) -> info(c.getSource())));
    }

    private static int info(CommandSourceStack source) {
        if (WorldSaveData.getInstance().isEnabled()) {
            ServerPlayer sender = source.getPlayer();
            if (sender == null) {
                source.sendFailure(Component.literal("you have to be a player"));
                return -1;
            }
            long balance = sender.getPersistentData().contains("balance") ? sender.getPersistentData().getLong("balance") : 0;
            source.sendSuccess(Component.literal("Kontostand: $" + balance), true);
            return 1;
        }
        source.sendFailure(Component.literal("Money is deactivated!"));
        return 0;
    }

}
