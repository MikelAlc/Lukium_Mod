package pigman.mod.world;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;
import pigman.mod.init.PotionInit;

@EventBusSubscriber
public class WorldEvents
{
	@SubscribeEvent
	public static void rayVisionActive(PlayerTickEvent event)
	{
		boolean isActive=false;
		if(event.player.isPotionActive(PotionInit.RAY_VISION_EFFECT)) isActive=true;
		if(isActive)
		{
			event.player.addPotionEffect(new PotionEffect(MobEffects.BLINDNESS, 20));
			World worldIn=event.player.world;
			List<Entity> ent= worldIn.getLoadedEntityList();
			
			for(int i=0; i < ent.size();i++ )
			{
				if(ent.get(i) instanceof EntityLivingBase)
				{
					EntityLivingBase lent=(EntityLivingBase) ent.get(i);
					lent.addPotionEffect(new PotionEffect(MobEffects.GLOWING, 20));
				}
			}
		}
	}
}
