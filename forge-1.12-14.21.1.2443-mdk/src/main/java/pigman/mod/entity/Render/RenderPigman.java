package pigman.mod.entity.Render;


import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.layers.LayerBipedArmor;
import net.minecraft.util.ResourceLocation;
import pigman.mod.entity.EntityPigman;
import pigman.mod.entity.model.ModelPigman;
import pigman.mod.util.Reference;

public class RenderPigman extends RenderBiped<EntityPigman>
{
	
	public static final ResourceLocation TEXTURES= new ResourceLocation(Reference.MODID + ":textures/entity/pigman.png");

	public RenderPigman(RenderManager manager)
	{
		super(manager, new ModelPigman(),0.5F);
		//this.addLayer(new LayerHeldItem(this));
		
		LayerBipedArmor layerbipedarmor = new LayerBipedArmor(this)
        {
            protected void initArmor()
            {
                this.modelLeggings = new ModelPigman(0.5F);
                this.modelArmor = new ModelPigman(1.0F);
            }
        };
        this.addLayer(layerbipedarmor);
        
	}
	
	@Override
	protected ResourceLocation getEntityTexture(EntityPigman entity) 
	{
		return TEXTURES;
	}


	@Override
	protected void applyRotations(EntityPigman entityLiving, float p_77043_2_, float rotationYaw, float partialTicks)
	{
		super.applyRotations(entityLiving, p_77043_2_, rotationYaw, partialTicks);
	}
	
	
}
