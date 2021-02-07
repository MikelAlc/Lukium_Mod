package pigman.mod.util.handlers;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import pigman.mod.entity.EntityKingPigman;
import pigman.mod.entity.EntityPigman;
import pigman.mod.entity.Render.RenderKingPigman;
import pigman.mod.entity.Render.RenderPigman;
import pigman.mod.renderers.TileEntityPigZombieSkullRenderer;
import pigman.mod.tileentity.TileEntityPigZombieSkull;

public class RenderHandler
{
	
	public static void registerEntityRenders()
	{
		RenderingRegistry.registerEntityRenderingHandler(EntityPigman.class, new IRenderFactory<EntityPigman>()
		{
			@Override
			public Render<? super EntityPigman> createRenderFor(RenderManager manager) 
			{
				return new RenderPigman(manager);
			}
		});
		
		RenderingRegistry.registerEntityRenderingHandler(EntityKingPigman.class, new IRenderFactory<EntityKingPigman>()
		{
			@Override
			public Render<? super EntityKingPigman> createRenderFor(RenderManager manager) 
			{
				return new RenderKingPigman(manager);
			}
		});
		
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityPigZombieSkull.class,TileEntityPigZombieSkullRenderer.INSTANCE);
		
	}
}
