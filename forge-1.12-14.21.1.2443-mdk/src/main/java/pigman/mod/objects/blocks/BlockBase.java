package pigman.mod.objects.blocks;

import pigman.mod.Main;
import pigman.mod.init.BlockInit;
import pigman.mod.init.ItemInit;
import pigman.mod.util.interfaces.IHasModel;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.Item;
import net.minecraft.block.material.Material;


public class BlockBase extends Block implements IHasModel
{
	
	
	public BlockBase(String name, Material material)
	{
		super(material);
		setUnlocalizedName(name);
		setRegistryName(name);
		setCreativeTab(Main.LukiumTab);

		BlockInit.BLOCKS.add(this);
		ItemInit.ITEMS.add(new ItemBlock(this).setRegistryName(this.getRegistryName()));
	}
	
	public BlockBase(String name, Material material, boolean b)
	{
		super(material);
		setUnlocalizedName(name);
		setRegistryName(name);
		setCreativeTab(Main.LukiumTab);

		
	}

	@Override
	public void registerModels()
	{
		Main.proxy.registerItemRenderer(Item.getItemFromBlock(this),0,"Inventory");
	}
	
}

