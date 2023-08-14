package com.identifier;

import com.google.inject.Provides;
import javax.annotation.Nullable;
import javax.inject.Inject;
import lombok.AccessLevel;
import lombok.Getter;
import net.runelite.api.Actor;
import net.runelite.api.Client;
import net.runelite.api.DecorativeObject;
import net.runelite.api.GameObject;
import net.runelite.api.GameState;
import net.runelite.api.GroundObject;
import net.runelite.api.MenuAction;
import net.runelite.api.NPC;
import net.runelite.api.Scene;
import net.runelite.api.Tile;
import net.runelite.api.TileObject;
import net.runelite.api.WallObject;
import net.runelite.api.events.GameStateChanged;
import net.runelite.api.events.GameTick;
import net.runelite.api.events.InteractingChanged;
import net.runelite.api.events.MenuOptionClicked;
import net.runelite.api.events.NpcDespawned;
import net.runelite.api.widgets.WidgetID;
import net.runelite.api.widgets.WidgetInfo;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import com.identifier.IdentifierConfig;
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
    @Getter
    private TileObject interactedObject;
    private NPC interactedNpc;

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