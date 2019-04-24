package com.brewingcoder.eioocshim.oc;

import com.enderio.core.common.TileEntityBase;
import li.cil.oc.api.Network;
import li.cil.oc.api.driver.NamedBlock;
import li.cil.oc.api.network.Visibility;
import li.cil.oc.api.prefab.AbstractManagedEnvironment;
import net.minecraft.util.EnumFacing;

public class ManagedTileEnvironment<T extends TileEntityBase> extends AbstractManagedEnvironment implements NamedBlock {
    protected T tile;
    protected String name;
    protected EnumFacing side;

    public ManagedTileEnvironment(final T tile, EnumFacing side, String name) {
        this.tile = tile;
        this.name=name;
        this.side=side;
        this.setNode(Network.newNode(this, Visibility.Network).withComponent(name,Visibility.Network).create());
    }
    @Override
    public String preferredName() {
        return this.name;
    }

    @Override
    public int priority() {
        return 5;
    }

}
