package pigman.mod.libs;

import pigman.mod.renderers.ZombiePigmanHead;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.item.ItemStack;

public class VanillaHelper extends HeadDropHelper
{
	private static String zombie_pigman = "zombie_pigman";

	public VanillaHelper() 
	{
		super("minecraft");
		super.registerMobHead(zombie_pigman, ZombiePigmanHead.INSTANCE);
	}
	
	@Override
	public ItemStack getHeadForEntity(String entityName, Entity entity)
	{
		if(entity instanceof EntityMob)
		{
			if(entity instanceof EntityZombie)
			{
				if(entity instanceof EntityPigZombie)
					return getStack(modID, zombie_pigman);
			}
		}
		
		return super.getHeadForEntity(entityName, entity);
	}
	
	@Override
	public String getTextureLocBase()
	{
		return "textures/entity/";
	}
}