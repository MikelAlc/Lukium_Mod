package pigman.mod.util.handlers;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import pigman.mod.util.Reference;

public class SoundsHandler 
{
	//These SoundEvents are put within the sound getter methods in my EntityKingPigman class
	public static SoundEvent ENTITY_KING_PIGMAN_HURT;
	public static SoundEvent ENTITY_KING_PIGMAN_AMBIENT;
	public static SoundEvent ENTITY_KING_PIGMAN_DEATH;
	public static SoundEvent ENTITY_KING_PIGMAN_CHARGE;
	
	//Called within my RegistryHandler class to registers all the custom sounds
	public static void registerSounds()
	{
		ENTITY_KING_PIGMAN_HURT=registerSound("entity.king_pigman.hurt");
		ENTITY_KING_PIGMAN_AMBIENT=registerSound("entity.king_pigman.ambient");
		ENTITY_KING_PIGMAN_DEATH=registerSound("entity.king_pigman.death");
		ENTITY_KING_PIGMAN_CHARGE=registerSound("entity.king_pigman.charge");
	}
	
	//Creates a new resource location and sound event 
	private static SoundEvent registerSound(String name)
	{
		ResourceLocation location = new ResourceLocation(Reference.MODID, name);
		SoundEvent event = new SoundEvent(location);
		event.setRegistryName(name);
		ForgeRegistries.SOUND_EVENTS.register(event);
		return event;
	}
}
