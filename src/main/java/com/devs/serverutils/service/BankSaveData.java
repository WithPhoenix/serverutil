package com.devs.serverutils.service;

import com.devs.serverutils.ServerUtils;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraftforge.event.level.LevelEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class BankSaveData extends SavedData {

    public static BankSaveData INSTANCE = new BankSaveData();
    private boolean enabled = false;
    private Map<UUID, Long> bank = new HashMap<>();

    public boolean isEnabled() {
        return this.enabled;
    }

    public void setEnabled(boolean val) {
        this.enabled = val;
    }

    public void addPlayer(UUID uuid) {
        if (this.bank.containsKey(uuid)) return;
        this.bank.put(uuid, 0L);
    }

    public static BankSaveData create() {
        ServerUtils.LOGGER.error("created");
        return new BankSaveData();
    }

    public static BankSaveData load(CompoundTag tag) {
        ServerUtils.LOGGER.error("loaded");
        BankSaveData data = create();
        data.enabled = tag.getBoolean("enabled");
        return data;
    }

    @Override
    public CompoundTag save(CompoundTag p_77763_) {
        ServerUtils.LOGGER.error("saved");
        p_77763_.putBoolean("enabled", enabled);
        setDirty();
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
