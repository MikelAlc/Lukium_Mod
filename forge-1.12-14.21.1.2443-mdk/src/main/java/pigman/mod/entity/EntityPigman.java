package pigman.mod.entity;

import java.lang.reflect.Field;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nullable;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.IMerchant;
import net.minecraft.entity.INpc;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
//import net.minecraft.entity.ai.EntityAIAvoidEntity;
import net.minecraft.entity.ai.EntityAILookAtTradePlayer;
import net.minecraft.entity.ai.EntityAIMoveIndoors;
import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
import net.minecraft.entity.ai.EntityAIOpenDoor;
import net.minecraft.entity.ai.EntityAIPlay;
import net.minecraft.entity.ai.EntityAIRestrictOpenDoor;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAITradePlayer;
import net.minecraft.entity.ai.EntityAIWanderAvoidWater;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.ai.EntityAIWatchClosest2;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;


import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.village.MerchantRecipe;
import net.minecraft.village.MerchantRecipeList;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import pigman.mod.entity.ai.EntityAIPigmanHarvest;
import pigman.mod.entity.ai.EntityAIPigmanMate;
import pigman.mod.init.ItemInit;
import pigman.mod.util.Reference;



public class EntityPigman extends EntityVillager implements IMerchant, INpc
{
	 private static final DataParameter<Integer> ROLE = EntityDataManager.createKey(EntityPigman.class, DataSerializers.VARINT);
	
	 private boolean areAdditionalTasksSet;
	 public boolean debug = false;
	 private EntityAIBase AIHurtByTarget;
	 
	 private long lastTradeTime = 0;
	
	 //TODO:Tweeak this 
	 public static int MAX_HOME_DISTANCE = 12;
	
    private boolean hasHealItem=true;
    private int delayTick=100;
    private int eatTick=20;
    
 
	 public enum Roles
	 {
		 BLACKSMITH,
		 GUARD,
	     TRADER;
		 
		 private static final Map<Integer, Roles> lookup = new HashMap<>();
	     static { for(Roles e : EnumSet.allOf(Roles.class)) { lookup.put(e.ordinal(), e); } }
	     public static Roles get(int intValue) { return lookup.get(intValue); }
     }

	public EntityPigman(World worldIn)
	{
		super(worldIn,0);
		this.isImmuneToFire = true;
		//Should prevent Pigman from unlocking unwanted trades
		this.setProfession(5);
		this.setSize(0.6F, 1.95F);
		//this.onInitialSpawn(this.world.getDifficultyForLocation(new BlockPos(this)), (IEntityLivingData)null);
		
	}
	
	
	
	@Override
	public EntityVillager createChild(EntityAgeable ageable) 
	{
        
        EntityPigman entityvillager = new EntityPigman(this.world);
        entityvillager.onInitialSpawn(this.world.getDifficultyForLocation(new BlockPos(entityvillager)), (IEntityLivingData)null);
       
        
        return entityvillager;
	}
	

    public void setBlacksmith()
    {
        this.getDataManager().set(ROLE, Integer.valueOf(Roles.BLACKSMITH.ordinal()));
     
    }

    public void setTrader() 
    {
        this.getDataManager().set(ROLE, Integer.valueOf(Roles.TRADER.ordinal()));
      
        
    }
    
    public void setGuard()
    {
    	 this.getDataManager().set(ROLE, Integer.valueOf(Roles.GUARD.ordinal()));
        
    }
    
  
    @Override
    protected void setEquipmentBasedOnDifficulty(DifficultyInstance difficulty)
    {
    	int i = this.rand.nextInt(3);
    	
    	//Weapon
    		if(this.getRole()==Roles.GUARD)
    		{
    			if(this.world.getDifficulty() == EnumDifficulty.HARD)
    				this.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack(ItemInit.LUKIUM_AXE));
    			else if(this.world.getDifficulty() == EnumDifficulty.NORMAL)
    				this.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack(ItemInit.LUKIUM_SWORD));
    			else
    				this.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack(Items.GOLDEN_AXE));
    		}
    		else
    			this.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack(Items.GOLDEN_SWORD));
    
    	
    	//Armor
    	if(this.getRole()==Roles.GUARD) 
    	{
    		if(this.world.getDifficulty() == EnumDifficulty.EASY)
    		{
    			this.setItemStackToSlot(EntityEquipmentSlot.HEAD,new ItemStack(Items.GOLDEN_HELMET));
    			this.setItemStackToSlot(EntityEquipmentSlot.CHEST,new ItemStack(Items.GOLDEN_CHESTPLATE));
    			this.setItemStackToSlot(EntityEquipmentSlot.LEGS,new ItemStack(Items.GOLDEN_LEGGINGS));
    			 this.setItemStackToSlot(EntityEquipmentSlot.FEET,new ItemStack(Items.GOLDEN_BOOTS));
    		}
    		else
    		{
    			this.setItemStackToSlot(EntityEquipmentSlot.HEAD,new ItemStack(ItemInit.LUKIUM_HELMET));
    			this.setItemStackToSlot(EntityEquipmentSlot.CHEST,new ItemStack(ItemInit.LUKIUM_CHESTPLATE));
    	
    			if(this.world.getDifficulty() == EnumDifficulty.HARD)
    			{
    				this.setItemStackToSlot(EntityEquipmentSlot.LEGS,new ItemStack(ItemInit.LUKIUM_LEGGINGS));
    				this.setItemStackToSlot(EntityEquipmentSlot.FEET,new ItemStack(ItemInit.LUKIUM_BOOTS));
    			}
    		}
    	}
    	else
    	{
	    	if(this.world.getDifficulty() == EnumDifficulty.HARD || this.world.getDifficulty() == EnumDifficulty.NORMAL)
	    	{
	    		if(i==0)
	    			this.setItemStackToSlot(EntityEquipmentSlot.CHEST,new ItemStack(Items.GOLDEN_CHESTPLATE));
	    	}
	    	
	    	if(this.world.getDifficulty() == EnumDifficulty.HARD)
	    	{
	    		i=this.rand.nextInt(4);
	    		if(i==0) this.setItemStackToSlot(EntityEquipmentSlot.HEAD,new ItemStack(Items.GOLDEN_HELMET));
	    		i=this.rand.nextInt(4);
	    		if(i==0) this.setItemStackToSlot(EntityEquipmentSlot.LEGS,new ItemStack(Items.GOLDEN_LEGGINGS));
	    		i=this.rand.nextInt(4);
	    		if(i==0) this.setItemStackToSlot(EntityEquipmentSlot.FEET,new ItemStack(Items.GOLDEN_BOOTS));
	    	}
    	}
    }
    
   
	
    public void onLivingUpdate()
    {
    	super.onLivingUpdate();
    	
    	if(this.getHealth() < this.getMaxHealth() && hasHealItem)
		{
    		this.setItemStackToSlot(EntityEquipmentSlot.OFFHAND,new ItemStack(ItemInit.NETHER_SOUP));
    		if(eatTick--<0)
    		{
    			this.playSound(SoundEvents.ENTITY_PLAYER_BURP, 0.5F, world.rand.nextFloat() * 0.1F + 0.9F);
            	this.addPotionEffect(new PotionEffect(MobEffects.REGENERATION, 100, 3));
    			this.setItemStackToSlot(EntityEquipmentSlot.OFFHAND,new ItemStack(Items.AIR));
    			hasHealItem=false;
    		}
		}
    	
    	if(!hasHealItem && delayTick--<0)
    	{
			delayTick=600;
			eatTick=20;
			hasHealItem=true;
    	}
    	
    	
    }
    
	@Override
	protected void entityInit() 
	{
		super.entityInit();
        this.getDataManager().register(ROLE, Integer.valueOf(0));
	}
	
	 
	  public long getLastTradeTime() 
	  {
	        return lastTradeTime;
	  }

	  public void setLastTradeTime(long lastTradeTime)
	  {
	        this.lastTradeTime = lastTradeTime;
	  }
	  
	 public Roles getRole() 
	 {
	    return Roles.get(this.getDataManager().get(ROLE));
	 }
	

	 
	@Override
	public float getEyeHeight()
    {
		return this.isChild() ? 0.81F : 1.74F;
	
	}
	@Override
	protected void initEntityAI()
	{
		this.tasks.addTask(0, new EntityAISwimming(this));
		//Fix 
        //this.tasks.addTask(1, new EntityAITradePlayer(this));
        this.tasks.addTask(1, new EntityAILookAtTradePlayer(this));
        this.tasks.addTask(2, new EntityAIMoveIndoors(this));
        this.tasks.addTask(3, new EntityAIRestrictOpenDoor(this));
        this.tasks.addTask(4, new EntityAIOpenDoor(this, true));
        this.tasks.addTask(5, new EntityAIMoveTowardsRestriction(this, 0.6D));
        //this.tasks.addTask(6, new EntityAIVillagerMate(this));
        
     
        this.tasks.addTask(7,new EntityAITradePlayer(this));
        this.tasks.addTask(9, new EntityAIWatchClosest2(this, EntityPlayer.class, 3.0F, 1.0F));
        //this.tasks.addTask(9, new EntityAIVillagerInteract(this));
        this.tasks.addTask(9, new EntityAIWanderAvoidWater(this, 0.6D));
        this.tasks.addTask(10, new EntityAIWatchClosest(this, EntityLiving.class, 8.0F));
        this.tasks.addTask(2, new EntityAIAttackMelee(this, 0.7D, false));
        this.tasks.addTask(12,new EntityAIPigmanMate(this));
      
         AIHurtByTarget = new EntityAIHurtByTarget(this, true, new Class[0]);
        this.targetTasks.addTask(6, AIHurtByTarget);
	}
	

	
	@Override
	protected void applyEntityAttributes() 
	{
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(40.0D);
		this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.5D);
		this.getAttributeMap().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE);
		this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(3.0D);
	}

	

	@Override
	protected SoundEvent getAmbientSound() 
	{
		return SoundEvents.ENTITY_PIG_AMBIENT;
	}
	
	@Override
	protected SoundEvent getHurtSound(DamageSource source) 
	{
		Entity yeet=source.getTrueSource();
		if (yeet instanceof EntityPlayer)
		{
			if(Reference.DEBUG) yeet.sendMessage(new TextComponentString(this.getHealth()+" "+delayTick+ " "+hasHealItem +" "+ this.rotationYaw+" "+this.rotationPitch));
		}
		return SoundEvents.ENTITY_PIG_HURT;
	}
	
	@Override
	protected SoundEvent getDeathSound() 
	{
		return SoundEvents.ENTITY_PIG_DEATH;
	}
	
	 @Override
    public void playSound(SoundEvent soundIn, float volume, float pitch) 
	 {

        //cancel villager trade sounds
        if (soundIn == SoundEvents.ENTITY_VILLAGER_YES || soundIn == SoundEvents.ENTITY_VILLAGER_NO) {
            return;
        }

        super.playSound(soundIn, volume, pitch);
    }
	
	public static final ResourceLocation PIGMAN_LOOT_TABLE = new ResourceLocation("lukium", "pigman");
	
	@Nullable
	@Override
	protected ResourceLocation getLootTable()
	{
		return PIGMAN_LOOT_TABLE;
	}
	
	
	 
	 public void addTradeForCurrencies(MerchantRecipeList list, ItemStack sell) 
	 {
		double goldIngotWorth = 1;
        double goldBlockWorth = 2;

        ItemStack stack1 = sell.copy();
        stack1.setCount((int)Math.round(sell.getCount() * goldIngotWorth));
        list.add(new MerchantRecipe(new ItemStack(Items.GOLD_INGOT), stack1));

        ItemStack stack2 = sell.copy();
        stack2.setCount((int)Math.round(sell.getCount() * goldBlockWorth));
        list.add(new MerchantRecipe(new ItemStack(Items.GOLDEN_CARROT), stack2));

	  }
	 
	 public void addCurrenciesForTrades(MerchantRecipeList list, ItemStack sell) 
	 {
        
        ItemStack stack1 = sell.copy();
        stack1.setCount(8);
        list.add(new MerchantRecipe(new ItemStack(Items.CAKE), stack1));
        
        ItemStack stack2 = sell.copy();
        stack2.setCount(1);
        list.add(new MerchantRecipe(new ItemStack(Items.APPLE), stack2));
        
        ItemStack stack3 = sell.copy();
        stack3.setCount(64);
        list.add(new MerchantRecipe(new ItemStack(Items.NETHER_STAR), stack3));
        
        ItemStack stack4 = sell.copy();
        stack4.setCount(2);
        list.add(new MerchantRecipe(new ItemStack(Items.CARROT), stack4));

      
	  } 
	 
	 public void addTrade(MerchantRecipeList list, ItemStack sell, Item buy, int minSell, int maxSell, int minBuy, int maxBuy)
	 {
		 ItemStack stack1 = sell.copy();
		 stack1.setCount((int)((Math.random()*(maxSell-minSell+1))+ minSell));
		 list.add(new MerchantRecipe(new ItemStack(buy, (int)((Math.random()*(maxBuy-minBuy+1))+ minBuy)),stack1));
	 }

	 private void addSoupTrade(MerchantRecipeList list)
	 {
		list.add(new MerchantRecipe(new ItemStack(Items.GOLD_INGOT,(int)((Math.random()*(4))+ 12) ),new ItemStack(Items.BOWL),new ItemStack(ItemInit.NETHER_SOUP)));			
	 }
	 
	 public void initTrades() 
	 {
		 MerchantRecipeList list = new MerchantRecipeList();
		 if (getRole() == Roles.BLACKSMITH) 
		 {
			addTrade(list,new ItemStack(ItemInit.LUKIUM_HELMET),ItemInit.LUKIUM_INGOT,1,1,5,5);
			addTrade(list,new ItemStack(ItemInit.LUKIUM_CHESTPLATE),ItemInit.LUKIUM_INGOT,1,1,8,8);
			addTrade(list,new ItemStack(ItemInit.LUKIUM_LEGGINGS),ItemInit.LUKIUM_INGOT,1,1,7,7);
			addTrade(list,new ItemStack(ItemInit.LUKIUM_BOOTS),ItemInit.LUKIUM_INGOT,1,1,4,4);
			list.add(new MerchantRecipe(new ItemStack(ItemInit.LUKIUM_INGOT,2),new ItemStack(Items.BLAZE_ROD),new ItemStack(ItemInit.LUKIUM_SWORD)));	
			list.add(new MerchantRecipe(new ItemStack(ItemInit.LUKIUM_INGOT,3),new ItemStack(Items.BLAZE_ROD,2),new ItemStack(ItemInit.LUKIUM_AXE)));		
			list.add(new MerchantRecipe(new ItemStack(ItemInit.LUKIUM_INGOT,3),new ItemStack(Items.BLAZE_ROD,2),new ItemStack(ItemInit.LUKIUM_PICKAXE)));	
			list.add(new MerchantRecipe(new ItemStack(ItemInit.LUKIUM_INGOT,1),new ItemStack(Items.BLAZE_ROD,2),new ItemStack(ItemInit.LUKIUM_SHOVEL)));	
			list.add(new MerchantRecipe(new ItemStack(ItemInit.LUKIUM_INGOT,2),new ItemStack(Items.BLAZE_ROD,2),new ItemStack(ItemInit.LUKIUM_HOE)));
		 }
		 else if (getRole() == Roles.TRADER) 
		 {
			 
			 addSoupTrade(list);
			 addTrade(list,new ItemStack(Items.FLINT_AND_STEEL), Items.GOLD_INGOT,1,1,3,6);
			 addTrade(list,new ItemStack(Items.BLAZE_POWDER),Items.GOLD_INGOT,1,1,1,2);
			 addTrade(list,new ItemStack(Items.MAGMA_CREAM),Items.GOLD_INGOT,1,1,2,3);
			 addTrade(list,new ItemStack(ItemInit.MAGMA_BAR),Items.GOLD_INGOT,1,1,3,6);
			 addTrade(list,new ItemStack(Items.QUARTZ),Items.GOLD_INGOT,1,3,1,1);
			 addTrade(list,new ItemStack(Items.GLOWSTONE_DUST),Items.GOLD_INGOT,1,3,1,1);
			 addTrade(list,new ItemStack(Items.NETHERBRICK),Items.GOLD_INGOT,1,3,1,1);
			 addTrade(list,new ItemStack(Items.GHAST_TEAR),Items.GOLD_INGOT,1,1,10,20);
			 addTrade(list,new ItemStack(Items.GOLD_INGOT),Items.NETHER_STAR,64,64,1,1);
			 addTrade(list,new ItemStack(ItemInit.LUKIUM_INGOT),Items.NETHER_STAR,3,6,1,1);
	
			 
			 
		 }
		 try 
		 {
            _buyingList.set(this, list);
		 } 
		 catch (Exception ex) 
		 {
            ex.printStackTrace();
		 }
	 }
	 

    /*public void updateUniqueEntityAI()
    {
        Set<EntityAITasks.EntityAITaskEntry> executingTaskEntries = ReflectionHelper.getPrivateValue(EntityAITasks.class, this.tasks, "field_75780_b", "executingTaskEntries");
        if (executingTaskEntries != null)
        {
            for (EntityAITasks.EntityAITaskEntry entry : this.tasks.taskEntries)
            {
                entry.action.resetTask();
            }
            executingTaskEntries.clear();
        }

        Set<EntityAITasks.EntityAITaskEntry> executingTaskEntries2 = ReflectionHelper.getPrivateValue(EntityAITasks.class, this.targetTasks, "field_75780_b", "executingTaskEntries");
        if (executingTaskEntries2 != null) 
        {
            for (EntityAITasks.EntityAITaskEntry entry : this.targetTasks.taskEntries)
            {
                entry.action.resetTask();
            }
	            executingTaskEntries2.clear();
        }

        this.tasks.taskEntries.clear();
        this.targetTasks.taskEntries.clear();

        int curPri = 0;

        this.tasks.addTask(curPri++, new EntityAISwimming(this)); 
        this.tasks.addTask(curPri++, new EntityAITradePlayer(this));
        this.tasks.addTask(curPri++, new EntityAITradePlayer(this));
    }*/
    
   

	public void rollDiceRole() 
    {
        int randValRole = this.world.rand.nextInt(Roles.values().length);
        if (randValRole == Roles.BLACKSMITH.ordinal()) {
            this.setBlacksmith();
        } else if (randValRole == Roles.TRADER.ordinal()) {
            this.setTrader();
        } else if(randValRole==Roles.GUARD.ordinal()){
        	this.setGuard();
        }
   
    }
    
    public void rollDiceChild() {
        int childChance = 20;
        if (childChance >= this.world.rand.nextInt(100)) {
            this.setGrowingAge(-24000);
        }
    }
    
    @Override
    protected void onGrowingAdult() 
    {
    	this.tasks.addTask(5, new EntityAIPigmanHarvest(this,0.6D));
        super.onGrowingAdult();
    }
   
    private static final Field _buyingList = ReflectionHelper.findField(EntityVillager.class, "field_70963_i", "buyingList");
	 
    @Override
    public boolean processInteract(EntityPlayer player, EnumHand hand) {

        if (hand != EnumHand.MAIN_HAND) return false;

    	boolean ret = false;
    	try
    	{
            boolean doTrade = true;
            if (!this.world.isRemote)
            {                  
                if (doTrade) 
                {
                    // Make the super method think this villager is already trading, to block the GUI from opening
                    //_buyingPlayer.set(this, player);
                    ret = super.processInteract(player, hand);
                    //_buyingPlayer.set(this, null);
                }
            }
    	} 
    	catch (Exception e) 
    	{
    		throw new RuntimeException(e);
    	}
    	return ret;
    }
    
    @Nullable
    @Override
    public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, @Nullable IEntityLivingData livingdata) 
    {
       this.setHomePosAndDistance(this.getPosition(), MAX_HOME_DISTANCE);
        rollDiceRole();
        rollDiceChild();
        setEquipmentBasedOnDifficulty(difficulty);
        IEntityLivingData data = super.onInitialSpawn(difficulty, livingdata);
        initTrades();

        return data;
    }
    
    
    
    @Override
    public void writeEntityToNBT(NBTTagCompound compound)
    {
        super.writeEntityToNBT(compound);
        compound.setInteger("home_X", getHomePosition().getX());
        compound.setInteger("home_Y", getHomePosition().getY());
        compound.setInteger("home_Z", getHomePosition().getZ());


        NBTTagList nbttaglist = new NBTTagList();

        

        compound.setTag("pigman_inventory", nbttaglist);

        compound.setInteger("role_id", this.getDataManager().get(ROLE));
        

    

        compound.setLong("lastTradeTime", lastTradeTime);

       
    }
    
    @Override
    public void readEntityFromNBT(NBTTagCompound compound) {
        super.readEntityFromNBT(compound);
        if (compound.hasKey("home_X")) {
            this.setHomePosAndDistance(new BlockPos(compound.getInteger("home_X"), compound.getInteger("home_Y"), compound.getInteger("home_Z")), MAX_HOME_DISTANCE);
        }

    
        this.getDataManager().set(ROLE, compound.getInteger("role_id"));
       
        this.lastTradeTime = compound.getLong("lastTradeTime");
        
        this.setAdditionalAItasks();
       
    }
    
    private void setAdditionalAItasks()
    {
        if (!this.areAdditionalTasksSet)
        {
            this.areAdditionalTasksSet = true;

            if (this.isChild())
            {
                this.tasks.addTask(8, new EntityAIPlay(this, 0.32D));
            }
            else
            {
                this.tasks.addTask(6, new EntityAIPigmanHarvest(this, 0.6D));
               
            }
        }
    }
    
    protected boolean canDespawn()
    {
        return false;
    }
    
    @Override
    public ITextComponent getDisplayName()
    {
    	String name="";
    	
    	if (getRole() == Roles.BLACKSMITH) 
    		name+="Blacksmith Pigman";
    	else if (getRole()==Roles.TRADER) 
    		name+="Trader Pigman";
    	else 
    		name+="Guard Pigman";
    	
    	
    	
    			
    	return new TextComponentTranslation(name);  	 
    }
    
    @Override
    public void setAttackTarget(@Nullable EntityLivingBase entitylivingbaseIn)
    {
        super.setAttackTarget(entitylivingbaseIn);
    }

    /** Copied from EntityMob */
    @Override
    public boolean attackEntityAsMob(Entity entityIn)
    {
        float f = (float)this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue();
        int i = 0;

        if (entityIn instanceof EntityLivingBase)
        {
            f += EnchantmentHelper.getModifierForCreature(this.getHeldItemMainhand(), ((EntityLivingBase)entityIn).getCreatureAttribute());
            i += EnchantmentHelper.getKnockbackModifier(this);
        }

        boolean flag = entityIn.attackEntityFrom(DamageSource.causeMobDamage(this), f);

        if (flag)
        {
            if (i > 0 && entityIn instanceof EntityLivingBase)
            {
                ((EntityLivingBase)entityIn).knockBack(this, (float)i * 0.5F, (double) MathHelper.sin(this.rotationYaw * 0.017453292F), (double)(-MathHelper.cos(this.rotationYaw * 0.017453292F)));
                this.motionX *= 0.6D;
                this.motionZ *= 0.6D;
            }

            int j = EnchantmentHelper.getFireAspectModifier(this);

            if (j > 0)
            {
                entityIn.setFire(j * 4);
            }

            if (entityIn instanceof EntityPlayer)
            {
                EntityPlayer entityplayer = (EntityPlayer)entityIn;
                ItemStack itemstack = this.getHeldItemMainhand();
                ItemStack itemstack1 = entityplayer.isHandActive() ? entityplayer.getActiveItemStack() : ItemStack.EMPTY;

                if (!itemstack.isEmpty() && !itemstack1.isEmpty() && itemstack.getItem() instanceof ItemAxe && itemstack1.getItem() == Items.SHIELD)
                {
                    float f1 = 0.25F + (float)EnchantmentHelper.getEfficiencyModifier(this) * 0.05F;

                    if (this.rand.nextFloat() < f1)
                    {
                        entityplayer.getCooldownTracker().setCooldown(Items.SHIELD, 100);
                        this.world.setEntityState(entityplayer, (byte)30);
                    }
                }
            }

            this.applyEnchantments(this, entityIn);
        }

        return flag;
   }


 @Override
   public boolean attackEntityFrom(DamageSource source, float amount) {
       boolean result = super.attackEntityFrom(source, amount);

       return result;
   }

 public void onStruckByLightning(EntityLightningBolt lightningBolt)
 {
     if (!this.world.isRemote && !this.isDead)
     {
         EntityPigZombie entitypigzombie = new EntityPigZombie(this.world);
         entitypigzombie.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack(Items.GOLDEN_SWORD));
         entitypigzombie.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, this.rotationPitch);
         entitypigzombie.setNoAI(this.isAIDisabled());

         if (this.hasCustomName())
         {
             entitypigzombie.setCustomNameTag(this.getCustomNameTag());
             entitypigzombie.setAlwaysRenderNameTag(this.getAlwaysRenderNameTag());
         }

         this.world.spawnEntity(entitypigzombie);
         this.setDead();
     }
 }
 
 	@Override
	 public void onDeath(DamageSource cause)
	 {
	     
	     Entity entity = cause.getTrueSource();
	
	     if (entity != null)
	     {
	         if (entity instanceof EntityZombie)
	         {
	        	 EntityPigZombie entitypigzombie = new EntityPigZombie(this.world);
	             entitypigzombie.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack(Items.GOLDEN_SWORD));
	             entitypigzombie.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, this.rotationPitch);
	             entitypigzombie.setNoAI(this.isAIDisabled());
	
	             if (this.hasCustomName())
	             {
	                 entitypigzombie.setCustomNameTag(this.getCustomNameTag());
	                 entitypigzombie.setAlwaysRenderNameTag(this.getAlwaysRenderNameTag());
	             }
	             
	             this.world.spawnEntity(entitypigzombie);
	         }
	         
	     }
	 
	     
	     super.onDeath(cause);
	 }
	 
	 public void dbg(String msg) 
	 {
        if (debug) {
            System.out.println(msg);
        }
	 }


   
     
     
     
     
	 @Override
	 public boolean getIsWillingToMate(boolean updateFirst) 
	 {
        this.setIsWillingToMate(true);
        return true;
	 }
    
	 public boolean willBone(EntityPigman bonie)
	 {
        EntityPigman boner = this;
        if (!bonie.getIsWillingToMate(true)) return false;
        if (boner.isChild() || bonie.isChild()) return false;    
      
        return true;
	 }
 
	 public void pacify()
	 {
		 //removeTask(this, EntityAIMeleeAttack.class);
	 }

	public void resetTargetAI()
	{
		this.targetTasks.removeTask(AIHurtByTarget);
        AIHurtByTarget = new EntityAIHurtByTarget(this, true, new Class[0]);
        this.targetTasks.addTask(6, AIHurtByTarget);
	}
	
	
	 
	
}
