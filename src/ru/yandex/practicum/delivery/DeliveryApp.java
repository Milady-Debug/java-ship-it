package ru.yandex.practicum.delivery;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DeliveryApp {

    private static final Scanner scanner = new Scanner(System.in);
    private static List<Parcel> allParcels = new ArrayList<>();
    private static List<Trackable> trackableParcels = new ArrayList<>();

    private static ParcelBox<StandardParcel> standardParcelBox;
    private static ParcelBox<FragileParcel> fragileParcelBox;
    private static ParcelBox<PerishableParcel> perishableParcelBox;

    public static void main(String[] args) {
        initializeWeightOfBoxes();
        boolean running = true;
        while (running) {
            showMenu();
            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1:
                    addParcel();
                    break;
                case 2:
                    sendParcels();
                    break;
                case 3:
                    calculateCosts();
                    break;
                case 4:
                    trackParcels();
                    break;
                case 5:
                    showWhatBoxContains();
                    break;
                case 0:
                    running = false;
                    break;
                default:
                    System.out.println("Неверный выбор.");
            }
        }
    }


    private static void showMenu() {
        System.out.println("Выберите действие:");
        System.out.println("1 — Добавить посылку");
        System.out.println("2 — Отправить все посылки");
        System.out.println("3 — Посчитать стоимость доставки");
        System.out.println("4 — Изменить адрес доставки(для хрупких)");
        System.out.println("5 — Показать содержимое коробки");
        System.out.println("0 — Завершить");
    }

    // реализуйте методы ниже

    private static void addParcel() {
        System.out.println("Укажите тип посылки: " +
                "1 - стандартная посылка; " +
                "2 - хрупкая посылка; " +
                "3 - скоропортящаяся посылка.");
        int choiceOfType = Integer.parseInt(scanner.nextLine());

        System.out.print("Введите описание посылки: ");
        String description = scanner.nextLine();

        System.out.print("Введите вес посылки (целое число): ");
        int weight = Integer.parseInt(scanner.nextLine());

        System.out.print("Введите адрес доставки: ");
        String address = scanner.nextLine();

        System.out.print("Введите день отправки (число месяца): ");
        int sendDay = Integer.parseInt(scanner.nextLine());

        Parcel parcel = null;

        switch (choiceOfType) {
            case 1:
                StandardParcel standardParcel = new StandardParcel(description, weight, address, sendDay);
                parcel = standardParcel;
                if(standardParcelBox.getTotalWeight() + standardParcel.getWeight() > standardParcelBox.getMaxWeight()){
                    System.out.println("Превышен максимальный вес коробки");
                    return;
                }
                standardParcelBox.addParcel(standardParcel);
                allParcels.add(parcel);
                System.out.println("Посылка успешно добавлена!");
                break;
            case 2:
                FragileParcel fragileParcel = new FragileParcel(description, weight, address, sendDay);
                parcel = fragileParcel;
                fragileParcelBox.addParcel(fragileParcel);
                if(fragileParcelBox.getTotalWeight() + parcel.getWeight() > fragileParcelBox.getMaxWeight()) {
                    System.out.println("Превышен максимальный вес коробки");
                    return;
                }
                trackableParcels.add(fragileParcel);
                allParcels.add(parcel);
                System.out.println("Посылка успешно добавлена!");
                break;
            case 3:
                System.out.print("Введите срок годности (в днях): ");
                int timeToLive = Integer.parseInt(scanner.nextLine());
                PerishableParcel perishableParcel = new PerishableParcel(
                        description, weight, address, sendDay, timeToLive
                );
                parcel = perishableParcel;
                if(perishableParcelBox.getTotalWeight() + parcel.getWeight() > perishableParcelBox.getMaxWeight()){
                    System.out.println("Превышен максимальный вес коробки");
                    return;
                }
                perishableParcelBox.addParcel(perishableParcel);
                allParcels.add(parcel);
                System.out.println("Посылка успешно добавлена!");
                break;
            default:
                System.out.println("Такого типа нет.");
                return;
        }
    }

    private static void sendParcels() {
        if (allParcels.isEmpty()) {
            System.out.println("Нет посылок для отправки.");
            return;
        }

        System.out.print("Введите текущий день месяца: ");
        int currentDay = Integer.parseInt(scanner.nextLine());

        for (Parcel parcel : allParcels) {
            parcel.packageItem();
            if (parcel instanceof PerishableParcel) { //Если учитывать, что содержимое помылки испортилось
                PerishableParcel perishable = (PerishableParcel) parcel;
                if (perishable.isExpired(currentDay)) {
                    System.out.println("Посылка испортилась и не может быть отправлена!");
                    continue;
                } else {
                    System.out.println("Можно отправлять.");
                }
            }
            System.out.println("Доставка.");
            parcel.deliver();
        }
    }

    private static void calculateCosts() {

        if (allParcels.isEmpty()) {
            System.out.println("Нет посылок для расчета.");
            return;
        }
        int totalCost = 0;
        for (Parcel parcel : allParcels) {
            int cost = parcel.calculateDeliveryCost();
            totalCost += cost;
        }
        System.out.println("Общая стоимость доставки всех посылок: " + totalCost + " руб.");
    }

    private static void trackParcels(){
        if(trackableParcels.isEmpty()){
            System.out.println("Нет посылок, поддерживающих трекинг.");
            return;
        }
        System.out.print("Введите новое местоположение посылок: ");
        String newLocation = scanner.nextLine();

        for (Trackable trackable : trackableParcels) {
            trackable.reportStatus(newLocation);
        }
    }

    private static void initializeWeightOfBoxes() {

        System.out.println("Введите максимальный вес для коробки стандартных посылок: ");
        int standardMaxWeight = Integer.parseInt(scanner.nextLine());
        standardParcelBox = new ParcelBox<>(standardMaxWeight);

        System.out.println("Введите максимальный вес для коробки хрупких посылок: ");
        int fragileMaxWeight = Integer.parseInt(scanner.nextLine());
        fragileParcelBox = new ParcelBox<>(fragileMaxWeight);

        System.out.println("Введите максимальный вес для коробки скоропортящихся посылок: ");
        int perishableMaxWeight = Integer.parseInt(scanner.nextLine());
        perishableParcelBox = new ParcelBox<>(perishableMaxWeight);
    }

    private static <T extends Parcel> void boxContents(ParcelBox<T> box) {
        List<T> parcels = box.getAllParcels();
        if (parcels.isEmpty()) {
            System.out.println("Коробка пуста.");
        } else {
            System.out.println("Список посылок:");
            for (int i = 0; i < parcels.size(); i++) {
                Parcel parcel = parcels.get(i);
                System.out.println(parcel.getDescription() +
                        " (вес: " + parcel.getWeight() + " кг, " +
                        "адрес: " + parcel.getDeliveryAddress() + ")");
            }
        }
    }

    private static void showWhatBoxContains(){
        System.out.println("Выберите коробку для просмотра:");
        System.out.println("1 - Коробка стандартных посылок");
        System.out.println("2 - Коробка хрупких посылок");
        System.out.println("3 - Коробка скоропортящихся посылок");

        int choice = Integer.parseInt(scanner.nextLine());

        switch (choice) {
            case 1:
                boxContents(standardParcelBox);
                break;
            case 2:
                boxContents(fragileParcelBox);
                break;
            case 3:
                boxContents(perishableParcelBox);
                break;
            default:
                System.out.println("Такой коробки не существует.");
        }
    }
}