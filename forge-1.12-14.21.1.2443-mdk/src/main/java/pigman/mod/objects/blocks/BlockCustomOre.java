package pigman.mod.objects.blocks;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import pigman.mod.Main;
import pigman.mod.init.BlockInit;
import pigman.mod.init.ItemInit;
import pigman.mod.objects.blocks.item.ItemBlockVariants;
import pigman.mod.util.handlers.EnumHandler;
import pigman.mod.util.interfaces.IHasModel;
import pigman.mod.util.interfaces.IMetaName;


public class BlockCustomOre extends BlockBase implements IMetaName,IHasModel
{
	
	private String dimension;
	public static final PropertyEnum<EnumHandler.EnumType> VARIANT = PropertyEnum.<EnumHandler.EnumType>create("variant",EnumHandler.EnumType.class);
	
	public BlockCustomOre(String name, Material material, Float hardness, int resistance, int hrvstLvl,String dimension)
	{
		super(name,material,true);
		setSoundType(SoundType.STONE);
		setHardness(hardness);
		setResistance(resistance);
		setHarvestLevel("pickaxe",hrvstLvl);
		setCreativeTab(Main.LukiumTab);
		setDefaultState(this.blockState.getBaseState().withProperty(VARIANT, EnumHandler.EnumType.LUKIUM));
		
		this.dimension=dimension;
		
		BlockInit.BLOCKS.add(this);
		ItemInit.ITEMS.add(new ItemBlockVariants(this).setRegistryName(this.getRegistryName()));
	}

	
	@Override
	public int damageDropped(IBlockState state)
	{
		return ((EnumHandler.EnumType)state.getValue(VARIANT)).getMeta();
	}
	
	@Override
	public int getMetaFromState(IBlockState state)
	{
		return ((EnumHandler.EnumType)state.getValue(VARIANT)).getMeta();
	}
	
	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		return this.getDefaultState().withProperty(VARIANT, EnumHandler.EnumType.byMetaData(meta));
	}
	
	@Override 
	public ItemStack getPickBlock(IBlockState state,RayTraceResult target,World world, BlockPos pos, EntityPlayer player)
	{
		return new ItemStack(Item.getItemFromBlock(this), 1, getMetaFromState(world.getBlockState(pos)));
	}
	
	@Override 
	public void getSubBlocks(CreativeTabs ItemIn, NonNullList<ItemStack> items)
	{
		for (EnumHandler.EnumType variant:EnumHandler.EnumType.values())
		{
			items.add(new ItemStack(this,1,variant.getMeta()));
		}
	}
	
	@Override
	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this,new IProperty[] {VARIANT});
	}
	
	@Override
	public void registerModels()
	{
		for(int i=0; i< EnumHandler.EnumType.values().length;i++)
		{
			Main.proxy.registerVariantRenderer(Item.getItemFromBlock(this), i, "ore_"+this.dimension+"_"+ EnumHandler.EnumType.values()[i].getName(), "inventory");
		}
	}


	@Override
	public String getSpecialName(ItemStack stack)
	{
	
		return EnumHandler.EnumType.values()[stack.getItemDamage()].getName();
	}
}
