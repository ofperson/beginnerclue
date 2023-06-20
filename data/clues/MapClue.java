package data.clues;

import org.dreambot.api.methods.map.Tile;

import java.util.Arrays;
import java.util.List;

public class MapClue extends ClueScroll {
	public static List<MapClue> MAP_CLUES = Arrays.asList(
			new MapClue(346, new Tile(3166, 3361, 0),"West of the Champions' Guild"),
			new MapClue(347, new Tile(3290, 3374, 0), "Outside Varrock East Mine"),
			new MapClue(351, new Tile(3043, 3399, 0), "Stones north of Falador"),
			new MapClue(356, new Tile(3110, 3152, 0), "South side of the Wizard's Tower (DIS)"),
			new MapClue(348, new Tile(3091, 3227, 0), "South of Draynor Village Bank")
	);

	public MapClue(int widgetGroup, Tile location, String description) {
		super(null, description, location, widgetGroup);
		setSpadeNeeded(true);
	}

	public static MapClue forWidget(int widgetGroupID)  {
		for (MapClue clue : MAP_CLUES) {
			if (clue.getWidgetPath() == widgetGroupID) {
				return clue;
			}
		}
		return null;
	}
}