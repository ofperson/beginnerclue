package tasks.anagram;

import data.Utilities;
import data.Vars;
import data.clues.AnagramClue;
import framework.Task;
import org.dreambot.api.methods.dialogues.Dialogues;
import org.dreambot.api.methods.interactive.Players;
import org.dreambot.api.utilities.Sleep;
import tasks.AbstractClueTask;

public class AnagramClueTask extends AbstractClueTask implements Task {

    public AnagramClueTask(AnagramClue clue) {
        super(clue);
    }

    @Override
    public void execute() {
        if (!clue.getArea().contains(Players.getLocal())) {
            if (Utilities.walkWait(clue.getArea())) {
                Sleep.sleep(600, 900);
            }
        } else {
            Vars.get().setStatus(clue.getDescription());
            if (Utilities.waitNpc(((AnagramClue) clue).getNpc())
                    && Sleep.sleepUntil(Dialogues::canContinue, 5000)
                    && Dialogues.clickContinue()) {
                Sleep.sleep(600, 900);
                Vars.get().setClue(null);
            }
        }
    }

}