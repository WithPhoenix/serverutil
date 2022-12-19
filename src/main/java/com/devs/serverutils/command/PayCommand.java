package com.devs.serverutils.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

public class PayCommand {
    //todo global an aus variable und command
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("pay")
                .then(Commands.argument("target", EntityArgument.player()))
                .then(Commands.argument("amount", IntegerArgumentType.integer(1)))
                .executes((p) -> {
                    return pay(p.getSource(), EntityArgument.getPlayer(p, "target"), IntegerArgumentType.getInteger(p, "amount"));
                }));
    }

    private static int pay(CommandSourceStack source, ServerPlayer target, int amount) {
        ServerPlayer sender = source.getPlayer();
        if (sender == null) {
            source.sendFailure(Component.literal("you have to be a player"));
            return -1;
        }
        source.sendSuccess(Component.translatable("Du hast ", amount, " an", target.getDisplayName(), "überwiesen!"), false);
        return 1;
    }
}
