package pigman.mod.world.gen;


import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.fml.common.IWorldGenerator;
import pigman.mod.village.town.TownObject;
import pigman.mod.entity.EntityPigman;
import pigman.mod.util.UtilBuild;
import pigman.mod.village.town.SpawnLocationData;
import pigman.mod.world.gen.WorldGenStructure;



public class PigmanVillage extends TownObject implements IWorldGenerator
{
	private static int maxPopulationSize = 10;
	private int x,y,z;
	
	public static final ResourceLocation NETHER_CHEST_LOOT_TABLE = new ResourceLocation("nether_villages", "base_village");
	public static final WorldGenStructure HOUSE = new WorldGenStructure("house");
	public static final WorldGenStructure VILLAGETWO = new WorldGenStructure("villagetwo");
	
	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) 
	{
		switch(world.provider.getDimension())
		{
		
			
		case 1:
			
			
			break;
			
		case 0:
			
			
			
			break;
			
		case -1:
			//House doesn't generate?
			generateStructure(HOUSE, world, random, chunkX, chunkZ, 20, Blocks.NETHERRACK);
			//Change rarity?
			generateStructure(VILLAGETWO, world, random, chunkX, chunkZ, 20, Blocks.NETHERRACK);
			//Generate 1 diamond block find like spawn origin of village use it to see if isAreaClear works
			
		}
	}
	
	private void generateStructure(WorldGenerator generator, World world, Random random, int chunkX, int chunkZ, int chance, Block topBlock)
	{
		
		int x = (chunkX * 16) + random.nextInt(15);
		int z = (chunkZ * 16) + random.nextInt(15);
		
		
		int y = calculateGenerationHeight(world, x, z, topBlock);
		
		this.x=x;
		this.y=y;
		this.z=z;
		
		
		BlockPos pos = new BlockPos(x,y,z);
		
		
		
		if(world.getWorldType() != WorldType.FLAT)
		{
			if (isAreaClear(pos,world))
			{
				if(random.nextInt(chance) == 0 &&  y>34 )
				{
					BlockPos pos2 = new BlockPos(x,y-1,z);
					generator.generate(world, random, pos2);
					world.setBlockState(pos2,Blocks.GOLD_BLOCK.getDefaultState(), 2);
					
					BlockPos chestPos= new BlockPos(x,y+3,z);
					world.setBlockState(chestPos,Blocks.CHEST.getDefaultState(),2);
			        TileEntityChest chest = (TileEntityChest) world.getTileEntity(chestPos);
			        
			        Random rand= new Random();
                    chest.setLootTable(NETHER_CHEST_LOOT_TABLE, rand.nextLong());
                    

		             
			        
				}
			}
		}
		
	}
	
	
	
	private static boolean isAreaClear(BlockPos pos, World world)
	{
		
		//int posX= pos.getX()+1;
		//int posZ= pos.getZ()+1;
		//Block block = null;
		
		if (world != null)
		{
			/*for (int x = 0 ; x< posX + 28; x++)
			{
				for (int z = 0; z < posZ + 14 ; z++)
				{
					block=world.getBlockState(new BlockPos(x,pos.getY(),z)).getBlock();
					if (block != Blocks.NETHERRACK)
					{
						return false;
					}
				}
			}*/
			
			/*if(!world.getBlockState(pos.down()).isSideSolid(world, pos, EnumFacing.UP))
			//{
				// return false;
            }
            else
            {*/
				 BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos();
	             int posX = pos.getX();
	             int posZ = pos.getZ();
	             
	             for(int x = posX; x < posX + 29; x++)
	             {
	                 for(int y = pos.getY()+1; y < pos.getY() + 8; y++)
	                 {
	
	                     for(int z = posZ; z < posZ + 15; z++)
	                     {
	                    	 
	                         if(world.getBlockState(mutableBlockPos.setPos(x, y, z)).isNormalCube())
	                         {
	
	                             return false;
	
	                         }
	                        // BlockPos pos2= new BlockPos(x,y,z);
	                    	 //world.setBlockState(pos2,Blocks.DIAMOND_BLOCK.getDefaultState(), 2);
	
	                     }
	
	                 }
	             }
	             //world.setBlockState(pos,Blocks.GOLD_BLOCK.getDefaultState(), 2);
	             return true;
			}
		
		
		return false;
	}
	
	private static int calculateGenerationHeight(World world, int x, int z, Block topBlock)
	{
		int y = 70;
		boolean foundGround = false;
		
		//Find lowest y 
		while(!foundGround && y-- >= 34)
		{
			Block block = world.getBlockState(new BlockPos(x,y,z)).getBlock();
			foundGround = block == topBlock;
		}
		
		return y;
	}
	
	//*************************************************************************************************************************
	//Data Collecting
	//*************************************************************************************************************************
	
	 public PigmanVillage()
	 {
	    super();
	     generateSpawnCoords();
	 }
	 
	 public PigmanVillage(boolean psuedo)
	 {
		 super();
	 }
	 
	
	
	//Maybe needed???
	private void generateSpawnCoords() {
		 registerSpawnLocation(new SpawnLocationData(getCoordsWithRelFromCorner(2, 3, 8), "hunter"));
		 registerSpawnLocation(new SpawnLocationData(getCoordsWithRelFromCorner(12, 3, 8), "gatherer"));
		 registerSpawnLocation(new SpawnLocationData(getCoordsWithRelFromCorner(2, 3, 17), "hunter"));
		
	}
	
	private BlockPos getCoordsWithRelFromCorner(int i, int j, int k) 
	{
		BlockPos pos = new BlockPos(x+i,y+j,z+k);
		return pos;
	}
	
	 @Override
    public EntityLivingBase spawnMemberAtSpawnLocation(SpawnLocationData parData) {
        super.spawnMemberAtSpawnLocation(parData);

        EntityPigman ent = null;

        

        ent = new EntityPigman(getWorld());

        if (ent != null) {
            
        	//TODO: After finishing Pigman
            //ent.setVillageAndDimID(this.locationID, this.dimID);
            ent.setPosition(spawn.getX() + parData.coords.getX() + 0.5F, spawn.getY() + parData.coords.getY(), spawn.getZ() + parData.coords.getZ() + 0.5F);
            getWorld().spawnEntity(ent);
            parData.entityUUID = ent.getPersistentID();
            ent.onInitialSpawn(getWorld().getDifficultyForLocation(ent.getPosition()), null);

            //ent.postSpawnGenderFix();

            addEntity(ent);
        }

        return ent;
    }

	@Override
    public void tickUpdate() 
	 {
        super.tickUpdate();

        if (getWorld().getTotalWorldTime() % 20 == 0) {
            System.out.println("Pigman village tick - " + spawn.getX() + ", " + spawn.getZ() + " - E/PE: " + listLoadedEntities.size() + "/" + listPersistantEntities.size());
        }
    }

    @Override
    public void initFirstTime()
    {
        super.initFirstTime();
    }
    
    public void spawnEntitiesForce() 
    {
        System.out.println("Spawning pigman village population for village: " + spawn);
        tickMonitorPersistantMembers(true);
    }
    
   

	public static  int getMaxPopulationSize() 
	{
		return maxPopulationSize;
	}

    public void setMaxPopulationSize(int maxPopulationSize) 
    {
        PigmanVillage.maxPopulationSize = maxPopulationSize;
    }	
    
    public NBTTagCompound getInitNBTTileEntity() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void readFromNBT(NBTTagCompound var1) {
        super.readFromNBT(var1);
        

        NBTTagCompound nbtPersistantEntities = var1.getCompoundTag("lookupGenders");
        Iterator it = nbtPersistantEntities.getKeySet().iterator();
        while (it.hasNext()) {
            String entryName = (String) it.next();
            NBTTagCompound entry = nbtPersistantEntities.getCompoundTag(entryName);
            UUID uuid = UUID.fromString(entry.getString("UUID"));
            int gender = entry.getInteger("gender");
            lookupEntityToGender.put(uuid, gender);
        }

    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound var1) {
        super.writeToNBT(var1);
        

        NBTTagCompound nbtListPersistantEntities = new NBTTagCompound();
        int count = 0;
        for (Map.Entry<UUID, Integer> entry : lookupEntityToGender.entrySet()) {
            NBTTagCompound nbtEntry = new NBTTagCompound();
            nbtEntry.setString("UUID", entry.getKey().toString());
            nbtEntry.setInteger("gender", entry.getValue());
            nbtListPersistantEntities.setTag("entry_" + count++, nbtEntry);
        }
        var1.setTag("lookupGenders", nbtListPersistantEntities);
        
        return var1;
    }
    
    @Override
    public void addEntity(EntityLivingBase ent) {
        super.addEntity(ent);

        if (ent instanceof EntityPigman) {
            //EntityPigman koa = (EntityPigman) ent;

            if (lookupEntityToGender.containsKey(ent.getPersistentID())) {
                //again maybe false positives
                UtilBuild.dbg("WARNING: adding already existing entitys _persistant ID_ to lookupEntityToGender");
            } else {
              //  lookupEntityToGender.put(ent.getPersistentID(), koa.getGender().ordinal());|GR|
            }
        }
    }
    
    @Override
    public void cleanup() {
        super.cleanup();

        lookupEntityToGender.clear();
    }
    
    @Override
    public void hookEntityDied(EntityLivingBase ent) {
        super.hookEntityDied(ent);

        lookupEntityToGender.remove(ent.getPersistentID());
    }

   
    
    
	
}
