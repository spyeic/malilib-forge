package fi.dy.masa.malilib.mixin;


import net.minecraft.item.ItemGroup;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import net.minecraft.client.gui.screen.ingame.CreativeInventoryScreen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import fi.dy.masa.malilib.event.RenderEventHandler;

@Mixin(CreativeInventoryScreen.class)
public abstract class MixinCreativeInventoryScreen
{
    @Shadow private static int selectedTab;
    private boolean isSearchTab;

    @Inject(method = "renderTooltip", at = @At("HEAD"))
    private void onRenderToolTipStart(MatrixStack matrixStack, ItemStack stack, int x, int y, CallbackInfo ci) {
        isSearchTab = (selectedTab == ItemGroup.SEARCH.getIndex());
    }
    // @Inject(method = "renderTooltip", at = @At(value = "INVOKE", shift = At.Shift.AFTER,
    //         target = "Lnet/minecraft/client/gui/screen/ingame/CreativeInventoryScreen;renderTooltip(Lnet/minecraft/client/util/math/MatrixStack;Ljava/util/List;Ljava/util/Optional;II)V"))
    // Can't use original injection with forge
    @Inject(method = "renderTooltip", at = @At("RETURN"))
    private void onRenderTooltip(MatrixStack matrixStack, ItemStack stack, int x, int y, CallbackInfo ci)
    {
        if (isSearchTab)
        {
            ((RenderEventHandler) RenderEventHandler.getInstance()).onRenderTooltipLast(matrixStack, stack, x, y);
        }
    }
}
