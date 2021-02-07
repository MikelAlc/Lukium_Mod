package pigman.mod.objects.armor;


import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import pigman.mod.Main;
import pigman.mod.init.ItemInit;
import pigman.mod.util.interfaces.IHasModel;

public class ArmorBase extends ItemArmor implements IHasModel
{

	public ArmorBase(String name, ArmorMaterial materialIn, int renderIndexIn, EntityEquipmentSlot equipmentSlotIn)
	{
		super(materialIn,renderIndexIn,equipmentSlotIn);
		setUnlocalizedName(name);
		setRegistryName(name);
		setCreativeTab(Main.LukiumTab);
		
		ItemInit.ITEMS.add(this);
	}
	
	@Override
	public void registerModels() 
	{
		Main.proxy.registerItemRenderer(this, 0, "inventory");
	}
}
