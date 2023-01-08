package com.github.mrmks.mc.lmf.cfm.ext.client;

import com.github.mrmks.mc.lmf.cfm.MirrorRenderGlobalAccessor;
import com.mrcrayfish.furniture.client.MirrorRenderGlobal;
import com.mrcrayfish.furniture.handler.ConfigurationHandler;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.culling.ICamera;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import java.util.Collection;

@Mixin(MirrorRenderGlobal.class)
public abstract class MixinMirrorRenderGlobal extends RenderGlobal implements MirrorRenderGlobalAccessor {

    public MixinMirrorRenderGlobal(Minecraft mcIn) {
        super(mcIn);
    }

//    boolean first = true;
//    WorldClient world;
    @Override
    public void setWorldAndLoadRenderers(@Nullable WorldClient worldClientIn) {}

    @Override
    public void loadRenderers() {}

    private RenderGlobal renderGlobal;
    @Override
    public void delegateRenderGlobal(RenderGlobal renderIn) {
        renderGlobal = renderIn;
    }

    @Override
    public void onResourceManagerReload(IResourceManager resourceManager) {
        if (renderGlobal != null) renderGlobal.onResourceManagerReload(resourceManager);
    }

    @Override
    public void makeEntityOutlineShader() {
        if (renderGlobal != null) renderGlobal.makeEntityOutlineShader();
    }

    @Override
    public void renderEntityOutlineFramebuffer() {
        if (renderGlobal != null) renderGlobal.renderEntityOutlineFramebuffer();
    }

    @Override
    public void createBindEntityOutlineFbs(int width, int height) {
        if (renderGlobal != null) renderGlobal.createBindEntityOutlineFbs(width, height);
    }

    @Override
    public void renderEntities(Entity renderViewEntity, ICamera camera, float partialTicks) {
        if (renderGlobal != null) renderGlobal.renderEntities(renderViewEntity, camera, partialTicks);
    }

    @Override
    public String getDebugInfoRenders() {
        return renderGlobal != null ? renderGlobal.getDebugInfoRenders() : "";
    }

    @Override
    public String getDebugInfoEntities() {
        return renderGlobal != null ? renderGlobal.getDebugInfoEntities() : "";
    }

    @Override
    public void setupTerrain(Entity viewEntity, double partialTicks, ICamera camera, int frameCount, boolean playerSpectator) {
        if (renderGlobal != null) renderGlobal.setupTerrain(viewEntity, partialTicks, camera, frameCount, playerSpectator);
    }

    @Override
    public int renderBlockLayer(BlockRenderLayer blockLayerIn, double partialTicks, int pass, Entity entityIn) {
        if (renderGlobal != null) return renderGlobal.renderBlockLayer(blockLayerIn, partialTicks, pass, entityIn);
        return 0;
    }

    @Override
    public void updateClouds() {
        if (ConfigurationHandler.mirrorClouds && renderGlobal != null) renderGlobal.updateClouds();
    }

    @Override
    public void renderSky(float partialTicks, int pass) {
        if (renderGlobal != null) renderGlobal.renderSky(partialTicks, pass);
    }

    @Override
    public boolean hasCloudFog(double x, double y, double z, float partialTicks) {
        return renderGlobal != null && renderGlobal.hasCloudFog(x, y, z, partialTicks);
    }

    @Override
    public void updateChunks(long finishTimeNano) {
        if (renderGlobal != null) renderGlobal.updateChunks(finishTimeNano);
    }

    @Override
    public void renderWorldBorder(Entity entityIn, float partialTicks) {
        if (renderGlobal != null) renderGlobal.renderWorldBorder(entityIn, partialTicks);
    }

    @Override
    public void drawBlockDamageTexture(Tessellator tessellatorIn, BufferBuilder bufferBuilderIn, Entity entityIn, float partialTicks) {
        if (renderGlobal != null) renderGlobal.drawBlockDamageTexture(tessellatorIn, bufferBuilderIn, entityIn, partialTicks);
    }

    @Override
    public void drawSelectionBox(EntityPlayer player, RayTraceResult movingObjectPositionIn, int execute, float partialTicks) {
        if (renderGlobal != null) renderGlobal.drawSelectionBox(player, movingObjectPositionIn, execute, partialTicks);
    }

    @Override
    public void notifyBlockUpdate(World worldIn, BlockPos pos, IBlockState oldState, IBlockState newState, int flags) {}

    @Override
    public void notifyLightSet(BlockPos pos) {}

    @Override
    public void markBlockRangeForRenderUpdate(int x1, int y1, int z1, int x2, int y2, int z2) {}

    @Override
    public void spawnParticle(int particleID, boolean ignoreRange, double xCoord, double yCoord, double zCoord, double xSpeed, double ySpeed, double zSpeed, int... parameters) {
        if (renderGlobal != null) renderGlobal.spawnParticle(particleID, ignoreRange, xCoord, yCoord, zCoord, xSpeed, ySpeed, zSpeed, parameters);
    }

    @Override
    public void spawnParticle(int id, boolean ignoreRange, boolean p_190570_3_, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed, int... parameters) {
        if (renderGlobal != null) renderGlobal.spawnParticle(id, ignoreRange, p_190570_3_, x, y, z, xSpeed, ySpeed, zSpeed, parameters);
    }

    @Override
    public void sendBlockBreakProgress(int breakerId, BlockPos pos, int progress) {
        if (renderGlobal != null) renderGlobal.sendBlockBreakProgress(breakerId, pos, progress);
    }

    @Override
    public boolean hasNoChunkUpdates() {
        return renderGlobal == null || renderGlobal.hasNoChunkUpdates();
    }

    @Override
    public void setDisplayListEntitiesDirty() {
        if (renderGlobal != null) renderGlobal.setDisplayListEntitiesDirty();
    }

    @Override
    public void updateTileEntities(Collection<TileEntity> tileEntitiesToRemove, Collection<TileEntity> tileEntitiesToAdd) {
        if (renderGlobal != null) renderGlobal.updateTileEntities(tileEntitiesToRemove, tileEntitiesToAdd);
    }

    /**
     * @author
     * @reason delegate to RenderGlobal
     */
    @Override
    @Overwrite
    public void renderClouds(float partialTicks, int pass, double p_180447_3_, double p_180447_5_, double p_180447_7_) {
        if (ConfigurationHandler.mirrorClouds && renderGlobal != null) {
            renderGlobal.renderClouds(partialTicks, pass, p_180447_3_, p_180447_5_, p_180447_7_);
        }
    }
}
