package data.clues;

import org.dreambot.api.methods.map.Tile;

import java.util.Arrays;
import java.util.List;

public class CrypticClue extends ClueScroll implements NpcClue {

    public static final List<CrypticClue> CLUES = Arrays.asList(
            new CrypticClue(
                    "In a village of barbarians, I am the one who guards the village from up high.",
                    "Cryptic: Hunding - Barbarian village tower",
                    "Hunding",
                    new Tile(3097, 3432, 2)
            ),
            new CrypticClue(
                    "In the place Duke Horacio calls home, talk to a man with a hat dropped by goblins.",
                    "Cryptic: Cook - Lumbridge Castle",
                    "Cook",
                    new Tile(3207, 3214, 0)
            ),
            new CrypticClue(
                    "Always walking around the castle grounds and somehow knows everyone's age.",
                    "Cryptic: Hans - Lumbridge Castle",
                    "Hans",
                    new Tile(3221, 3219, 0)
            ),
            new CrypticClue(
                    "Near the open desert I reside, to get past me you must abide. Go forward if you dare, for when you pass me, you'll be sweating by your hair.",
                    "Cryptic: Shantay - Shantay pass",
                    "Shantay",
                    new Tile(3304, 3123, 0)
            )
    );
    
    private String npc;

    public CrypticClue(String step, String description, String npc, Tile tile) {
        super(step, description, tile);
        this.npc = npc;
    }

    @Override
    public String getNpc() {
        return npc;
    }

    public static CrypticClue forStep(String step)  {
        for (CrypticClue clue : CLUES) {
            if (clue.getStep().equals(step)) {
                return clue;
            }
        }
        return null;
    }
    
}
