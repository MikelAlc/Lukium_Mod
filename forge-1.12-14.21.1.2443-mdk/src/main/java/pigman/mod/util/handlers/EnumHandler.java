package pigman.mod.util.handlers;

import net.minecraft.util.IStringSerializable;

public class EnumHandler
{
	public static enum EnumType implements IStringSerializable
	{
		LUKIUM(0,"lukium"),
		GOLD(1,"gold");
		
		private static final EnumType[] META_LOOKUP= new EnumType[values().length];
		private final int meta;
		private final String name, unlocalizedName;
		
		private EnumType(int meta, String name)
		{
			this(meta, name, name);
		}
		
		private EnumType(int meta, String name, String unlocalizeName)
		{
			this.meta=meta;
			this.name=name;
			this.unlocalizedName=unlocalizeName;
		}
		
		
		@Override
		public String getName()
		{
			return this.name;
		}

		public int getMeta()
		{
			return this.meta;
		}

		public String getUnlocalizedName()
		{
			return this.unlocalizedName;
		}
		
		public static EnumType byMetaData(int meta)
		{
			return META_LOOKUP[meta];
		}
		
		static
		{
			for(EnumType enumtype : values())
			{
				META_LOOKUP[enumtype.getMeta()]=enumtype;
			}
		}
		
	}
}
