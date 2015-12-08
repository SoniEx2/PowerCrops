package io.github.soniex2.powercrops.rf;

import io.github.soniex2.powercrops.base.BlockPowerCrops;
import io.github.soniex2.powercrops.base.TileEntityPowerCrops;
import net.minecraft.item.Item;
import net.minecraft.world.World;

/**
 * @author soniex2
 */
public class BlockRFCrop extends BlockPowerCrops {
    @Override
    public TileEntityPowerCrops createNewTileEntity(World p_149915_1_, int p_149915_2_) {
        return new TileEntityRFCrop();
    }

    @Override
    protected Item getProduct() {
        return RFIntegration.rf_powerseed;
    }
}
