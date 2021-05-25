package pigman.mod.objects.blocks;

import java.util.List;
import java.util.Random;


import net.minecraft.block.BlockCrops;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import pigman.mod.entity.EntityPigman;
import pigman.mod.init.BlockInit;
import pigman.mod.init.ItemInit;

public class BlockRayshroom extends BlockCrops
{
	
	private static final AxisAlignedBB[] RAYSHROOM_AABB = new AxisAlignedBB[] {new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.125D,1.0D), new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.25D, 1.0D), new AxisAlignedBB(0.0D, 0.0D, 0.0D,1.0D, 0.375D, 1.0D), new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.5625D, 1.0D), new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.8125D, 1.0D), new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D), new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D), new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D)};


	
	public BlockRayshroom(String name)
	{
		super();
		setUnlocalizedName(name);
		setRegistryName(name);
		setSoundType(SoundType.PLANT);
		setHardness(1.0F);
		setHarvestLevel("hoe",0);
		this.lightValue=3;

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
		
        return soil.getBlock()==Blocks.SOUL_SAND;
	}
	
	@Override
	public void grow(World worldIn, BlockPos pos, IBlockState state)
	{
		List<Entity> listEntities =worldIn.getLoadedEntityList();
        for (Entity ent : listEntities) 
        {
            if (ent instanceof EntityPlayer) 
            {
            	int i=(Integer)state.getValue(this.getAgeProperty()).intValue();
            	ent.sendMessage(new TextComponentString(String.valueOf(i)));
            }
        }
        
		super.grow(worldIn, pos, state);
		state=worldIn.getBlockState(pos);
		
		
        for (Entity ent : listEntities) 
        {
            if (ent instanceof EntityPlayer) 
            {
            	int i=(Integer)state.getValue(this.getAgeProperty()).intValue();
            	ent.sendMessage(new TextComponentString(String.valueOf(i)));
            }
        }
        
		
		int i=(Integer)state.getValue(this.getAgeProperty()).intValue();
		
		if(i<2) 
			this.lightValue=3;
		else if(i<4)
			this.lightValue=6;
		else if(i<7)
			this.lightValue=9;
		else if(i==7)
			this.lightValue=12;
	
	}
	
	@Override
	public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand)
	{
		super.updateTick(worldIn, pos, state, rand);
		state=worldIn.getBlockState(pos);
		int i=(Integer)state.getValue(this.getAgeProperty()).intValue();
		
		if(i<2) 
			this.lightValue=3;
		else if(i<4)
			this.lightValue=6;
		else if(i<7)
			this.lightValue=9;
		else if(i==7)
			this.lightValue=12;
	}

	
}