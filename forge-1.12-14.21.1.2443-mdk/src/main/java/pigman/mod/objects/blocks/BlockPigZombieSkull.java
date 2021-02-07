package pigman.mod.objects.blocks;
import javax.annotation.Nullable;

import com.google.common.base.Predicate;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.BlockSkull;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.BlockWorldState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.block.state.pattern.BlockPattern;
import net.minecraft.block.state.pattern.BlockStateMatcher;
import net.minecraft.block.state.pattern.FactoryBlockPattern;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntitySkull;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import pigman.mod.Main;
import pigman.mod.entity.EntityKingPigman;
import pigman.mod.init.BlockInit;
import pigman.mod.init.ItemInit;
import pigman.mod.objects.items.ItemPigZombieSkull;
import pigman.mod.tileentity.TileEntityPigZombieSkull;
import pigman.mod.util.interfaces.IHasModel;

public class BlockPigZombieSkull extends BlockSkull implements IHasModel 
{
	 private static final Predicate<IBlockState> IS_SKULL = new Predicate<IBlockState>()
	    {
	        public boolean apply(@Nullable IBlockState p_apply_1_)
	        {
	            return p_apply_1_ != null && (p_apply_1_.getBlock() == BlockInit.PIG_ZOMBIE_SKULL );
	        }
	    };

			
	private BlockPattern kingPigmanPattern;
	
	public BlockPigZombieSkull()
	{
		
		setUnlocalizedName("pig_zombie_skull");
		setRegistryName("pig_zombie_skull");
		setCreativeTab(Main.LukiumTab);
		setHardness(1.0F);
		setSoundType(SoundType.STONE);

		BlockInit.BLOCKS.add(this);
		ItemInit.ITEMS.add(new ItemPigZombieSkull(this).setRegistryName(this.getRegistryName()));
	}
	
	/**
     * Returns a new instance of a block's tile entity class. Called on placing the block.
     */
    public TileEntity createNewTileEntity(World worldIn, int meta)
    {
        return new TileEntityPigZombieSkull();
    }
    
    /**
     * Called after the block is set in the Chunk data, but before the Tile Entity is set
     */
    public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state)
    {
        super.onBlockAdded(worldIn, pos, state);
        this.trySpawnKingPigman(worldIn, pos);
    }
    
    private void trySpawnKingPigman(World worldIn, BlockPos pos)
    {
        BlockPattern.PatternHelper blockpattern$patternhelper = this.getKingPigmanPattern().match(worldIn, pos);

        if (blockpattern$patternhelper != null)
        {
            for (int i = 0; i < this.getKingPigmanPattern().getThumbLength(); ++i)
            {
                BlockWorldState blockworldstate = blockpattern$patternhelper.translateOffset(0, i, 0);
                worldIn.setBlockState(blockworldstate.getPos(), Blocks.AIR.getDefaultState(), 2);
            }

            EntityKingPigman entitykingpigman = new EntityKingPigman(worldIn);
            BlockPos blockpos1 = blockpattern$patternhelper.translateOffset(0, 2, 0).getPos();
            entitykingpigman.setLocationAndAngles((double)blockpos1.getX() + 0.5D, (double)blockpos1.getY() + 0.05D, (double)blockpos1.getZ() + 0.5D, 0.0F, 0.0F);
            worldIn.spawnEntity(entitykingpigman);

            for (EntityPlayerMP entityplayermp : worldIn.getEntitiesWithinAABB(EntityPlayerMP.class, entitykingpigman.getEntityBoundingBox().grow(5.0D)))
            {
                CriteriaTriggers.SUMMONED_ENTITY.trigger(entityplayermp, entitykingpigman);
            }

            for (int l = 0; l < 120; ++l)
            {
                worldIn.spawnParticle(EnumParticleTypes.LAVA, (double)blockpos1.getX() + worldIn.rand.nextDouble(), (double)blockpos1.getY() + worldIn.rand.nextDouble() * 2.5D, (double)blockpos1.getZ() + worldIn.rand.nextDouble(), 0.0D, 0.0D, 0.0D);
            }

            for (int i1 = 0; i1 < this.getKingPigmanPattern().getThumbLength(); ++i1)
            {
                BlockWorldState blockworldstate2 = blockpattern$patternhelper.translateOffset(0, i1, 0);
                worldIn.notifyNeighborsRespectDebug(blockworldstate2.getPos(), Blocks.AIR, false);
            }
        }
       
    }

    protected BlockPattern getKingPigmanPattern()
    {
        if (this.kingPigmanPattern == null)
        {
            this.kingPigmanPattern = FactoryBlockPattern.start().aisle("^", "#", "#").where('^', BlockWorldState.hasState(IS_SKULL)).where('#', BlockWorldState.hasState(BlockStateMatcher.forBlock(BlockInit.ROYAL_SOUL_SAND))).build();
        }

        return this.kingPigmanPattern;
    }



	@Override
	public boolean canDispenserPlace(World world, BlockPos pos, ItemStack stack)
	{
		return false;
	}

	@Override
	public void registerModels()
	{
		Main.proxy.registerItemRenderer(ItemPigZombieSkull.getItemFromBlock(this),0,"Inventory");	
	}
	
	@Override
	public void getDrops(net.minecraft.util.NonNullList<ItemStack> drops, IBlockAccess worldIn, BlockPos pos, IBlockState state, int fortune)
	{
		 if (!((Boolean)state.getValue(NODROP)).booleanValue())
         {
             TileEntity tileentity = worldIn.getTileEntity(pos);

             if (tileentity instanceof TileEntitySkull)
             {
                 
                 ItemStack itemstack = new ItemStack(ItemInit.ITEMS.get(ItemInit.ITEMS.size()-1), 1);

                 
                 drops.add(itemstack);
             }
         }
	}
	
	@Override
    public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state)
    {
        return new ItemStack(ItemInit.ITEMS.get(ItemInit.ITEMS.size()-1), 1);
    }
}
