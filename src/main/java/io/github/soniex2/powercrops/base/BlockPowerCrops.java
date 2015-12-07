package io.github.soniex2.powercrops.base;

import net.minecraft.block.Block;
import net.minecraft.block.BlockCrops;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.ListIterator;
import java.util.Random;

/**
 * @author soniex2
 */
public abstract class BlockPowerCrops extends BlockCrops implements ITileEntityProvider {

    @Override
    public void updateTick(World world, int x, int y, int z, Random rand) {
        int oldMeta = world.getBlockMetadata(x, y, z);
        super.updateTick(world, x, y, z, rand);
        int newMeta = world.getBlockMetadata(x, y, z);
        if (oldMeta < newMeta) {
            // growth happened
            TileEntity tileEntity = world.getTileEntity(x, y, z);
            if (tileEntity instanceof TileEntityPowerCrops) {
                ((TileEntityPowerCrops) tileEntity).grow();
            }
        }
    }

    @Override
    public void breakBlock(World world, int x, int y, int z, Block block, int meta) {
        super.breakBlock(world, x, y, z, block, meta);
        //world.removeTileEntity(x, y, z);
    }

    @Override
    public boolean onBlockEventReceived(World world, int x, int y, int z, int eid, int earg) {
        super.onBlockEventReceived(world, x, y, z, eid, earg);
        TileEntity tileentity = world.getTileEntity(x, y, z);
        return tileentity != null ? tileentity.receiveClientEvent(eid, earg) : false;
    }

    @Override
    public abstract TileEntityPowerCrops createNewTileEntity(World p_149915_1_, int p_149915_2_);

    // BlockCrops stuff

    @Override
    protected Item func_149866_i() {
        return getSeed();
    }
    
    @Override
    protected Item func_149865_P() {
        return getProduct();
    }

    /**
     * Gets the item to drop when this crop is fully grown.
     *
     * @return The item to drop when this crop is fully grown.
     */
    protected abstract Item getProduct();
    
    protected Item getSeed() {
        return null;
    }

    @Override
    public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune) {
        ArrayList<ItemStack> ret = super.getDrops(world, x, y, z, metadata, fortune);

        if (metadata >= 7) {
            for (ListIterator<ItemStack> iter = ret.listIterator(); iter.hasNext(); ) {
                ItemStack is = iter.next();
                if (is.getItem() == null) iter.remove();
            }
        }

        return ret;
    }

    // IGrowable stuff

    /**
     * Should bonemeal be consumed?
     */
    @Override
    public boolean func_149851_a(World world, int x, int y, int z, boolean isRemote) {
        return false;
    }

    /**
     * Should we try to grow the crop?
     */
    @Override
    public boolean func_149852_a(World world, Random rand, int x, int y, int z) {
        return false;
    }

    /**
     * Forcefully grows the crop.
     */
    @Override
    public void func_149853_b(World world, Random rand, int x, int y, int z) {

    }
}
