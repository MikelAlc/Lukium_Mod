package pigman.mod.objects.blocks;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.block.BlockSoulSand;
import net.minecraft.block.SoundType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import pigman.mod.Main;
import pigman.mod.init.BlockInit;
import pigman.mod.init.ItemInit;
import pigman.mod.util.interfaces.IHasModel;

public class BlockRoyalSoulSand extends BlockSoulSand implements IHasModel
{
	public BlockRoyalSoulSand(String name)
	{
		super();
		setUnlocalizedName(name);
		setRegistryName(name);
		setCreativeTab(Main.LukiumTab);
		setSoundType(SoundType.SAND);
		setHardness(1.0F);
		setHarvestLevel("shovel",0);

		BlockInit.BLOCKS.add(this);
		ItemInit.ITEMS.add(new ItemBlock(this).setRegistryName(this.getRegistryName()));
	}
	
	@Override
	public void registerModels()
	{
		Main.proxy.registerItemRenderer(Item.getItemFromBlock(this),0,"Inventory");
	}
	
	 public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity entityIn)
    {
        entityIn.motionX *= 0.4D;
        entityIn.motionZ *= 0.4D;
    }
}
