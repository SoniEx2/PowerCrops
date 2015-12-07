package io.github.soniex2.powercrops.rf;

import cofh.api.energy.*;
import io.github.soniex2.powercrops.PowerCrops;
import io.github.soniex2.powercrops.base.TileEntityPowerCrops;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

/**
 * @author soniex2
 */
public class TileEntityRFCrop extends TileEntityPowerCrops implements IEnergyProvider {
    private static final int GEN_RATE = 2000; // 2000 RF / update
    private static final int SEND_RATE = GEN_RATE / 20; // 100 RF / tick
    private static final int GROWTH_UPDATES = 7; // there are 8 growth stages (0-7) but only 7 updates (stage changes)

    private EnergyStorage storage = new EnergyStorage(GEN_RATE * GROWTH_UPDATES, GEN_RATE, SEND_RATE);

    @Override
    public void updateEntity() {
        super.updateEntity();

        for (ForgeDirection fd : ForgeDirection.VALID_DIRECTIONS) {
            TileEntity te = worldObj.getTileEntity(fd.offsetX + xCoord, fd.offsetY + yCoord, fd.offsetZ + zCoord);
            if (te instanceof IEnergyHandler || te instanceof IEnergyReceiver) {
                if (((IEnergyConnection) te).canConnectEnergy(fd.getOpposite())) {
                    int power = 0;

                    if (te instanceof IEnergyHandler) {
                        IEnergyHandler handler = (IEnergyHandler) te;
                        int maxEnergy = handler.receiveEnergy(fd.getOpposite(), storage.getEnergyStored(), true);
                        power = storage.extractEnergy(maxEnergy, true);
                    } else if (te instanceof IEnergyReceiver) {
                        IEnergyReceiver handler = (IEnergyReceiver) te;
                        int maxEnergy = handler.receiveEnergy(fd.getOpposite(), storage.getEnergyStored(), true);
                        power = storage.extractEnergy(maxEnergy, true);
                    }

                    if (power <= 0) {
                        continue;
                    }

                    if (te instanceof IEnergyHandler) {
                        IEnergyHandler handler = (IEnergyHandler) te;
                        int neededRF = handler.receiveEnergy(fd.getOpposite(), power, false);
                        storage.extractEnergy(neededRF, false);
                    } else if (te instanceof IEnergyReceiver) {
                        IEnergyReceiver handler = (IEnergyReceiver) te;
                        int neededRF = handler.receiveEnergy(fd.getOpposite(), power, false);
                        storage.extractEnergy(neededRF, false);
                    }
                }
            }
        }
    }

    @Override
    public boolean canConnectEnergy(ForgeDirection from) {
        return true;
    }

    /**
     * Remove energy from an IEnergyProvider, internal distribution is left entirely to the IEnergyProvider.
     *
     * @param from       Orientation the energy is extracted from.
     * @param maxExtract Maximum amount of energy to extract.
     * @param simulate   If TRUE, the extraction will only be simulated.
     * @return Amount of energy that was (or would have been, if simulated) extracted.
     */
    @Override
    public int extractEnergy(ForgeDirection from, int maxExtract, boolean simulate) {
        return storage.extractEnergy(maxExtract, simulate);
    }

    /**
     * Returns the amount of energy currently stored.
     *
     * @param from
     */
    @Override
    public int getEnergyStored(ForgeDirection from) {
        return storage.getEnergyStored();
    }

    /**
     * Returns the maximum amount of energy that can be stored.
     *
     * @param from
     */
    @Override
    public int getMaxEnergyStored(ForgeDirection from) {
        return storage.getMaxEnergyStored();
    }

    @Override
    public void grow() {
        storage.receiveEnergy(GEN_RATE, false);
    }

    @Override
    public void readFromNBT(NBTTagCompound tag) {
        super.readFromNBT(tag);
        storage.readFromNBT(tag);
    }

    @Override
    public void writeToNBT(NBTTagCompound tag) {
        super.writeToNBT(tag);
        storage.writeToNBT(tag);
    }
}
