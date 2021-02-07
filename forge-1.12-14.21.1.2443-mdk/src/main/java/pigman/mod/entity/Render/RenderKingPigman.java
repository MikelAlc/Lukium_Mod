package pigman.mod.entity.Render;


import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import pigman.mod.entity.EntityKingPigman;
import pigman.mod.entity.model.ModelKingPigman;
import pigman.mod.util.Reference;

public class RenderKingPigman extends RenderLiving<EntityKingPigman> 
{
	public static final ResourceLocation TEXTURES= new ResourceLocation(Reference.MODID + ":textures/entity/king_pigman.png");

	public RenderKingPigman(RenderManager rendermanagerIn) 
	{
		super(rendermanagerIn, new ModelKingPigman(), 0.5F);
		
	}
	
	@Override
	protected ResourceLocation getEntityTexture(EntityKingPigman entity) 
	{
		return TEXTURES;
	}
	
	@Override
	protected void applyRotations(EntityKingPigman entityLiving, float p_77043_2_, float rotationYaw, float partialTicks)
	{
		super.applyRotations(entityLiving, p_77043_2_, rotationYaw, partialTicks);
	}

	

}
