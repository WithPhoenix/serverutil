package com.devs.serverutils.service;

import com.devs.serverutils.ServerUtils;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraftforge.event.level.LevelEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

public class WorldSaveData
//        extends SavedData
{

//    static volatile WorldSaveData instance;
//    private boolean enabled = false;
//
//    public boolean isEnabled() {
//        return this.enabled;
//    }
//
//    public void setEnabled(boolean val) {
//        this.enabled = val;
//    }
//
//    public static synchronized WorldSaveData getInstance() {
//        if (instance == null) {
//            synchronized (WorldSaveData.class) {
//                if (instance == null) {
//                    instance = new WorldSaveData();
//                }
//            }
//        }
//        return instance;
//    }
//
//    public static WorldSaveData create() {
//        return instance;
//    }
//
//    public static WorldSaveData load(CompoundTag tag) {
//        WorldSaveData data = create();
//        data.enabled = tag.getBoolean("enabled");
//        return data;
//    }
//
//    @Override
//    public CompoundTag save(CompoundTag p_77763_) {
//        p_77763_.putBoolean("enabled", enabled);
//        setDirty();
//        return p_77763_;
//    }
//
//    public static void generateFile(MinecraftServer server) {
//        server.overworld().getDataStorage().computeIfAbsent(WorldSaveData::load, WorldSaveData::create, "bank");
//    }

//    @Mod.EventBusSubscriber(modid = ServerUtils.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
//    public class DataStorageEvents {
//        @SubscribeEvent
//        public static void worldSave(LevelEvent.Save event) {
//            WorldSaveData.generateFile(event.getLevel().getServer());
//        }
//
//        @SubscribeEvent
//        public static void worldLoad(LevelEvent.Load event) {
//        }
//    }
}
