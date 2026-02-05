package ru.yandex.practicum.delivery;

import java.util.ArrayList;
import java.util.List;

public class ParcelBox<T extends Parcel>{
    private List<T> parcels = new ArrayList<>();
    private int maxWeight;

    public ParcelBox(int maxWeight) {
        this.maxWeight = maxWeight;
    }

    public int getMaxWeight() {
        return maxWeight;
    }

    public void addParcel(T parcel){
        if(getTotalWeight() + parcel.getWeight() > maxWeight){
            System.out.println("Превышен максимальный вес посылки");
            return;
        }
        parcels.add(parcel);
        System.out.println("Посылка добавлена в коробку.");
    }

    public List<T> getAllParcels() {
        return new ArrayList<>(parcels);
    }

    public int getTotalWeight(){
        int totalWeight = 0;
        for(T oneParsel : parcels){
            totalWeight += oneParsel.getWeight();
        }
        return totalWeight;
    }
}
