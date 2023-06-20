package data;

import data.clues.ClueScroll;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Vars {

    private boolean isRunning = true;

    private String status = null;

    private ClueScroll clue = null;

    private int initialCasketCount = 0;
    private int casketCount = 0;

    // gui options
    private ClueMonster monster;
    private Set<String> equipment;
    private boolean restockCharlie = false;
    private int buyPlusTicks = 0;
    private int charlieRestockAmount = 0;
    private boolean buyEmoteItems = false;
    private int buyEmotePlusTicks = 0;

    private List<RestockJob> restockJobs = new ArrayList<>();

    private boolean getDevice = false;

    private static Vars instance = new Vars();

    public static Vars get() {
        return instance;
    }

    public boolean isRunning() {
        return isRunning;
    }

    public void setRunning(boolean running) {
        isRunning = running;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ClueScroll getClue() {
        return clue;
    }

    public void setClue(ClueScroll clue) {
        this.clue = clue;
    }

    public int getInitialCasketCount() {
        return initialCasketCount;
    }

    public void setInitialCasketCount(int initialCasketCount) {
        this.initialCasketCount = initialCasketCount;
    }

    public int getCasketCount() {
        return casketCount;
    }

    public void setCasketCount(int casketCount) {
        this.casketCount = casketCount;
    }

    public ClueMonster getMonster() {
        return monster;
    }

    public void setMonster(ClueMonster monster) {
        this.monster = monster;
    }

    public Set<String> getEquipment() {
        return equipment;
    }

    public void setEquipment(Set<String> equipment) {
        this.equipment = equipment;
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

    public List<RestockJob> getRestockJobs() {
        return restockJobs;
    }

    public void addRestockJob(RestockJob job) {
        restockJobs.add(job);
    }

    public void removeRestockJob(RestockJob job) {
        restockJobs.remove(job);
    }

    public boolean isGetDevice() {
        return getDevice;
    }

    public void setGetDevice(boolean getDevice) {
        this.getDevice = getDevice;
    }
}