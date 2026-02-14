#!/usr/bin/env python3
"""
Generate placeholder Minecraft entity textures as PNG files.
Each texture is 64x64 pixels with unique colors for identification.
"""

from PIL import Image, ImageDraw
import os

# Output directory
output_dir = r"c:\Users\eleog\OneDrive\Desktop\MOD\src\main\resources\assets\examplemod\textures\entity"

# Texture definitions: (filename, primary_color, secondary_color)
textures = [
    ("zamir.png", (255, 100, 50), (200, 50, 50)),        # Orange/Red - bike rider
    ("coooltiiiii.png", (100, 150, 255), (50, 100, 200)), # Blue - water vibes
    ("candots.png", (200, 100, 200), (150, 50, 150)),     # Purple - smoke
    ("pallina.png", (255, 255, 100), (200, 150, 50)),     # Yellow/Gold - soccer
    ("aldo.png", (50, 200, 100), (100, 150, 200)),        # Green/Cyan - hide and seek
    ("stefano.png", (255, 150, 50), (200, 100, 100)),     # Orange/Brown - curly hair
    ("fabero_castelo.png", (200, 50, 50), (100, 50, 100)), # Red/Purple - explosive
    ("elixir_golem.png", (100, 200, 100), (50, 150, 50)),  # Green - golem
    ("nonegro.png", (100, 100, 100), (50, 50, 50)),        # Gray - zombie
    ("re_pippi.png", (150, 100, 200), (200, 150, 255)),    # Purple/Blue - ghost
    ("custom_npc.png", (100, 200, 200), (50, 150, 150)),   # Cyan
    ("custom_mob.png", (200, 100, 100), (150, 50, 50)),    # Brownish
]

def create_texture(filename, primary_color, secondary_color):
    """Create a simple placeholder texture"""
    # Create 64x64 image
    img = Image.new('RGB', (64, 64), color=primary_color)
    draw = ImageDraw.Draw(img)
    
    # Draw simple face/pattern
    # Head
    draw.ellipse([12, 8, 52, 48], fill=primary_color, outline=secondary_color, width=2)
    
    # Eyes
    draw.ellipse([20, 16, 28, 24], fill=secondary_color)
    draw.ellipse([36, 16, 44, 24], fill=secondary_color)
    
    # Mouth
    draw.rectangle([20, 32, 44, 36], fill=secondary_color)
    
    # Body
    draw.rectangle([20, 48, 44, 60], fill=secondary_color)
    
    # Arms
    draw.rectangle([12, 48, 20, 56], fill=secondary_color)
    draw.rectangle([44, 48, 52, 56], fill=secondary_color)
    
    # Save
    filepath = os.path.join(output_dir, filename)
    img.save(filepath)
    print(f"Created: {filename}")

# Create all textures
print("Generating placeholder textures...")
for filename, primary, secondary in textures:
    try:
        create_texture(filename, primary, secondary)
    except Exception as e:
        print(f"Error creating {filename}: {e}")

print(f"\nAll textures created in: {output_dir}")
print("You can now replace these with your own PNG files!")
