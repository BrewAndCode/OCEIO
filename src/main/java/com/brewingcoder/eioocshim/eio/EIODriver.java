package com.brewingcoder.eioocshim.eio;

import com.brewingcoder.eioocshim.oc.ManagedTileEnvironment;
import com.enderio.core.common.TileEntityBase;
import li.cil.oc.api.network.ManagedEnvironment;
import li.cil.oc.api.prefab.DriverSidedTileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public abstract class EIODriver<T extends TileEntityBase> extends DriverSidedTileEntity {
    private Class<T> clazz;

    public EIODriver(Class<T> clazz){
        this.clazz = clazz;
    }

    @Override
    public Class<?> getTileEntityClass(){
        return clazz;
    }

    @Override
    public ManagedEnvironment createEnvironment(World world, BlockPos pos, EnumFacing side) {
        return create((T) world.getTileEntity(pos),side);
    }

    public abstract ManagedTileEnvironment<T> create(T tile, EnumFacing side);
}
