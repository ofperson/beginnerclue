package tasks.hotcold;

import org.dreambot.api.methods.map.Tile;

import java.awt.*;

import static tasks.hotcold.HotColdArea.MISTHALIN;


public enum HotColdLocation {

	DRAYNOR_MANOR_MUSHROOMS(HotColdType.BEGINNER, new Tile(3096, 3379, 0), MISTHALIN, "Patch of mushrooms just northwest of Draynor Manor"),
	DRAYNOR_WHEAT_FIELD(HotColdType.BEGINNER, new Tile(3120, 3282, 0), MISTHALIN, "Inside the wheat field next to Draynor Village"),
	ICE_MOUNTAIN(HotColdType.BEGINNER, new Tile(3007, 3475, 0), MISTHALIN, "Atop Ice Mountain"),
	LUMBRIDGE_COW_FIELD(HotColdType.BEGINNER,  new Tile(3174, 3336, 0), MISTHALIN, "Cow field north of Lumbridge"),
	NORTHEAST_OF_AL_KHARID_MINE(HotColdType.BEGINNER, new Tile(3332, 3313, 0), MISTHALIN, "Northeast of Al Kharid Mine"),
	;

	private final HotColdType type;
	private final Tile worldTile;
	private final HotColdArea hotColdArea;
	private final String area;

	public enum HotColdType {
		BEGINNER;
	}

	HotColdLocation(HotColdType type, Tile worldTile, HotColdArea hotColdArea, String area) {
		this.type = type;
		this.worldTile = worldTile;
		this.hotColdArea = hotColdArea;
		this.area = area;
	}

	public Rectangle getRect() {
		final int digRadius = HotColdTemperature.BEGINNER_VISIBLY_SHAKING.getMaxDistance();
		return new Rectangle(worldTile.getX() - digRadius, worldTile.getY() - digRadius, digRadius * 2 + 1, digRadius * 2 + 1);
	}

	public boolean isBeginnerClue()
	{
		return type == HotColdType.BEGINNER;
	}

	public HotColdType getType() {
		return type;
	}

	public Tile getTile() {
		return worldTile;
	}

	public HotColdArea getHotColdArea() {
		return hotColdArea;
	}

	public String getArea() {
		return area;
	}

}