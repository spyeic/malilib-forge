package fi.dy.masa.malilib.gui.widgets;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import fi.dy.masa.malilib.render.RenderUtils;

public abstract class WidgetBase
{
    protected final MinecraftClient mc;
    protected final TextRenderer textRenderer;
    protected final int fontHeight;
    protected int x;
    protected int y;
    protected int width;
    protected int height;
    protected int zLevel;

    public WidgetBase(int x, int y, int width, int height)
    {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.mc = MinecraftClient.getInstance();
        this.textRenderer = this.mc.textRenderer;
        this.fontHeight = this.textRenderer.fontHeight;
    }

    public int getX()
    {
        return this.x;
    }

    public int getY()
    {
        return this.y;
    }

    public void setPosition(int x, int y)
    {
        this.x = x;
        this.y = y;
    }

    public void setX(int x)
    {
        this.x = x;
    }

    public void setY(int y)
    {
        this.y = y;
    }

    public void setZLevel(int zLevel)
    {
        this.zLevel = zLevel;
    }

    public int getWidth()
    {
        return this.width;
    }

    public int getHeight()
    {
        return this.height;
    }

    public void setWidth(int width)
    {
        this.width = width;
    }

    public void setHeight(int height)
    {
        this.height = height;
    }

    public boolean isMouseOver(int mouseX, int mouseY)
    {
        return mouseX >= this.x && mouseX < this.x + this.width &&
               mouseY >= this.y && mouseY < this.y + this.height;
    }

    public boolean onMouseClicked(int mouseX, int mouseY, int mouseButton)
    {
        if (this.isMouseOver(mouseX, mouseY))
        {
            return this.onMouseClickedImpl(mouseX, mouseY, mouseButton);
        }

        return false;
    }

    protected boolean onMouseClickedImpl(int mouseX, int mouseY, int mouseButton)
    {
        return false;
    }

    public void onMouseReleased(int mouseX, int mouseY, int mouseButton)
    {
        this.onMouseReleasedImpl(mouseX, mouseY, mouseButton);
    }

    public void onMouseReleasedImpl(int mouseX, int mouseY, int mouseButton)
    {
    }

    public boolean onMouseScrolled(int mouseX, int mouseY, double mouseWheelDelta)
    {
        if (this.isMouseOver(mouseX, mouseY))
        {
            return this.onMouseScrolledImpl(mouseX, mouseY, mouseWheelDelta);
        }

        return false;
    }

    public boolean onMouseScrolledImpl(int mouseX, int mouseY, double mouseWheelDelta)
    {
        return false;
    }

    public boolean onKeyTyped(int keyCode, int scanCode, int modifiers)
    {
        return this.onKeyTypedImpl(keyCode, scanCode, modifiers);
    }

    protected boolean onKeyTypedImpl(int keyCode, int scanCode, int modifiers)
    {
        return false;
    }

    public boolean onCharTyped(char charIn, int modifiers)
    {
        return this.onCharTypedImpl(charIn, modifiers);
    }

    protected boolean onCharTypedImpl(char charIn, int modifiers)
    {
        return false;
    }

    /**
     * Returns true if this widget can be selected by clicking at the given point
     */
    public boolean canSelectAt(int mouseX, int mouseY, int mouseButton)
    {
        return this.isMouseOver(mouseX, mouseY);
    }

    public void bindTexture(Identifier texture)
    {
        RenderUtils.bindTexture(texture);
    }

    public int getStringWidth(String text)
    {
        return this.textRenderer.getWidth(text);
    }

    public void drawString(int x, int y, int color, String text, MatrixStack matrixStack)
    {
        this.textRenderer.draw(matrixStack, text, x, y, color);
    }

    public void drawCenteredString(int x, int y, int color, String text, MatrixStack matrixStack)
    {
        this.textRenderer.draw(matrixStack, text, x - this.getStringWidth(text) / 2, y, color);
    }

    public void drawStringWithShadow(int x, int y, int color, String text, MatrixStack matrixStack)
    {
        this.textRenderer.drawWithShadow(matrixStack, text, x, y, color);
    }

    public void drawCenteredStringWithShadow(int x, int y, int color, String text, MatrixStack matrixStack)
    {
        this.textRenderer.drawWithShadow(matrixStack, text, x - this.getStringWidth(text) / 2, y, color);
    }

    public void render(int mouseX, int mouseY, boolean selected, MatrixStack matrixStack)
    {
    }

    public void postRenderHovered(int mouseX, int mouseY, boolean selected, MatrixStack matrixStack)
    {
    }
}
