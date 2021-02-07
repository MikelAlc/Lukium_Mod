package pigman.mod.objects.items.food;


import net.minecraft.item.ItemFood;
import pigman.mod.Main;
import pigman.mod.init.ItemInit;
import pigman.mod.util.interfaces.IHasModel;

public class ItemCustomFood extends ItemFood implements IHasModel
{

	public ItemCustomFood(String name, int amount, boolean isWolfFood)
	{
		super(amount, isWolfFood);
		setUnlocalizedName(name);
		setRegistryName(name);
		setCreativeTab(Main.LukiumTab);
		
		ItemInit.ITEMS.add(this);
	}

	@Override
	public void registerModels()
	{
		Main.proxy.registerItemRenderer(this, 0, "inventory");	
	}
	
}
