package pigman.mod.init;

import net.minecraft.init.Items;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;


public class ModRecipes
{
	public static void init()
	{
		GameRegistry.addSmelting(new ItemStack(BlockInit.ORE_NETHER,1,1), new ItemStack(Items.GOLD_INGOT,1), 1.0f);
		GameRegistry.addSmelting(new ItemStack(BlockInit.ORE_NETHER,1,0), new ItemStack(ItemInit.LUKIUM_INGOT,1), 1.0f);
		GameRegistry.addSmelting(Blocks.SOUL_SAND, new ItemStack(Blocks.STAINED_GLASS,1,(short) 12 ), 1.0f);
		
	}
}

