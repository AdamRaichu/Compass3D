package io.github.adamraichu.compass3d.config;

import io.github.adamraichu.compass3d.Compass3DMod;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

@Config(name = Compass3DMod.MOD_ID)
public class ConfigOptions implements ConfigData {
    /** Disables the mod. */
    @ConfigEntry.Gui.Tooltip()
    public boolean disableMod = false;

    @ConfigEntry.Gui.Tooltip()
    public boolean renderFramedCompasses = true;

    /** x offset */
    @ConfigEntry.Category("icon_position")
    @ConfigEntry.Gui.Tooltip()
    @ConfigEntry.BoundedDiscrete(min = 0, max = 16)
    public int translateX = 12;

    /** y offset */
    @ConfigEntry.Category("icon_position")
    @ConfigEntry.Gui.Tooltip()
    @ConfigEntry.BoundedDiscrete(min = 0, max = 16)
    public int translateY = 12;

    /** z offset - for if it appears above or below other mod overlays */
    @ConfigEntry.Category("icon_position")
    @ConfigEntry.Gui.Tooltip()
    @ConfigEntry.BoundedDiscrete(min = 0, max = 16)
    public int translateZ = 10;

    /** scale value */
    @ConfigEntry.Category("icon_position")
    @ConfigEntry.Gui.Tooltip()
    @ConfigEntry.BoundedDiscrete(min = 0, max = 16)
    public int scale = 10;

    /**
     * x offset - default location overlaps with stacked compasses count indicator
     */
    @ConfigEntry.Category("icon_position")
    @ConfigEntry.Gui.Tooltip()
    @ConfigEntry.BoundedDiscrete(min = 0, max = 16)
    public int stackedTranslateX = 4;

    /**
     * y offset - default location overlaps with stacked compasses count indicator
     */
    @ConfigEntry.Category("icon_position")
    @ConfigEntry.Gui.Tooltip()
    @ConfigEntry.BoundedDiscrete(min = 0, max = 16)
    public int stackedTranslateY = 12;

    /**
     * z offset - default location overlaps with stacked compasses count indicator
     */
    @ConfigEntry.Category("icon_position")
    @ConfigEntry.Gui.Tooltip()
    @ConfigEntry.BoundedDiscrete(min = 0, max = 16)
    public int stackedTranslateZ = 9;

    /**
     * scale value - default location overlaps with stacked compasses count
     * indicator
     */
    @ConfigEntry.Category("icon_position")
    @ConfigEntry.Gui.Tooltip()
    @ConfigEntry.BoundedDiscrete(min = 0, max = 16)
    public int stackedScale = 10;

    @ConfigEntry.Category("icon_position")
    @ConfigEntry.Gui.PrefixText()
    @ConfigEntry.Gui.Tooltip()
    @ConfigEntry.BoundedDiscrete(min = -10, max = 16)
    public int framedTranslateX = 7;

    @ConfigEntry.Category("icon_position")
    @ConfigEntry.Gui.Tooltip()
    @ConfigEntry.BoundedDiscrete(min = -6, max = 24)
    public int framedTranslateY = 16;

    @ConfigEntry.Category("icon_position")
    @ConfigEntry.Gui.Tooltip()
    @ConfigEntry.BoundedDiscrete(min = -10, max = 16)
    public int framedTranslateZ = 6;

    @ConfigEntry.Category("icon_position")
    @ConfigEntry.Gui.Tooltip()
    public float framedScale = 1;

    @ConfigEntry.Category("icon_style")
    @ConfigEntry.Gui.Tooltip()
    @ConfigEntry.Gui.PrefixText()
    public ArrowSettings vanillaCompass = ArrowSettings.DEFAULT;

    @ConfigEntry.Category("icon_style")
    @ConfigEntry.Gui.Tooltip()
    public ArrowSettings lodestoneCompass = ArrowSettings.MATCH_COMPASS_STYLE;

    @ConfigEntry.Category("icon_style")
    public ArrowSettings recoveryCompass = ArrowSettings.MATCH_COMPASS_STYLE;

    // It looks weird, but it helps me.
    @ConfigEntry.Gui.PrefixText()

    @ConfigEntry.Category("icon_style")
    @ConfigEntry.Gui.Tooltip()
    public ArrowSettings netheriteCompass = ArrowSettings.MATCH_COMPASS_STYLE;

    @ConfigEntry.Category("icon_style")
    @ConfigEntry.Gui.Tooltip()
    public ArrowSettings oreCompass = ArrowSettings.MATCH_COMPASS_STYLE;

    @ConfigEntry.Category("icon_style")
    @ConfigEntry.Gui.Tooltip()
    public ArrowSettings darkCompass = ArrowSettings.MATCH_COMPASS_STYLE;

    @ConfigEntry.Category("icon_style")
    @ConfigEntry.Gui.Tooltip()
    public ArrowSettings portalCompass = ArrowSettings.MATCH_COMPASS_STYLE;

    // Currently unused.
    public static enum ArrowSettings {
        DEFAULT,
        MATCH_COMPASS_STYLE,
        DISABLED
    }
}