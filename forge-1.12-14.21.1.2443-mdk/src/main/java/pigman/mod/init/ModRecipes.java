package pigman.mod.init;

import net.minecraft.init.Items;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModRecipes
{
	public static void init()
	{
		
		GameRegistry.addSmelting(BlockInit.ORE_NETHER, new ItemStack(Items.GOLD_INGOT,1), 1.0f);
		GameRegistry.addSmelting(Blocks.SOUL_SAND, new ItemStack(Blocks.STAINED_GLASS,1,(short) 12 ), 1.0f);
		
	}
}

