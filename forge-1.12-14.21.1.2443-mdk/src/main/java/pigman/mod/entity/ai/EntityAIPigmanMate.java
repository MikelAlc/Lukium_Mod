package pigman.mod.entity.ai;

import java.util.List;


import net.minecraft.entity.ai.EntityAIBase;


import net.minecraft.world.World;
import pigman.mod.entity.EntityPigman;
import pigman.mod.world.gen.PigmanVillage;

import net.minecraft.init.SoundEvents;
import net.minecraft.util.SoundCategory;



public class EntityAIPigmanMate extends EntityAIBase
{
	 private final EntityPigman villagerObj;
	 private EntityPigman mate;
	 private final World world;
	 private int matingTimeout;
	 
	 public EntityAIPigmanMate(EntityPigman villagerIn)
	 {
        this.villagerObj = villagerIn;
        this.world = villagerIn.world;
        this.setMutexBits(3);
    }
	 
	  /* Returns whether the EntityAIBase should begin execution.
     */
    public boolean shouldExecute()
    {
        if (this.villagerObj.getGrowingAge() != 0)
        {
            return false;
        }
        else if (this.villagerObj.getRNG().nextInt(500) != 0)
        {
            return false;
        }
        else
        {
        	if (this.canTownHandleMoreVillagers() && this.villagerObj.getIsWillingToMate(true)) {
                List<EntityPigman> listEntities = this.world.getEntitiesWithinAABB(EntityPigman.class, this.villagerObj.getEntityBoundingBox().grow(8.0D, 3.0D, 8.0D));
                EntityPigman clEnt = null;
                double clDist = 9999;
                for (EntityPigman ent : listEntities) {
                    if (ent != villagerObj) {
                        if (villagerObj.willBone(ent)) {
                            if (villagerObj.getDistanceToEntity(ent) < clDist) {
                                clEnt = ent;
                                clDist = villagerObj.getDistanceToEntity(ent);
                            }
                        }
                    }
                }
                if (clEnt != null) {
                    this.mate = clEnt;
                    //System.out.println("start mate");
                    return true;
                }
            }
        }
       return false;
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void startExecuting()
    {
    	 this.matingTimeout = 300;
         this.villagerObj.setMating(true);
         if (this.mate != null) {
             this.mate.setMating(true);
         }
    }

    /**
     * Reset the task's internal state. Called when this task is interrupted by another one
     */
    public void resetTask()
    {
        this.mate = null;
        this.villagerObj.setMating(false);
    }

    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    public boolean shouldContinueExecuting()
    {
    	 boolean result = this.matingTimeout >= 0 && this.canTownHandleMoreVillagers() && this.villagerObj.getGrowingAge() == 0 && this.villagerObj.getIsWillingToMate(false);
         if (!result) {
             //System.out.println("mating reset");
         }
         return result;
    }

    /**
     * Keep ticking a continuous task that has already been started
     */
    public void updateTask()
    {
        --this.matingTimeout;
        this.villagerObj.getLookHelper().setLookPositionWithEntity(this.mate, 10.0F, 30.0F);

        if (this.villagerObj.getDistanceSqToEntity(this.mate) > 2.25D)
        {
            this.villagerObj.getNavigator().tryMoveToEntityLiving(this.mate, 0.25D);
        }
        else if (this.matingTimeout == 0 && this.mate.isMating())
        {
            this.giveBirth();
        }

        if (this.villagerObj.getRNG().nextInt(35) == 0)
        {
            this.world.setEntityState(this.villagerObj, (byte)12);
        }
    }
    
    /*
    private boolean canTownHandleMoreVillagers()
    {	
    	List<EntityPigman> listEntities = this.world.getEntitiesWithinAABB(EntityPigman.class, this.villagerObj.getEntityBoundingBox().grow(8.0D, 3.0D, 8.0D));
 		return listEntities.size()<=PigmanVillage.getMaxPopulationSize();
    }
     */

    private boolean canTownHandleMoreVillagers()
    {
    	//PigmanVillage village =new PigmanVillage(true);
    	
    	//check the area around pigman to see how many pigman there are
    	int count=villagerObj.world.countEntities(villagerObj.getClass());
    	//int villageCount=villagerObj.world.countEntities(village.getClass());
    	//if (villageCount !=0)
    		return count<=PigmanVillage.getMaxPopulationSize();
    	
    	//return false;
    		
    		
    		
    	/*PigmanVillage village = villagerObj.getVillage();

        if (village == null) {
            if (villagerObj.findAndSetTownID(true)) {
                village = villagerObj.getVillage();

                //just in case
                if (village == null) return false;
            } else {
                return false;
            }
        }

        return village.getPopulationSize() < village.getMaxPopulationSize();
        */
    }

    private void giveBirth()
    {
    	  net.minecraft.entity.EntityAgeable entityvillager = this.villagerObj.createChild(this.mate);
          this.mate.setGrowingAge(6000);
          this.villagerObj.setGrowingAge(6000);
          this.mate.setIsWillingToMate(false);
          this.villagerObj.setIsWillingToMate(false);

          //final net.minecraftforge.event.entity.living.BabyEntitySpawnEvent event = new net.minecraftforge.event.entity.living.BabyEntitySpawnEvent(villagerObj, mate, entityvillager);
          //if (net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(event) || event.getChild() == null) { return; }
          //entityvillager = event.getChild();
          entityvillager.setGrowingAge(-24000);
          entityvillager.setLocationAndAngles(this.villagerObj.posX, this.villagerObj.posY, this.villagerObj.posZ, 0.0F, 0.0F);
          if (entityvillager instanceof EntityPigman) {
              ((EntityPigman) entityvillager).setVillageAndDimID(villagerObj.getVillageID(), villagerObj.getVillageDimID());
              entityvillager.setHomePosAndDistance(villagerObj.getHomePosition(), EntityPigman.MAX_HOME_DISTANCE);
              PigmanVillage village = villagerObj.getVillage();
              if (village != null) {
               

                  village.addEntity(entityvillager);
              }

            

              ((EntityPigman) entityvillager).getWorld().playSound(null, entityvillager.getPosition(), SoundEvents.ENTITY_CHICKEN_EGG, SoundCategory.AMBIENT, 1, 1);
          }



          this.world.spawnEntity(entityvillager);
          this.world.setEntityState(entityvillager, (byte)12);
    }
}
