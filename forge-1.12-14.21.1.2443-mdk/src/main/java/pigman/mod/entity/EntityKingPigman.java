package pigman.mod.entity;

import net.minecraft.world.BossInfo;
import net.minecraft.world.BossInfoServer;
//import net.minecraft.entity.monster.EntityMob;
import net.minecraft.world.World;


import pigman.mod.entity.ai.EntityAIKingPigmanChargeAttack;
import pigman.mod.util.handlers.SoundsHandler;

import java.lang.reflect.Method;
import java.util.UUID;

import javax.annotation.Nullable;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.text.TextComponentString;

public class EntityKingPigman extends EntityPigZombie
{
	   private static final UUID ATTACK_SPEED_BOOST_MODIFIER_UUID = UUID.fromString("49455A49-7EC5-45BA-B886-3B90B23A1718");
	    private static final AttributeModifier ATTACK_SPEED_BOOST_MODIFIER = (new AttributeModifier(ATTACK_SPEED_BOOST_MODIFIER_UUID, "Attacking speed boost", 0.05D, 0)).setSaved(false);
	    /** Above zero if this PigZombie is Angry. */
	    private int angerLevel;
	    /** A random delay until this PigZombie next makes a sound. */
	    private int randomSoundDelay;
	    private UUID angerTargetUUID;
	private final BossInfoServer bossInfo = (BossInfoServer)(new BossInfoServer(this.getDisplayName(), BossInfo.Color.PURPLE, BossInfo.Overlay.PROGRESS)).setDarkenSky(true);
	
	/** Prevents the entity from spawning an infinite amount of reinforcements*/
	private boolean firstWaveReinforcements=true,secondWaveReinforcements=true;
	
		
	public EntityKingPigman(World worldIn) 
	{
		super(worldIn);
		this.isImmuneToFire=true;
		this.setSize(1.6F, 2.95F);
		// TODO Auto-generated constructor stubs
    }
	
	 protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SPAWN_REINFORCEMENTS_CHANCE).setBaseValue(0.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.23000000417232513D);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(10.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(250.0D);
    }
	 
	
		
		
	protected void applyEntityAI()
    {
        this.targetTasks.addTask(1, new EntityKingPigman.AIHurtByAggressor(this));
        this.targetTasks.addTask(2, new EntityKingPigman.AITargetAggressor(this));
        this.tasks.addTask(0, new EntityAIKingPigmanChargeAttack(this, 5.0D));
        
        //this.targetTasks.addTask(1, new EntityAIKingCharge(this, 6.0D, true));
    }
	
	@Override
	protected void updateAITasks() 
	{
		IAttributeInstance iattributeinstance = this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED);

        if (this.isAngry())
        {
            if (!iattributeinstance.hasModifier(ATTACK_SPEED_BOOST_MODIFIER))
            {
                iattributeinstance.applyModifier(ATTACK_SPEED_BOOST_MODIFIER);
            }

            --this.angerLevel;
        }
        else if (iattributeinstance.hasModifier(ATTACK_SPEED_BOOST_MODIFIER))
        {
            iattributeinstance.removeModifier(ATTACK_SPEED_BOOST_MODIFIER);
        }

        if (this.randomSoundDelay > 0 && --this.randomSoundDelay == 0)
        {
            this.playSound(SoundEvents.ENTITY_ZOMBIE_PIG_ANGRY, this.getSoundVolume() * 2.0F, ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F) * 0.8F);
        }

        if (this.angerLevel > 0 && this.angerTargetUUID != null && this.getRevengeTarget() == null)
        {
            EntityPlayer entityplayer = this.world.getPlayerEntityByUUID(this.angerTargetUUID);
            this.setRevengeTarget(entityplayer);
            this.attackingPlayer = entityplayer;
            this.recentlyHit = this.getRevengeTimer();
           
            this.attackingPlayer.sendMessage(new TextComponentString("Task Added"));
        }
        
        
     
        
	 this.bossInfo.setPercent(this.getHealth() / this.getMaxHealth());
	 
	 if((this.getHealth() / this.getMaxHealth())<0.5 && firstWaveReinforcements)
	 {
		for(int i=0; i<2; i++)
		{
			 EntityPigZombie entitypigzombie = new EntityPigZombie(this.world);
	         entitypigzombie.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack(Items.GOLDEN_SWORD));
	         entitypigzombie.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, this.rotationPitch);
	         entitypigzombie.setNoAI(this.isAIDisabled());
	       
	         this.playSound(SoundEvents.ENTITY_ZOMBIE_PIG_ANGRY, 1.0F, 0.5F);
	     
	         
	         this.world.spawnEntity(entitypigzombie);  
	         
	         try {
				makePigZombieAngryAt(entitypigzombie);
			} catch (Exception e) {
				attackingPlayer.sendMessage(new TextComponentString(e.toString()));
				this.setDead();
				e.printStackTrace();
			}
		}
		
		//Remove this for infinite reinforcements
		firstWaveReinforcements=false;
		
	 }
		 
	 if((this.getHealth() / this.getMaxHealth())<0.1 && secondWaveReinforcements)
	 {
		for(int i=0; i<5; i++)
		{
			 EntityPigZombie entitypigzombie = new EntityPigZombie(this.world);
	         entitypigzombie.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack(Items.GOLDEN_SWORD));
	         entitypigzombie.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, this.rotationPitch);
	         entitypigzombie.setNoAI(this.isAIDisabled());
	       
	         this.playSound(SoundEvents.ENTITY_ZOMBIE_PIG_ANGRY, 1.0F, 2.0F);
	     
	         this.world.spawnEntity(entitypigzombie);  
	         
	         try {
	        	 makePigZombieAngryAt(entitypigzombie);
	         } catch (Exception e) {
	        	 attackingPlayer.sendMessage(new TextComponentString(e.toString()));
	        	 this.setDead();
	        	 e.printStackTrace();
	        	 }
		}
		
		secondWaveReinforcements=false;
		
	 }
		  
	}
	
	public void makePigZombieAngryAt(EntityPigZombie entitypigzombie) throws Exception
	{
		/*  Helped find becomeAngryAt() encrypted name
			Class<?> c=entitypigzombie.getClass();
			
			Method[] ms=c.getDeclaredMethods();
			
			for(Method m: ms)
			{
				attackingPlayer.sendMessage(new TextComponentString(m.toString()));
			}
		*/
		
			Method m = entitypigzombie.getClass().getDeclaredMethod("func_70835_c", Entity.class);
			m.setAccessible(true);
			m.invoke(entitypigzombie, this.attackingPlayer);
	}
	
    public void setCustomNameTag(String name)
    {
        super.setCustomNameTag(name);
        this.bossInfo.setName(this.getDisplayName());
    }
    
    /**
     * Causes this PigZombie to become angry at the supplied Entity (which will be a player).
     */
    private void becomeAngryAt(Entity p_70835_1_)
    {
        this.angerLevel = 400 + this.rand.nextInt(400);
        this.randomSoundDelay = this.rand.nextInt(40);

        if (p_70835_1_ instanceof EntityLivingBase)
        {
            this.setRevengeTarget((EntityLivingBase)p_70835_1_);
        }
    }
    
    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
    public void writeEntityToNBT(NBTTagCompound compound)
    {
        super.writeEntityToNBT(compound);
        //compound.setInteger("Invul", this.getInvulTime());
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void readEntityFromNBT(NBTTagCompound compound)
    {
        super.readEntityFromNBT(compound);
       // this.setInvulTime(compound.getInteger("Invul"));

        if (this.hasCustomName())
        {
            this.bossInfo.setName(this.getDisplayName());
        }
    }
    
    /**
     * Add the given player to the list of players tracking this entity. For instance, a player may track a boss in
     * order to view its associated boss bar.
     */
    public void addTrackingPlayer(EntityPlayerMP player)
    {
        super.addTrackingPlayer(player);
        this.bossInfo.addPlayer(player);
    }

    /**
     * Removes the given player from the list of players tracking this entity. See {@link Entity#addTrackingPlayer} for
     * more information on tracking.
     */
    public void removeTrackingPlayer(EntityPlayerMP player)
    {
        super.removeTrackingPlayer(player);
        this.bossInfo.removePlayer(player);
    }

	
	@Override
	protected SoundEvent getAmbientSound() 
	{
		return SoundsHandler.ENTITY_KING_PIGMAN_AMBIENT;
	}
	
	@Override
	protected SoundEvent getHurtSound(DamageSource source) 
	{
		return SoundsHandler.ENTITY_KING_PIGMAN_HURT;
	}
	
	@Override
	protected SoundEvent getDeathSound() 
	{
		return SoundsHandler.ENTITY_KING_PIGMAN_DEATH;
	}
	
	public SoundEvent getChargeSound()
	{
		return SoundsHandler.ENTITY_KING_PIGMAN_CHARGE;
	}
	
	@Override
	public float getEyeHeight()
    {
		return 2.74F;
    }
	
	public static final ResourceLocation KING_PIGMAN_LOOT_TABLE = new ResourceLocation("nether_villages", "king_pigman");
	
	@Nullable
	@Override
	protected ResourceLocation getLootTable()
	{
		return KING_PIGMAN_LOOT_TABLE;
	}
	
	
	
	@Override
	public boolean isChild() 
	{
		return false;
	}
	
	public void onKillEntity(EntityLivingBase entityLivingIn)
    {
    }
	
	 static class AIHurtByAggressor extends EntityAIHurtByTarget
     {
         public AIHurtByAggressor(EntityPigZombie p_i45828_1_)
         {
             super(p_i45828_1_, true);
         }

         protected void setEntityAttackTarget(EntityCreature creatureIn, EntityLivingBase entityLivingBaseIn)
         {
             super.setEntityAttackTarget(creatureIn, entityLivingBaseIn);
             
             if(creatureIn.getAttackTarget()!=null)
             	creatureIn.getAttackTarget().sendMessage(new TextComponentString("angry"));
             
             if (creatureIn instanceof EntityPigZombie)
             {
                 ((EntityKingPigman)creatureIn).becomeAngryAt(entityLivingBaseIn);
              
                 
             }
         }
     }
	 
	
	
 static class AITargetAggressor extends EntityAINearestAttackableTarget<EntityPlayer>
     {
         public AITargetAggressor(EntityPigZombie p_i45829_1_)
         {
             super(p_i45829_1_, EntityPlayer.class, true);
         }

         /**
          * Returns whether the EntityAIBase should begin execution.
          */
         public boolean shouldExecute()
         {
             return ((EntityPigZombie)this.taskOwner).isAngry() && super.shouldExecute();
         }
     }
 
 
}
