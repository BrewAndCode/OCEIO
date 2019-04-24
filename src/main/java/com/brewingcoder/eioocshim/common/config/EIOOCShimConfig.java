package com.brewingcoder.eioocshim.common.config;

import info.loenwind.autoconfig.factory.IValue;
import info.loenwind.autoconfig.factory.IValueFactory;

public final class EIOOCShimConfig {
    public static final IValueFactory F = Config.F.section("enderioocshim");

    public static final IValue<Boolean> enableTier1 = F.make("enable_tier1",true,"enable integration with tier1 (Basic) capacitor banks").sync();
    public static final IValue<Boolean> enableTier2 = F.make("enable_tier2",true,"enable integration with tier2 (normal) Capacitor banks").sync();
    public static final IValue<Boolean> enableTier3 = F.make("enable_tier3",true,"enable integration with tier3 (Vibrant) Capacitor banks").sync();
}
