package data.clues;

import java.util.Arrays;
import java.util.List;

public class HotColdClue extends ClueScroll {

    public static List<HotColdClue> CLUES = Arrays.asList(
            new HotColdClue(
                    "Buried beneath the ground, who knows where it's found.<br><br>Lucky for you, a man called Reldo may have a clue.",
                    "Hot/Cold"
            )
    );
    
    public HotColdClue(String step, String description) {
        super(step, description);
        setSpadeNeeded(true);
    }

    public static HotColdClue forStep(String step)  {
        for (HotColdClue clue : CLUES) {
            if (clue.getStep().equals(step)) {
                return clue;
            }
        }
        return null;
    }
    
}
