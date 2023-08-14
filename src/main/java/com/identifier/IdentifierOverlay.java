package com.identifier;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import javax.inject.Inject;
import net.runelite.api.Actor;
import net.runelite.api.Client;
import net.runelite.api.MenuAction;
import net.runelite.api.MenuEntry;
import net.runelite.api.NPC;
import net.runelite.api.Point;
import net.runelite.api.TileObject;
import net.runelite.api.widgets.WidgetID;
import net.runelite.api.widgets.WidgetInfo;
import com.identifier.IdentifierConfig;
import com.identifier.IdentifierPlugin;
import net.runelite.client.ui.overlay.*;
import net.runelite.client.ui.overlay.outline.ModelOutlineRenderer;
import net.runelite.client.util.ColorUtil;
import net.runelite.client.util.Text;

class IdentifierOverlay extends Overlay
{
    // Variables
    private static final int OVERHEAD_TEXT_MARGIN = 40;

    private final Client client;
    private final IdentifierPlugin plugin;
    private final IdentifierConfig config;
    private final ModelOutlineRenderer modelOutlineRenderer;

    // Injects
    @Inject
    private IdentifierOverlay(Client client,
                              IdentifierPlugin plugin,
                              IdentifierConfig config,
                              ModelOutlineRenderer modelOutlineRenderer) {
        this.client = client;
        this.plugin = plugin;
        this.config = config;
        this.modelOutlineRenderer = modelOutlineRenderer;

        // Set overlay parameters
        setPosition(OverlayPosition.DYNAMIC);
        setLayer(OverlayLayer.ABOVE_SCENE);
        setPriority(OverlayPriority.HIGH);
    }

    // Overrides
    @Override
    public Dimension render(Graphics2D graphics) {
        renderMouseover(graphics);
        return null;
    }

    private void renderMouseover(Graphics2D graphics) {
        // Store menu entries for currently right-clicked entity
        MenuEntry[] menuEntries = client.getMenuEntries();

        // Do nothing if there are no entries
        if (menuEntries.length == 0) {
            return;
        }

        // Get the menu entry and it's type
        MenuEntry entry = client.isMenuOpen() ? hoveredMenuEntry(menuEntries) : menuEntries[menuEntries.length - 1];
        MenuAction menuAction = entry.getType();

        switch (menuAction) {
            // Every case in which an object is selected
            case WIDGET_TARGET_ON_GAME_OBJECT:
            case GAME_OBJECT_FIRST_OPTION:
            case GAME_OBJECT_SECOND_OPTION:
            case GAME_OBJECT_THIRD_OPTION:
            case GAME_OBJECT_FOURTH_OPTION:
            case GAME_OBJECT_FIFTH_OPTION:
            case EXAMINE_OBJECT: {
                int x = entry.getParam0();
                int y = entry.getParam1();
                int id = entry.getIdentifier();

                TileObject tileObject = plugin.findTileObject(x, y, id);

                // If object exists, and config allows, render outline
                if (tileObject != null && config.objectShowHover()) {
                    modelOutlineRenderer.drawOutline(tileObject,
                            config.borderWidth(),
                            config.objectHighlightColor(),
                            config.outlineFeather());

                    if (config.objectShowID()) {
                        renderIDText(tileObject, graphics);
                    }
                }
                break;
            }

            // Every case in which an NPC is selected
            case WIDGET_TARGET_ON_NPC:
            case NPC_FIRST_OPTION:
            case NPC_SECOND_OPTION:
            case NPC_THIRD_OPTION:
            case NPC_FOURTH_OPTION:
            case NPC_FIFTH_OPTION:
            case EXAMINE_NPC: {
                NPC npc = entry.getNpc();

                // If NPC exists and config allows, render outline
                if (npc != null && config.npcShowHover()) {
                    modelOutlineRenderer.drawOutline(npc,
                            config.borderWidth(),
                            config.npcHighlightColor(),
                            config.outlineFeather());

                    if (config.npcShowID()) {
                        renderIDText(npc, graphics);
                    }
                }
            }
        }
    }

    private void renderIDText(TileObject tileObject, Graphics2D graphics) {
        // Render ID above target
        final int zOffset = 200; // This should take the logical height of an object?
        final String stringID = Text.sanitize(Integer.toString(tileObject.getId()));
        Point textLocation = tileObject.getCanvasTextLocation(graphics, stringID, zOffset);

        if (textLocation == null)
        {
            return;
        }

        // Render text on screen
        OverlayUtil.renderTextLocation(graphics, textLocation, stringID, config.objectHighlightColor());
    }

    private void renderIDText(NPC npc, Graphics2D graphics){
        // Render ID above target
        final int zOffset = npc.getLogicalHeight() + OVERHEAD_TEXT_MARGIN;
        final String stringID = Text.sanitize(Integer.toString(npc.getId()));
        Point textLocation = npc.getCanvasTextLocation(graphics, stringID, zOffset);

        if (textLocation == null)
        {
            return;
        }

        // Render text on screen
        OverlayUtil.renderTextLocation(graphics, textLocation, stringID, config.npcHighlightColor());
    }

    private MenuEntry hoveredMenuEntry(final MenuEntry[] menuEntries) {
        // Menu position and width
        final int menuX = client.getMenuX();
        final int menuY = client.getMenuY();
        final int menuWidth = client.getMenuWidth();

        // Point object containing mouse position on screen space
        final Point mousePosition = client.getMouseCanvasPosition();

        // I don't get understand what this all does :S
        int dy = mousePosition.getY() - menuY;
        dy -= 19; // Height of Choose Option
        if (dy < 0)
        {
            return menuEntries[menuEntries.length - 1];
        }

        int idx = dy / 15; // Height of each menu option
        idx = menuEntries.length - 1 - idx;

        if (mousePosition.getX() > menuX && mousePosition.getX() < menuX + menuWidth
                && idx >= 0 && idx < menuEntries.length)
        {
            return menuEntries[idx];
        }
        return menuEntries[menuEntries.length - 1];
    }
}