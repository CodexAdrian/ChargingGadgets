package com.direwolf20.charginggadgets.common.capabilities;

import com.direwolf20.charginggadgets.common.tiles.ChargingStationTile;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.energy.EnergyStorage;

public class ChargerEnergyStorage extends EnergyStorage implements INBTSerializable<CompoundNBT> {
    private static final String KEY = "energy";
    private ChargingStationTile tile;

    public ChargerEnergyStorage(ChargingStationTile tile, int capacity) {
        super(capacity, Integer.MAX_VALUE);
        this.tile = tile;
    }

    public void setEnergy(int energy) {
        this.energy = energy;
    }

    // We don't want other blocks or things extracting our energy
    @Override
    public int extractEnergy(int maxExtract, boolean simulate) {
        return 0;
    }

    public int internalExtractEnergy(int maxExtract, boolean simulate) {
        if( !simulate)
            tile.markDirty();

        return super.extractEnergy(maxExtract, simulate);
    }

    @Override
    public int receiveEnergy(int maxReceive, boolean simulate) {
        if( !simulate )
            tile.markDirty();

        return super.receiveEnergy(maxReceive, simulate);
    }

    @Override
    public CompoundNBT serializeNBT() {
        CompoundNBT tag = new CompoundNBT();
        tag.putInt(KEY, getEnergyStored());
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {
        setEnergy(nbt.getInt(KEY));

    }
}