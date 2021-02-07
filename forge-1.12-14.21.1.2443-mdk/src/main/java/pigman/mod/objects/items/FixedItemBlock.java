package pigman.mod.objects.items;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;


public abstract class FixedItemBlock extends ItemBlock {

	public FixedItemBlock(Block block) {
		super(block);
	}

	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		IBlockState state = world.getBlockState(pos);
		Block block = state.getBlock();
		ItemStack stack = player.getHeldItem(hand);

		if (!block.isReplaceable(world, pos))
			pos = pos.offset(facing);

		if (stack.getCount() == 0)
			return EnumActionResult.FAIL;
		else if (!player.canPlayerEdit(pos, facing, stack))
			return EnumActionResult.FAIL;
		else if (canBlockBePlaced(world, this.block, pos, false, facing, null, stack))
		{
			int meta = getMetadata(stack.getMetadata());
			IBlockState newState = this.block.getStateForPlacement(world, pos, facing, hitX, hitY, hitZ, meta, player, hand);

			if (placeBlockAt(stack, player, world, pos, facing, hitX, hitY, hitZ, newState)) 
			{
				SoundType soundtype = this.block.getSoundType(state, world, pos, player);
				world.playSound(player, pos, soundtype.getPlaceSound(), SoundCategory.BLOCKS, (soundtype.getVolume() + 1.0F) / 2.0F, soundtype.getPitch() * 0.8F);
				stack.shrink(1);
			}
			
			
			

			return EnumActionResult.SUCCESS;
		} 
		else
			return EnumActionResult.FAIL;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean canPlaceBlockOnSide(World world, BlockPos pos, EnumFacing side, EntityPlayer player, ItemStack stack) {
		Block block = world.getBlockState(pos).getBlock();

		if (block == Blocks.SNOW_LAYER && block.isReplaceable(world, pos))
			side = EnumFacing.UP;
		else if (!block.isReplaceable(world, pos))
			pos = pos.offset(side);

		return canBlockBePlaced(world, block, pos, false, side, null, stack);
	}

	public boolean canBlockBePlaced(World world, Block blockToPlace, BlockPos pos, boolean useBounds, EnumFacing side, Entity entity, ItemStack stack) {
		IBlockState state = world.getBlockState(pos);
		Block block = state.getBlock();
		AxisAlignedBB bb = useBounds ? null : state.getBoundingBox(world, pos);
		if (bb != null && !world.checkNoEntityCollision(bb, entity))
			return false;
		return block.isReplaceable(world, pos);
	}

}