package io.github.soniex2.powercrops.buildcraft;

import io.github.soniex2.powercrops.Module;
import buildcraft.api.recipes.BuildcraftRecipeRegistry;
import io.github.soniex2.powercrops.rf.RFIntegration;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

/**
 * @author soniex2
 */
public class BuildCraftIntegration implements Module {
    @Override
    public void registerBlocksItemsAndTileEntities() {

    }

    @Override
    public void registerRecipes() {
        if (BuildcraftRecipeRegistry.assemblyTable != null) {
            BuildcraftRecipeRegistry.assemblyTable.addRecipe("powercrops:rf_powerseed", 15000, new ItemStack(RFIntegration.rf_powerseed), new ItemStack(Items.wheat_seeds));
        }
    }
}
