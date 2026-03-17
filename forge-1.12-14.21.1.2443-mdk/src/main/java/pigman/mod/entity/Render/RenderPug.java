package pigman.mod.entity.Render;

import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderWolf;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.util.ResourceLocation;
import pigman.mod.entity.model.ModelPug;
import pigman.mod.util.Reference;

public class RenderPug extends RenderWolf {
    public static final ResourceLocation TEXTURES= new ResourceLocation(Reference.MODID + ":textures/entity/pug.png");

	public RenderPug(RenderManager rendermanagerIn) 
	{
		super(rendermanagerIn);
		this.mainModel = new ModelPug();
		
	}
	
	@Override
	protected ResourceLocation getEntityTexture(EntityWolf entity) 
	{
		return TEXTURES;
	}

    @Override
	protected void applyRotations(EntityWolf entityLiving, float p_77043_2_, float rotationYaw, float partialTicks)
	{
		super.applyRotations(entityLiving, p_77043_2_, rotationYaw, partialTicks);
	}

}
