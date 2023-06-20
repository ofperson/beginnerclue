package tasks.cryptic;

import data.clues.CrypticClue;
import org.dreambot.api.methods.map.Area;

public class HansTalk extends CrypticClueTask {

    public static final CrypticClue CLUE = CrypticClue.CLUES.get(2);

    public HansTalk() {
        super(CLUE);
        clue.setArea(Area.generateArea(12, clue.getTile()));
    }

}