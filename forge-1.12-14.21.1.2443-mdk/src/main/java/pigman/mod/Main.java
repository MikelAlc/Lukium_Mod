package pigman.mod;

import java.io.File;

import net.minecraft.creativetab.CreativeTabs;
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
import pigman.mod.tabs.CustomTab;

@Mod(modid=Reference.MODID, name = Reference.NAME, version=Reference.VERSION)

public class Main 
{
	public static File config;
	
	@Instance
	public static Main instance;
	
	
	public static final CreativeTabs LukiumTab=new CustomTab("lukium_tab");
	
	@SidedProxy(clientSide=Reference.CLIENT, serverSide=Reference.COMMON)
	public static CommonProxy proxy;
	
	@EventHandler
	public static void preInt(FMLPreInitializationEvent event) {RegistryHandler.preInitRegistries(event);}
	
	@EventHandler
	public static void init(FMLInitializationEvent event){RegistryHandler.initRegistries(event);}
	
	@EventHandler
	public static void postInt(FMLPostInitializationEvent event) {}
	
	
}
	