package com.devs.serverutils;

import com.devs.serverutils.command.*;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.logging.LogUtils;
import net.minecraft.commands.CommandSourceStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

@Mod(ServerUtils.MODID)
public class ServerUtils {

    public static final String MODID = "serverutils";
    public static final Logger LOGGER = LogUtils.getLogger();

    public ServerUtils() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        modEventBus.addListener(this::commonSetup);


        MinecraftForge.EVENT_BUS.register(this);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
    }


    @SubscribeEvent
    public void loadCommands(final RegisterCommandsEvent event) {
        CommandDispatcher<CommandSourceStack> dispatcher = event.getDispatcher();
        PayCommand.register(dispatcher);
        ConvertCommand.register(dispatcher);
        BalanceCommand.register(dispatcher);
        ClearCommand.register(dispatcher);
        DebitCommand.register(dispatcher);
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        LOGGER.info("Initialized bank!");
    }


    //    @SubscribeEvent
//    public void registerPlayer(final EntityJoinLevelEvent event) {
//        if (event.getEntity() instanceof ServerPlayer player) {
//            BankSaveData.INSTANCE.addPlayer(player.getUUID());
//        }
//    }
}
