package com.devs.serverutils.event;

import com.devs.serverutils.ServerUtils;
import com.devs.serverutils.command.PayCommand;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = ServerUtils.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.DEDICATED_SERVER)
public class Events {

    @SubscribeEvent
    public static void registerCommands(RegisterCommandsEvent event) {
        PayCommand.register(event.getDispatcher());
    }


}
