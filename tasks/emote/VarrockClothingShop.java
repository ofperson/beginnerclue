package tasks.emote;

import data.clues.EmoteClue;
import org.dreambot.api.methods.map.Tile;

public class VarrockClothingShop extends EmoteClueTask {

    public static final Tile BANK_TILE = new Tile(3185, 3436, 0);

    public static final EmoteClue CLUE = EmoteClue.CLUES.get(1);

    public VarrockClothingShop() {
        super(CLUE, BANK_TILE);
    }

}
