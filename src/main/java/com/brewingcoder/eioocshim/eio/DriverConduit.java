package com.brewingcoder.eioocshim.eio;

import com.brewingcoder.eioocshim.EIOOCShim;
import com.brewingcoder.eioocshim.oc.ManagedTileEnvironment;
import crazypants.enderio.base.conduit.IConduit;
import crazypants.enderio.base.conduit.IConduitNetwork;
import crazypants.enderio.base.machine.modes.RedstoneControlMode;
import crazypants.enderio.conduits.conduit.TileConduitBundle;
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

        private NetworkPowerManager GetNetworkPowerManager(){
            PowerConduit pc = tile.getConduit(PowerConduit.class);
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
            return  pm.getNetworkPowerTracker();
        }

        @Callback (doc="function(string EnumFacing) : string - redstone control mode for given side of power conduit")
        public Object[] getExtractionRedstoneMode(Context ctx, Arguments args){
            PowerConduit pc = tile.getConduit(PowerConduit.class);
            return (pc != null)
                ? new Object[] {pc.getExtractionRedstoneMode(EnumFacing.valueOf(args.checkString(0)))}
                : new Object[] {null};
        }

        @Callback (doc="function(string RedstoneControlMode, string EnumFacing): {number:result,string:message} sets redstone control mode for given face")
        public Object[] setExtractionRedstoneMode(Context ctx, Arguments args){
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
        public Object[] getMaxPowerInConduits(Context ctx, Arguments args){
            NetworkPowerManager pm = GetNetworkPowerManager();
            return (pm != null)
                    ? new Object[]{pm.getMaxPowerInConduits()}
                    : new Object[]{null};
        }

        @Callback (doc="function():number; Returns the current power stored in the conduits")
        public Object[] getPowerInConduits(Context ctx, Arguments args){
            NetworkPowerManager pm = GetNetworkPowerManager();
            return (pm != null)
                    ? new Object[]{pm.getPowerInConduits()}
                    : new Object[]{null};
        }

        @Callback (doc="function():number; Returns the maximum power that can reside in power receptors on this network")
        public Object[] getMaxPowerInReceptors(Context ctx, Arguments args){
            NetworkPowerManager pm = GetNetworkPowerManager();
            return (pm != null)
                    ? new Object[]{pm.getMaxPowerInReceptors()}
                    : new Object[]{null};
        }

        @Callback (doc="function():number; Returns the current power stored in the receptors")
        public Object[] getPowerInReceptors(Context ctx, Arguments args){
            NetworkPowerManager pm = GetNetworkPowerManager();
            return (pm != null)
                    ? new Object[]{pm.getPowerInReceptors()}
                    : new Object[]{null};
        }

        @Callback (doc="function():number; Returns the average output per tick across this conduit bundle")
        public Object[] getAverageOutputPerTick(Context ctx, Arguments args){
            PowerTracker pt = GetNetworkPowerTracker();
            return (pt != null)
                    ? new Object[]{pt.getAverageRfTickSent()}
                    : new Object[]{null};
        }

        @Callback (doc="function():number; Returns the average input per Tick")
        public Object[] getAverageInputPerTick(Context ctx, Arguments args){
            PowerTracker pt = GetNetworkPowerTracker();
            return (pt != null)
                    ? new Object[]{pt.getAverageRfTickRecieved()}
                    : new Object[]{null};
        }

        @Callback(doc = "function():number; Returns Max Energy Stored")
        public Object[] getMaxEnergyStored(Context ctx, Arguments args){
            NetworkPowerManager pm = GetNetworkPowerManager();
            return (pm != null)
                    ? new Object[]{pm.getMaxPowerInCapacitorBanks()}
                    : new Object[]{null};
        }

        @Callback(doc = "function():number; Returns Current Energy Stored")
        public Object[] getCurrentEnergyStored(Context ctx, Arguments args){
            NetworkPowerManager pm = GetNetworkPowerManager();
            return (pm != null)
                    ? new Object[]{pm.getPowerInCapacitorBanks()}
                    : new Object[]{null};
        }

        @Callback(doc = "function():bool; Returns true if the bundle contains a power conduit")
        public Object[] doesBundleContainPowerConduit(Context context, Arguments args){
            for (IConduit con : tile.getConduits()){
                if(con.getClass().toString().contains("AbstractConduit")){
                    return new Object[]{true};
                }
            }
            return new Object[]{false};
        }
    }
}
