package com.devs.serverutils.event;


import com.devs.serverutils.ServerUtils;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = ServerUtils.MODID)
public class Events {


    @SubscribeEvent
    public static void onPlayerCloneEvent(PlayerEvent.Clone event) {
        event.getEntity().getPersistentData().putLong("balance", event.getOriginal().getPersistentData().getLong("balance"));
    }
}
