package pigman.mod.util.handlers;

import java.io.File;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import pigman.mod.Main;
import pigman.mod.util.Reference;

public class ConfigHandler
{
	public static Configuration config;
	
	public static int ENTITY_PIGMAN=387;
	public static int ENTITY_KINGPIGMAN=388;
	
	public static void init(File file)
	{
		config = new Configuration(file);
		
		String category;
		
		category = "Entity IDs";
		config.addCustomCategoryComment(category, "Set the ID's for the entities to ensure that they don't clash with other mod's ids");
		ENTITY_PIGMAN = config.getInt("ENTITY_Pigman_ID", category, 387, 120, 999, "ID for the Pigman Entity");
		ENTITY_KINGPIGMAN = config.getInt("ENTITY_Pigman_ID", category, 388, 120, 999, "ID for the King Pigman Entity");
		
		config.save();
	}
	
	public static void registerConfig(FMLPreInitializationEvent event)
	{
		Main.config= new File(event.getModConfigurationDirectory()+"/"+Reference.MODID);
		Main.config.mkdirs();
		init(new File(Main.config.getPath(), Reference.MODID+".cfg"));
	}
}
