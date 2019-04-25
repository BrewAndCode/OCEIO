package com.brewingcoder.eioocshim.eio;

import com.brewingcoder.eioocshim.EIOOCShim;
import com.brewingcoder.eioocshim.oc.ManagedTileEnvironment;
import com.enderio.core.common.util.DyeColor;
import crazypants.enderio.base.conduit.IConduitNetwork;
import crazypants.enderio.base.machine.modes.RedstoneControlMode;
import crazypants.enderio.conduits.conduit.TileConduitBundle;
import crazypants.enderio.conduits.conduit.item.ItemConduit;
import crazypants.enderio.conduits.conduit.power.NetworkPowerManager;
import crazypants.enderio.conduits.conduit.power.PowerConduit;
import crazypants.enderio.conduits.conduit.power.PowerConduitNetwork;
import crazypants.enderio.conduits.conduit.power.PowerTracker;
import li.cil.oc.api.machine.Arguments;
import li.cil.oc.api.machine.Callback;
import li.cil.oc.api.machine.Context;
import net.minecraft.util.EnumFacing;

public class DriverConduit extends EIODriver<TileConduitBundle> {
    public DriverConduit() {super(TileConduitBundle.class);}

    @Override
    public ManagedTileEnvironment<TileConduitBundle> create(TileConduitBundle tile, EnumFacing side) {
        return new Env(tile,side,"enderio_conduit_bundle");
    }

    public static final class Env extends  ManagedTileEnvironment<TileConduitBundle> {
        Env(TileConduitBundle tile, EnumFacing side, String name) {
            super(tile, side, name);
        }

        @Override
        public int priority() {return 2;}

        @Callback(doc = "function():string;  -- Returns name of mod providing integration. Used for validation that mod is installed prior to calling these functions.")
        public Object[] getIntegrationProvider(Context context, Arguments args){
            return new Object[]{EIOOCShim.MODID};
        }

        @Callback(doc = "function():table -- Returns a table of all conduits in bundle")
        public Object[] getBundleConduits(Context context, Arguments args){
            return new Object[]{tile.getConduits()};
        }

        private ItemConduit GetItemConduit(){
            return tile.getConduit(ItemConduit.class);
        }

        @Callback(doc = "fuction():bool; Returns tru if the bundle contains an item conduit")
        public Object[] item_hasConduit(Context ctx, Arguments args){
            return new Object[]{GetItemConduit() != null};
        }

        @Callback (doc="function(string RedstoneControlMode, string EnumFacing): {number:result,string:message} sets redstone control mode for given face")
        public Object[] item_getExtractionRedstoneMode(Context ctx, Arguments args){
            ItemConduit ic = GetItemConduit();
            return (ic != null)
                    ? new Object[]  {ic.getExtractionRedstoneMode(EnumFacing.valueOf(args.checkString(0)))}
                    : new Object[] {null};
        }

        @Callback (doc="function(string RedstoneControlMode, string EnumFacing): {number:result,string:message} sets redstone control mode for given face")
        public Object[] item_setExtractionRedstoneMode(Context ctx, Arguments args){
            ItemConduit ic = GetItemConduit();
            if(ic != null){
                try{
                    ic.setExtractionRedstoneMode(
                            RedstoneControlMode.valueOf(args.checkString(0)),
                            EnumFacing.valueOf(args.checkString(1)));
                    return new Object[] {0,"OK"};
                }catch(Exception e)
                {
                    return new Object[]{-1, e.getMessage()};
                }
            }
            return new Object[] { -1,"Was Null"};
        }

        @Callback (doc="function(string EnumFacing : string Dye Color")
        public Object[] item_getExtractionSignalColor(Context ctx, Arguments args)
        {
            ItemConduit ic = GetItemConduit();
            return (ic != null)
                    ? new Object[] {(ic.getExtractionSignalColor(EnumFacing.valueOf(args.checkString(0))))}
                    : new Object[] {null};
        }

        @Callback (doc="function(string enumfacing, string DyeColor) returns result code and description,  0/OK means success")
        public Object[] item_setExtractionSignalColor(Context ctx, Arguments args)
        {
            ItemConduit ic = GetItemConduit();
            if(ic != null){
                try{
                    ic.setExtractionSignalColor(EnumFacing.valueOf(args.checkString(0)), DyeColor.valueOf(args.checkString(1)));
                    return new Object[] {0,"OK"};
                }catch(Exception e)
                {
                    return new Object[] {-1, e.getMessage()};
                }
            }
            return new Object[] {-1, "Was Null"};
        }

        private PowerConduit GetPowerConduit(){
            return tile.getConduit(PowerConduit.class);
        }
        private NetworkPowerManager GetNetworkPowerManager(){
            PowerConduit pc = GetPowerConduit();
            if (pc != null) {
                IConduitNetwork<?, ?> n = pc.getNetwork();
                if (n instanceof PowerConduitNetwork) {
                    return ((PowerConduitNetwork)n).getPowerManager();
                }
            }
            return null;
        }
        private PowerTracker GetNetworkPowerTracker(){
            NetworkPowerManager pm = GetNetworkPowerManager();
            return pm != null ? pm.getNetworkPowerTracker() : null;
        }

        @Callback(doc = "function():bool; Returns true if the bundle contains a power conduit")
        public Object[] pwr_hasConduit(Context context, Arguments args){
            return new Object[]{GetPowerConduit() != null};
        }

        @Callback (doc="function(string EnumFacing) : string - redstone control mode for item extraction on the given face")
        public Object[] pwr_getExtractionRedstoneMode(Context ctx, Arguments args){
            PowerConduit pc = tile.getConduit(PowerConduit.class);
            return (pc != null)
                ? new Object[] {pc.getExtractionRedstoneMode(EnumFacing.valueOf(args.checkString(0)))}
                : new Object[] {null};
        }

        @Callback (doc="function(string RedstoneControlMode, string EnumFacing): {number:result,string:message} sets redstone control for item extraction on the given face")
        public Object[] pwr_setExtractionRedstoneMode(Context ctx, Arguments args){
            PowerConduit pc = tile.getConduit(PowerConduit.class);
            if (pc != null){
                try {
                    pc.setExtractionRedstoneMode(
                            RedstoneControlMode.valueOf(args.checkString(0)),
                            EnumFacing.valueOf(args.checkString(1)));
                    return new Object[] {0,"OK"};

                }catch (Exception e)
                {
                    return new Object[] {-1,e.getMessage()};
                }
            }
            return new Object[] {-1,"Was Null"};
        }

        @Callback (doc="function():number; Returns the maximum power that can reside in the conduits")
        public Object[] pwr_getMaxPowerInConduits(Context ctx, Arguments args){
            NetworkPowerManager pm = GetNetworkPowerManager();
            return (pm != null)
                    ? new Object[]{pm.getMaxPowerInConduits()}
                    : new Object[]{null};
        }

        @Callback (doc="function():number; Returns the current power stored in the conduits")
        public Object[] pwr_getPowerInConduits(Context ctx, Arguments args){
            NetworkPowerManager pm = GetNetworkPowerManager();
            return (pm != null)
                    ? new Object[]{pm.getPowerInConduits()}
                    : new Object[]{null};
        }

        @Callback (doc="function():number; Returns the maximum power that can reside in power receptors on this network")
        public Object[] pwr_getMaxPowerInReceptors(Context ctx, Arguments args){
            NetworkPowerManager pm = GetNetworkPowerManager();
            return (pm != null)
                    ? new Object[]{pm.getMaxPowerInReceptors()}
                    : new Object[]{null};
        }

        @Callback (doc="function():number; Returns the current power stored in the receptors")
        public Object[] pwr_getPowerInReceptors(Context ctx, Arguments args){
            NetworkPowerManager pm = GetNetworkPowerManager();
            return (pm != null)
                    ? new Object[]{pm.getPowerInReceptors()}
                    : new Object[]{null};
        }

        @Callback (doc="function():number; Returns the average output per tick across this conduit bundle")
        public Object[] pwr_getAverageOutputPerTick(Context ctx, Arguments args){
            PowerTracker pt = GetNetworkPowerTracker();
            return (pt != null)
                    ? new Object[]{pt.getAverageRfTickSent()}
                    : new Object[]{null};
        }

        @Callback (doc="function():number; Returns the average input per Tick")
        public Object[] pwr_getAverageInputPerTick(Context ctx, Arguments args){
            PowerTracker pt = GetNetworkPowerTracker();
            return (pt != null)
                    ? new Object[]{pt.getAverageRfTickRecieved()}
                    : new Object[]{null};
        }

        @Callback(doc = "function():number; Returns Max Energy Stored")
        public Object[] pwr_getMaxEnergyStored(Context ctx, Arguments args){
            NetworkPowerManager pm = GetNetworkPowerManager();
            return (pm != null)
                    ? new Object[]{pm.getMaxPowerInCapacitorBanks()}
                    : new Object[]{null};
        }

        @Callback(doc = "function():number; Returns Current Energy Stored")
        public Object[] pwr_getCurrentEnergyStored(Context ctx, Arguments args){
            NetworkPowerManager pm = GetNetworkPowerManager();
            return (pm != null)
                    ? new Object[]{pm.getPowerInCapacitorBanks()}
                    : new Object[]{null};
        }

    }
}
