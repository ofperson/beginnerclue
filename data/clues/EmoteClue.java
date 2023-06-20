package data.clues;



import org.dreambot.api.methods.emotes.Emote;
import org.dreambot.api.methods.map.Tile;

import java.util.Arrays;
import java.util.List;

public class EmoteClue extends ClueScroll implements NpcClue, ItemClue {

    public static final List<EmoteClue> CLUES = Arrays.asList(
            new EmoteClue(
                    "Spin at Flynn's Mace Shop.",
                    "Emote: SPIN - Falador mace shop",
                    "Uri",
                    new String[] {},
                    Emote.SPIN,
                    new Tile(2950, 3387, 0)
            ),
            new EmoteClue(
                    "Cheer at Iffie Nitter. Equip a chef hat and a red cape.",
                    "Emote: CHEER - Varrock clothing store",
                    "Uri",
                    new String[] {"Chef's hat", "Red cape"},
                    Emote.CHEER,
                    new Tile(3206, 3416, 0)
            ),
            new EmoteClue(
                    "Panic at Al Kharid mine.",
                    "Emote: PANIC - Al-Kharid mine",
                    "Uri",
                    new String[] {},
                    Emote.PANIC,
                    new Tile(3299, 3280, 0)
            ),
            new EmoteClue(
                    "Bow to Brugsen Bursen at the Grand Exchange.", //ForegroundColor[42,159,214][01:10:37] [Info] Clue: Blow a raspberry at Aris in her tent. Equip a gold ring and a gold necklace.
                    "Emote: BOW - Grand Exchange",
                    "Uri",
                    new String[] {},
                    Emote.BOW,
                    new Tile(3164, 3477, 0)
            ),
            new EmoteClue(
                    "Blow a raspberry at Aris in her tent. Equip a gold ring and a gold necklace.",
                    "Emote: RASPBERRY - Varrock fortune teller",
                    "Uri",
                    new String[] {"Gold ring", "Gold necklace"},
                    Emote.RASPBERRY,
                    new Tile(3203, 3424, 0)
            ),
            new EmoteClue(
                    "Clap at Bob's Brilliant Axes. Equip a bronze axe and leather boots.",
                    "Emote: CLAP - Lumbridge, Bob's Axe shop",
                    "Uri",
                    new String[] {"Bronze axe", "Leather boots"},
                    Emote.CLAP,
                    new Tile(3231, 3203, 0)
            )
    );
    
    private String npc;
    private String[] items;
    private Emote emote;

    public EmoteClue(String step, String description, String npc, String[] items, Emote emote, Tile tile) {
        super(step, description, tile);
        this.npc = npc;
        this.items = items;
        this.emote = emote;
    }

    @Override
    public String[] getItems() {
        return items;
    }

    @Override
    public String getNpc() {
        return null;
    }

    public Emote getEmote() {
        return emote;
    }

    public static EmoteClue forStep(String step)  {
        for (EmoteClue clue : CLUES) {
            if (clue.getStep().equals(step)) {
                return clue;
            }
        }
        return null;
    }
    
}