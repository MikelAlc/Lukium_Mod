package pigman.mod.objects.blocks;

import net.minecraft.block.BlockCrops;
import net.minecraft.block.BlockSoulSand;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import pigman.mod.init.BlockInit;
import pigman.mod.init.ItemInit;

public class BlockRayshroom extends BlockCrops
{
	
	private static final AxisAlignedBB[] RAYSHROOM_AABB = new AxisAlignedBB[] {new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.125D,1.0D), new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.125D, 1.0D), new AxisAlignedBB(0.0D, 0.0D, 0.0D,1.0D, 0.375D, 1.0D), new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.375D, 1.0D), new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.5D, 1.0D), new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.5D, 1.0D), new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.5D, 1.0D), new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.625D, 1.0D)};

	public BlockRayshroom(String name)
	{
		super();
		setUnlocalizedName(name);
		setRegistryName(name);
		setSoundType(SoundType.PLANT);
		setHardness(0.0F);
		setHarvestLevel("hoe",0);

		BlockInit.BLOCKS.add(this);
		ItemInit.ITEMS.add(new ItemBlock(this).setRegistryName(this.getRegistryName()));
	}
	
	
	@Override
	protected Item getSeed()
	{
		return ItemInit.RAYSHROOM;
	}
	
	@Override
	protected Item getCrop()
	{
		return ItemInit.RAYSHROOM;
	}
	
	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
	{	
		return RAYSHROOM_AABB[(Integer)state.getValue(this.getAgeProperty()).intValue()];
	}
	
	@Override
	public boolean canBlockStay(World worldIn, BlockPos pos, IBlockState state)
	{	
		IBlockState soil = worldIn.getBlockState(pos.down());	
        return soil.getBlock() instanceof BlockSoulSand;
	}
	

	
	@Override
	public int getLightValue(IBlockState state, IBlockAccess world, BlockPos pos)
	{
		
		int age=state.getValue(this.getAgeProperty());
		
		if(age<2) 
			return 3;
		else if(age<4)
			return 6;
		else if(age<7)
			return 9;
		
		return 12;
	}

	
}