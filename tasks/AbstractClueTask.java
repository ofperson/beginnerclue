package tasks;

import data.Constants;
import data.Vars;
import data.clues.ClueScroll;
import framework.Priority;
import org.dreambot.api.methods.container.impl.Inventory;

public abstract class AbstractClueTask {

    protected ClueScroll clue;

    public AbstractClueTask(ClueScroll clue) {
        this.clue = clue;
    }

    public Priority priority() {
        return Priority.MEDIUM;
    }

    public boolean validate() {
        return Inventory.contains(Constants.CLUE_ID) &&
                (Vars.get().getClue().getStep() != null
                        ? Vars.get().getClue().getStep().equals(clue.getStep())
                        : Vars.get().getClue().getWidgetPath() == clue.getWidgetPath());
    }

    public ClueScroll getClue() {
        return clue;
    }

    protected void setClue(ClueScroll clue) {
        this.clue = clue;
    }

    public abstract void execute();

    @Override
    public String toString() {
        return clue.getDescription();
    }
}