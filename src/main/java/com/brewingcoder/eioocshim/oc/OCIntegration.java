package com.brewingcoder.eioocshim.oc;

import com.brewingcoder.eioocshim.eio.DriverCapacitorBank;
import com.brewingcoder.eioocshim.eio.DriverItemBuffer;
import li.cil.oc.api.Driver;

public class OCIntegration {

    public OCIntegration() {
    }

    public void preInit(){
    }

    public void init(){
        Driver.add(new DriverCapacitorBank());
        Driver.add(new DriverItemBuffer());
    }

    public void postInit(){

    }
}
