package data;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.methods.container.impl.equipment.Equipment;
import org.dreambot.api.methods.dialogues.Dialogues;
import org.dreambot.api.methods.emotes.Emote;
import org.dreambot.api.methods.emotes.Emotes;
import org.dreambot.api.methods.input.Camera;
import org.dreambot.api.methods.interactive.GameObjects;
import org.dreambot.api.methods.interactive.NPCs;
import org.dreambot.api.methods.interactive.Players;
import org.dreambot.api.methods.item.GroundItems;
import org.dreambot.api.methods.map.Area;
import org.dreambot.api.methods.map.Tile;
import org.dreambot.api.methods.walking.impl.Walking;
import org.dreambot.api.utilities.Sleep;
import org.dreambot.api.wrappers.interactive.GameObject;
import org.dreambot.api.wrappers.interactive.NPC;
import org.dreambot.api.wrappers.items.GroundItem;
import org.dreambot.api.wrappers.items.Item;

public class Utilities {

    public static boolean waitObject(int id) {
        GameObject obj = GameObjects.closest(id);
        return (obj != null && obj.exists()
                && obj.canReach()
                && obj.interact());
    }

    public static boolean waitNpc(String name) {
        NPC npc = NPCs.closest(name);
        return (npc != null && npc.exists()
                && npc.canReach()
                && npc.interact("Talk-to"));
    }

    public static boolean clickInventory(String name) {
        Item item = Inventory.get(name);
        return (item != null && item.interact());
    }

    public static boolean pickUp(String name) {
        GroundItem gi = GroundItems.closest(name);
        return (gi != null
                && gi.exists()
                && gi.canReach()
                && gi.interact("Take"));
    }

    public static boolean waitUri() {
        return waitNpc("Uri");
    }

    public static boolean handleUriEmote(Emote emote) {
        return (Emotes.doEmote(emote)
                && Sleep.sleepUntil(Utilities::waitUri, 3000)
                && Sleep.sleepUntil(Dialogues::canContinue, 3000)
                && Dialogues.clickContinue());
    }

    public static void fixCamera() {
        Camera.setZoom(15);
        Camera.keyboardRotateTo(Camera.getYaw(), Calculations.random(367, 383));
    }

    public static boolean walkWait(Tile tile) {
        fixCamera();
        while (!tile.equals(Players.getLocal().getTile())) {
            if (Walking.shouldWalk() && Walking.walkExact(tile))
                Sleep.sleep(600, 900);
        }
        return tile.equals(Players.getLocal().getTile());
    }

    public static boolean walkWait(Area area) {
        fixCamera();
        while (!area.contains(Players.getLocal().getTile())) {
            if (Walking.shouldWalk() && Walking.walk(area.getCenter()))
                Sleep.sleep(600, 900);
        }
        return area.contains(Players.getLocal().getTile());
    }

    public static boolean inventoryContainsAll(String[] items) {
        for (String item: items) {
            if (!Inventory.contains(item))
                return false;
        }
        return true;
    }

    public static boolean equipmentContainsAll(String[] items) {
        for (String item: items) {
            if (!Equipment.contains(item))
                return false;
        }
        return true;
    }

    public static boolean itemsInInvOrEquipped(String[] items) {
        // inventory
        for (String item: items) {
            if (!Inventory.contains(item) && !Equipment.contains(item))
                return false;
        }
        return true;
    }
}
