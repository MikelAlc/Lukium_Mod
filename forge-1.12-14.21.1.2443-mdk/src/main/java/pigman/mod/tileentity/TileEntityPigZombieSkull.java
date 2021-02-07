package pigman.mod.tileentity; 



import net.minecraft.nbt.NBTTagCompound;



import net.minecraft.tileentity.TileEntitySkull;



import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import pigman.mod.libs.HeadDrop;

public class TileEntityPigZombieSkull extends TileEntitySkull
{
    
	private String entName="zombie_pigman";

	public HeadDrop getModel()
	{
		return HeadDrop.DEFAULT;
	}

	public String getSkullModel()
	{
		return entName;
	}

	public void setSkullModel(String entName)
	{
		this.entName = entName;
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbt)
	{
		nbt = super.writeToNBT(nbt);
		nbt.setString("SkullOwner", entName);
		return nbt;
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt)
	{
		super.readFromNBT(nbt);
		entName = nbt.getString("SkullOwner");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public AxisAlignedBB getRenderBoundingBox()
	{
		return new AxisAlignedBB(getPos().add(-1, -1, -1), getPos().add(2, 2, 2));
	}
}