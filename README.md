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


**version 1.0.002**

## Supported Devices ##


### Ender IO Conduit Bundles ###

Bundles currently supports working with power and item conduits in the same bundle.  Simply put the adapter adjacent to
a conduit bundle -- though you won't see it actually connect it can still interact with it.

Power: This works the same way the EnderIO Power monitors do.  It requires that you use an EnderIO power conduit
of any tier, and have that conduit attached to an EnderIO capacitor or array of capacitors.

Item: this allows you to get/set the extraction redstone mode as well as the channel color.  Do note that 
EnderIO seems to use different visual colors than the enumerator returns.  For example, the RS signal channel will return
'ORANGE' even though it really is red.  Totally weird.  Setting it to 'ORANGE' will make the signal channel red.  Don't ask me :P 
I didn't write EIO :)

**Make sure that you check the doesBundleContainPowerConduit() method first to ensure that the bundle 
still has a power conduit in it, otherwise the API will fail, albeit gracefully. (hopefully)**

 - **getBundleConduits**=function():table -- Returns a table of all conduits in bundle,
 - **getIntegrationProvider**=function():string;  -- Returns name of mod providing integration. Used for validation that mod is installed prior to calling these functions.,
 
 - **item_hasConduit**=fuction():bool; Returns tru if the bundle contains an item conduit,
 - **item_getExtractionRedstoneMode**=function(string RedstoneControlMode, string EnumFacing): {number,message} sets redstone control mode for given face,
 - **item_getExtractionSignalColor**=function(string EnumFacing : string Dye Color,
 - **item_setExtractionRedstoneMode**=function(string RedstoneControlMode, string EnumFacing): {number:,message} sets redstone control mode for given face,
 - **item_setExtractionSignalColor**=function(string enumfacing, string DyeColor) returns result code and description,  0/OK means success,
 
 - **pwr_hasConduit**=function():bool; Returns true if the bundle contains a power conduit,
 - **pwr_getAverageInputPerTick**=function():number; Returns the average input per Tick,
 - **pwr_getAverageOutputPerTick**=function():number; Returns the average output per tick across this conduit bundle,
 - **pwr_getCurrentEnergyStored**=function():number; Returns Current Energy Stored,
 - **pwr_getExtractionRedstoneMode**=function(string EnumFacing) : string - redstone control mode for item extraction on the given face,
 - **pwr_getMaxEnergyStored**=function():number; Returns Max Energy Stored,
 - **pwr_getMaxPowerInConduits**=function():number; Returns the maximum power that can reside in the conduits,
 - **pwr_getMaxPowerInReceptors**=function():number; Returns the maximum power that can reside in power receptors on this network,
 - **pwr_getPowerInConduits**=function():number; Returns the current power stored in the conduits,
 - **pwr_getPowerInReceptors**=function():number; Returns the current power stored in the receptors,
 - **pwr_setExtractionRedstoneMode**=function(string RedstoneControlMode, string EnumFacing): {number,string} sets redstone control for item extraction on the given face,


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