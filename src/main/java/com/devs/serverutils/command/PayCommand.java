package com.devs.serverutils.command;

import com.devs.serverutils.service.BankSaveData;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.tree.LiteralCommandNode;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

import java.util.Collection;

public class PayCommand {
    //todo global an aus variable und command
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        LiteralCommandNode<CommandSourceStack> literalCommandNode = dispatcher.register(Commands.literal("pay").then(Commands.argument("targets", EntityArgument.players()).then(Commands.argument("amount", IntegerArgumentType.integer(1)).executes((p) -> {
            Collection<ServerPlayer> collection = EntityArgument.getPlayers(p, "targets");
            if (!collection.isEmpty()) {
                pay(p.getSource(), collection, IntegerArgumentType.getInteger(p, "amount"));
            }
            return collection.size();
        }))));
        dispatcher.register(Commands.literal("p").redirect(literalCommandNode));
    }

    private static void pay(CommandSourceStack source, Collection<ServerPlayer> target, int amount) {
        ServerPlayer sender = source.getPlayer();
        if (sender == null) {
            source.sendFailure(Component.literal("you have to be a player"));
            return;
        }
        if (!BankSaveData.INSTANCE.isEnabled()) {
            source.sendFailure(Component.literal("Geldsystem is deaktiviert!"));
            return;
        }
        StringBuilder string = new StringBuilder();
        for (ServerPlayer p : target) {
            string.append(p.getDisplayName()).append(" ");
        }
        source.sendSuccess(Component.literal("Du hast " + amount + " an " + string + "überwiesen!"), false);
    }
}
