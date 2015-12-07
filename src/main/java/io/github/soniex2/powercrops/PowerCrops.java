package io.github.soniex2.powercrops;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.ModAPIManager;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import io.github.soniex2.powercrops.rf.RFIntegration;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;

@Mod(modid = PowerCrops.MODID, version = PowerCrops.VERSION, name = "PowerCrops",
     dependencies = "after:CoFHAPI|energy")
public class PowerCrops {
    public static final String MODID = "powercrops";
    public static final String VERSION = "0.1.0";

    @Mod.Instance(PowerCrops.MODID)
    public static PowerCrops instance;

    public Logger log;

    private ArrayList<Module> modules = new ArrayList<Module>();

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        log = event.getModLog();
        if (ModAPIManager.INSTANCE.hasAPI("CoFHAPI|energy")) {
            modules.add(new RFIntegration());
        }

        for (Module mod : modules) {
            mod.registerBlocksItemsAndTileEntities();
        }
    }
    
    @EventHandler
    public void init(FMLInitializationEvent event) {
    }
}
