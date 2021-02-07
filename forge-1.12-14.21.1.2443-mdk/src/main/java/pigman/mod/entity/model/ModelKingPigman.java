package pigman.mod.entity.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.client.renderer.GlStateManager;


/**
 * ModelSlime - Either Mojang or a mod author
 * Created using Tabula 7.0.1
 */
public class ModelKingPigman extends ModelBase {
    public double[] modelScale = new double[] { 0.75D, 0.75D, 0.75D };
    public ModelRenderer KingPigmanRightArm;
    public ModelRenderer KingPigmanRightLeg;
    public ModelRenderer KingPigmanBody;
    public ModelRenderer KingPigmanLeftArm;
    public ModelRenderer KingPigmanLeftLeg;
    public ModelRenderer KingPigmanHead;
    public ModelRenderer KingPigmanSnout;
    public ModelRenderer KingPigmanRightTusk;
    public ModelRenderer KingPigmanLeftTusk;

    public ModelKingPigman() {
        this.textureWidth = 64;
        this.textureHeight = 64;
        this.KingPigmanLeftLeg = new ModelRenderer(this, 0, 16);
        this.KingPigmanLeftLeg.mirror = true;
        this.KingPigmanLeftLeg.setRotationPoint(2.8F, 6.0F, 0.1F);
        this.KingPigmanLeftLeg.addBox(-2.0F, 0.0F, -2.0F, 6, 12, 6, 0.0F);
        this.KingPigmanRightArm = new ModelRenderer(this, 40, 16);
        this.KingPigmanRightArm.setRotationPoint(-6.0F, -6.0F, -4.0F);
        this.KingPigmanRightArm.addBox(-3.0F, -2.0F, -2.0F, 4, 12, 4, 0.0F);
        this.KingPigmanRightLeg = new ModelRenderer(this, 0, 16);
        this.KingPigmanRightLeg.setRotationPoint(-3.7F, 6.0F, 0.1F);
        this.KingPigmanRightLeg.addBox(-2.0F, 0.0F, -2.0F, 6, 12, 6, 0.0F);
        this.KingPigmanLeftArm = new ModelRenderer(this, 40, 16);
        this.KingPigmanLeftArm.mirror = true;
        this.KingPigmanLeftArm.setRotationPoint(8.0F, -6.0F, -4.0F);
        this.KingPigmanLeftArm.addBox(-1.0F, -2.0F, -2.0F, 4, 12, 4, 0.0F);
         this.KingPigmanHead = new ModelRenderer(this, 32, 0);
        this.KingPigmanHead.setRotationPoint(0.6F, -10.0F, -5.4F);
        this.KingPigmanHead.addBox(-4.0F, -8.0F, -4.0F, 8, 8, 8, 0.5F);
        this.KingPigmanBody = new ModelRenderer(this, 0, 38);
        this.KingPigmanBody.setRotationPoint(-1.3F, -9.0F, -7.0F);
        this.KingPigmanBody.addBox(-4.0F, 0.0F, -2.0F, 12, 18, 8, 0.0F);
        this.setRotateAngle(KingPigmanBody, 0.5094616086571448F, 0.0F, 0.0F);
       
        
        
        this.KingPigmanSnout = new ModelRenderer(this, 0, 0);
        this.KingPigmanSnout.setRotationPoint(-1.6F, -14.0F, -12.5F);
        this.KingPigmanSnout.addBox(-0.4F, 11.2F, 7.0F, 4, 2, 3, 0.2F);
        this.KingPigmanHead.addChild(this.KingPigmanSnout);
        this.KingPigmanRightTusk = new ModelRenderer(this, 0, 6);
        this.KingPigmanRightTusk.setRotationPoint(-2.5F, -14.9F, -13.0F);
        this.KingPigmanRightTusk.addBox(-0.8F, 5.0F, 12.5F, 1, 1, 5, 0.0F);
        this.setRotateAngle(KingPigmanRightTusk, -0.6373942428283291F, 0.0F, 0.0F);
        this.KingPigmanLeftTusk = new ModelRenderer(this, 13, 6);
        //this.KingPigmanLeftTusk.mirror = true;
        this.KingPigmanLeftTusk.setRotationPoint(3.2F, -14.9F, -13.0F);
        this.KingPigmanLeftTusk.addBox(-0.85F, 5.0F, 12.5F, 1, 1, 5, 0.0F);
        this.setRotateAngle(KingPigmanLeftTusk, -0.6373942428283291F, 0.0F, 0.0F);
       
        
        this.KingPigmanHead.addChild(this.KingPigmanSnout);
        this.KingPigmanHead.addChild(this.KingPigmanLeftTusk);
        this.KingPigmanHead.addChild(this.KingPigmanRightTusk);
       
       
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) { 
        GlStateManager.pushMatrix();
        GlStateManager.scale(1D / modelScale[0], 1D / modelScale[1], 1D / modelScale[2]);
        this.KingPigmanLeftLeg.render(f5);
        GlStateManager.pushMatrix();
        GlStateManager.translate(this.KingPigmanRightArm.offsetX, this.KingPigmanRightArm.offsetY, this.KingPigmanRightArm.offsetZ);
        GlStateManager.translate(this.KingPigmanRightArm.rotationPointX * f5, this.KingPigmanRightArm.rotationPointY * f5, this.KingPigmanRightArm.rotationPointZ * f5);
        GlStateManager.scale(1.5D, 1.5D, 2.0D);
        GlStateManager.translate(-this.KingPigmanRightArm.offsetX, -this.KingPigmanRightArm.offsetY, -this.KingPigmanRightArm.offsetZ);
        GlStateManager.translate(-this.KingPigmanRightArm.rotationPointX * f5, -this.KingPigmanRightArm.rotationPointY * f5, -this.KingPigmanRightArm.rotationPointZ * f5);
        this.KingPigmanRightArm.render(f5);
        GlStateManager.popMatrix();
        this.KingPigmanRightLeg.render(f5);
        GlStateManager.pushMatrix();
        GlStateManager.translate(this.KingPigmanSnout.offsetX, this.KingPigmanSnout.offsetY, this.KingPigmanSnout.offsetZ);
        GlStateManager.translate(this.KingPigmanSnout.rotationPointX * f5, this.KingPigmanSnout.rotationPointY * f5, this.KingPigmanSnout.rotationPointZ * f5);
        GlStateManager.scale(1.2D, 1.2D, 1.2D);
        GlStateManager.translate(-this.KingPigmanSnout.offsetX, -this.KingPigmanSnout.offsetY, -this.KingPigmanSnout.offsetZ);
        GlStateManager.translate(-this.KingPigmanSnout.rotationPointX * f5, -this.KingPigmanSnout.rotationPointY * f5, -this.KingPigmanSnout.rotationPointZ * f5);
        //this.KingPigmanSnout.render(f5);
        GlStateManager.popMatrix();
        //this.KingPigmanRightTusk.render(f5);
        //this.KingPigmanLeftTusk.render(f5);
        GlStateManager.pushMatrix();
        GlStateManager.translate(this.KingPigmanHead.offsetX, this.KingPigmanHead.offsetY, this.KingPigmanHead.offsetZ);
        GlStateManager.translate(this.KingPigmanHead.rotationPointX * f5, this.KingPigmanHead.rotationPointY * f5, this.KingPigmanHead.rotationPointZ * f5);
        GlStateManager.scale(1.2D, 1.2D, 1.2D);
        GlStateManager.translate(-this.KingPigmanHead.offsetX, -this.KingPigmanHead.offsetY, -this.KingPigmanHead.offsetZ);
        GlStateManager.translate(-this.KingPigmanHead.rotationPointX * f5, -this.KingPigmanHead.rotationPointY * f5, -this.KingPigmanHead.rotationPointZ * f5);
        this.KingPigmanHead.render(f5);
        GlStateManager.popMatrix();
        this.KingPigmanBody.render(f5);
        GlStateManager.pushMatrix();
        GlStateManager.translate(this.KingPigmanLeftArm.offsetX, this.KingPigmanLeftArm.offsetY, this.KingPigmanLeftArm.offsetZ);
        GlStateManager.translate(this.KingPigmanLeftArm.rotationPointX * f5, this.KingPigmanLeftArm.rotationPointY * f5, this.KingPigmanLeftArm.rotationPointZ * f5);
        GlStateManager.scale(1.5D, 1.5D, 2.0D);
        GlStateManager.translate(-this.KingPigmanLeftArm.offsetX, -this.KingPigmanLeftArm.offsetY, -this.KingPigmanLeftArm.offsetZ);
        GlStateManager.translate(-this.KingPigmanLeftArm.rotationPointX * f5, -this.KingPigmanLeftArm.rotationPointY * f5, -this.KingPigmanLeftArm.rotationPointZ * f5);
        this.KingPigmanLeftArm.render(f5);
        GlStateManager.popMatrix();
        GlStateManager.popMatrix();
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
    	this.KingPigmanLeftArm.rotateAngleX=MathHelper.cos(limbSwing*0.6662F) * 1.4F * limbSwingAmount;
    	this.KingPigmanRightLeg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
    	this.KingPigmanRightArm.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 1.4F * limbSwingAmount;
    	this.KingPigmanLeftLeg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 1.4F * limbSwingAmount;
   
    	this.KingPigmanHead.rotateAngleY = netHeadYaw * 0.017453292F;
    	this.KingPigmanHead.rotateAngleX = headPitch * 0.017453292F;
    	
    	/*
    	final float TUSK_SNOUT_CONST=0.02F;
    			//scaleFactor;
    			//0.1F;
    			//0.017453292F;
    	
    	this.KingPigmanLeftTusk.rotateAngleY = netHeadYaw * TUSK_SNOUT_CONST;
    	this.KingPigmanLeftTusk.rotateAngleX = headPitch * TUSK_SNOUT_CONST;
    	this.KingPigmanRightTusk.rotateAngleY = netHeadYaw * TUSK_SNOUT_CONST;
    	this.KingPigmanRightTusk.rotateAngleX = headPitch * TUSK_SNOUT_CONST;
    	this.KingPigmanSnout.rotateAngleY = netHeadYaw * TUSK_SNOUT_CONST;
    	this.KingPigmanSnout.rotateAngleX = headPitch * TUSK_SNOUT_CONST;
    	*/
    }
}
