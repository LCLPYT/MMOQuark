# MMOContent - Quark Module
[![Gradle Publish](https://github.com/LCLPYT/MMOQuark/actions/workflows/gradle-publish.yml/badge.svg)](https://github.com/LCLPYT/MMOQuark/actions/workflows/gradle-publish.yml)

A lightweight Fabric port of the famous [Quark](https://github.com/VazkiiMods/Quark) Minecraft modification, that aims to provide compatibility for porting worlds created in Quark's official MinecraftForge version.

## What this mod aims to be
This mod does not aim to be an exact port of the original Quark mod.
It only provides a big portion of Quark's blocks, items and entities.
The goal of this mod is that worlds, created in MinecraftForge with the Quark mod,
can be ported to Fabric with ease. By design, worlds created in Forge with the original mod, can be loaded in Fabric with this mod.
For more information about world compatibility, see below.

Originally, this mod was started to provide Fabric content to LCLPServer 5.0.
Therefore, some blocks have their functionality removed for performance reasons, e.g. pipes, the Ender Watcher.
Some other features, like certain mobs or blocks have not been ported at all. Maybe these will follow in the future.

Feel free to add these features yourself. Contributions are welcome, just respect Quark's [LICENSE](https://github.com/VazkiiMods/Quark/blob/master/LICENSE.md).

All the assets of this project were taken from the original Quark mod, for more information,
please refer to this project's LICENSE file.

## Download and installation

- locate your release on the [downloads page](https://github.com/LCLPYT/MMOQuark/releases) and download the jar file (e.g. `mmoquark-1.0.0.jar`).
- install [Fabric](https://fabricmc.net/)
- this project requires [FabricAPI](https://www.curseforge.com/minecraft/mc-mods/fabric-api), so download it as well, if you haven't already
- this project requires [MMOContent](https://github.com/LCLPYT/MMOContent) (a modding library), download it as well
- put MMOQuark, MMOContent and FabricAPI inside your `/mods` directory

## World compatibility
### Migrate 1.16 worlds to 1.18
From 1.16 to 1.18, some blocks were replaced with other ones (e.g. marble -> calcite + variants).
However, you will have to migrate your world manually, before you first load it.
Since 1.18, this mod bundles [MCCT](https://github.com/LCLPYT/MCChunkTransform), a world NBT transformer mod.
This mod automatically registers a transformer, that migrates all the blocks.
To apply the transformation, follow the instructions provided on the [MCCT Page](https://github.com/LCLPYT/MCChunkTransform#readme).

## Dev Setup

Clone the repository, and import it into your IDE. (IntelliJ IDEA is recommended, along with the MinecraftDevelopment plugin)
If there are no run configurations, reopen your IDE.
For more information consult the [Fabric wiki](https://fabricmc.net/wiki/start).

### Building

To build the project, use:

```bash
./gradlew build
```
