package pigman.mod.objects.tools;

import net.minecraft.item.ItemSpade;
import pigman.mod.Main;
import pigman.mod.init.ItemInit;
import pigman.mod.util.interfaces.IHasModel;

public class ToolShovel extends ItemSpade implements IHasModel
{

	public ToolShovel(String name, ToolMaterial material)
	{
		super(material);
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