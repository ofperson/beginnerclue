package tasks.cryptic;

import data.clues.CrypticClue;
import org.dreambot.api.methods.map.Area;

public class CookTalk extends CrypticClueTask {

    public static final CrypticClue CLUE = CrypticClue.CLUES.get(1);

    public CookTalk() {
        super(CLUE);
        clue.setArea(Area.generateArea(5, clue.getTile()));
    }

}
