package ru.yandex.practicum.delivery;

public abstract class Parcel {
    private String description;
    private int weight;
    private String deliveryAddress;
    private int sendDay;

    public static final int STANDARD_BASE_COST = 2;
    public static final int PERISHABLE_BASE_COST = 3;
    public static final int FRAGILE_BASE_COST = 4;

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
