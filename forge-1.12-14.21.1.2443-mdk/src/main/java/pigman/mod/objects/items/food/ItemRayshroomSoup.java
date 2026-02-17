package pigman.mod.objects.items.food;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class ItemRayshroomSoup extends ItemCustomFood
{

	public ItemRayshroomSoup()
	{
		super("rayshroom_soup", 6, false);
		this.setMaxStackSize(1);
	}
	
    protected void onFoodEaten(ItemStack stack, World worldIn, EntityPlayer player)
    {
        if (!worldIn.isRemote)
        	player.addPotionEffect(new PotionEffect(MobEffects.GLOWING, 500));    
        
    }
    
    public ItemStack onItemUseFinish(ItemStack stack, World worldIn, EntityLivingBase entityLiving)
    {
        super.onItemUseFinish(stack, worldIn, entityLiving);
        return new ItemStack(Items.BOWL);
    }  

}

