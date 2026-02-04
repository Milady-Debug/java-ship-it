package ru.yandex.practicum.delivery;

public class StandardParcel extends Parcel{

    @Override
    public void packageItem(){
        printPackageMessage();
    }

    @Override
    public int calculateDeliveryCost() {
        return getWeight() * STANDARD_BASE_COST;
    }

    public StandardParcel(String description, int weight, String deliveryAddress, int sendDay) {
        super(description, weight, deliveryAddress, sendDay);
    }

}