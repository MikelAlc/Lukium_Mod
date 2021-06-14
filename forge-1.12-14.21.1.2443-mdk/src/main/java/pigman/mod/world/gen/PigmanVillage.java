package pigman.mod.world.gen;



import java.util.Random;

import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.block.Block;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.fml.common.IWorldGenerator;
import pigman.mod.entity.EntityPigman;
import pigman.mod.world.gen.WorldGenStructure;



public class PigmanVillage implements IWorldGenerator
{
	private static int maxPopulationSize = 10;
	
	
	public static final ResourceLocation NETHER_CHEST_LOOT_TABLE = new ResourceLocation("lukium", "base_village");
	public static final WorldGenStructure HOUSE = new WorldGenStructure("house");
	public static final WorldGenStructure VILLAGE = new WorldGenStructure("village");
	
	
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) 
	{
		switch(world.provider.getDimension())
		{
		
			
		case 1:
			
			
			break;
			
		case 0:
			
			
			
			break;
			
		case -1:
			//Change rarity?
			generateStructure(VILLAGE, world, random, chunkX, chunkZ, 20, Blocks.NETHERRACK);
			
			
		}
	}
	
	private void generateStructure(WorldGenerator generator, World world, Random random, int chunkX, int chunkZ, int chance, Block topBlock)
	{
		
		int x = (chunkX * 16) + random.nextInt(15);
		int z = (chunkZ * 16) + random.nextInt(15);
		
		
		int y = calculateGenerationHeight(world, x, z, topBlock);
		
		
		
		
		BlockPos pos = new BlockPos(x,y,z);
		
		
		
		
		if (isAreaClear(pos,world))
		{
			if(random.nextInt(chance) == 0 &&  y>34 )
			{
				BlockPos pos2 = new BlockPos(x,y-1,z);
				generator.generate(world, random, pos2);
				//world.setBlockState(pos2,Blocks.GOLD_BLOCK.getDefaultState(), 2);
				
				
				genPigmen(world, pos2);
				setChestsLootTable(world, pos2);
				/*
				BlockPos chestPos= new BlockPos(x,y+3,z);
				world.setBlockState(chestPos,Blocks.CHEST.getDefaultState(),2);
		        TileEntityChest chest = (TileEntityChest) world.getTileEntity(chestPos);
		        
		        
		        
		        Random rand= new Random();
                chest.setLootTable(NETHER_CHEST_LOOT_TABLE, rand.nextLong());
                */
                
	             
		        
			}
		}
		
		
	}
	
	private void genPigmen(World world, BlockPos pos)
	{
		Random rand= new Random();
		
        for(int i=0;i<(rand.nextInt(2)+3);i++)
        {
			EntityPigman entitypigman = new EntityPigman(world);
	        entitypigman.setLocationAndAngles(pos.getX()+6+i, pos.getY()+2, pos.getZ()+10, 300F, 0.0F);
	        entitypigman.onInitialSpawn(world.getDifficultyForLocation(new BlockPos(entitypigman)), (IEntityLivingData)null);
	        world.spawnEntity(entitypigman);
        }
     
	}
	
	private void setChestsLootTable(World world,BlockPos pos)
	{
		Random rand= new Random();
		if(rand.nextInt(2)==0) setChestLootTable(1,9,pos,world,rand);
		if(rand.nextInt(2)==0) setChestLootTable(13,7,pos,world,rand);
		if(rand.nextInt(2)==0) setChestLootTable(13,13,pos,world,rand);
		if(rand.nextInt(2)==0) setChestLootTable(1,18,pos,world,rand);	
	}
	
	private void setChestLootTable(int x,int z, BlockPos pos, World world, Random rand)
	{
		BlockPos chestPos= new BlockPos(pos.getX()+x, pos.getY()+3, pos.getZ()+z);
		TileEntityChest chest = (TileEntityChest) world.getTileEntity(chestPos);
		chest.setLootTable(NETHER_CHEST_LOOT_TABLE, rand.nextLong());
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
	             
	             for(int x = posX; x < posX + 15; x++)
	             {
	                 for(int y = pos.getY()+1; y < pos.getY() + 8; y++)
	                 {
	
	                     for(int z = posZ; z < posZ + 29; z++)
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

	public static int getMaxPopulationSize()
	{
		return PigmanVillage.maxPopulationSize;
	}
	
	
}
	
	
