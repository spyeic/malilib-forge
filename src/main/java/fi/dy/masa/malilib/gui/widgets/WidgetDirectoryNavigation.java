package fi.dy.masa.malilib.gui.widgets;

import java.io.File;
import java.util.Arrays;
import javax.annotation.Nullable;
import net.minecraft.client.util.math.MatrixStack;
import fi.dy.masa.malilib.gui.GuiBase;
import fi.dy.masa.malilib.gui.GuiTextInputFeedback;
import fi.dy.masa.malilib.gui.LeftRight;
import fi.dy.masa.malilib.gui.interfaces.IDirectoryNavigator;
import fi.dy.masa.malilib.gui.interfaces.IFileBrowserIconProvider;
import fi.dy.masa.malilib.render.RenderUtils;
import fi.dy.masa.malilib.util.DirectoryCreator;
import fi.dy.masa.malilib.util.FileUtils;
import fi.dy.masa.malilib.util.GuiUtils;
import fi.dy.masa.malilib.util.StringUtils;

public class WidgetDirectoryNavigation extends WidgetSearchBar
{
    protected final File currentDir;
    protected final File rootDir;
    protected final IDirectoryNavigator navigator;
    protected final WidgetIcon iconRoot;
    protected final WidgetIcon iconUp;
    protected final WidgetIcon iconCreateDir;

    public WidgetDirectoryNavigation(int x, int y, int width, int height,
            File currentDir, File rootDir, IDirectoryNavigator navigator, IFileBrowserIconProvider iconProvider)
    {
        super(x, y, width, height, 0, iconProvider.getIconSearch(), LeftRight.RIGHT);

        this.currentDir = currentDir;
        this.rootDir = rootDir;
        this.navigator = navigator;
        this.iconRoot = new WidgetIcon(x, y + 1, iconProvider.getIconRoot());
        x += this.iconRoot.getWidth() + 2;

        this.iconUp = new WidgetIcon(x, y + 1, iconProvider.getIconUp());
        x += this.iconUp.getWidth() + 2;

        this.iconCreateDir = new WidgetIcon(x, y + 1, iconProvider.getIconCreateDirectory());
    }

    @Override
    protected boolean onMouseClickedImpl(int mouseX, int mouseY, int mouseButton)
    {
        if (this.searchOpen == false)
        {
            WidgetIcon hoveredIcon = this.getHoveredIcon(mouseX, mouseY);

            if (hoveredIcon == this.iconRoot)
            {
                this.navigator.switchToRootDirectory();
                return true;
            }
            else if (hoveredIcon == this.iconUp)
            {
                this.navigator.switchToParentDirectory();
                return true;
            }
            else if (hoveredIcon == this.iconCreateDir)
            {
                String title = "malilib.gui.title.create_directory";
                DirectoryCreator creator = new DirectoryCreator(this.currentDir, this.navigator);
                GuiTextInputFeedback gui = new GuiTextInputFeedback(256, title, "", GuiUtils.getCurrentScreen(), creator);
                GuiBase.openGui(gui);
                return true;
            }
        }

        return super.onMouseClickedImpl(mouseX, mouseY, mouseButton);
    }

    @Nullable
    protected WidgetIcon getHoveredIcon(int mouseX, int mouseY)
    {
        if (this.searchOpen == false)
        {
            if (this.iconRoot.isMouseOver(mouseX, mouseY))
            {
                return this.iconRoot;
            }
            else if (this.iconUp.isMouseOver(mouseX, mouseY))
            {
                return this.iconUp;
            }
            else if (this.iconCreateDir.isMouseOver(mouseX, mouseY))
            {
                return this.iconCreateDir;
            }
        }

        return null;
    }

    @Override
    public void render(int mouseX, int mouseY, boolean selected, MatrixStack matrixStack)
    {
        super.render(mouseX, mouseY, selected, matrixStack);

        if (this.searchOpen == false)
        {
            WidgetIcon hoveredIcon = this.getHoveredIcon(mouseX, mouseY);

            this.iconRoot.render(false, hoveredIcon == this.iconRoot);
            this.iconUp.render(false, hoveredIcon == this.iconUp);
            this.iconCreateDir.render(false, hoveredIcon == this.iconCreateDir);

            int pathStartX = this.iconCreateDir.x + this.iconCreateDir.getWidth() + 6;

            // Draw the directory path text background
            RenderUtils.drawRect(pathStartX, this.y, this.width - pathStartX - 2, this.height, 0x20FFFFFF);

            int textColor = 0xC0C0C0C0;
            int maxLen = (this.width - 40) / this.getStringWidth("a") - 4; // FIXME
            String path = FileUtils.getJoinedTrailingPathElements(this.currentDir, this.rootDir, maxLen, " / ");
            this.drawString(pathStartX + 3, this.y + 3, textColor, path, matrixStack);
        }
    }

    @Override
    public void postRenderHovered(int mouseX, int mouseY, boolean selected, MatrixStack matrixStack)
    {
        super.postRenderHovered(mouseX, mouseY, selected, matrixStack);

        if (this.searchOpen == false)
        {
            WidgetIcon hoveredIcon = this.getHoveredIcon(mouseX, mouseY);

            if (hoveredIcon == this.iconRoot)
            {
                RenderUtils.drawHoverText(mouseX, mouseY, Arrays.asList(StringUtils.translate("malilib.gui.button.hover.directory_widget.root")), matrixStack);
            }
            else if (hoveredIcon == this.iconUp)
            {
                RenderUtils.drawHoverText(mouseX, mouseY, Arrays.asList(StringUtils.translate("malilib.gui.button.hover.directory_widget.up")), matrixStack);
            }
            else if (hoveredIcon == this.iconCreateDir)
            {
                RenderUtils.drawHoverText(mouseX, mouseY, Arrays.asList(StringUtils.translate("malilib.gui.button.hover.directory_widget.create_directory")), matrixStack);
            }

            RenderUtils.disableDiffuseLighting();
        }
    }
}
