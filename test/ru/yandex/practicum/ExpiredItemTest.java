package ru.yandex.practicum;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.delivery.PerishableParcel;


import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ExpiredItemTest {
    @Test
    void testIsExpired_NotExpired() {
        // Скоропортящаяся посылка со сроком годности 5 дней
        PerishableParcel parcel = new PerishableParcel("Молоко", 2,
                "ул. Ленина, 10", 15, 5);

        // Текущий день - день отправки (срок годности не истек)
        assertFalse(parcel.isExpired(15));

        // Текущий день - через 3 дня после отправки (срок годности не истек)
        assertFalse(parcel.isExpired(18));

        // Текущий день - через 5 дней после отправки (последний день срока годности)
        assertFalse(parcel.isExpired(20));
    }

    @Test
    void testIsExpired_Expired() {
        // Скоропортящаяся посылка со сроком годности 5 дней
        PerishableParcel parcel = new PerishableParcel("Молоко", 2,
                "ул. Ленина, 10", 15, 5);

        // Текущий день - через 6 дней после отправки (срок годности истек)
        assertTrue(parcel.isExpired(21));

        // Текущий день - через 10 дней после отправки (истекший срок)
        assertTrue(parcel.isExpired(25));
    }

    @Test
    void testIsExpired_BoundaryCases() {
        // Граничный случай: срок годности 0 дней (должен испортиться сразу)
        PerishableParcel zeroShelfLifeParcel = new PerishableParcel("Быстропортящийся товар", 1,
                "ул. Мира, 5", 15, 0);
        // День отправки - пока не истек
        assertFalse(zeroShelfLifeParcel.isExpired(15));

        // Граничный случай: срок годности 1 день
        PerishableParcel oneDayShelfLifeParcel = new PerishableParcel("Товар", 1,
                "ул. Мира, 5", 15, 1);
        // День отправки - не истек
        assertFalse(oneDayShelfLifeParcel.isExpired(15));
        // Следующий день - последний день срока
        assertFalse(oneDayShelfLifeParcel.isExpired(16));
        // Через 2 дня - истек
        assertTrue(oneDayShelfLifeParcel.isExpired(17));
    }
}
