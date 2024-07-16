package me.flashyreese.mods.reeses_sodium_options.mixin.sodium;

import me.flashyreese.mods.reeses_sodium_options.client.gui.FlatButtonWidgetExtended;
import me.jellysquid.mods.sodium.client.gui.widgets.AbstractWidget;
import me.jellysquid.mods.sodium.client.gui.widgets.FlatButtonWidget;
import me.jellysquid.mods.sodium.client.util.Dim2i;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(FlatButtonWidget.class)
public abstract class MixinFlatButtonWidget extends AbstractWidget implements FlatButtonWidgetExtended {

    @Shadow
    @Final
    private Dim2i dim;

    @Unique
    private boolean leftAligned;

    @Redirect(method = "render", at = @At(value = "INVOKE", target = "Lme/jellysquid/mods/sodium/client/gui/widgets/FlatButtonWidget;drawString(Lnet/minecraft/client/gui/GuiGraphics;Lnet/minecraft/network/chat/Component;III)V"))
    public void redirectDrawString(FlatButtonWidget instance, GuiGraphics guiGraphics, Component text, int x, int y, int color) {
        if (this.leftAligned) {
            this.drawString(guiGraphics, text, this.dim.x() + 10, y, color);
        } else {
            this.drawString(guiGraphics, text, x, y, color);
        }
    }

    @Redirect(method = "render", at = @At(value = "INVOKE", target = "Lme/jellysquid/mods/sodium/client/gui/widgets/FlatButtonWidget;drawRect(Lnet/minecraft/client/gui/GuiGraphics;IIIII)V", ordinal = 1))
    public void redirectDrawRect(FlatButtonWidget instance, GuiGraphics guiGraphics, int x1, int y1, int x2, int y2, int color) {
        if (this.leftAligned) {
            this.drawRect(guiGraphics, x1, this.dim.y(), x1 + 1, y2, color);
        } else {
            this.drawRect(guiGraphics, x1, y1, x2, y2, color);
        }
    }

    @Override
    public Dim2i getDimensions() {
        return this.dim;
    }

    @Override
    public boolean isLeftAligned() {
        return this.leftAligned;
    }

    @Override
    public void setLeftAligned(boolean leftAligned) {
        this.leftAligned = leftAligned;
    }
}
