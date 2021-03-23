package pigman.mod.init;

import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import pigman.mod.Main;
import pigman.mod.entity.EntityKingPigman;
import pigman.mod.entity.EntityPigman;
import pigman.mod.util.Reference;
import pigman.mod.util.handlers.ConfigHandler;

public class EntityInit
{
	
	public static void registerEntities()
	{
		registryEntity("pigman",EntityPigman.class,ConfigHandler.ENTITY_PIGMAN,50,14387850,9793114);
		registryEntity("king_pigman",EntityKingPigman.class,ConfigHandler.ENTITY_KINGPIGMAN,50,14387850,16768768);
	}
	
	public static void registryEntity(String name,Class<? extends Entity> entity,int id, int range, int color1, int color2)
	{
		EntityRegistry.registerModEntity(new ResourceLocation(Reference.MODID + ":" + name), entity, name, id, Main.instance, range, 1, true, color1, color2);
	}

}
