package pigman.mod.objects.blocks;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import pigman.mod.Main;
import pigman.mod.init.BlockInit;
import pigman.mod.init.ItemInit;
import pigman.mod.util.interfaces.IHasModel;

public class BlockLukium extends BlockBase implements IHasModel
{
	public BlockLukium(String name)
	{
		super(name,Material.IRON);
		setCreativeTab(Main.LukiumTab);
		setSoundType(SoundType.METAL);
		setHardness(6.0F);
		setResistance(30);
		setHarvestLevel("pickaxe",3);
		
		//BlockInit.BLOCKS.add(this);
		//ItemInit.ITEMS.add(new ItemBlock(this).setRegistryName(this.getRegistryName()));
	}
	
	@Override
	public void registerModels()
	{
		Main.proxy.registerItemRenderer(Item.getItemFromBlock(this),0,"Inventory");
	}
}
