package data.clues;

import org.dreambot.api.methods.map.Tile;

import java.util.Arrays;
import java.util.List;

public class CharlieClue extends ClueScroll implements NpcClue, ItemClue {

    public static final List<CharlieClue> CLUES = Arrays.asList(
            new CharlieClue("Talk to Charlie the Tramp in Varrock.",
                    "Charlie the Tramp",
                    "Charlie the Tramp",
                    new String[] {
                            "Iron ore",
                            "Iron dagger",
                            "Raw herring",
                            "Raw trout",
                            "Trout",
                            "Pike",
                            "Leather body",
                            "Leather chaps"
                    },
                    new Tile(3209, 3392, 0)
            )
    );
    
    private String npc;
    private String[] items;
    
    public CharlieClue(String step, String description, String npc, String[] items, Tile tile) {
        super(step, description, tile);
        this.npc = npc;
        this.items = items;
    }

    @Override
    public String getNpc() {
        return npc;
    }

    @Override
    public String[] getItems() {
        return new String[0];
    }

    public static CharlieClue forStep(String step)  {
        for (CharlieClue clue : CLUES) {
            if (clue.getStep().equals(step)) {
                return clue;
            }
        }
        return null;
    }
    
}