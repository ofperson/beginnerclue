package tasks.cluemanagement;

import data.Constants;
import data.Vars;
import data.clues.*;
import framework.Priority;
import framework.Task;
import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.methods.input.Keyboard;
import org.dreambot.api.methods.tabs.Tab;
import org.dreambot.api.methods.tabs.Tabs;
import org.dreambot.api.methods.widget.Widget;
import org.dreambot.api.methods.widget.Widgets;
import org.dreambot.api.utilities.Logger;
import org.dreambot.api.utilities.Sleep;
import org.dreambot.api.wrappers.items.Item;
import org.dreambot.api.wrappers.widgets.WidgetChild;

import java.util.ArrayList;
import java.util.List;

public class ClueCheck implements Task {

    @Override
    public Priority priority() {
        return Priority.HIGH;
    }

    @Override
    public boolean validate() {
        return Inventory.contains(Constants.CLUE_ID) && Vars.get().getClue() == null;
    }

    private static List<Integer> widgetIds = null;

    public ClueCheck() {
        widgetIds = new ArrayList<>();
        widgetIds.add(203);
        widgetIds.add(346);
        widgetIds.add(347);
        widgetIds.add(348);
        widgetIds.add(351);
        widgetIds.add(356);
    }

    @Override
    public void execute() {
        if (clickClue() && Sleep.sleepUntil(() -> getClueWidget() != null, 5000)) {
            Widget w = getClueWidget();

            if (w.getID() == 203) {
                WidgetChild clueScreen = w.getChild(2);
                String text = clueScreen.getText();
                if (text != null) {
                    Logger.info("Clue: " + text);
                    ClueScroll scroll = fromStep(text);
                    if (scroll == null) {
                        Logger.error("Clue not supported: " + text);
                        Vars.get().setRunning(false);
                        return;
                    }
                    Vars.get().setClue(scroll);
                } else {
                    Logger.error("Clue not found!");
                    Vars.get().setRunning(false);
                }
            } else {
                // map clue
                ClueScroll scroll = MapClue.forWidget(w.getID());
                if (scroll == null) {
                    Logger.error("Found a non-supported map clue!");
                    Vars.get().setRunning(false);
                    return;
                }
                Vars.get().setClue(scroll);
                Logger.info("Currently set clue: " + Vars.get().getClue().getDescription());
            }
            Sleep.sleep(600, 900);
            Keyboard.closeInterfaceWithESC();
        }
    }

    private boolean clickClue() {
        if (!Tabs.isOpen(Tab.INVENTORY)) {
            Tabs.open(Tab.INVENTORY);
        }
        Item item = Inventory.get(Constants.CLUE_ID);
        return item != null && item.isValid() && Inventory.interact(item.getName());
    }

    public static Widget getClueWidget() {
        for (Integer id: widgetIds) {
            if (isWidgetVisible(id)) {
                return Widgets.getWidget(id);
            }
        }
        return null;
    }

    public static boolean isWidgetVisible(int indexPath) {
        Widget w = Widgets.getWidget(indexPath);
        return w != null && w.isVisible();
    }

    public ClueScroll fromStep(String step) {
        AnagramClue anagramClue = AnagramClue.forStep(step);
        if (anagramClue != null)
            return anagramClue;

        CrypticClue crypticClue = CrypticClue.forStep(step);
        if (crypticClue != null)
            return crypticClue;

        CharlieClue charlieClue = CharlieClue.forStep(step);
        if (charlieClue != null)
            return charlieClue;

        EmoteClue emoteClue = EmoteClue.forStep(step);
        if (emoteClue != null)
            return emoteClue;

        HotColdClue hotColdClue = HotColdClue.forStep(step);
        if (hotColdClue != null)
            return hotColdClue;

        return null;
    }

    @Override
    public String toString() {
        return "Determining current clue...";
    }

}