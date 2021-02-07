package pigman.mod.init;

import java.util.List;
import java.util.ArrayList;


import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraftforge.common.util.EnumHelper;
import pigman.mod.objects.items.ItemBase;
import pigman.mod.objects.items.PigmanTusk;
import pigman.mod.objects.items.food.ItemMagmaBar;
import pigman.mod.objects.items.food.ItemNetherSoup;
import pigman.mod.objects.tools.ToolAxe;
import pigman.mod.objects.tools.ToolHoe;
import pigman.mod.objects.tools.ToolPickaxe;
import pigman.mod.objects.tools.ToolShovel;
import pigman.mod.objects.tools.ToolSword;
import pigman.mod.util.Reference;
import pigman.mod.objects.armor.ArmorBase;


public class ItemInit 
{
	public static final List<Item> ITEMS = new ArrayList<Item>();
	
	//Materials
	public static final ToolMaterial LUKIUM_TOOL= EnumHelper.addToolMaterial("lukium_tool", 3, 2031, 10.0F, 4.0F, 14);
	public static final ArmorMaterial LUKIUM_ARMOR= EnumHelper.addArmorMaterial("lukium_armor", Reference.MODID+":lukium", 42,  new int[]{3, 6, 8, 3}, 12, SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 3.0F);
	
	
	//Tools
	public static final Item LUKIUM_SWORD = new ToolSword("lukium_sword", LUKIUM_TOOL);
	public static final Item LUKIUM_PICKAXE = new ToolPickaxe("lukium_pickaxe", LUKIUM_TOOL);
	public static final Item LUKIUM_AXE = new ToolAxe("lukium_axe", LUKIUM_TOOL);
	public static final Item LUKIUM_SHOVEL = new ToolShovel("lukium_shovel", LUKIUM_TOOL);
	public static final Item LUKIUM_HOE = new ToolHoe("lukium_hoe", LUKIUM_TOOL);
	//Armor
	public static final Item LUKIUM_HELMET = new ArmorBase("lukium_helmet", LUKIUM_ARMOR, 1, EntityEquipmentSlot.HEAD);
	public static final Item LUKIUM_CHESTPLATE = new ArmorBase("lukium_chestplate", LUKIUM_ARMOR,1,EntityEquipmentSlot.CHEST);
	public static final Item LUKIUM_LEGGINGS = new ArmorBase("lukium_leggings", LUKIUM_ARMOR,2,EntityEquipmentSlot.LEGS);
	public static final Item LUKIUM_BOOTS = new ArmorBase("lukium_boots", LUKIUM_ARMOR,1,EntityEquipmentSlot.FEET);
	
	//Items
	public static final Item LUKIUM_INGOT = new ItemBase("lukium_ingot");
	public static final Item PIGMAN_TUSK= new PigmanTusk("pigman_tusk");
	
	
	//Food
	public static final Item MAGMA_BAR = new ItemMagmaBar();
	public static final Item NETHER_SOUP= new ItemNetherSoup();
	

}
