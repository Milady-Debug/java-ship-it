package ru.yandex.practicum.delivery;

public abstract class Parcel {
    private String description;
    private int weight;
    private String deliveryAddress;
    private int sendDay;

    private static final int STANDARD_BASE_COST = 2;
    private static final int PERISHABLE_BASE_COST = 3;
    private static final int FRAGILE_BASE_COST = 4;

    public int getStandardBaseCost(){
        return STANDARD_BASE_COST;
    }

    public int getPerishableBaseCost(){
        return PERISHABLE_BASE_COST;
    }

    public int getFragileBaseCost(){
        return FRAGILE_BASE_COST;
    }

    public Parcel(String description, int weight, String deliveryAddress, int sendDay) {
        this.description = description;
        this.weight = weight;
        this.deliveryAddress = deliveryAddress;
        this.sendDay = sendDay;
    }

    public String getDescription() {
        return description;
    }

    public int getWeight() {
        return weight;
    }

    public int getSendDay() {
        return sendDay;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }


    protected void printPackageMessage() {
        System.out.println("Посылка <<" + description + ">> упакована");
    }

    public abstract void packageItem();

    public void deliver(){
        System.out.println("Посылка <<" + description + ">> доставлена по адресу " + deliveryAddress);
    }

    public abstract int calculateDeliveryCost();

}
