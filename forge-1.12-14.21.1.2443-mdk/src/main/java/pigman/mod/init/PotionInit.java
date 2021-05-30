package pigman.mod.init;

import net.minecraft.init.Items;
import net.minecraft.init.PotionTypes;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionHelper;
import net.minecraft.potion.PotionType;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import pigman.mod.potions.CustomPotion;

public class PotionInit
{
	public static final Potion RAY_VISION_EFFECT = new CustomPotion("ray_vision", false, 2466205, 0, 0);
	
	public static final PotionType RAY_VISION= new PotionType("ray_vision",new PotionEffect[] {new PotionEffect(RAY_VISION_EFFECT,200)}).setRegistryName("ray_vision");
	public static final PotionType LONG_RAY_VISION= new PotionType("ray_vision",new PotionEffect[] {new PotionEffect(RAY_VISION_EFFECT,400)}).setRegistryName("long_ray_vision");
	
	public static void registerPotions()
	{
		registerPotion(RAY_VISION,LONG_RAY_VISION,RAY_VISION_EFFECT);
		registerPotionMixes();
	}
	
	private static void registerPotion(PotionType defaultPotion, PotionType longPotion, Potion effect)
	{
		ForgeRegistries.POTIONS.register(effect);
		ForgeRegistries.POTION_TYPES.register(defaultPotion);
		ForgeRegistries.POTION_TYPES.register(longPotion);
	}
	
	private static void registerPotionMixes()
	{
		PotionHelper.addMix(RAY_VISION,Items.REDSTONE,LONG_RAY_VISION);
		PotionHelper.addMix(PotionTypes.AWKWARD,ItemInit.RAYSHROOM,RAY_VISION);
	}
}
