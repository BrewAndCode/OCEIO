package com.brewingcoder.eioocshim;

import com.brewingcoder.eioocshim.common.config.Config;
import com.brewingcoder.eioocshim.oc.OCIntegration;
import info.loenwind.autoconfig.ConfigHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nonnull;

@SuppressWarnings("ALL")
@Mod(modid = EIOOCShim.MODID, name = EIOOCShim.NAME, version = EIOOCShim.VERSION,
    dependencies =    "required-after:opencomputers@[1.6.2,);"
                    + "required-after:enderio@[5.0.40,);"
)
public class EIOOCShim
{
    public static final @Nonnull String MODID = "eioocshim";
    public static final @Nonnull String NAME = "EnderIO OpenComputer Shim";
    public static final @Nonnull String VERSION = "@VERSION@";
    public static Logger logger;
    public static OCIntegration opencomputers;

    @Mod.Instance(MODID) public static EIOOCShim instance;

    public static ConfigHandler configHandler;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        logger = event.getModLog();
        configHandler = new ConfigHandler(event, Config.F);
        opencomputers = new OCIntegration();
        opencomputers.preInit();
    }

    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        opencomputers.init();
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {
        opencomputers.postInit();
    }
}
