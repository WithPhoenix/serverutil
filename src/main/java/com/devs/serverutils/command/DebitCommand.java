package com.devs.serverutils.command;

import com.devs.serverutils.service.BankSaveData;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.tree.LiteralCommandNode;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;

public class DebitCommand {

    //todo poisonous potato wird coin,
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        LiteralCommandNode<CommandSourceStack> literalCommandNode = dispatcher.register(Commands.literal("debit").then(Commands.argument("targets", EntityArgument.players()).then(Commands.argument("amount", IntegerArgumentType.integer(1)).executes((p) -> {
            return debit(p.getSource());
        }))));
        dispatcher.register(Commands.literal("auszahlen").redirect(literalCommandNode));
    }

    private static int debit(CommandSourceStack source) {
        if (BankSaveData.INSTANCE.isEnabled()) {

        }
        source.sendFailure(Component.literal("Money is deactivated!"));
        return -1;
    }
}
