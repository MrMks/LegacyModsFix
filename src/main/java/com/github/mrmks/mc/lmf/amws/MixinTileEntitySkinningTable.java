package com.github.mrmks.mc.lmf.amws;

import moe.plushie.armourers_workshop.common.inventory.ModInventory;
import moe.plushie.armourers_workshop.common.tileentities.TileEntitySkinningTable;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(TileEntitySkinningTable.class)
public class MixinTileEntitySkinningTable {
    @Redirect(
            method = "checkForValidRecipe",
            at = @At(
                    value = "INVOKE", target = "Lmoe/plushie/armourers_workshop/common/inventory/ModInventory;setInventorySlotContents(ILnet/minecraft/item/ItemStack;)V"
            )
    )
    private void setInventorySlotContents(ModInventory instance, int index, ItemStack stack) {
        stack.setCount(1);
        instance.setInventorySlotContents(index, stack);
    }
}
