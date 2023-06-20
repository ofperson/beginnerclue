package tasks.emote;

import data.clues.EmoteClue;
import org.dreambot.api.methods.map.Tile;

public class BobClap extends EmoteClueTask {

    public static final Tile BANK_TILE = new Tile(3208, 3219, 2);

    public static final EmoteClue CLUE = EmoteClue.CLUES.get(5);

    public BobClap() {
        super(CLUE, BANK_TILE);
    }

}
