package fi.dy.masa.malilib.mixin;

import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.Mouse;
import net.minecraft.client.util.Window;
import fi.dy.masa.malilib.event.InputEventHandler;

@Mixin(Mouse.class)
public abstract class MixinMouse
{
    @Shadow @Final private MinecraftClient client;
    @Shadow private double eventDeltaWheel;

    @Inject(method = "onCursorPos",
            at = @At(value = "FIELD", target = "Lnet/minecraft/client/Mouse;hasResolutionChanged:Z", ordinal = 0))
    private void hookOnMouseMove(long handle, double xpos, double ypos, CallbackInfo ci)
    {
        Window window = this.client.getWindow();
        int mouseX = (int) (((Mouse) (Object) this).getX() * (double) window.getScaledWidth() / (double) window.getWidth());
        int mouseY = (int) (((Mouse) (Object) this).getY() * (double) window.getScaledHeight() / (double) window.getHeight());

        ((InputEventHandler) InputEventHandler.getInputManager()).onMouseMove(mouseX, mouseY);
    }

    @Inject(method = "onMouseScroll", cancellable = true,
            at = @At(value = "FIELD", target = "Lnet/minecraft/client/MinecraftClient;currentScreen:Lnet/minecraft/client/gui/screen/Screen;", ordinal = 0))
    private void hookOnMouseScroll(long handle, double xOffset, double yOffset, CallbackInfo ci)
    {
        Window window = this.client.getWindow();
        int mouseX = (int) (((Mouse) (Object) this).getX() * (double) window.getScaledWidth() / (double) window.getWidth());
        int mouseY = (int) (((Mouse) (Object) this).getY() * (double) window.getScaledHeight() / (double) window.getHeight());

        if (((InputEventHandler) InputEventHandler.getInputManager()).onMouseScroll(mouseX, mouseY, xOffset, yOffset))
        {
            this.eventDeltaWheel = 0.0;
            ci.cancel();
        }
    }

    @Inject(method = "onMouseButton", cancellable = true,
            at = @At(value = "FIELD", target = "Lnet/minecraft/client/MinecraftClient;IS_SYSTEM_MAC:Z", ordinal = 0))
    private void hookOnMouseClick(long handle, final int button, final int action, int mods, CallbackInfo ci)
    {
        Window window = this.client.getWindow();
        int mouseX = (int) (((Mouse) (Object) this).getX() * (double) window.getScaledWidth() / (double) window.getWidth());
        int mouseY = (int) (((Mouse) (Object) this).getY() * (double) window.getScaledHeight() / (double) window.getHeight());
        final boolean keyState = action == GLFW.GLFW_PRESS;

        if (((InputEventHandler) InputEventHandler.getInputManager()).onMouseClick(mouseX, mouseY, button, keyState))
        {
            ci.cancel();
        }
    }
}
