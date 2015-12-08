package io.github.soniex2.powercrops.rf;

import cpw.mods.fml.common.event.FMLInterModComms;
import cpw.mods.fml.common.registry.GameRegistry;
import io.github.soniex2.powercrops.Module;
import io.github.soniex2.powercrops.PowerCrops;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemSeeds;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

/**
 * @author soniex2
 */
@GameRegistry.ObjectHolder(PowerCrops.MODID)
public class RFIntegration implements Module {
    public static final Block rf_powercrop;
    public static final Item rf_powerseed;

    static {
        rf_powercrop = new BlockRFCrop().setBlockName("powercrops:rf").setBlockTextureName("powercrops:rf");
        rf_powerseed = new ItemSeeds(rf_powercrop, Blocks.farmland).setUnlocalizedName("powercrops:rf_seeds").setTextureName("powercrops:rf");
    }

    @Override
    public void registerBlocksItemsAndTileEntities() {
        GameRegistry.registerBlock(rf_powercrop, null, "rf_powercrop");
        GameRegistry.registerItem(rf_powerseed, "rf_powerseed");
        GameRegistry.registerTileEntity(TileEntityRFCrop.class, "powercrops:rf");
    }

    @Override
    public void registerRecipes() {
        // ThermalExpansion recipe
        NBTTagCompound toSend = new NBTTagCompound();

        toSend.setInteger("energy", 15000);
        toSend.setTag("input", new NBTTagCompound());
        toSend.setTag("output", new NBTTagCompound());

        new ItemStack(Items.wheat_seeds).writeToNBT(toSend.getCompoundTag("input"));
        new ItemStack(RFIntegration.rf_powerseed).writeToNBT(toSend.getCompoundTag("output"));
        FMLInterModComms.sendMessage("ThermalExpansion", "ChargerRecipe", toSend);
    }
}
