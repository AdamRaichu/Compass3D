# Compass3D

[![Modrinth Downloads](https://img.shields.io/modrinth/dt/compass3d?logo=modrinth)](https://modrinth.com/mod/compass3d)
[![CurseForge Downloads](https://img.shields.io/curseforge/dt/971238?logo=curseforge)](https://www.curseforge.com/minecraft/mc-mods/compass3d)

This mod renders an arrow over compasses in the inventory/hotbar. This arrow points up or down, showing you which direction (on the Y axis) you need to travel to reach the coordinate.

As of `v2.0.0`, this also applies to compasses placed in Item Frames. These arrows have their own configs for placement. This features works by default with all compasses except the Recovery Compass. You can install another mod [Framed Recovery Compass Fix](https://github.com/AdamRaichu/framed-recovery-compass-fix) to make it work.

I had the idea for this mod when working on the tracking compass for my [Server Side Commands](https://modrinth.com/mod/server-side-commands) mod.
I realized that you had no way to determine the Y value of the coordinates of a compass.
So, I did some research, and found the mod [Simple Shulker Preview](https://github.com/BVengo/simple-shulker-preview), which did something similar to what I wanted, and forked it. *That project was under the LGPL-3.0 license so click [here](https://github.com/AdamRaichu/Compass3D/blob/main/LGPL_CHANGES.md) for a list of the changes I made*.

This mod also supports several mods. See the [Mod Support List](https://github.com/AdamRaichu/Compass3D/issues/2) for a list of supported mods, or to suggest new mods to support.

<!-- markdownlint-disable MD033 MD045 -->

<img src="https://raw.githubusercontent.com/Jab125/Jab125/main/imgs/requiredClothConfig.png" width="300" height="100">

## Configs

This mod gives you the ability to configure the size and placement of arrows, as well as the arrow type for each supported compass. The config menu is fairly self-explanatory, so I will not elaborate on that further here.

~~This mod comes with the [Cloth Config API](https://www.curseforge.com/minecraft/mc-mods/cloth-config) built in to
implement configs (there should be no need to download it yourself).~~ **You need to install [Cloth Config API](https://www.curseforge.com/minecraft/mc-mods/cloth-config) in order for this project to work.** I would recommend installing [ModMenu](https://www.curseforge.com/minecraft/mc-mods/modmenu) alongside it if you actually want to use them though, because I didn't want to create yet another config mapping for such a simple mod.

## Resource Pack

I made a couple resource packs with alternate icons if you want. You can download them [here][alternate-icons].

If you don't like the textures and want to make a resource pack to change them, here are the paths to the textures.
Please let me know if you do (via a GitHub issue) so I can link them there.

- `assets/compass3d/textures/item/up_arrow.png`
- `assets/compass3d/textures/item/down_arrow.png`
- `assets/compass3d/textures/item/recovery_up_arrow.png`
- `assets/compass3d/textures/item/recovery_down_arrow.png`

There are also other arrows for modded compasses. Check the directory on GitHub to see them all.

## What is the item `compass3d:*_arrow`?

So to add the arrow overlays, I had to register a nonexistent item on the client side.
However, I discovered in testing that it shows up in autocomplete when doing `/give`.
This generates an error when used in multiplayer, but as the item doesn't exist you shouldn't worry about it.

[alternate-icons]: https://modrinth.com/resourcepack/compass3d-alternate-icons
