package ru.yandex.practicum;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.delivery.FragileParcel;
import ru.yandex.practicum.delivery.Parcel;
import ru.yandex.practicum.delivery.PerishableParcel;
import ru.yandex.practicum.delivery.StandardParcel;

import static org.junit.jupiter.api.Assertions.*;

public class DeliveryCostTest {
    @Test
    void testFragileParcelCost() {

        FragileParcel parcel = new FragileParcel("Ваза", 3,
                "ул. Пушкина, 20", 15);
        int expectedCost = 3 * Parcel.FRAGILE_BASE_COST; // 3 * 4 = 12
        assertEquals(expectedCost, parcel.calculateDeliveryCost());

        FragileParcel heavyParcel = new FragileParcel("Хрусталь", 10,
                "ул. Лермонтова, 30", 15);
        int expectedHeavyCost = 10 * Parcel.FRAGILE_BASE_COST; // 10 * 4 = 40
        assertEquals(expectedHeavyCost, heavyParcel.calculateDeliveryCost());
    }

    @Test
    void testStandardParcelCost() {
        StandardParcel parcel = new StandardParcel("Книги", 5,
                "ул. Ленина, 10", 15);
        int expectedCost = 5 * Parcel.STANDARD_BASE_COST; // 5 * 2 = 10
        assertEquals(expectedCost, parcel.calculateDeliveryCost());

        StandardParcel lightParcel = new StandardParcel("Письмо", 1,
                "Проспект Мира, 10", 15);
        int expectedLightCost = 1 * Parcel.STANDARD_BASE_COST; // 1 * 2 = 2
        assertEquals(expectedLightCost, lightParcel.calculateDeliveryCost());
    }

    @Test
    void testPerishableParcelCost(){
        PerishableParcel parcel = new PerishableParcel("Хлеб", 2,
                "ул. Ломоносова", 4, 6);
        int expectedCost = 2 * Parcel.PERISHABLE_BASE_COST; // 2 * 3 = 6
        assertEquals(expectedCost, parcel.calculateDeliveryCost());

        PerishableParcel zeroWeightParcel = new PerishableParcel("Конфеты", 1,
                "ул. Садовая, 15", 15, 5);
        int expectedZeroCost = 1 * Parcel.PERISHABLE_BASE_COST; // 1 * 3 = 3
        assertEquals(expectedZeroCost, zeroWeightParcel.calculateDeliveryCost());
    }
}
