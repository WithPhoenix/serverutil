package com.devs.serverutils.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.tree.LiteralCommandNode;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.scores.Objective;
import net.minecraft.world.scores.Score;
import net.minecraft.world.scores.Scoreboard;

import java.util.Collection;

public class DebitCommand {

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        LiteralCommandNode<CommandSourceStack> literalCommandNode = dispatcher.register(Commands.literal("debit").then(Commands.argument("amount", IntegerArgumentType.integer(1, 1152)).executes((p) -> {
            return debit(p.getSource(), IntegerArgumentType.getInteger(p, "amount"));
        })));
        dispatcher.register(Commands.literal("auszahlen").redirect(literalCommandNode));
    }

    private static int debit(CommandSourceStack source, int amount) {
        ServerPlayer player = source.getPlayer();
        if (player == null) {
            source.sendFailure(Component.literal("you have to be a player"));
            return -1;
        }

        long balance = player.getPersistentData().contains("balance") ? player.getPersistentData().getLong("balance") : 0; // 100  20
        long exact = Math.min(balance, amount); //30 -> 30 20
        long update = balance - exact; //100 - 30 -> 70 20 - 20 -> 0
        if (exact == 0) {
            source.sendFailure(Component.literal("Your balance is too low!"));
            return -1;
        }

        player.getPersistentData().putLong("balance", update);

        Scoreboard scoreboard = source.getServer().getScoreboard();
        Collection<Objective> collection = scoreboard.getObjectives();
        for (Objective o : collection) {
            if (o.getName().equals("dumb")) {
                Score score = scoreboard.getOrCreatePlayerScore(player.getScoreboardName(), o);
                score.add((int) exact);
                source.sendSuccess(Component.literal("Debit was successful!"), true);
                return 1;
            }
        }

        source.sendFailure(Component.literal("Your balance is too low!"));
        return -1;
    }
}
