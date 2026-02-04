package ru.yandex.practicum.delivery;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DeliveryApp {

    private static final Scanner scanner = new Scanner(System.in);
    private static List<Parcel> allParcels = new ArrayList<>();

    public static void main(String[] args) {
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
                parcel = new StandardParcel(description, weight, address, sendDay);
                break;
            case 2:
                parcel = new FragileParcel(description, weight, address, sendDay);
                break;
            case 3:
                System.out.print("Введите срок годности (в днях): ");
                int timeToLive = Integer.parseInt(scanner.nextLine());
                parcel = new PerishableParcel(description, weight, address, sendDay, timeToLive);
                break;
            default:
                System.out.println("Такого типа нет.");
                return;
        }
        allParcels.add(parcel);
        System.out.println("Посылка успешно добавлена!");

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

}