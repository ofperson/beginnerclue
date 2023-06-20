package data;

import org.dreambot.api.methods.grandexchange.LivePrices;

import java.util.Objects;

public class RestockJob {

    private String item;
    private Integer amount;

    public RestockJob(String item, Integer amount) {
        this.item = item;
        this.amount = amount;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public double getCost() {
        double tickPlus = Vars.get().getBuyPlusTicks() == 0 ? 1 : (1 + (0.05 * Vars.get().getBuyPlusTicks()));
        return LivePrices.get(item) * tickPlus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RestockJob that = (RestockJob) o;
        return Objects.equals(item, that.item) && Objects.equals(amount, that.amount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(item, amount);
    }
}