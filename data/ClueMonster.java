package data;

import org.dreambot.api.methods.map.Area;
import org.dreambot.api.methods.map.Tile;

public enum ClueMonster {

    GOBLIN("Goblin", new Area(new Tile(3233, 3270, 0),
            new Tile(3241, 3229, 0),
            new Tile(3267, 3211, 0),
            new Tile(3267, 3223, 0),
            new Tile(3268, 3226, 0),
            new Tile(3268, 3232, 0),
            new Tile(3267, 3234, 0),
            new Tile(3267, 3238, 0),
            new Tile(3266, 3240, 0),
            new Tile(3266, 3247, 0),
            new Tile(3265, 3248, 0),
            new Tile(3265, 3255, 0),
            new Tile(3253, 3255, 0),
            new Tile(3253, 3270, 0))),
    MAN("Man", new Area(new Tile(3231, 3214, 0),
            new Tile(3230, 3218, 0),
            new Tile(3230, 3221, 0),
            new Tile(3242, 3221, 0),
            new Tile(3242, 3216, 0),
            new Tile(3240, 3216, 0),
            new Tile(3240, 3212, 0),
            new Tile(3238, 3212, 0),
            new Tile(3250, 3195, 0),
            new Tile(3224, 3201, 0))),
    COW("Cow", new Area(new Tile(3192, 3303, 0),
            new Tile(3192, 3281, 0),
            new Tile(3214, 3284, 0),
            new Tile(3214, 3294, 0),
            new Tile(3210, 3304, 0))),
    GIANT_FROG("Giant frog", new Area(3182, 3196, 3212, 3161));

    private String name;
    private Area area;
    /*new Area(new Tile(3237, 3263, 0),
            new Tile(3253, 3263, 0),
            new Tile(3253, 3255, 0),
            new Tile(3265, 3255, 0),
            new Tile(3269, 3228, 0),
            new Tile(3267, 3210, 0),
            new Tile(3249, 3222, 0),
            new Tile(3239, 3236, 0))*/
    ClueMonster(String name, Area area) {
        this.name = name;
        this.area = area;
    }

    public static ClueMonster fromName(String name) {
        for (ClueMonster cm: ClueMonster.values()) {
            if (cm.name.equals(name))
                return cm;
        }
        return null;
    }

    public String getName() {
        return name;
    }

    public Area getArea() {
        return area;
    }
}
