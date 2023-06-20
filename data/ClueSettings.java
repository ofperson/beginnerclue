package data;

import java.io.Serializable;

public class ClueSettings implements Serializable {

    private static final long serialVersionUID = 1L;

    private String monster;
    private String location;
    private String weapon;
    private String shield;
    private String chest;
    private String legs;
    private String helmet;
    private String cape;
    private String glove;
    private String boots;
    private String amulet;
    private String ring;
    private boolean restockCharlie = true;
    private int buyPlusTicks = 30;
    private int charlieRestockAmount = 25;
    private boolean buyEmoteItems = true;
    private int buyEmotePlusTicks = 30;

    public ClueSettings() {
        // empty constructor
    }

    public String getMonster() {
        return monster;
    }

    public void setMonster(String monster) {
        this.monster = monster;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getWeapon() {
        return weapon;
    }

    public void setWeapon(String weapon) {
        this.weapon = weapon;
    }

    public String getShield() {
        return shield;
    }

    public void setShield(String shield) {
        this.shield = shield;
    }

    public String getChest() {
        return chest;
    }

    public void setChest(String chest) {
        this.chest = chest;
    }

    public String getLegs() {
        return legs;
    }

    public void setLegs(String legs) {
        this.legs = legs;
    }

    public String getHelmet() {
        return helmet;
    }

    public void setHelmet(String helmet) {
        this.helmet = helmet;
    }

    public String getCape() {
        return cape;
    }

    public void setCape(String cape) {
        this.cape = cape;
    }

    public String getGlove() {
        return glove;
    }

    public void setGlove(String glove) {
        this.glove = glove;
    }

    public String getBoots() {
        return boots;
    }

    public void setBoots(String boots) {
        this.boots = boots;
    }

    public String getAmulet() {
        return amulet;
    }

    public void setAmulet(String amulet) {
        this.amulet = amulet;
    }

    public String getRing() {
        return ring;
    }

    public void setRing(String ring) {
        this.ring = ring;
    }

    public boolean isRestockCharlie() {
        return restockCharlie;
    }

    public void setRestockCharlie(boolean restockCharlie) {
        this.restockCharlie = restockCharlie;
    }

    public int getBuyPlusTicks() {
        return buyPlusTicks;
    }

    public void setBuyPlusTicks(int buyPlusTicks) {
        this.buyPlusTicks = buyPlusTicks;
    }

    public int getCharlieRestockAmount() {
        return charlieRestockAmount;
    }

    public void setCharlieRestockAmount(int charlieRestockAmount) {
        this.charlieRestockAmount = charlieRestockAmount;
    }

    public boolean isBuyEmoteItems() {
        return buyEmoteItems;
    }

    public void setBuyEmoteItems(boolean buyEmoteItems) {
        this.buyEmoteItems = buyEmoteItems;
    }

    public int getBuyEmotePlusTicks() {
        return buyEmotePlusTicks;
    }

    public void setBuyEmotePlusTicks(int buyEmotePlusTicks) {
        this.buyEmotePlusTicks = buyEmotePlusTicks;
    }
}
