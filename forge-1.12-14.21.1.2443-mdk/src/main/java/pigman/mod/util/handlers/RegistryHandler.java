package pigman.mod.util.handlers;

import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.LootTableList;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import pigman.mod.init.BlockInit;
import pigman.mod.init.EntityInit;
import pigman.mod.init.ItemInit;
import pigman.mod.init.ModRecipes;
import pigman.mod.libs.HeadDropRegistry;
import pigman.mod.libs.VanillaHelper;
import pigman.mod.tileentity.TileEntityPigZombieSkull;
import pigman.mod.util.Reference;
import pigman.mod.util.interfaces.IHasModel;
import pigman.mod.world.gen.PigmanVillage;
import pigman.mod.world.gen.WorldGenCustomOres;
/* 
import net.minecraft.block.BlockSkull;
import pigman.mod.objects.blocks.BlockPigZombieSkull;
import pigman.mod.objects.items.ItemPigZombieSkull; 
import net.minecraft.block.Block;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.LootContext;
import net.minecraft.world.storage.loot.LootEntryItem;
import net.minecraft.world.storage.loot.LootPool;
import net.minecraft.world.storage.loot.LootTableList;
import net.minecraft.world.storage.loot.conditions.LootCondition;
import net.minecraft.world.storage.loot.functions.LootFunction;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
*/
//import pigman.mod.world.gen.WorldGenPigmanVillage;

@EventBusSubscriber
public class RegistryHandler
{
	
	
	
	@SubscribeEvent
	public static void onItemRegister(RegistryEvent.Register<Item> event)
	{
		event.getRegistry().registerAll(ItemInit.ITEMS.toArray(new  Item[0]));
		
	}
	
	@SubscribeEvent
	public static void onBlockRegister(RegistryEvent.Register<Block> event)
	{
		event.getRegistry().registerAll(BlockInit.BLOCKS.toArray(new  Block[0]));
		
	}
	
	@SubscribeEvent
	public static void onModelRegister(ModelRegistryEvent event)
	{
		for (Item item : ItemInit.ITEMS)
		{
			if(item instanceof IHasModel)
			{
				((IHasModel)item).registerModels();
			}
		}
		
		for (Block block : BlockInit.BLOCKS)
		{
			if(block instanceof IHasModel)
			{
				((IHasModel)block).registerModels();
			}
		}	
	}
	
	public static void preInitRegistries()
	{
		GameRegistry.registerWorldGenerator(new WorldGenCustomOres(),0);
		GameRegistry.registerWorldGenerator(new PigmanVillage(),0);
		EntityInit.registerEntities();
		RenderHandler.registerEntityRenders();
		LootTableList.register(new ResourceLocation("modid", "loot_table_name"));
		GameRegistry.registerTileEntity(TileEntityPigZombieSkull.class, Reference.MODID+":TileEntityZombiePigman");
		HeadDropRegistry.register(new VanillaHelper());
	}
	
	public static void initRegistries()
	{
		SoundsHandler.registerSounds();
		ModRecipes.init();
	}
}
