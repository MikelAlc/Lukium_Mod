package pigman.mod;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import pigman.mod.proxy.CommonProxy;
import pigman.mod.util.Reference;
import pigman.mod.util.handlers.RegistryHandler;
import pigman.mod.village.town.WorldDataInstance;
import pigman.mod.tabs.CustomTab;

/*Bugs to fix 
 * 
 * Improve Village generation; no more hanging off of cliffs
 * Delete Village data collecting
 * 
 * Trading w/agrro pigman temp stops him maybe switch priority
 * Clean comments 
 * Find area checked by diamonds
 * Recipie for Lukium block and vice versa
 * Lukium ore world gen
 * Fix smelting recipies
 * 
 * 
 * trading with pigman unlocks villagers trades. probaly fixed
 * Remove extra Pigman from spawing
 * 
 * Lmao just use this to get population count within the village  List<EntityPigman> listEntities = this.world.getEntitiesWithinAABB(EntityPigman.class, this.villagerObj.getEntityBoundingBox().grow(8.0D, 3.0D, 8.0D));
 * give @p minecraft:diamond_sword 1 0 {ench:[{id:16,lvl:1000},{id:17,lvl:1000},{id:18,lvl:1000},{id:19,lvl:1000},{id:20,lvl:1000},{id:21,lvl:10},{id:34,lv­l:1000}]}Just copy and paste this into Minecraft. 
*/

@Mod(modid=Reference.MODID, name = Reference.NAME, version=Reference.VERSION)

public class Main 
{
	
	@Instance
	public static Main instance;
	
	@CapabilityInject(WorldDataInstance.class)
	public static final Capability<WorldDataInstance> WORLD_DATA_INSTANCE = null;
	
	public static final CreativeTabs LukiumTab=new CustomTab("lukium_tab");
	
	@SidedProxy(clientSide=Reference.CLIENT, serverSide=Reference.COMMON)
	public static CommonProxy proxy;
	
	@EventHandler
	public static void preInt(FMLPreInitializationEvent event) {RegistryHandler.preInitRegistries();}
	
	@EventHandler
	public static void init(FMLInitializationEvent event){RegistryHandler.initRegistries();}
	
	@EventHandler
	public static void postInt(FMLPostInitializationEvent event) {}
	
	
}
	