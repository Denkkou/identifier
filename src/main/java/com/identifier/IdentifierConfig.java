package com.identifier;

import java.awt.Color;
import net.runelite.client.config.Alpha;
import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;
import net.runelite.client.config.ConfigSection;
import net.runelite.client.config.Range;

@ConfigGroup("identifier")
public interface IdentifierConfig extends Config
{
    // Set up sections of the config panel
    @ConfigSection(
            name = "NPCs",
            description = "Settings for NPC identification",
            position = 0
    )
    String npcSection = "npcSection";

    @ConfigSection(
            name = "Objects",
            description = "Settings for Object identification",
            position = 0
    )
    String objectSection = "objectSection";

    // Items within config panel
    @ConfigItem(
            keyName = "npcShowHover",
            name = "Show on hover",
            description = "Outline NPCs when hovered",
            position = 1,
            section = npcSection
    )
    default boolean npcShowHover()
    {
        return true;
    }

    @ConfigItem(
            keyName = "npcShowID",
            name = "ID on hover",
            description = "Display NPC's ID when hovered",
            position = 2,
            section = npcSection
    )
    default boolean npcShowID()
    {
        return true;
    }

    @ConfigItem(
            keyName = "npcHighlightColor",
            name = "NPC hover",
            description = "The color of the hover outline for NPCs",
            position = 3,
            section = npcSection
    )
    default Color npcHighlightColor() { return new Color(0x9000FFFF, true); }


    @ConfigItem(
            keyName = "objectShowHover",
            name = "Show on hover",
            description = "Outline objects when hovered",
            position = 1,
            section = objectSection
    )
    default boolean objectShowHover()
    {
        return true;
    }


    @ConfigItem(
            keyName = "objectShowID",
            name = "ID on hover",
            description = "Display Object's ID when hovered",
            position = 2,
            section = objectSection
    )
    default boolean objectShowID()
    {
        return true;
    }

    @ConfigItem(
            keyName = "objectHighlightColor",
            name = "Object hover",
            description = "The color of the hover outline for Objects",
            position = 3,
            section = objectSection
    )
    default Color objectHighlightColor() { return new Color(0x9000FFFF, true); }

    // Uncategorised settings
    @ConfigItem(
            keyName = "borderWidth",
            name = "Border Width",
            description = "Width of the outlined border",
            position = 7
    )
    default int borderWidth()
    {
        return 4;
    }

    @ConfigItem(
            keyName = "outlineFeather",
            name = "Outline feather",
            description = "Specify between 0-4 how much of the model outline should be faded",
            position = 8
    )
    @Range(
            max = 4
    )
    default int outlineFeather()
    {
        return 4;
    }
}