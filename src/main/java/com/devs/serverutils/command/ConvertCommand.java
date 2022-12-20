package com.devs.serverutils.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.tree.LiteralCommandNode;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class ConvertCommand {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        LiteralCommandNode<CommandSourceStack> literalCommandNode = dispatcher.register(Commands.literal("convert").then(Commands.literal("iron").executes((p) -> {
            return iron(p.getSource());
        })).then(Commands.literal("gold").executes((p) -> {
            return gold(p.getSource());
        })));
    }

    private static int iron(CommandSourceStack source) {
        ServerPlayer sender = source.getPlayer();
        if (sender == null) {
            source.sendFailure(Component.literal("you have to be a player"));
            return -1;
        }
        int count = sender.getInventory().items.stream()
                .filter(stack -> stack.getItem() == Items.RAW_IRON)
                .mapToInt(ItemStack::getCount)
                .sum();
        long balance = sender.getPersistentData().contains("balance") ? sender.getPersistentData().getLong("balance") + count : count;
        for (ItemStack stack : sender.getInventory().items) {
            if (stack.getItem() == Items.RAW_IRON) {
                stack = ItemStack.EMPTY;
            }
        }
        sender.getPersistentData().putLong("balance", balance);
        source.sendSuccess(Component.literal("Erfolgreich $" + count + " aufgeladen"), true);
        return count;
    }

    private static int gold(CommandSourceStack source) {
        ServerPlayer sender = source.getPlayer();
        if (sender == null) {
            source.sendFailure(Component.literal("you have to be a player"));
            return -1;
        }
        int count = sender.getInventory().items.stream()
                .filter(stack -> stack.getItem() == Items.RAW_GOLD)
                .mapToInt(ItemStack::getCount)
                .sum();
        int inc = count * 3;
        long balance = sender.getPersistentData().contains("balance") ? sender.getPersistentData().getLong("balance") + inc : inc;
        for (ItemStack stack : sender.getInventory().items) {
            if (stack.getItem() == Items.RAW_GOLD) {
                stack = ItemStack.EMPTY;
            }
        }
        sender.getPersistentData().putLong("balance", balance);
        source.sendSuccess(Component.literal("Erfolgreich $" + inc + " aufgeladen"), true);
        return inc;
    }

}
