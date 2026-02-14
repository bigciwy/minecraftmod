# Entity Textures & Models - Setup Guide

## Current Status ✅

### What's Been Created:
1. **10 Entity Renderer Classes** - Handle rendering all NPCs and summoned mobs
2. **Client Registration** - All renderers are registered and ready
3. **Model JSON Files** - Basic humanoid models for Zamir and COOOLTIIIII
4. **Model Directory** - `src/main/resources/assets/examplemod/models/entity/` is ready for your models

## Your PNG Textures

Since you mentioned you already have PNG texture files, here's how to use them:

### Step 1: Place Your PNGs
Put your PNG texture files in:
```
src/main/resources/assets/examplemod/textures/entity/
```

**File naming (must match renderer texture paths):**
- `zamir.png` - Zamir boss
- `coooltiiiii.png` - COOOLTIIIII boss
- `candots.png` - Candots boss
- `pallina.png` - PALLINA boss (soccer NPC)
- `aldo.png` - Aldo hide-and-seek boss
- `stefano.png` - STEFANO final boss
- `fabero_castelo.png` - Fabero (summoned by PALLINA)
- `elixir_golem.png` - Elixir Golem (summoned by COOOLTIIIII)
- `nonegro.png` - Nonegro (summoned by STEFANO)
- `re_pippi.png` - RE_PIPPI quest giver ghost
- `custom_npc.png` - Original CustomNPC
- `custom_mob.png` - Original CustomMob

### Step 2: PNG Texture Format
Each texture should be:
- **Size:** 64x64 or 128x128 pixels
- **Format:** PNG with transparency support
- **Layout:** Standard Minecraft humanoid texture layout (player-like)

### Step 3: Reference Textures
A standard Minecraft player texture layout looks like this:
```
[0-8, 0-8]    = Rightside face
[8-16, 0-8]   = Left side head
[16-24, 0-8]  = Top head
[24-32, 0-8]  = Front head
[32-40, 0-8]  = Back head
[40-48, 0-8]  = Bottom head (if used)
```

## Creating Custom Models

### For Each Entity, You Can:

1. **Use the Generic Model** (Already created) - Humanoid shape with 6 parts
   - Works for: all 10 entities right now

2. **Create Custom Models** - Edit JSON files in `models/entity/`
   - Zamir model - `zamir.json` ✅ (Already created)
   - COOOLTIIIII model - `coooltiiiii.json` ✅ (Already created)

### JSON Model Template:
```json
{
  "credit": "Entity Name",
  "textures": {
    "0": "examplemod:entity/texture_name"
  },
  "elements": [
    {
      "name": "Head",
      "from": [6, 24, 2],
      "to": [10, 28, 6],
      "faces": {
        "north": {"uv": [0, 0, 4, 4], "texture": "#0"},
        "east": {"uv": [4, 0, 8, 4], "texture": "#0"},
        "south": {"uv": [8, 0, 12, 4], "texture": "#0"},
        "west": {"uv": [12, 0, 16, 4], "texture": "#0"},
        "up": {"uv": [0, 0, 4, 4], "texture": "#0"},
        "down": {"uv": [0, 4, 4, 8], "texture": "#0"}
      }
    }
  ]
}
```

## Key Coordinates for Humanoid Models:

- **Head:** Y: 24-28 (top of body)
- **Body:** Y: 12-24 (torso)
- **Legs:** Y: 0-12 (from feet to hips)
- **Arms:** Y: 12-24 (same as body)

## How It Works

1. **Renderer Class** reads texture path
2. **Renderer** displays entity in-game with the texture
3. **Model JSON** (optional) defines custom 3D shape
4. **Texture PNG** provides the visual appearance

## For Summoned Mobs

**Fabero Castelo, Elixir Golem, Nonegro** are treated exactly the same:
- They need their own PNG texture
- They have their own renderer class
- They're registered in `ClientSetup.java`

## Next Steps

1. **Add your PNG files** to `textures/entity/` folder
2. **Copy the texture filenames** to match the renderers
3. **Test compilation** with `gradlew build`
4. **Run mod** with `gradlew runClient`
5. **(Optional) Customize JSON models** for unique shapes

## File Locations Reference

```
src/main/resources/assets/examplemod/
├── textures/
│   └── entity/          ← Place your PNG files here
├── models/
│   └── entity/          ← JSON model files (optional customization)
```

Java renderers:
```
src/main/java/com/example/examplemod/client/
├── ClientSetup.java                ← Registers all renderers
└── renderer/
    ├── ZamirRenderer.java
    ├── COOOLTIIIIRenderer.java
    ├── CandotsRenderer.java
    ├── PALLINARenderer.java
    ├── STEFANORenderer.java
    ├── AldoRenderer.java
    ├── FaberoCasteloRenderer.java
    ├── ElixirGolemRenderer.java
    ├── NonnegroRenderer.java
    ├── RE_PIPPIRenderer.java
    ├── CustomNPCRenderer.java
    └── CustomMobRenderer.java
```

## Tools for Creating Textures

- **Blockbench** - https://blockbench.net/ (Official Minecraft modeling tool)
- **Aseprite** - Pixel art editor
- **Paint.NET** - Free image editor
- **Piskel** - Online pixel art tool

All support creating 64x64 PNG textures!
