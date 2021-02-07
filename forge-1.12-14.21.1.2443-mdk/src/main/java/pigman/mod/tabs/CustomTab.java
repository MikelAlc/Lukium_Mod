package pigman.mod.tabs;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import pigman.mod.init.ItemInit;

public class CustomTab extends CreativeTabs
{

	public CustomTab(String label)
	{
		super(label);
	}

	@Override
	public ItemStack getTabIconItem()
	{	
		return new ItemStack((ItemInit.LUKIUM_INGOT));
	}

}
