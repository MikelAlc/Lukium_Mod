package pigman.mod.init;

import java.util.List;
import java.util.ArrayList;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import pigman.mod.objects.blocks.BlockCustomOre;
import pigman.mod.objects.blocks.BlockLukium;
import pigman.mod.objects.blocks.BlockPigZombieSkull;
import pigman.mod.objects.blocks.BlockRayshroom;
import pigman.mod.objects.blocks.BlockRoyalSoulSand;


public class BlockInit 
{
	public static final List<Block> BLOCKS = new ArrayList<Block>();
	
	public static final Block ORE_NETHER = new BlockCustomOre("ore_nether", Material.ROCK,5.0F,15,3,"nether");
	public static final Block LUKIUM_BLOCK = new BlockLukium("lukium_block");
	public static final Block ROYAL_SOUL_SAND = new BlockRoyalSoulSand("royal_soul_sand");
	public static final Block RAYSHROOM = new BlockRayshroom("rayshroom");
	
	//Must be last so that Zombie Pigman drop their skulls
	public static final BlockPigZombieSkull PIG_ZOMBIE_SKULL = new BlockPigZombieSkull();
	
}

