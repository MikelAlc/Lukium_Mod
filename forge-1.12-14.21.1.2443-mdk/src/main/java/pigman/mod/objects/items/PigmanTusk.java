package pigman.mod.objects.items;

import java.lang.reflect.Field;

import java.util.List;



import net.minecraft.entity.ai.EntityAIAvoidEntity;
import net.minecraft.entity.monster.EntityPigZombie;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import pigman.mod.entity.EntityKingPigman;
import pigman.mod.entity.EntityPigman;
import pigman.mod.util.Reference;
import pigman.mod.util.handlers.SoundsHandler;


public class PigmanTusk extends ItemBase
{

	public PigmanTusk(String name)
	{
		super(name);
		this.maxStackSize = 1;
		//Makes the it break after 32 uses
        this.setMaxDamage(64);
	}
		
	/**
	 *
	 */
	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn)
	{
		ItemStack item=playerIn.getHeldItem(handIn);
		boolean kingDetected=false;
		//Change pitch?
		EntityPigZombie kingPigman=null;
		
		
		
		
		 try {
			 //For each zombie pigman in the world unanger them
			 List<EntityPigZombie> zombiePigmen = worldIn.getEntities(EntityPigZombie.class, EntitySelectors.IS_ALIVE);
			 List<EntityPigman> pigmen = worldIn.getEntities(EntityPigman.class, EntitySelectors.IS_ALIVE);
			 
			for(EntityPigZombie pig: zombiePigmen)
			{
				if(pig.getClass().equals(EntityKingPigman.class))
				{
					if (Reference.DEBUG) playerIn.sendMessage(new TextComponentString("King Detected"));
					kingPigman=pig;
					kingDetected=true;
					
				}
			}
			
			playerIn.playSound(kingDetected? SoundsHandler.ENTITY_KING_PIGMAN_CHARGE:SoundEvents.ENTITY_ZOMBIE_PIG_AMBIENT, 1, 0.5F);
			 
			 for(int i=0;i<zombiePigmen.size() && !kingDetected;i++)
				 
			 {
				 if (zombiePigmen.get(i).isAngry())
					 if(Reference.DEBUG)playerIn.sendMessage(new TextComponentString("angry"));
				 
				Field[] pzFields=EntityPigZombie.class.getDeclaredFields();	
				
			
				Field angerLevel=pzFields[2];
				
				angerLevel.setAccessible(true);
				angerLevel.setInt(zombiePigmen.get(i), 0);
				
				Field angerTargetUUID =pzFields[4];
				angerTargetUUID.setAccessible(true);
				angerTargetUUID.set(zombiePigmen.get(i), null);
				
				zombiePigmen.get(i).setRevengeTarget(null);
				playerIn.sendMessage(new TextComponentString("ayo watch your jet"));
				zombiePigmen.get(i).tasks.addTask(1, new EntityAIAvoidEntity(zombiePigmen.get(i), EntityPlayer.class, 8.0F, 0.6D, 1.2D));
			 }
			 
			 
			 for(int i=0;i<pigmen.size();i++) 
			 {
				if(Reference.DEBUG) playerIn.sendMessage(new TextComponentString(pigmen.get(i).getHeldEquipment().toString()));
				pigmen.get(i).resetTargetAI();
				pigmen.get(i).setRevengeTarget(kingDetected? kingPigman : null);
				pigmen.get(i).setAttackTarget(kingDetected? kingPigman : null);
			 }
			
			 
		 } 
		 catch (Exception e) {		 	
			playerIn.sendMessage(new TextComponentString(e.toString()));
		}
		
		item.damageItem(1, playerIn);
		
		
		return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, item);		
	}
	

}
