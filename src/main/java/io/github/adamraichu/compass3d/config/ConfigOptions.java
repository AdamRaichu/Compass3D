package io.github.adamraichu.compass3d.config;

import io.github.adamraichu.compass3d.Compass3DMod;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

@Config(name = Compass3DMod.MOD_ID)
public class ConfigOptions implements ConfigData {
    /** x offset */
    @ConfigEntry.Gui.Tooltip()
    @ConfigEntry.BoundedDiscrete(min = 0, max = 16)
    public int translateX = 12;

    /** y offset */
    @ConfigEntry.Gui.Tooltip()
    @ConfigEntry.BoundedDiscrete(min = 0, max = 16)
    public int translateY = 12;

    /** z offset - for if it appears above or below other mod overlays */
    @ConfigEntry.Gui.Tooltip()
    @ConfigEntry.BoundedDiscrete(min = 0, max = 16)
    public int translateZ = 10;

    /** scale value */
    @ConfigEntry.Gui.Tooltip()
    @ConfigEntry.BoundedDiscrete(min = 0, max = 16)
    public int scale = 10;

    /** Disables the mod. */
    @ConfigEntry.Gui.Tooltip()
    public boolean disableMod = false;

    /**
     * x offset - default location overlaps with stacked compasses count indicator
     */
    @ConfigEntry.Category("compatibility")
    @ConfigEntry.Gui.Tooltip()
    @ConfigEntry.BoundedDiscrete(min = 0, max = 16)
    public int stackedTranslateX = 4;

    /**
     * y offset - default location overlaps with stacked compasses count indicator
     */
    @ConfigEntry.Category("compatibility")
    @ConfigEntry.Gui.Tooltip()
    @ConfigEntry.BoundedDiscrete(min = 0, max = 16)
    public int stackedTranslateY = 12;

    /**
     * z offset - default location overlaps with stacked compasses count indicator
     */
    @ConfigEntry.Category("compatibility")
    @ConfigEntry.Gui.Tooltip()
    @ConfigEntry.BoundedDiscrete(min = 0, max = 16)
    public int stackedTranslateZ = 9;

    /**
     * scale value - default location overlaps with stacked compasses count
     * indicator
     */
    @ConfigEntry.Category("compatibility")
    @ConfigEntry.Gui.Tooltip()
    @ConfigEntry.BoundedDiscrete(min = 0, max = 16)
    public int stackedScale = 10;
}
