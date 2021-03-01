package pigman.mod.world.gen;

import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.fml.common.IWorldGenerator;
import pigman.mod.init.BlockInit;
import pigman.mod.objects.blocks.BlockCustomOre;
import pigman.mod.util.handlers.EnumHandler;

import java.util.Random;
import net.minecraft.block.state.pattern.BlockMatcher;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;


public class WorldGenCustomOres implements IWorldGenerator
{
	private WorldGenerator ore_nether_lukium,ore_nether_gold;
	
	public WorldGenCustomOres()
	{
		ore_nether_gold = new WorldGenMinable(BlockInit.ORE_NETHER.getDefaultState().withProperty(BlockCustomOre.VARIANT, EnumHandler.EnumType.GOLD),4,BlockMatcher.forBlock(Blocks.NETHERRACK));
		ore_nether_lukium = new WorldGenMinable(BlockInit.ORE_NETHER.getDefaultState().withProperty(BlockCustomOre.VARIANT, EnumHandler.EnumType.LUKIUM),3,BlockMatcher.forBlock(Blocks.NETHERRACK));
	}
	
	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) 
	{
		switch(world.provider.getDimension())
		{
			case -1:
				//Test fix
				
				runGenerator(ore_nether_lukium,world,random,chunkX,chunkZ,4,100,125);
				runGenerator(ore_nether_gold, world, random, chunkX, chunkZ, 20, 0, 100);
				break;
			case 0:
			
				break;
			
			case 1:
		}
	}
				
		
	
	private void runGenerator(WorldGenerator gen, World world, Random rand, int chunkX, int chunkZ, int chance, int minHeight, int maxHeight)
	{
		if (minHeight>maxHeight|| minHeight<0)  throw new IllegalArgumentException("Ore generated out of bounds");
		{
			int heightDiff = maxHeight - minHeight + 1;
			for(int i = 0; i < chance; i++)
			{
				int x = chunkX * 16 + rand.nextInt(16);
				int y = minHeight + rand.nextInt(heightDiff);
				int z = chunkZ * 16 + rand.nextInt(16);
				
				gen.generate(world, rand, new BlockPos(x,y,z));
			}
		}
	}
	
	
}
