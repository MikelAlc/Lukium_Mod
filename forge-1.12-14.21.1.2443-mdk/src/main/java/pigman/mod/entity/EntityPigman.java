package pigman.mod.entity;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
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
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.AxisAlignedBB;
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
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import pigman.mod.Main;
import pigman.mod.entity.ai.EntityAIPigmanHarvest;
import pigman.mod.entity.ai.EntityAIPigmanMate;
import pigman.mod.init.ItemInit;
import pigman.mod.util.interfaces.ISimulationTickable;
import pigman.mod.village.town.WorldDataInstance;
import pigman.mod.world.gen.PigmanVillage;



public class EntityPigman extends EntityVillager implements IMerchant, INpc
{
	 private static final DataParameter<Integer> ROLE = EntityDataManager.createKey(EntityPigman.class, DataSerializers.VARINT);
	
	 private boolean areAdditionalTasksSet;
	 public boolean debug = false;
	 private EntityAIBase AIHurtByTarget;
	 
	 private long lastTradeTime = 0;
	
	 //TODO:Tweeak this 
	 public static int MAX_HOME_DISTANCE = 12;
	 
    private static final int INVALID_DIM = Integer.MAX_VALUE;
    private int villageID = -1;
    private int villageDimID = INVALID_DIM;
	
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
	protected void updateAITasks() {
	    //cancel villager AI that overrides our home position
	   
		//TODO maybe??
	    //temp until we use AT
	  // Util.removeTask(this, EntityAIHarvestFarmland.class);
	    //Util.removeTask(this, EntityAIPlay.class);
	
		
		
	    monitorHomeVillage();
	    //adjust home position to chest right nearby for easy item spawning
	    findAndSetHomeToCloseChest(false);
	   
	    
	    findAndSetTownID(false);
	
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
		yeet.sendMessage(new TextComponentString(this.getHealth()+" "+delayTick+ " "+hasHealItem));
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
		list.add(new MerchantRecipe(new ItemStack(Items.GOLD_INGOT,(int)((Math.random()*(4))+ 3) ),new ItemStack(Items.BOWL),new ItemStack(ItemInit.NETHER_SOUP)));			
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
//        findAndSetHomeToCloseChest(true);
      

        //updateUniqueEntityAI();

        IEntityLivingData data = super.onInitialSpawn(difficulty, livingdata);

        /*VillagerRegistry.VillagerProfession koaProfession = new VillagerRegistry.VillagerProfession("koa_profession", "");
        this.setProfession(koaProfession);*/

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
        

        compound.setInteger("village_id", villageID);
        compound.setInteger("village_dim_id", villageDimID);

        compound.setLong("lastTradeTime", lastTradeTime);

       
    }
    
    @Override
    public void readEntityFromNBT(NBTTagCompound compound) {
        super.readEntityFromNBT(compound);
        if (compound.hasKey("home_X")) {
            this.setHomePosAndDistance(new BlockPos(compound.getInteger("home_X"), compound.getInteger("home_Y"), compound.getInteger("home_Z")), MAX_HOME_DISTANCE);
        }

        

       /* if (compound.hasKey("koa_inventory", 9)) {
            NBTTagList nbttaglist = compound.getTagList("koa_inventory", 10);
            //this.initHorseChest();

            for (int i = 0; i < nbttaglist.tagCount(); ++i) {
                NBTTagCompound nbttagcompound = nbttaglist.getCompoundTagAt(i);
                int j = nbttagcompound.getByte("Slot") & 255;

                this.inventory.setInventorySlotContents(j, new ItemStack(nbttagcompound));
            }
        }*/

        this.villageID = compound.getInteger("village_id");

        //backwards compat
        if (!compound.hasKey("village_dim_id")) {
            this.villageDimID = -1;
        } else {
            this.villageDimID = compound.getInteger("village_dim_id");
        }



        
        this.getDataManager().set(ROLE, compound.getInteger("role_id"));
       
        this.lastTradeTime = compound.getLong("lastTradeTime");
        
        this.setAdditionalAItasks();
       
        //updateUniqueEntityAI();
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


     public void monitorHomeVillage()
     {
	     if (villageDimID != INVALID_DIM) {
	         //if (villageID != -1) {
	
	    	 	//TODO too much copying?
	             //if not in home dimension, full reset
	             if (this.world.provider.getDimension() != villageDimID) {
	                 dbg("pigman detected different dimension, zapping memory");
	                 zapMemory();
	                 
	                 addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 5));
	             }
	         //}
	     }
	
	     findOrCreateNewVillage();
	 }
     
     public void zapMemory() 
     {
         
         setHomePosAndDistance(BlockPos.ORIGIN, -1);
         
         villageDimID = INVALID_DIM;
         villageID = -1;
     }
     
     public void findOrCreateNewVillage() 
     {
         if ((world.getTotalWorldTime()+this.getEntityId()) % (20*5) != 0) return;

         if (getVillage() == null) 
         {
             PigmanVillage village = getVillageWithinRange(64);
             if (village != null) 
             {
                 dbg("Pigman found a village to join: " + village.locationID);
                 this.setVillageAndDimID(village.locationID, village.dimID);
             } 
             else
             {
            	 //TODO custom villages
                 /*check we have a chest locked in
                 TileEntity tile = world.getTileEntity(getHomePosition());
                 if ((tile instanceof TileEntityChest))
                 {
                     village = createNewVillage(getHomePosition());
                     if (village != null) 
                     {
                         this.setVillageAndDimID(village.locationID, village.dimID);
                         dbg("Pigman created a new village!");
                     } else 
                     {
                         dbg("village wasnt created, critical error!");
                     }
                 } 
                 else 
                 {
                     dbg("no village near and no chest!");
                 }*/
             }
         }
     }
     
     public void findAndSetHomeToCloseChest(boolean force) {

         if (!force && (world.getTotalWorldTime()+this.getEntityId()) % (20*30) != 0) return;

         //validate home position
         boolean tryFind = false;
         if (getHomePosition() == null) {
             tryFind = true;
         } else {
             TileEntity tile = world.getTileEntity(getHomePosition());
             if (!(tile instanceof TileEntityChest)) {
                 //home position isnt a chest, keep current position but find better one
                 tryFind = true;
             }
         }
//TODO check this
         if (tryFind) {
             int range = 20;
             for (int x = -range; x <= range; x++) {
                 for (int y = -range / 2; y <= range / 2; y++) {
                     for (int z = -range; z <= range; z++) {
                         BlockPos pos = this.getPosition().add(x, y, z);
                         TileEntity tile = world.getTileEntity(pos);
                         if (tile instanceof TileEntityChest) {
                             //System.out.println("found chest, updating home position to " + pos);
                             dbg("found chest, updating home position to " + pos);
                             setHomePosAndDistance(pos, MAX_HOME_DISTANCE);
                             return;
                         }
                     }
                 }
             }
         }
     }
   
     public boolean findAndSetTownID(boolean force) {
         if (!force && (world.getTotalWorldTime()+this.getEntityId()) % (20*30) != 0) return false;

         boolean tryFind = false;

         if (villageID == -1 || villageDimID == INVALID_DIM) {
             tryFind = true;
             //make sure return status is correct
             villageID = -1;
         }

         if (tryFind) {
             List<EntityPigman> listEnts = world.getEntitiesWithinAABB(EntityPigman.class, new AxisAlignedBB(this.getPosition()).grow(20, 20, 20));
             Collections.shuffle(listEnts);
             for (EntityPigman ent : listEnts) {
                 if (ent.villageID != -1 && ent.villageDimID != INVALID_DIM) {
                     this.setVillageAndDimID(ent.villageID, ent.villageDimID);
                     break;
                 }
             }
         }

         return this.villageID != -1;
     }
     
     public int getVillageID() {
         return villageID;
     }

     /*public void setVillageID(int villageID) {
         this.villageID = villageID;
     }*/

     public void setVillageAndDimID(int villageID, int villageDimID) {
         this.villageID = villageID;
         this.villageDimID = villageDimID;
     }

     public int getVillageDimID() {
         return villageDimID;
     }

     /*public void setVillageDimID(int villageDimID) {
         this.villageDimID = villageDimID;
     }*/

     public PigmanVillage getVillage() {
         if (this.villageDimID == INVALID_DIM || this.villageID == -1) return null;

         World world = DimensionManager.getWorld(villageDimID);

         if (world != null) {
             WorldDataInstance data = world.getCapability(Main.WORLD_DATA_INSTANCE, null);
             if (data != null) {
                 ISimulationTickable sim = data.getLocationByID(villageID);
                 if (sim instanceof PigmanVillage) {
                     return (PigmanVillage) sim;
                 } else {
                     //System.out.println("critical: couldnt find village by ID");
                 }
             } else {
                 //System.out.println("critical: no world cap");
             }
         }
         return null;
     }

    
     
     
     public PigmanVillage getVillageWithinRange(int range) 
     {
         World world = this.world;


         double distSq = range * range;
         double closestDist = 99999;
         PigmanVillage closestVillage = null;

         if (world != null) {
             WorldDataInstance data = world.getCapability(Main.WORLD_DATA_INSTANCE, null);
             if (data != null) {
                 for (ISimulationTickable entry : data.lookupTickingManagedLocations.values()) {
                     if (entry instanceof PigmanVillage) {
                         PigmanVillage village = (PigmanVillage) entry;
                         if (village.getPopulationSize() < PigmanVillage.getMaxPopulationSize()) {
                             double dist = village.getOrigin().distanceSq(this.getPos());
                             if (dist < distSq && dist < closestDist) {
                                 closestDist = dist;
                                 closestVillage = village;
                             }
                         }
                     }
                 }

             }
         }

         return closestVillage;
     }
     
	 @Override
	     public boolean getIsWillingToMate(boolean updateFirst) 
	     {
	         this.setIsWillingToMate(true);
	         return true;
	     }
     /*TODO inventory tracking?
     public boolean tryDumpInventoryIntoHomeChest() {
         TileEntity tile = world.getTileEntity(this.getHomePosition());
         if (tile instanceof TileEntityChest) {
             TileEntityChest chest = (TileEntityChest)tile;

             for (int i = 0; i < this.inventory.getSizeInventory(); ++i) {
                 ItemStack itemstack = this.inventory.getStackInSlot(i);

                 if (!itemstack.isEmpty()) {
                     this.inventory.setInventorySlotContents(i, this.addItem(chest, itemstack));
                 }
             }
         }
         //maybe return false if inventory not emptied entirely
         return true;
     }

     @Nullable
     public ItemStack addItem(TileEntityChest chest, ItemStack stack)
     {
         ItemStack itemstack = stack.copy();

         for (int i = 0; i < chest.getSizeInventory(); ++i)
         {
             ItemStack itemstack1 = chest.getStackInSlot(i);

             if (itemstack1.isEmpty())
             {
                 chest.setInventorySlotContents(i, itemstack);
                 chest.markDirty();
                 return ItemStack.EMPTY;
             }

             if (ItemStack.areItemsEqual(itemstack1, itemstack))
             {
                 int j = Math.min(chest.getInventoryStackLimit(), itemstack1.getMaxStackSize());
                 int k = Math.min(itemstack.getCount(), j - itemstack1.getCount());

                 if (k > 0)
                 {
                     itemstack1.grow(k);
                     itemstack.shrink(k);

                     if (itemstack.getCount() <= 0)
                     {
                         chest.markDirty();
                         return ItemStack.EMPTY;
                     }
                 }
             }
         }

         if (itemstack.getCount() != stack.getCount())
         {
             chest.markDirty();
         }

         return itemstack;
     }
	*/
     
	 @Override
    public void setDead() 
	 {
        super.setDead();
        if (!world.isRemote) 
        {
            //System.out.println("hook dead " + this);
            PigmanVillage village = getVillage();
            if (village != null) {
                village.hookEntityDied(this);
            }
        }
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
	
	
	 
	 /*
	 //From Tropicraft mod
	 public static boolean removeTask(EntityCreature ent, Class taskToReplace) 
	 {
		 for (EntityAITasks.EntityAITaskEntry entry : ent.tasks.taskEntries) 
	     {
			 if (taskToReplace.isAssignableFrom(entry.action.getClass()))
	         {
				 ent.tasks.removeTask(entry.action);
	             return true;
	         }
	     }
	        
        return false;
	 }*/
}
