package com.devs.serverutils.command;

import com.devs.serverutils.service.BankSaveData;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;

public class DisableEnableCommand {

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("money").requires((p) -> {
            return p.hasPermission(2);
        }).then(Commands.literal("enable").executes((p) -> {
            return enable(p.getSource());
        })).then(Commands.literal("disable").executes((p) -> {
            return disable(p.getSource());
        })));
    }

    private static int enable(CommandSourceStack source) {
        BankSaveData.ENABLED = true;
        source.sendSuccess(Component.literal("Bank enabled!"), true);
        return 1;
    }

    private static int disable(CommandSourceStack source) {
        BankSaveData.ENABLED = false;
        source.sendSuccess(Component.literal("Bank disabled!"), true);
        return 0;
    }

}
