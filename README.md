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

## Resource Pack

I made a couple resource packs with alternate icons if you want. You can download them [here][alternate-icons].

*(Still waiting for project to be approved at time of writing (5/25), so just come back in a couple days if the link gives a "Project not found" error.) Update (8/29): Moderators withheld release because I didn't add any gallery images. Will resubmit with those later today. The link does work though, it is just not findable in search yet. Update (later on 8/29): Now under review again. However, that means that the link doesn't work. So when they knew I wasn't up to standard with the content policies, it was ok to show with a warning. But now that I've theoretically addressed their problems, you cannot use the link. Because that makes sense.*

If you don't like the textures and want to make a resource pack to change them, here are the paths to the textures.
Please let me know if you do (via a GitHub issue) so I can link them there.

- `assets/compass3d/textures/item/up_arrow.png`
- `assets/compass3d/textures/item/down_arrow.png`

## What is the item `compass3d:<dir>_arrow`?

So to add the arrow overlays, I had to register a nonexistent item on the client side.
However, I discovered in testing that it shows up in autocomplete when doing `/give`.
This generates an error when used in multiplayer, but as the item doesn't exist you shouldn't worry about it.

[alternate-icons]: https://modrinth.com/resourcepack/compass3d-alternate-icons
