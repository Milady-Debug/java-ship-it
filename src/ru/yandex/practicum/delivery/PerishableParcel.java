package ru.yandex.practicum.delivery;

public class PerishableParcel extends Parcel{
    private int timeToLive;

    @Override
    public int calculateDeliveryCost() {
        return getWeight() * PERISHABLE_BASE_COST;
    }

    @Override
    public void packageItem(){
        printPackageMessage();
    }

    public PerishableParcel(String description, int weight, String deliveryAddress, int sendDay,  int timeToLive) {
        super(description, weight, deliveryAddress, sendDay);
        this.timeToLive = timeToLive;
    }

    public boolean isExpired(int currentDay){
        if((getSendDay() + timeToLive) >= currentDay){
            return false;
        }
        return true;
    }
}
