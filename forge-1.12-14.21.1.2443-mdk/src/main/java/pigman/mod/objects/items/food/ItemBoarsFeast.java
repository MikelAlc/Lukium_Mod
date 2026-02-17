package pigman.mod.objects.items.food;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;

import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public class ItemBoarsFeast extends ItemCustomFood
{
	public ItemBoarsFeast()
	{
		super("boars_feast", 12, false);
		this.setMaxStackSize(1);
		this.setAlwaysEdible();
	}
	
    protected void onFoodEaten(ItemStack stack, World worldIn, EntityPlayer player)
    {
        if (!worldIn.isRemote)
        {
        	player.addPotionEffect(new PotionEffect(MobEffects.FIRE_RESISTANCE, 3000, 0));    
        	player.addPotionEffect(new PotionEffect(MobEffects.REGENERATION, 100, 3));
        	player.addPotionEffect(new PotionEffect(MobEffects.GLOWING, 500));
        }
    }
    
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn)
    {
    	ActionResult<ItemStack> test= super.onItemRightClick(worldIn, playerIn, handIn);
    	if(test.getType()==EnumActionResult.SUCCESS)
    		playerIn.setFire(2);
    	return test;
    }
    
    public ItemStack onItemUseFinish(ItemStack stack, World worldIn, EntityLivingBase entityLiving)
    {
        super.onItemUseFinish(stack, worldIn, entityLiving);
        return new ItemStack(Items.BOWL,2);
    }
    
    @Override
    public boolean hasEffect(ItemStack stack)
    {
    	
    	return true;
    }
}
