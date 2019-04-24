package com.brewingcoder.eioocshim.eio;

import com.brewingcoder.eioocshim.EIOOCShim;
import com.brewingcoder.eioocshim.oc.ManagedTileEnvironment;
import crazypants.enderio.base.machine.modes.RedstoneControlMode;
import crazypants.enderio.machines.machine.buffer.TileBuffer;
import li.cil.oc.api.machine.Arguments;
import li.cil.oc.api.machine.Callback;
import li.cil.oc.api.machine.Context;
import net.minecraft.util.EnumFacing;

public class DriverItemBuffer extends EIODriver<TileBuffer.TileBufferItem> {
    public DriverItemBuffer() {super(TileBuffer.TileBufferItem.class);}

    @Override
    public ManagedTileEnvironment<TileBuffer.TileBufferItem> create(TileBuffer.TileBufferItem tile, EnumFacing side) {
        return new Env(tile,side,"enderio_item_buffer");
    }

    public static final class Env extends  ManagedTileEnvironment<TileBuffer.TileBufferItem>{
        Env(TileBuffer.TileBufferItem tile, EnumFacing side, String name) {super(tile,side,name);}

        @Override
        public int priority() {return 2;}

        @Callback(doc = "function():string;  -- Returns name of mod providing integration. Used for validation that mod is installed prior to calling these functions.")
        public Object[] getIntegrationProvider(Context context, Arguments args){
            return new Object[]{EIOOCShim.MODID};
        }
        @Callback(doc = "function():number; -- Returns the size (slots) of the inventory of the buffer")
        public Object[] getSizeInventory(Context c, Arguments a){
            return new Object[]{tile.getSizeInventory()};
        }

        @Callback(doc = "function(number):ItemStack; -- Returns the stack located in the passed slot")
        public Object[] getStackInSlot(Context c, Arguments a) {
            return new Object[]{tile.getStackInSlot(a.checkInteger(0))};
        }

        @Callback(doc = "function():string; -- Returns the redstone control mode setting")
        public Object[] getRedstoneControlMode(Context c, Arguments a) {
            return new Object[]{tile.getRedstoneControlMode()};
        }

        @Callback(doc = "function():string; -- sets the Redstone Control mode (ON,OFF,IGNORE,NEVER")
        public Object[] setRedstoneControlMode(Context c, Arguments a) {
            if(tile != null) {
                try {
                    tile.setRedstoneControlMode(RedstoneControlMode.valueOf(a.checkString(0)));
                    return new Object[]{0,"OK"};
                }
                catch(IllegalArgumentException e) {
                    return new Object[]{-1,e.getMessage()};
                }
            }
            return new Object[]{-1,"Tile Entity is not valid"};
        }
    }
}
