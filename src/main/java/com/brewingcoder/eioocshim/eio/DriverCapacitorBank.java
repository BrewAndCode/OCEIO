package com.brewingcoder.eioocshim.eio;

import com.brewingcoder.eioocshim.EIOOCShim;
import com.brewingcoder.eioocshim.oc.ManagedTileEnvironment;
import crazypants.enderio.base.machine.modes.RedstoneControlMode;
import crazypants.enderio.powertools.machine.capbank.CapBankType;
import crazypants.enderio.powertools.machine.capbank.TileCapBank;
import li.cil.oc.api.machine.Arguments;
import li.cil.oc.api.machine.Callback;
import li.cil.oc.api.machine.Context;
import net.minecraft.util.EnumFacing;

public class DriverCapacitorBank extends EIODriver<TileCapBank> {

    public DriverCapacitorBank(){
        super(TileCapBank.class);
    }

    @Override
    public ManagedTileEnvironment<TileCapBank> create(TileCapBank tile, EnumFacing side) {

        String name;

        if (tile.getType() == CapBankType.SIMPLE) {name = "simple_capbank";}
        else if (tile.getType() == CapBankType.VIBRANT) {name= "vibrant_capbank";}
        else if (tile.getType() == CapBankType.CREATIVE) {name="creative_capbank";}
        else {name=tile.getType().toString();}

        return new Env(tile,side,name);
    }

    public static final class Env extends ManagedTileEnvironment<TileCapBank>{
        Env(TileCapBank tile, EnumFacing side, String name){
            super(tile, side, name);
        }

        @Override
        public int priority(){
            return 2;
        }

        @Callback(doc = "function():string;  -- Returns name of mod providing integration. Used for validation that mod is installed prior to calling these functions.")
        public Object[] getIntegrationProvider(Context context, Arguments args){
            return new Object[]{EIOOCShim.MODID};
        }

        @Callback(doc = "function():number;  -- Returns the current average input of uI per tick.")
        public Object[] getAverageInputPerTick(Context c, Arguments a){
            return tile.getNetwork() != null
                    ? new Object[] {tile.getNetwork().getAverageInputPerTick()}
                    : new Object[]{null};
        }

        @Callback(doc = "function():number;  -- Returns the current average output of uI per tick.")
        public Object[] getAverageOutputPerTick(Context c, Arguments a){
            return tile.getNetwork() != null
                    ? new Object[] {tile.getNetwork().getAverageOutputPerTick()}
                    : new Object[]{null};
        }

        @Callback(doc = "function():number;  -- Returns the current average change of uI per tick.")
        public Object[] getAverageChangePerTick(Context c, Arguments a){
            return tile.getNetwork() != null
                    ? new Object[] {tile.getNetwork().getAverageChangePerTick()}
                    : new Object[]{null};
        }

        @Callback(doc = "function():string;  -- Returns the current redstone INPUT control mode.")
        public Object[] getInputControlMode(Context c, Arguments a){
            return tile.getNetwork() != null
                    ? new Object[] {tile.getNetwork().getInputControlMode().toString()}
                    : new Object[]{null};
        }

        @Callback(doc = "function():string;  -- Returns the current redstone INPUT control mode.")
        public Object[] getOutputControlMode(Context c, Arguments a){
            return tile.getNetwork() != null
                    ? new Object[] {tile.getNetwork().getOutputControlMode().toString()}
                    : new Object[]{null};
        }

        @Callback(doc = "function(string):number,string;  -- Sets the current redstone OUTPUT control mode.")
        public Object[] setOutputControlMode(Context c, Arguments a){
            if (tile.getNetwork() != null) {
                try {
                    tile.getNetwork().setOutputControlMode(RedstoneControlMode.valueOf(a.checkString(0)));
                    return new Object[]{0,"OK"};
                } catch (IllegalArgumentException e) {
                    return new Object[]{-1,e.getMessage()};
                }
            }else{
                return new Object[] {-1,"EIO Power Network not available! (returned nil)"};
            }
        }

        @Callback(doc = "function(string):number,string;  -- Sets the current redstone INPUT control mode.")
        public Object[] setInputControlMode(Context c, Arguments a){
            if (tile.getNetwork() != null) {
                try {
                    tile.getNetwork().setInputControlMode(RedstoneControlMode.valueOf(a.checkString(0)));
                    return new Object[]{0,"OK"};
                } catch (IllegalArgumentException e) {
                    return new Object[]{-1,e.getMessage()};
                }
            }else{
                return new Object[] {-1,"EIO Power Network not available! (returned nil)"};
            }
        }

        @Callback(doc = "function():number;  -- Returns the maximum energy this bank can hold")
        public Object[] getMaximumStorage(Context c, Arguments a){
            return tile.getNetwork() != null
                    ? new Object[] {tile.getNetwork().getMaxEnergyStoredL()}
                    : new Object[]{null};
        }

        @Callback(doc = "function():number;  -- Returns the current energy stored in the bank")
        public Object[] getCurrentStorage(Context c, Arguments a){
            return tile.getNetwork() != null
                    ? new Object[] {tile.getNetwork().getEnergyStoredL()}
                    : new Object[]{null};


        }


    }
}
