package tasks.emote;

import data.clues.EmoteClue;
import org.dreambot.api.methods.map.Tile;

public class ArisRaspberry extends EmoteClueTask {

    public static final Tile BANK_TILE = new Tile(3185, 3436, 0);

    public static final EmoteClue CLUE = EmoteClue.CLUES.get(4);

    public ArisRaspberry() {
        super(CLUE, BANK_TILE);
    }

}