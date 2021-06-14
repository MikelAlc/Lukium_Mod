package pigman.mod.objects.items.food;

import net.minecraft.block.BlockSoulSand;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.MobEffects;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.common.IPlantable;
import pigman.mod.init.BlockInit;
import pigman.mod.util.interfaces.IHasModel;

public class ItemRayshroom extends ItemCustomFood implements IHasModel,IPlantable
{

	public ItemRayshroom()
	{
		super("rayshrom",1,false);
		
	
	}

	@Override
	public EnumPlantType getPlantType(IBlockAccess world, BlockPos pos)
	{
		return EnumPlantType.Crop;
	}
	
	@Override
	public EnumActionResult onItemUse(EntityPlayer player,World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
	{
		ItemStack stack = player.getHeldItem(hand);
		IBlockState state= worldIn.getBlockState(pos);
		if(facing==EnumFacing.UP && player.canPlayerEdit(pos.offset(facing), facing, stack) && state.getBlock() instanceof BlockSoulSand && worldIn.isAirBlock(pos.up()))
		{
			worldIn.setBlockState(pos.up(),BlockInit.RAYSHROOM.getDefaultState());
			stack.shrink(1);
			return EnumActionResult.SUCCESS;
		}
		else return EnumActionResult.FAIL;
		
	}
	
	protected void onFoodEaten(ItemStack stack, World worldIn, EntityPlayer player)
    {
        if (!worldIn.isRemote)
        	player.addPotionEffect(new PotionEffect(MobEffects.GLOWING, 100));    
        
    }
	
	@Override
	public IBlockState getPlant(IBlockAccess world, BlockPos pos)
	{
		return BlockInit.RAYSHROOM.getDefaultState();
	}
	

}
