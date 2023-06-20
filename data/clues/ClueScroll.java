package data.clues;

import org.dreambot.api.methods.map.Area;
import org.dreambot.api.methods.map.Tile;

public abstract class ClueScroll {

    private boolean spadeNeeded = false;
    private String step = null;
    private int widgetPath = 203;

    private String description = null;

    private Tile tile = null;

    private Area area = null;

    public ClueScroll(String step, String description) {
        this.step = step;
        this.description = description;
    }

    public ClueScroll(String step, String description, Tile tile) {
        this.step = step;
        this.description = description;
        this.tile = tile;
        this.area = Area.generateArea(2, tile);
    }

    public ClueScroll(String step, String description, Tile tile, int widgetPath) {
        this.step = step;
        this.description = description;
        this.tile = tile;
        this.area = Area.generateArea(2, tile);
        this.widgetPath = widgetPath;
    }

    public boolean isSpadeNeeded() {
        return spadeNeeded;
    }

    public void setSpadeNeeded(boolean spadeNeeded) {
        this.spadeNeeded = spadeNeeded;
    }

    public String getStep() {
        return step;
    }

    public void setStep(String step) {
        this.step = step;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Tile getTile() {
        return tile;
    }

    public void setTile(Tile tile) {
        this.tile = tile;
    }

    public int getWidgetPath() {
        return widgetPath;
    }

    public void setWidgetPath(int widgetPath) {
        this.widgetPath = widgetPath;
    }

    public Area getArea() {
        return area;
    }

    public void setArea(Area area) {
        this.area = area;
    }

}