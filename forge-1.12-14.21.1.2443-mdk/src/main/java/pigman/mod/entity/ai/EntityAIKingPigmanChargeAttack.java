package pigman.mod.entity.ai;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextComponentString;
import pigman.mod.entity.EntityKingPigman;
import pigman.mod.util.Reference;
import pigman.mod.util.handlers.SoundsHandler;

public class EntityAIKingPigmanChargeAttack extends EntityAIAttackMelee 
{
	
	private int delayTick =100;
	private int chargeFailedCount=0;
	

	public EntityAIKingPigmanChargeAttack(EntityKingPigman creature, double speedIn) 
	{
		super(creature, speedIn, false);
		 this.setMutexBits(8);
	}
	
	
	public boolean shouldExecute()
    {
		
		
		
		EntityLivingBase entitylivingbase = this.attacker.getAttackTarget();

        if (entitylivingbase == null)
        {
            return false;
        }
        
        
        
        if(--delayTick>=0)
        {
        	if(delayTick==13) this.attacker.playSound(SoundsHandler.ENTITY_KING_PIGMAN_CHARGE,1 ,1); //sound angry; 
        	if(Reference.DEBUG) entitylivingbase.sendMessage(new TextComponentString("delay "+ delayTick));
			return false;
        }
        
        if(Reference.DEBUG) entitylivingbase.sendMessage(new TextComponentString("check"));
		
		if(10 <this.attacker.getDistanceSq(entitylivingbase.posX, entitylivingbase.getEntityBoundingBox().minY, entitylivingbase.posZ))
		{
				if(Reference.DEBUG) entitylivingbase.sendMessage(new TextComponentString("should execute"));
			
				return super.shouldExecute();
				
		}
		
		
		if(Reference.DEBUG) entitylivingbase.sendMessage(new TextComponentString("fail check" + chargeFailedCount));
		
		if(chargeFailedCount++==3)
		{
			delayTick+=20;
			chargeFailedCount=0;
		}
		return false;
				
    }
	
	@Override
	protected void checkAndPerformAttack(EntityLivingBase p_190102_1_, double p_190102_2_)
    {
        double d2 = this.getAttackReachSqr(p_190102_1_);
        
        if(p_190102_1_.posY-attacker.posY>=2)
        {
            double d0 = p_190102_1_.posX - this.attacker.posX;
            double d1 = p_190102_1_.posZ - this.attacker.posZ;
            float f = MathHelper.sqrt(d0 * d0 + d1 * d1);

            if ((double)f >= 1.0E-4D)
            {
                this.attacker.motionX += d0 / (double)f * 0.5D * 0.800000011920929D + this.attacker.motionX * 0.20000000298023224D;
                this.attacker.motionZ += d1 / (double)f * 0.5D * 0.800000011920929D + this.attacker.motionZ * 0.20000000298023224D;
            }

            this.attacker.motionY = (double)0.6F;
        }

        if (p_190102_2_ <= d2 && this.attackTick <= 0)
        {
     
            this.attackTick = 20;
            this.attacker.swingArm(EnumHand.MAIN_HAND);
            this.attacker.swingArm(EnumHand.OFF_HAND);
            this.attacker.attackEntityAsMob(p_190102_1_);
            if(Reference.DEBUG) p_190102_1_.sendMessage(new TextComponentString("knockback"));
            p_190102_1_.motionY += 0.75D;
            
            
            double hpPercentage=this.attacker.getHealth()/this.attacker.getMaxHealth();
            if(Reference.DEBUG) p_190102_1_.sendMessage(new TextComponentString(hpPercentage+""));
            
            if(hpPercentage>0.66) this.delayTick=100;
            else if(hpPercentage>0.33) this.delayTick=66;
            else this.delayTick=45;
        }
    }

}
