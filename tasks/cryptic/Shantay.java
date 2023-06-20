package tasks.cryptic;

import data.clues.CrypticClue;
import org.dreambot.api.methods.map.Area;

public class Shantay extends CrypticClueTask {

    public static final CrypticClue CLUE = CrypticClue.CLUES.get(3);

    public Shantay() {
        super(CLUE);
        clue.setArea(Area.generateArea(5, clue.getTile()));
    }

}
