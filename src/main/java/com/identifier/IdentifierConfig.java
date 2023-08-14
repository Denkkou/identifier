/*
BSD 2-Clause License

Copyright (c) 2018, Tomas Slusny <slusnucky@gmail.com>
Copyright (c) 2021, Adam <Adam@sigterm.info>
Copyright (c) 2023, Joe S. <JoeSchoDev@gmail.com>

Redistribution and use in source and binary forms, with or without
modification, are permitted provided that the following conditions are met:

1. Redistributions of source code must retain the above copyright notice, this
   list of conditions and the following disclaimer.

2. Redistributions in binary form must reproduce the above copyright notice,
   this list of conditions and the following disclaimer in the documentation
   and/or other materials provided with the distribution.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/

package com.identifier;

import java.awt.Color;
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