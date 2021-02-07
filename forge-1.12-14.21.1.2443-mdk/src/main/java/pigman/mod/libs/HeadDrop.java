package pigman.mod.libs;

import com.mojang.authlib.GameProfile;

import pigman.mod.renderers.ModelHead;
import net.minecraft.util.ResourceLocation;


public class HeadDrop
{
	public static final HeadDrop DEFAULT = new HeadDrop("textures/entity/zombie_pigman", ModelHead.INSTANCE);
	
	private final ResourceLocation texture;
	private final ModelHead model;

	public HeadDrop(String texture, ModelHead model)
	{
		this.texture = getResource(texture + ".png");
		this.model = model;
		if(model == null)
			throw new IllegalArgumentException("Head model for " + this + " cannot be null!");
	}
	
	public static ResourceLocation getResource(String path) {
		return new ResourceLocation(path);
	}

	public ResourceLocation getTexture(GameProfile profile)
	{
		return texture;
	}

	public ModelHead getModel()
	{
		return model;
	}
	
	
}
