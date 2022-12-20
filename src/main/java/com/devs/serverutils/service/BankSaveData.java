package com.devs.serverutils.service;

import com.devs.serverutils.ServerUtils;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraftforge.event.level.LevelEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.core.jmx.Server;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class BankSaveData extends SavedData {

    public static boolean ENABLED = false;
    public static Map<UUID, Long> BANK = new HashMap<>();

    public static BankSaveData create() {
        return new BankSaveData();
    }

    public static BankSaveData load(CompoundTag tag) {
        BankSaveData data = create();
        ENABLED = tag.getBoolean("enabled");
        return data;
    }

    @Override
    public CompoundTag save(CompoundTag p_77763_) {
        p_77763_.putBoolean("enabled", ENABLED);
        return p_77763_;
    }

    public static void generateFile(MinecraftServer server) {
        server.overworld().getDataStorage().computeIfAbsent(BankSaveData::load, BankSaveData::create, "bank");
    }

    @Mod.EventBusSubscriber(modid = ServerUtils.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
    public class DataStorageEvents {
        @SubscribeEvent
        public static void worldSave(LevelEvent.Save event) {
            ServerUtils.LOGGER.info("saved bank!");
            BankSaveData.generateFile(event.getLevel().getServer());
        }

        @SubscribeEvent
        public static void worldLoad(LevelEvent.Load event) {

        }

    }


}
