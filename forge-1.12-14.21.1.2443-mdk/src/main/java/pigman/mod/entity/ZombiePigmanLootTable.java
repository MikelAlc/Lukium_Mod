package pigman.mod.entity;

import net.minecraft.world.storage.loot.LootEntryItem;
import net.minecraft.world.storage.loot.LootPool;
import net.minecraft.world.storage.loot.LootTableList;
import net.minecraft.world.storage.loot.conditions.LootCondition;
import net.minecraft.world.storage.loot.conditions.RandomChance;
import net.minecraft.world.storage.loot.functions.LootFunction;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import pigman.mod.init.ItemInit;


@EventBusSubscriber
public class ZombiePigmanLootTable
{
	
	@SubscribeEvent
	public static void onLootTablesLoaded(LootTableLoadEvent event)
	{
	 
	    if (event.getName().equals(LootTableList.ENTITIES_ZOMBIE_PIGMAN))
	    {
	 
	        final LootPool pool1 = event.getTable().getPool("pool1");
	 
	        if (pool1 != null) 
	        {
	            pool1.addEntry(new LootEntryItem(ItemInit.ITEMS.get(ItemInit.ITEMS.size()-1), 10, 0, new LootFunction[0], new LootCondition[] {new RandomChance(0.15f)}, "nether_villages:pig_zombie_skull"));
	        }
	    }
	    
	    
	    
	}
	
	
}
