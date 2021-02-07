package pigman.mod.objects.items.food;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class ItemNetherSoup extends ItemCustomFood
{

	public ItemNetherSoup()
	{
		super("nether_soup", 0, false);
		this.setMaxStackSize(1);
		this.setAlwaysEdible();
	}
	
    protected void onFoodEaten(ItemStack stack, World worldIn, EntityPlayer player)
    {
        if (!worldIn.isRemote)
        	player.addPotionEffect(new PotionEffect(MobEffects.REGENERATION, 100, 3));    
        
    }
    
    public ItemStack onItemUseFinish(ItemStack stack, World worldIn, EntityLivingBase entityLiving)
    {
        super.onItemUseFinish(stack, worldIn, entityLiving);
        return new ItemStack(Items.BOWL);
    }
    
    @Override
    public int getMaxItemUseDuration(ItemStack stack)
    {
    	return 16;
    }

}
