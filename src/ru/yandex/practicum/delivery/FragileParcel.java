package ru.yandex.practicum.delivery;

public class FragileParcel extends Parcel {

    @Override
    public void packageItem() {
        System.out.println("Посылка <<" + getDescription() + ">> обёрнута в защитную плёнку");
        printPackageMessage();
    }

    @Override
    public int calculateDeliveryCost() {
        return getWeight() * FRAGILE_BASE_COST;
    }

    public FragileParcel(String description, int weight, String deliveryAddress, int sendDay) {
        super(description, weight, deliveryAddress, sendDay);
    }

}