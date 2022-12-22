package com.devs.serverutils.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.tree.LiteralCommandNode;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.ChatType;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.OutgoingChatMessage;
import net.minecraft.network.chat.PlayerChatMessage;
import net.minecraft.server.level.ServerPlayer;

import java.util.Collection;

public class PayCommand {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        LiteralCommandNode<CommandSourceStack> literalCommandNode = dispatcher.register(Commands.literal("pay").then(Commands.argument("targets", EntityArgument.players()).then(Commands.argument("amount", IntegerArgumentType.integer(1)).executes((p) -> {
            Collection<ServerPlayer> collection = EntityArgument.getPlayers(p, "targets");
            if (!collection.isEmpty()) {
                pay(p.getSource(), collection, IntegerArgumentType.getInteger(p, "amount"));
            }
            return collection.size();
        }))));
        dispatcher.register(Commands.literal("p").redirect(literalCommandNode));
        dispatcher.register(Commands.literal("bezahle").redirect(literalCommandNode));
    }

    private static void pay(CommandSourceStack source, Collection<ServerPlayer> targets, int amount) {
        ServerPlayer sender = source.getPlayer();
        if (sender == null) {
            source.sendFailure(Component.literal("you have to be a player"));
            return;
        }
        if (modifyTag(source, sender.getPersistentData(), amount, targets)) {
            StringBuilder string = new StringBuilder();
            for (ServerPlayer p : targets) {
                string.append(p.getDisplayName().getString()).append(" ");
            }
            source.sendSuccess(Component.literal("Du hast " + amount + " an " + string + "überwiesen!"), false);
            return;
        }
        if (payOffline()) {
            return;
        }
        source.sendFailure(Component.literal("Not enough money!"));
    }

    private static boolean modifyTag(CommandSourceStack stack, CompoundTag tag, int amount, Collection<ServerPlayer> targets) {
        if (tag.contains("balance")) {
            long factor = targets.size();
            long balance = tag.getLong("balance");
            long n = balance - (amount * factor);
            if (n < 0) return false;

            tag.putLong("balance", n);
            for (ServerPlayer player : targets) {
                long b = player.getPersistentData().contains("balance") ? player.getPersistentData().getLong("balance") + amount : amount;
                player.getPersistentData().putLong("balance", b);

                ChatType.Bound chattype$bound = ChatType.bind(ChatType.CHAT, stack);
                String msg = " hat dir $" + amount + " überwiesen!";
                PlayerChatMessage message = PlayerChatMessage.system(msg);
                OutgoingChatMessage outgoingchatmessage = OutgoingChatMessage.create(message);
                player.sendChatMessage(outgoingchatmessage, false, chattype$bound);
            }
        }
        return true;
    }

    private static boolean payOffline() {
        return false;
    }
}
