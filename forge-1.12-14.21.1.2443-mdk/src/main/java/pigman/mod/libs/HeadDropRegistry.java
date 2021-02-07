package pigman.mod.libs;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import pigman.mod.libs.HeadDropHelper;
import pigman.mod.libs.VanillaHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class HeadDropRegistry
{
	public static final List<HeadDropHelper> helpers = new LinkedList<HeadDropHelper>();

	public static void register(HeadDropHelper helper)
	{
		if(helper instanceof VanillaHelper)
			helpers.add(0, helper);
		else
			helpers.add(helper);
	}

	public static ItemStack getHead(Entity entity)
	{
		ResourceLocation entName = EntityList.getKey(entity);
		return getHead(entName.getResourceDomain(), entName.getResourcePath(), entity);
	}

	public static ItemStack getHead(String modID, String entityName, Entity entity)
	{
		for(HeadDropHelper helper : helpers)
			if(helper.getModID().equals(modID) && helper.isEnabled())
				return helper.getHeadForEntity(entityName, entity);
		return ItemStack.EMPTY;
	}

	public static ItemStack getHead(String modID, String entityName)
	{
		for(HeadDropHelper helper : helpers)
			if(helper.getModID().equals(modID) && helper.isEnabled())
				return helper.getHeadForEntity(entityName, null);
		return ItemStack.EMPTY;
	}

	public static HeadDrop getHeadDrop(String modID, String entity)
	{
		for(HeadDropHelper helper : helpers)
			if(helper.getModID().equals(modID) && helper.isEnabled())
				return helper.getHeadDropForEntity(entity);
		return HeadDrop.DEFAULT;
	}

	public static void initModels()
	{
		for(HeadDropHelper helper : helpers)
			if(helper.isEnabled())
				helper.initModels();
	}

	public static List<ItemStack> getAllStacks()
	{
		List<ItemStack> heads = new ArrayList<ItemStack>();
		for(HeadDropHelper helper : helpers)
			if(helper.isEnabled())
				heads.addAll(helper.getItemStacks());

		return heads;
	}
}
