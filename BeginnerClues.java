import data.Constants;
import data.Vars;
import form.ClueFrame;
import framework.Task;
import framework.TaskSet;
import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.methods.input.Camera;
import org.dreambot.api.methods.map.Tile;
import org.dreambot.api.script.AbstractScript;
import org.dreambot.api.script.Category;
import org.dreambot.api.script.ScriptManager;
import org.dreambot.api.script.ScriptManifest;
import org.dreambot.api.utilities.Logger;
import org.dreambot.api.utilities.Sleep;
import org.dreambot.api.wrappers.items.Item;
import paint.CustomPaint;
import paint.PaintInfo;
import tasks.anagram.*;
import tasks.charlie.Tramp;
import tasks.cluemanagement.ClueCheck;
import tasks.cluemanagement.NewClue;
import tasks.cryptic.BarbVillageTower;
import tasks.cryptic.CookTalk;
import tasks.cryptic.HansTalk;
import tasks.cryptic.Shantay;
import tasks.emote.*;
import tasks.hotcold.HotCold;
import tasks.map.*;
import tasks.restock.Restocker;
import tasks.restock.StrangeDevice;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.TimeUnit;

@ScriptManifest(
        name = "Beginner Clue Solver",
        description = "Hunts for and solves all beginner clues.",
        author = "ironan11",
        version = 1.01,
        category = Category.MINIGAME
)
public class BeginnerClues extends AbstractScript implements PaintInfo {

    private TaskSet tasks;

    private ClueFrame gui;

    private long timeBegan;
    private long timeRan;

    @Override
    public void onStart() {
        timeBegan = System.currentTimeMillis();

        Vars.get().setStatus("Setup...");
        SwingUtilities.invokeLater(() -> {
            try {
                gui = new ClueFrame();
                gui.setVisible(true);
            } catch (Exception e) {
                Logger.error("Error loading GUI!");
                Vars.get().setRunning(false);
            }
        });
        Sleep.sleepUntil(() -> gui != null && gui.isVisible(), 2000000000);
        while(gui.isVisible())
            Sleep.sleep(500);

        Vars.get().setStatus("Setup finished!");

        tasks = new TaskSet(
                // clue management
                new NewClue(),
                new ClueCheck(),
                // map clues
                new ChampionsGuild(),
                new FaladorStones(),
                new VarrockMine(),
                new DraynorBank(),
                new WizardsTower(),
                // emote clues
                new SpinMaceShop(),
                new VarrockClothingShop(),
                new PanicMine(),
                new BrugsenBow(),
                new ArisRaspberry(),
                new BobClap(),
                // cryptic clues
                new BarbVillageTower(),
                new CookTalk(),
                new HansTalk(),
                new Shantay(),
                // anagram clues
                new Apothecary(),
                new PortSarimAxeShop(),
                new Doric(),
                new Veronica(),
                new Ranael(),
                new Archmage(),
                new Fortunato(),
                new Gertrude(),
                new Hairdresser(),
                // hot/cold clues
                new HotCold(),
                // tramp clues
                new Tramp(),
                // restocking
                new Restocker(),
                new StrangeDevice()
        );

        // get initial casket count
        updateCasketCount();

        Camera.setZoom(15);
    }

    @Override
    public int onLoop() {
        if (!Vars.get().isRunning())
            ScriptManager.getScriptManager().stop();

        Task task = tasks.getValidTask();

        if (task != null) {
            Vars.get().setStatus(task.toString());
            task.execute();
        }

        // check for new casket
        calcCasketsGained();

        return Calculations.random(20, 40);
    }

    @Override
    public void onExit() {
        if (gui != null && gui.isVisible())
            gui.dispose();
    }

    public void updateCasketCount() {
        Item casket = Inventory.get(Constants.CASKET);
        int casketCount = casket != null ? casket.getAmount() : 0;
        Logger.info("Starting with casket count: " + casketCount);
        Vars.get().setInitialCasketCount(casketCount);
    }

    public void calcCasketsGained() {
        Item casket = Inventory.get(Constants.CASKET);
        int curr = casket != null ? casket.getAmount() : 0;
        boolean casketGained = curr > (Vars.get().getCasketCount() + Vars.get().getInitialCasketCount());

        if (casketGained)
            Vars.get().setCasketCount(curr - Vars.get().getInitialCasketCount());
    }

    private String ft(long duration) {
        long days = TimeUnit.MILLISECONDS.toDays(duration);
        long hours = TimeUnit.MILLISECONDS.toHours(duration)
                - TimeUnit.DAYS.toHours(TimeUnit.MILLISECONDS.toDays(duration));
        long minutes = TimeUnit.MILLISECONDS.toMinutes(duration)
                - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS
                .toHours(duration));
        long seconds = TimeUnit.MILLISECONDS.toSeconds(duration)
                - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS
                .toMinutes(duration));

        if (days > 0)
            return String.format("%02d:%02d:%02d:%02d", days, hours, minutes, seconds);
        else
            return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }

    @Override
    public String[] getPaintInfo() {
        return new String[] {
                getManifest().name() + " v" + getManifest().version(),
                "Time Ran: " + ft(timeRan),
                "Status: " + Vars.get().getStatus(),
                "Caskets Gained: " + Vars.get().getCasketCount()
        };
    }

    private final CustomPaint CUSTOM_PAINT = new CustomPaint(this,
            CustomPaint.PaintLocations.TOP_LEFT_PLAY_SCREEN, new Color[]{new Color(255, 251, 255)},
            "Trebuchet MS",
            new Color[]{new Color(50, 50, 50, 175)},
            new Color[]{new Color(28, 28, 29)},
            1, false, 5, 3, 0);

    private final RenderingHints aa = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

    @Override
    public void onPaint(Graphics g) {
        timeRan = System.currentTimeMillis() - this.timeBegan;

        Graphics2D gg = (Graphics2D) g;
        gg.setRenderingHints(aa);

        CUSTOM_PAINT.paint(gg);
    }

}