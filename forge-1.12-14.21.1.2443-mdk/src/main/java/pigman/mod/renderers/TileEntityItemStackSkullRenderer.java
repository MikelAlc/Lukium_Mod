package pigman.mod.renderers;

import com.mojang.authlib.GameProfile;

import net.minecraft.client.renderer.tileentity.TileEntityItemStackRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;
import net.minecraftforge.common.util.Constants;


public class TileEntityItemStackSkullRenderer extends TileEntityItemStackRenderer {
	
	public static final String OWNER_TAG = "SkullOwner";
	public void func_192838_a(ItemStack stack, float p_192838_2_) {
		TileEntityPigZombieSkullRenderer.INSTANCE.renderHead(0.0F, -0.1F, 0.0F, stack.getMetadata(), 0f,getGameProfile(stack), -1);
	}
	
	public static GameProfile getGameProfile(ItemStack stack)
	{
		GameProfile profile = null;

		try
		{
			if(stack.hasTagCompound())
			{
				NBTTagCompound nbt = stack.getTagCompound();
				if(nbt.hasKey(OWNER_TAG, Constants.NBT.TAG_COMPOUND))
					profile = NBTUtil.readGameProfileFromNBT(nbt.getCompoundTag(OWNER_TAG));
				else if(nbt.hasKey(OWNER_TAG, Constants.NBT.TAG_STRING))
					profile = new GameProfile(null, nbt.getString(OWNER_TAG));
			}
		} catch(IllegalArgumentException e)
		{
			
		}

		return profile;
	}
}
