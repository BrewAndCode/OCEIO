package com.brewingcoder.eioocshim.common.config;

import com.brewingcoder.eioocshim.EIOOCShim;
import crazypants.enderio.base.config.factory.ValueFactoryEIO;

public final class Config {
    public static final ValueFactoryEIO F = new ValueFactoryEIO(EIOOCShim.MODID);
    static {
        EIOOCShimConfig.F.getClass();
    }
}
