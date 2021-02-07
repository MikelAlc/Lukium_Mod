package pigman.mod.util.handlers;


import net.minecraft.block.Block;
import net.minecraft.block.state.BlockWorldState;
import net.minecraft.block.state.pattern.BlockPattern;
import net.minecraft.block.state.pattern.BlockStateMatcher;
import net.minecraft.block.state.pattern.FactoryBlockPattern;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntitySkull;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import net.minecraftforge.event.world.BlockEvent.PlaceEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import pigman.mod.init.BlockInit;
import pigman.mod.tileentity.TileEntityPigZombieSkull;


public class EventHandler
{
	
	private BlockPattern kingPigmanPattern;
	
	@SubscribeEvent
	public void onSkullPlacedEvent(PlaceEvent event)
	{
		if(event.getState().getBlock() != BlockInit.PIG_ZOMBIE_SKULL)
		{
			return;
		}

		World world = event.getWorld();
		BlockPos pos = event.getPos();
		TileEntity tileEntity = world.getTileEntity(pos);
		if(tileEntity instanceof TileEntityPigZombieSkull)
		{
			TileEntitySkull tileSkull = (TileEntitySkull) tileEntity;
			if(tileSkull.getSkullType() == 3 && pos.getY() >= 2 && world.getDifficulty() != EnumDifficulty.PEACEFUL && !world.isRemote)
			{
				if(kingPigmanPattern == null)
					kingPigmanPattern = FactoryBlockPattern.start().aisle(new String[] { "^", "#", "#" }).where('#', BlockWorldState.hasState(BlockStateMatcher.forBlock(BlockInit.ROYAL_SOUL_SAND))).where('^', BlockWorldState.hasState(BlockStateMatcher.forBlock(BlockInit.PIG_ZOMBIE_SKULL))).build();
				BlockPattern.PatternHelper patternHelper = kingPigmanPattern.match(world, pos);
				if(patternHelper != null)
				{
				
					for(int i = 0; i < 3; i++)
					{
						BlockWorldState blockWorldState = patternHelper.translateOffset(0, -i, 0);
						world.setBlockState(blockWorldState.getPos(), Blocks.AIR.getDefaultState(), 2);
					}
					//world.setBlockState(pos.add(0, -1, 0), ModBlocks.player.getDefaultState());
					world.playEvent(null, 2001, pos.add(0, -1, 0), Block.getIdFromBlock(Blocks.SOUL_SAND) + (0 << 12));
					//world.setBlockState(pos.add(0, -2, 0), ModBlocks.empty.getDefaultState());
					world.playEvent(null, 2001, pos.add(0, -2, 0), Block.getIdFromBlock(Blocks.SOUL_SAND) + (0 << 12));
									
					//tile.setSkullRotation(MathHelper.floor(event.getPlayer().rotationYaw * 16.0F / 360.0F + 0.5D) & 15);
					world.notifyNeighborsOfStateChange(pos, Blocks.AIR, true);
				
					
					 EntityPigZombie entitypigzombie = new EntityPigZombie(world);
			         entitypigzombie.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack(Items.GOLDEN_SWORD));
			         entitypigzombie.setPosition(pos.getX(), pos.getY()-2, pos.getZ());
			 
			         world.spawnEntity(entitypigzombie);
				}
			}
		}
	}
}
