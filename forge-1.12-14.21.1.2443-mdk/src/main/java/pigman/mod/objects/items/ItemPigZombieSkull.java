package pigman.mod.objects.items;


import net.minecraft.block.Block;
import net.minecraft.block.BlockSkull;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import pigman.mod.Main;
import pigman.mod.tileentity.TileEntityPigZombieSkull;
import pigman.mod.util.Reference;
import pigman.mod.util.interfaces.IHasModel;

public class ItemPigZombieSkull extends FixedItemBlock implements IHasModel
{

	public ItemPigZombieSkull(Block block)
	{
		super(block);
		this.setMaxDamage(0);
		setHasSubtypes(true);
		super.setCreativeTab(Main.LukiumTab);
	}

	

	@Override
	public boolean placeBlockAt(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ, IBlockState newState)
	{
		if(side == EnumFacing.DOWN)
			return false;
		else
		{
			BlockPos clickedPos = pos.offset(side.getOpposite());
			IBlockState clickedState = world.getBlockState(clickedPos);
			if(!clickedState.getMaterial().isSolid() || !world.isSideSolid(clickedPos, side, true))
				return false;

			if(!player.canPlayerEdit(pos, side, stack))
				return false;
			else if(!block.canPlaceBlockAt(world, pos))
				return false;
			else
			{
				if(!world.isRemote)
				{
					if(!block.canPlaceBlockOnSide(world, pos, side))
						return false;
					world.setBlockState(pos, block.getDefaultState().withProperty(BlockSkull.FACING, side), 3);

					TileEntity tile = world.getTileEntity(pos);
					populateTile(stack, side, player, tile);
				}

				return true;
			}
		}
	}

	TileEntityPigZombieSkull tileSkull;
	
	protected void populateTile(ItemStack stack, EnumFacing side, EntityPlayer player, TileEntity tile)
	{
		if(tile instanceof TileEntityPigZombieSkull)
		{
			tileSkull = (TileEntityPigZombieSkull) tile;
			tileSkull.setType(stack.getMetadata());
			

			int rotation = 0;
			if(side == EnumFacing.UP)
				rotation = MathHelper.floor(player.rotationYaw * 16.0F / 360.0F + 0.5D) & 15;
			tileSkull.setSkullRotation(rotation);
			
		}
	}


	@Override
	public boolean isValidArmor(ItemStack stack, EntityEquipmentSlot armorType, Entity entity)
	{
		return armorType == EntityEquipmentSlot.HEAD;
	}

	
	
	@Override
	public void registerModels() 
	{
		Main.proxy.registerItemRenderer(this, 0, "inventory");
	}
	
	public String getTranslationKey(ItemStack stack)
	{
		return "item."+ getUnlocalisedName(getModelName(stack));
	}
	
	public static String getUnlocalisedName(String name) {
		return Reference.MODID + "." + name;
	}
	

	public static String getModelName(ItemStack stack)
	{
		return stack.hasTagCompound() ? stack.getTagCompound().getString("SkullModel") : "minecraft:zombie_pigman";
	}
	
	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) 
	{
		
		
		return super.onItemUse(player, world,pos,hand, facing,hitX, hitY,hitZ);
	}
	

	
}
	
