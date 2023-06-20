package tasks.anagram;

import data.clues.AnagramClue;

public class Archmage extends AnagramClueTask {

    public static AnagramClue CLUE = AnagramClue.CLUES.get(5);

    public Archmage() {
        super(CLUE);
    }
}
