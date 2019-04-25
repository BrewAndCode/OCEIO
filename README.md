# OCEIO - Open Computer Driver for EnderIO #

## Why I wrote this mod ##

It used to exist, in a mod called 'Computronics' by Vexatos located here: https://github.com/Vexatos/Computronics
It was part of a much bigger mod with broad support for many other mods.  I'm not sure where Computronics stands but the wiki with the download location 
is frequently offline and the mod itself is not hosted on curseforge.

Seeing the Xnet and ActuallyAdditions drivers pop up recently I decided to try my hand at 
creating a mod to integrate OpenComputers with EnderIO.  

Out of the box if you connect an OC Adapter to a large multi-block EnderIO capacitor bank you only 
get the basic energy_device adapter and that is limited to the actual capacitor the adapter is
connected to and not the entire array of capacitors.

## Supported Devices ##

Conduits are not yet in the published version on CurseForge!

~~### Ender IO Power Conduit with Capacitor Bank on same conduit line ###

This works the same way the EnderIO Power monitors do.  It requires that you use an EnderIO power conduit
of any tier, and have that conduit attached to an EnderIO capacitor or array of capacitors.

The conduit will not attach to the Adapter, simply make sure the adapter is in an adjacent block.

**Make sure that you check the doesBundleContainPowerConduit() method first to ensure that the bundle 
still has a power conduit in it, otherwise the API will fail, albeit gracefully. (hopefully)**

-  **doesBundleContainPowerConduit**=function():bool; Returns true if the bundle contains a power conduit,
-  **getAverageInputPerTick**=function():number; Returns the average input per Tick,
-  **getAverageOutputPerTick**=function():number; Returns the average output per tick across this conduit bundle,
-  **getBundleConduits**=function():table -- Returns a table of all conduits in bundle,
-  **getCurrentEnergyStored**=function():number; Returns Current Energy Stored,
-  **getExtractionRedstoneMode**=function(string EnumFacing) : string - redstone control mode for given side of power conduit,
-  **getIntegrationProvider**=function():string;  -- Returns name of mod providing integration. Used for validation that mod is installed prior to calling these functions.,
-  **getMaxEnergyStored**=function():number; Returns Max Energy Stored,
-  **getMaxPowerInConduits**=function():number; Returns the maximum power that can reside in the conduits,
-  **getMaxPowerInReceptors**=function():number; Returns the maximum power that can reside in power receptors on this network,
-  **getPowerInConduits**=function():number; Returns the current power stored in the conduits,
-  **getPowerInReceptors**=function():number; Returns the current power stored in the receptors,
-  **setExtractionRedstoneMode**=function(string RedstoneControlMode, string EnumFacing): {number theResult, string message} sets redstone control mode for given face,~~


### Ender IO Capacitor Banks ###

capacitor banks will be exposed as [capacitorlevel]_capbank. example:  vibrant_capbank

serializing the component shows the available methods:

 * **getIntegrationProvider**=function():string;  -- Returns name of mod providing integration('eioocshim'). Used for validation that mod is installed prior to calling these functions.
 * **canExtract**=function():number -- Returns whether this component can have energy extracted from the connected side.
 * **canReceive**=function():number -- Returns whether this component can receive energy on the connected side.
 * **getAverageChangePerTick**=function():number;  -- Returns the current average change of uI per tick.
 * **getAverageInputPerTick**=function():number;  -- Returns the current average input of uI per tick.
 * **getAverageOutputPerTick**=function():number;  -- Returns the current average output of uI per tick.
 * **getCurrentStorage**=function():number;  -- Returns the current energy stored in the bank
 * **getEnergyStored**=function():number -- Returns the amount of stored energy on the connected side.
 * **getInputControlMode**=function():string;  -- Returns the current redstone INPUT control mode.
 * **getMaxEnergyStored**=function():number -- Returns the maximum amount of stored energy on the connected side.
 * **getMaximumStorage**=function():number;  -- Returns the maximum energy this bank can hold
 * **getOutputControlMode**=function():string;  -- Returns the current redstone INPUT control mode.
 * **setInputControlMode**=function(string):number,string;  -- Sets the current redstone INPUT control mode.
 * **setOutputControlMode**=function(string):number,string;  -- Sets the current redstone OUTPUT control mode.
 
 ### Ender IO Item Buffer ###
 
 item buffers will be named "enderio_item_buffer and provides the following methods:
 
 * **getIntegrationProvider**=function():string;  -- Returns name of mod providing integration. Used for validation that mod is installed prior to calling these functions.
 * **getSizeInventory**=function():number; -- Returns the size (slots) of the inventory of the buffer
 * **getStackInSlot**=function(number):ItemStack; -- Returns the stack located in the passed slot
 * **getRedstoneControlMode**=function():string; -- Returns the redstone control mode setting
 * **setRedstoneControlMode**=function():string; -- sets the Redstone Control mode (ON,OFF,IGNORE,NEVER)