# Compass3D

I had the idea for this mod when working on the tracking compass for my [Server Side Commands](https://modrinth.com/mod/server-side-commands) mod.
I realized that you had no way to determine the Y value of the coordinates of a compass.
So, I did some research, and found the mod [Simple Shulker Preview](https://github.com/BVengo/simple-shulker-preview), which did something similar to what I wanted, and forked it. *That project was under the LGPL-3.0 license so click [here](https://github.com/AdamRaichu/Compass3D/blob/main/LGPL_CHANGES.md) for a list of the changes I made*.

## Configs

> This section is slightly modified from the original project, but is much the same.

| **Option**           | **Description**                                                       | **Default** |
| -------------------- | --------------------------------------------------------------------- | ----------- |
| `X Offset`           | The horizontal offset of the icon, from left to right.                | 12.0        |
| `Y Offset`           | The vertical offset of the icon, from top to bottom.                  | 12.0        |
| `Z Offset`           | In/out offset. Use when other mods cover / are covered by this.       | 100.0       |
| `Scale`              | Size of the icon.                                                     | 10.0        |
| `Disable Mod`        | Disables the mod so that overlay icons are not displayed.             | *False*     |

This mod comes with the [Cloth Config API](https://www.curseforge.com/minecraft/mc-mods/cloth-config) built in to
implement configs (there should be no need to download it yourself). I would recommend installing [ModMenu](https://www.curseforge.com/minecraft/mc-mods/modmenu) alongside it if you actually want to use them though, because I didn't want to create yet another config mapping for such a simple mod.

Please keep in mind that, although Compass3D may be available for certain versions, that doesn't always mean that ModMenu will be too. If you are desperate to edit the configs, they are stored in configs/compass3d.json.
