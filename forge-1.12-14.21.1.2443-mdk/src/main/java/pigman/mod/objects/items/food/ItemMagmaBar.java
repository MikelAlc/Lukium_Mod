package pigman.mod.objects.items.food;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public class ItemMagmaBar extends ItemCustomFood
{
	public ItemMagmaBar()
	{
		super("magma_bar", 4, true);
	}
	
    protected void onFoodEaten(ItemStack stack, World worldIn, EntityPlayer player)
    {
        if (!worldIn.isRemote)
        	player.addPotionEffect(new PotionEffect(MobEffects.FIRE_RESISTANCE, 3000, 0));    
        
    }
    
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn)
    {
    	ActionResult<ItemStack> test= super.onItemRightClick(worldIn, playerIn, handIn);
    	if(test.getType()==EnumActionResult.SUCCESS)
    		playerIn.setFire(2);
    	return test;
    }
}
