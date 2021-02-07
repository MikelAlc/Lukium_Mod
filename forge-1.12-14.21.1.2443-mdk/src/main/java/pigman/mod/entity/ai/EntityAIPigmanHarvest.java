package pigman.mod.entity.ai;

 
import net.minecraft.block.Block;

import net.minecraft.block.BlockNetherWart;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;

import net.minecraft.entity.ai.EntityAIMoveToBlock;
import net.minecraft.init.Blocks;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import pigman.mod.entity.EntityPigman;

public class EntityAIPigmanHarvest  extends EntityAIMoveToBlock
{
    private final EntityPigman pigman;
    private boolean hasFarmItem;
    private boolean wantsToReapStuff;
    /** 0 => harvest, 1 => replant, -1 => none */
    private int currentTask;

    public EntityAIPigmanHarvest(EntityPigman villagerIn, double speedIn)
    {
        super(villagerIn, speedIn, 16);
        this.pigman = villagerIn;
        
    }
    
    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean shouldExecute()
    {
        if (this.runDelay <= 0)
        {
            if (!this.pigman.world.getGameRules().getBoolean("mobGriefing"))
            {
                return false;
            }

            this.currentTask = -1;
            //Check
            this.hasFarmItem =true;
            this.wantsToReapStuff = true;
        }

        return super.shouldExecute();
    }
    
    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    public boolean shouldContinueExecuting()
    {
        return this.currentTask >= 0 && super.shouldContinueExecuting();
    }
    
    /**
     * Keep ticking a continuous task that has already been started
     */
    public void updateTask()
    {
        super.updateTask();
        this.pigman.getLookHelper().setLookPosition((double)this.destinationBlock.getX() + 0.5D, (double)(this.destinationBlock.getY() + 1), (double)this.destinationBlock.getZ() + 0.5D, 10.0F, (float)this.pigman.getVerticalFaceSpeed());

        if (this.getIsAboveDestination())
        {
            World world = this.pigman.world;
            BlockPos blockpos = this.destinationBlock.up();
            IBlockState iblockstate = world.getBlockState(blockpos);
            Block block = iblockstate.getBlock();

            if (this.currentTask == 0 && block instanceof BlockNetherWart && ((BlockNetherWart)block).getMetaFromState(iblockstate)>=3)
            {
                world.destroyBlock(blockpos, false);
            }
            else if (this.currentTask == 1 && iblockstate.getMaterial() == Material.AIR)
            {             
            	world.setBlockState(blockpos, Blocks.NETHER_WART.getDefaultState(), 3);       
            }

            this.currentTask = -1;
            this.runDelay = 10;
        }
    }
       
      

	
	protected boolean shouldMoveTo(World worldIn, BlockPos pos) 
	{
		Block block = worldIn.getBlockState(pos).getBlock();

        if (block == Blocks.SOUL_SAND)
        {
            pos = pos.up();
            IBlockState iblockstate = worldIn.getBlockState(pos);
            block = iblockstate.getBlock();

            if (block instanceof BlockNetherWart && ((BlockNetherWart)block).getMetaFromState(iblockstate)>=3 && this.wantsToReapStuff && (this.currentTask == 0 || this.currentTask < 0))
            {
                this.currentTask = 0;
                return true;
            }

            if (iblockstate.getMaterial() == Material.AIR && this.hasFarmItem && (this.currentTask == 1 || this.currentTask < 0))
            {
                this.currentTask = 1;
                return true;
            }
        }

		return false;
	}
    
    
}


