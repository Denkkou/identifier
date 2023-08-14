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

import com.google.inject.Provides;
import javax.inject.Inject;
import net.runelite.api.Client;
import net.runelite.api.DecorativeObject;
import net.runelite.api.GameObject;
import net.runelite.api.GroundObject;
import net.runelite.api.Scene;
import net.runelite.api.Tile;
import net.runelite.api.TileObject;
import net.runelite.api.WallObject;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.overlay.OverlayManager;

@PluginDescriptor(
        name = "Identifier",
        description = "Outlines and provides IDs for hovered entities.",
        enabledByDefault = true
)

public class IdentifierPlugin extends Plugin
{
    // Injects
    @Inject
    private OverlayManager overlayManager;

    @Inject
    private IdentifierOverlay identifierOverlay;

    @Inject
    private Client client;

    // Variables

    // Provide config
    @Provides
    IdentifierConfig provideConfig(ConfigManager configManager) {
        return configManager.getConfig(IdentifierConfig.class);
    }

    // Overrides
    @Override
    protected void startUp() { overlayManager.add(identifierOverlay );}

    @Override
    protected void shutDown() { overlayManager.remove(identifierOverlay); }

    // Subscribe to relevant events
    // ...

    TileObject findTileObject(int x, int y, int id) {
        // Get the 3D scene and relevant tile information
        Scene scene = client.getScene();
        Tile[][][] tiles = scene.getTiles();

        // The specific tile within the tiles array
        Tile tile = tiles[client.getPlane()][x][y];

        // If tile exists and has something to match target ID, find objects on tile
        if (tile != null) {
            for (GameObject gameObject : tile.getGameObjects()) {
                if (gameObject != null && gameObject.getId() == id) {
                    return gameObject;
                }
            }

            WallObject wallObject = tile.getWallObject();
            if (wallObject != null && wallObject.getId() == id) {
                return wallObject;
            }

            DecorativeObject decorativeObject = tile.getDecorativeObject();
            if (decorativeObject != null && decorativeObject.getId() == id) {
                return decorativeObject;
            }

            GroundObject groundObject = tile.getGroundObject();
            if (groundObject != null && groundObject.getId() == id) {
                return groundObject;
            }
        }
        return null;
    }
}