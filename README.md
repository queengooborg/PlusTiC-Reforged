# PlusTiC Reforged

Remember the classic [PlusTiC mod](https://www.curseforge.com/minecraft/mc-mods/plusticminusbad) for 1.12.2?  Ever wanted to see an update for modern Minecraft versions?  Well, look no further, because PlusTiC Reforged is here!

> [!NOTE]
> This version is still in early development, and is not ready for testing.  Please check back later.

## About

PlusTiC Reforged is a compatibility layer between Tinkers' Construct and 

## New Tools

- Katana. A fast two-handed weapon that deals increasing damage the more mobs you kill.
- Laser Gun. A ranged weapon that requires durability and energy (Forge, Tesla, RF).

## Supported Mods

### Materials

- [Biomes o' Plenty](https://www.curseforge.com/minecraft/mc-mods/biomes-o-plenty)
- [Project Red](https://www.curseforge.com/minecraft/mc-mods/project-red-core)
- [Mekanism](https://www.curseforge.com/minecraft/mc-mods/mekanism)
- [Botania](https://www.curseforge.com/minecraft/mc-mods/botania)
- [Advanced Rocketry](https://www.curseforge.com/minecraft/mc-mods/advanced-rocketry)
- [ArmorPlus](https://www.curseforge.com/minecraft/mc-mods/armorplus)
- [Thermal Foundation](https://www.curseforge.com/minecraft/mc-mods/thermal-foundation)
- [Draconic Evolution](https://www.curseforge.com/minecraft/mc-mods/draconic-evolution)
- [Actually Additions](https://www.curseforge.com/minecraft/mc-mods/actually-additions)
- [Natura](https://www.curseforge.com/minecraft/mc-mods/natura)
- [Psi](https://www.curseforge.com/minecraft/mc-mods/psi)
- [Avaritia](https://www.curseforge.com/minecraft/mc-mods/avaritia-1-10)
- [Industrial Foregoing](https://www.curseforge.com/minecraft/mc-mods/industrial-foregoing) / [MineFactory Reloaded](https://www.curseforge.com/minecraft/mc-mods/minefactory-reloaded)
- [Galacticraft Legacy](https://www.curseforge.com/minecraft/mc-mods/galacticraft-legacy)
- [Survivalist](https://www.curseforge.com/minecraft/mc-mods/survivalist)
- [ProjectE](https://www.curseforge.com/minecraft/mc-mods/projecte)
- [GemsPlusPlus](https://www.curseforge.com/minecraft/mc-mods/gemsplusplus)
- [Applied Energistics 2](https://www.curseforge.com/minecraft/mc-mods/applied-energistics-2)
- [Environmental Tech](https://www.curseforge.com/minecraft/mc-mods/environmental-tech)
- [Thaumcraft](https://www.curseforge.com/minecraft/mc-mods/thaumcraft)
- [Simply Jetpacks 2](https://www.curseforge.com/minecraft/mc-mods/simply-jetpacks-2)
- [Advent of Ascension (Nevermine)](https://www.curseforge.com/minecraft/mc-mods/advent-of-ascension-nevermine)

### Utility

- [Construct's Armory](https://www.curseforge.com/minecraft/mc-mods/constructs-armory)
- [Just Enough Items](https://www.curseforge.com/minecraft/mc-mods/jei)
- [HWYLA](https://www.curseforge.com/minecraft/mc-mods/hwyla)

### A note about Natural Pledge

Starting in 5.1.2.0, PlusTiC forces out and *overrides* Natural Pledge TiC materials by default. This can be changed in the config so that PlusTiC does *not* add Botania materials if Natural Pledge is loaded.

## Building from Source

After cloning this repository, run the command (with working directory in the folder with the repository)
```
./gradlew setupDecompWorkspace
```

If you're using Eclipse, run `./gradlew eclipse` to generate IDE files for Eclipse.

If you're using IntelliJ IDEA, open the project folder in IDEA or run `./gradlew idea` to generate IDE files.

Build with
```
./gradlew clean
./gradlew build
```

## Frequently Asked Questions (FAQ)

### What happened to the original PlusTiC mod?

PlusTiC was originally developed by Landmaster, a developer with a rich history in developing game mods.  Landmaster, along with a number of various contributors on GitHub, had built the majority of the mod into what it is today.

However, the mods that Landmaster developed have all been removed from CurseForge, due to an [introduction of malicious code that would prevent a specific player from being able to log onto any servers](https://github.com/TeamDman/PlusTiC/commit/9147573c6d514ff88825a8cc1ab8438f9c80a14c).  While they had [removed the code and released a new version just a few days later](https://github.com/TeamDman/PlusTiC/commit/d0b4d17ce542a186a1660c7fac6083aa3eea37aa#diff-a516bfe6aaa4dd4f38abbd0de3bdd0dbL197-L204), the damage was already done.  Since then, their mods have completely vanished from the internet, including the GitHub repositories.

PlusTiC was eventually forked by TeamDman in 2020, released as "xXx_MoreToolMats_xXx (a PlusTiC fork without the evil)", and maintained by them for almost exactly three years.  Development on the original mod had unfortunately come to a complete halt.

There was one feature in particular that I was looking forward to seeing at some point: an update for modern versions of Minecraft. Since it was never updated for versions beyond 1.12.2, it was one of the mods that kept me from upgrading my own modpack for playing with friends.  As such, I wanted to try my hand at updating the mod for newer versions of Minecraft, so I forked it and started developing PlusTiC Reforged.

### Why is this version called "Reforged"?

Contrary to what many may think, the name "Reforged" wasn't inspired by the NeoForge (or original Forge) modloader.  I felt that it was important to distinguish this version as a separate mod from the original, and as Tinkers' Construct is all about tool forging, I felt that it was fitting to utilize that as a part of the name!

### Will you port this mod to Fabric or Quilt?

Tinkers' Construct itself doesn't support Fabric or Quilt, so...definitely not.

### Are you going to add code that blocks a specific player's UUID from joining servers?

Of course not, that would be silly and stupid!

## Developers/Credits
PlusTiC Reforged maintained by @queengooborg and [various contributors on GitHub](https://github.com/queengooborg/PlusTiC-Reforged/graphs/contributors)

Original PlusTiC developed by @Landmaster, then maintained by @TeamDman

Chinese Translations: @DYColdWind

New Weapon Textures: @Tenebris11