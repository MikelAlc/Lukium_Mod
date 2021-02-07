package pigman.mod.entity.model;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

/**
 * ModelZombie - Either Mojang or a mod author
 * Created using Tabula 7.0.1
 */
public class ModelPigman extends ModelBiped {
    /*
	public ModelRenderer bipedRightArm;
    public ModelRenderer bipedRightLeg;
    public ModelRenderer bipedBody;
    public ModelRenderer bipedLeftArm;
    public ModelRenderer bipedLeftLeg;
    public ModelRenderer bipedHead;
	*/
	
    public ModelPigman() {
    	
        this.textureWidth = 64;
        this.textureHeight = 64;
        this.bipedRightArm = new ModelRenderer(this, 40, 16);
        this.bipedRightArm.setRotationPoint(-5.0F, 2.0F, 0.0F);
        this.bipedRightArm.addBox(-3.0F, -2.0F, -2.0F, 4, 12, 4, 0.0F);
        this.bipedBody = new ModelRenderer(this, 16, 16);
        this.bipedBody.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.bipedBody.addBox(-4.0F, 0.0F, -2.0F, 8, 12, 4, 0.0F);
        this.bipedHead = new ModelRenderer(this, 32, 0);
        this.bipedHead.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.bipedHead.addBox(-4.0F, -8.0F, -4.0F, 8, 8, 8, 0.5F);
       
        this.bipedHeadwear = new ModelRenderer(this, 32, 0);
        this.bipedHeadwear.addBox(-4.0F, -8.0F, -4.0F, 8, 8, 8, 0.5F);
        this.bipedHeadwear.setRotationPoint(0.0F, 0.0F, 0.0F);
        
        this.bipedRightLeg = new ModelRenderer(this, 0, 16);
        this.bipedRightLeg.setRotationPoint(-1.9F, 12.0F, 0.1F);
        this.bipedRightLeg.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, 0.0F);
        this.bipedLeftArm = new ModelRenderer(this, 40, 16);
        this.bipedLeftArm.mirror = true;
        this.bipedLeftArm.setRotationPoint(5.0F, 2.0F, 0.0F);
        this.bipedLeftArm.addBox(-1.0F, -2.0F, -2.0F, 4, 12, 4, 0.0F);
        this.bipedLeftLeg = new ModelRenderer(this, 0, 16);
        this.bipedLeftLeg.mirror = true;
        this.bipedLeftLeg.setRotationPoint(1.9F, 12.0F, 0.1F);
        this.bipedLeftLeg.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, 0.0F);
    	
    	
    }
    
    
    
   
    
    public ModelPigman(float scale)
	{
    	 this.textureWidth = 64;
         this.textureHeight = 32;
         this.bipedRightArm = new ModelRenderer(this, 40, 16);
         this.bipedRightArm.setRotationPoint(-5.0F, 2.0F, 0.0F);
         this.bipedRightArm.addBox(-3.0F, -2.0F, -2.0F, 4, 12, 4, scale);
         this.bipedBody = new ModelRenderer(this, 16, 16);
         this.bipedBody.setRotationPoint(0.0F, 0.0F, 0.0F);
         this.bipedBody.addBox(-4.0F, 0.0F, -2.0F, 8, 12, 4, scale);
         this.bipedHead = new ModelRenderer(this, 0, 0);
         this.bipedHead.setRotationPoint(0.0F, 0.0F, 0.0F);
         this.bipedHead.addBox(-4.0F, -8.0F, -4.0F, 8, 8, 8, scale);
       
         this.bipedHeadwear = new ModelRenderer(this, 32, 0);
         this.bipedHeadwear.addBox(-4.0F, -8.0F, -4.0F, 8, 8, 8, scale + 0.5F);
         this.bipedHeadwear.setRotationPoint(0.0F, 0.0F, 0.0F);
       
         this.bipedRightLeg = new ModelRenderer(this, 0, 16);
         this.bipedRightLeg.setRotationPoint(-1.9F, 12.0F, 0.1F);
         this.bipedRightLeg.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, scale);
         this.bipedLeftArm = new ModelRenderer(this, 40, 16);
         this.bipedLeftArm.mirror = true;
         this.bipedLeftArm.setRotationPoint(5.0F, 2.0F, 0.0F);
         this.bipedLeftArm.addBox(-1.0F, -2.0F, -2.0F, 4, 12, 4, scale);
         this.bipedLeftLeg = new ModelRenderer(this, 0, 16);
         this.bipedLeftLeg.mirror = true;
         this.bipedLeftLeg.setRotationPoint(1.9F, 12.0F, 0.1F);
         this.bipedLeftLeg.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, scale);
	}





    /**
     * This is a helper function from Tabula to set the rotation of model parts
     */
    
    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
    
    @Override
    public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn)
    {
    	
    	this.bipedLeftArm.rotateAngleX=MathHelper.cos(limbSwing*0.6662F) * 1.4F * limbSwingAmount;
    	this.bipedRightLeg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
    	this.bipedRightArm.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 1.4F * limbSwingAmount;
    	this.bipedLeftLeg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 1.4F * limbSwingAmount;
   
    	this.bipedHead.rotateAngleY = netHeadYaw * 0.017453292F;
    	this.bipedHead.rotateAngleX = headPitch * 0.017453292F;
    	copyModelAngles(this.bipedHead, this.bipedHeadwear);
    	 //super.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor, entityIn);
    	
    	/* Not needed apparently
    	this.bipedLeftArm.rotateAngleX=MathHelper.cos(limbSwing*0.6662F) * 1.4F * limbSwingAmount;
    	this.bipedRightLeg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
    	this.bipedRightArm.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 1.4F * limbSwingAmount;
    	this.bipedLeftLeg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 1.4F * limbSwingAmount;
    	
    	this.bipedHead.rotateAngleY = netHeadYaw * 0.017453292F;
    	this.bipedHead.rotateAngleX = headPitch * 0.017453292F;
		*/
    }
    
    
}
