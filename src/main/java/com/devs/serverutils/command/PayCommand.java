package com.devs.serverutils.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

import java.util.Collection;

public class PayCommand {
    //todo global an aus variable und command
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("pay")
                .then(Commands.argument("targets", EntityArgument.players()))
                .then(Commands.argument("amount", IntegerArgumentType.integer(1)))
                .executes((p) -> pay(p.getSource(), EntityArgument.getPlayers(p, "targets"), IntegerArgumentType.getInteger(p, "amount"))));
    }

    private static int pay(CommandSourceStack source, Collection<ServerPlayer> target, int amount) {
        ServerPlayer sender = source.getPlayer();
        if (sender == null) {
            source.sendFailure(Component.literal("you have to be a player"));
            return -1;
        }
        source.sendSuccess(Component.translatable("Du hast ", amount, " an", target.iterator().next().getDisplayName(), "überwiesen!"), false);
        return 1;
    }
}
