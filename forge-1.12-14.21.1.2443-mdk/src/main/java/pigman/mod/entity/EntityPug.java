package pigman.mod.entity;

import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;

public class EntityPug extends EntityWolf {
    public EntityPug(World worldIn){
        super(worldIn);
    }

    @Override
    public EntityWolf createChild(EntityAgeable ageable){
        return new EntityPug(world);
    }

    @Override
    protected SoundEvent getAmbientSound()
    {
        return SoundEvents.ENTITY_WOLF_AMBIENT; 
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource p_184601_1_)
    {
        return SoundEvents.ENTITY_WOLF_HURT;
    }

    @Override
    protected SoundEvent getDeathSound()
    {
        return SoundEvents.ENTITY_WOLF_DEATH;
    }
}
