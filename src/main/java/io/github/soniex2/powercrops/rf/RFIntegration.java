package io.github.soniex2.powercrops.rf;

import cpw.mods.fml.common.registry.GameRegistry;
import io.github.soniex2.powercrops.Module;
import io.github.soniex2.powercrops.PowerCrops;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemSeeds;

/**
 * @author soniex2
 */
@GameRegistry.ObjectHolder(PowerCrops.MODID)
public class RFIntegration implements Module {
    public static Block rf_powercrop;
    public static Item rf_powerseed;

    @Override
    public void registerBlocksItemsAndTileEntities() {
        GameRegistry.registerBlock(rf_powercrop = new BlockRFCrop().setBlockName("powercrops:rf").setBlockTextureName("powercrops:rf"), null, "rf_powercrop");
        GameRegistry.registerItem(rf_powerseed = new ItemSeeds(rf_powercrop, Blocks.farmland).setUnlocalizedName("powercrops:rf_seeds").setTextureName("powercrops:rf"), "rf_powerseed");
        GameRegistry.registerTileEntity(TileEntityRFCrop.class, "powercrops:rf");
    }
}
