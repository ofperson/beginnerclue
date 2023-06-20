package framework;

import org.dreambot.api.input.Mouse;
import org.dreambot.api.methods.tabs.Tab;
import org.dreambot.api.methods.tabs.Tabs;
import org.dreambot.api.methods.widget.Widget;
import org.dreambot.api.methods.widget.Widgets;
import org.dreambot.api.utilities.Sleep;
import org.dreambot.api.wrappers.widgets.WidgetChild;

import java.util.Optional;

public enum Emote {
    YES(0),
    NO(1),
    BOW(2),
    ANGRY(3),
    THINK(4),
    WAVE(5),
    SHRUG(6),
    CHEER(7),
    BECKON(8),
    LAUGH(9),
    JUMP_FOR_JOY(10),
    YAWN(11),
    DANCE(12),
    JIG(13),
    SPIN(14),
    HEAD_BANG(15),
    CRY(16),
    BLOW_KISS(17),
    PANIC(18),
    RASPBERRY(19),
    CLAP(20),
    SALUTE(21),
    GOBLIN_BOW(22),
    GOBLIN_SALUTE(23),
    GLASS_BOX(24),
    CLIMB_ROPE(25),
    LEAN(26),
    GLASS_WALL(27),
    IDEA(28),
    STAMP(29),
    FLAP(30),
    SLAP_HEAD(31),
    ZOMBIE_WALK(32),
    ZOMBIE_DANCE(33),
    SCARED(34),
    RABBIT_HOP(35),
    SIT_UP(36),
    PUSH_UP(37),
    STAR_JUMP(38),
    JOG(39),
    ZOMBIE_HAND(40),
    HYBERMOBILE_DRINKER(41),
    SKILL_CAPE(42),
    AIR_GUITAR(43),
    URI_TRANSFORM(44),
    SMOOTH_DANCE(45),
    CRAZY_DANCE(46),
    PREMIER_SHIELD(47),
    EXPLORE(48);

    private int widget;

    public int getWidget() {
        return widget;
    }

    public boolean doEmote() {
        WidgetChild tab = Widgets.get(216, 1);
        WidgetChild scroll = Widgets.get(216, 2);
        WidgetChild emoteInterface = Widgets.get(216, 2, widget);

        if (emoteInterface != null) {

            if (!Tabs.isOpen(Tab.EMOTES)) {
                Tabs.open(Tab.EMOTES);
            }

            if (!emoteInterface.isVisible()) {
                Mouse.move(emoteInterface.getRectangle());
                Sleep.sleep(500, 800);
                if (emoteInterface.getY() > 430)
                    Mouse.scrollDownUntil(3000, () -> emoteInterface.isVisible());
                else
                    Mouse.scrollUpUntil(3000, () -> emoteInterface.isVisible());
            }


            return emoteInterface.interact();
        }
        return false;
    }

    Emote(int widgets) {
        this.widget = widgets;
    }
}