package ru.yandex.practicum;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.delivery.FragileParcel;
import ru.yandex.practicum.delivery.ParcelBox;
import ru.yandex.practicum.delivery.StandardParcel;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class AddedParcelTest {
    private ParcelBox<StandardParcel> standardParcelBox;
    private ParcelBox<FragileParcel> fragileParcelBox;

    @BeforeEach
    void setUp() {
        // Создаем коробки с максимальным весом 10 кг
        standardParcelBox = new ParcelBox<>(10);
        fragileParcelBox = new ParcelBox<>(10);
    }

    @Test
    void testAddParcel_WithinWeightLimit() {
        // Стандартный сценарий: добавление посылки в пустую коробку
        StandardParcel parcel1 = new StandardParcel("Книги", 3,
                "ул. Ленина, 10", 15);
        standardParcelBox.addParcel(parcel1);
        assertEquals(1, standardParcelBox.getAllParcels().size());
        assertEquals(3, standardParcelBox.getTotalWeight());

        // Добавление второй посылки (суммарный вес в пределах лимита)
        StandardParcel parcel2 = new StandardParcel("Одежда", 4, "ул. Мира, 5", 15);
        standardParcelBox.addParcel(parcel2);
        assertEquals(2, standardParcelBox.getAllParcels().size());
        assertEquals(7, standardParcelBox.getTotalWeight());
    }

    @Test
    void testAddParcel_ExceedsWeightLimit() {
        // Добавляем первую посылку
        FragileParcel parcel1 = new FragileParcel("Ваза", 7,
                "ул. Пушкина, 20", 15);
        fragileParcelBox.addParcel(parcel1);
        assertEquals(7, fragileParcelBox.getTotalWeight());

        // Пытаемся добавить вторую посылку, которая превысит лимит
        FragileParcel parcel2 = new FragileParcel("Зеркало", 4,
                "ул. Гагарина, 30", 15);
        fragileParcelBox.addParcel(parcel2); // Должно вернуть false
        assertEquals(1, fragileParcelBox.getAllParcels().size()); // Только одна посылка
        assertEquals(7, fragileParcelBox.getTotalWeight()); // Вес не изменился

        // Пытаемся добавить посылку, которая точно превысит лимит
        FragileParcel parcel3 = new FragileParcel("Люстра", 10,
                "ул. Садовая, 15", 15);
        fragileParcelBox.addParcel(parcel3);
        assertEquals(1, fragileParcelBox.getAllParcels().size());
        assertEquals(7, fragileParcelBox.getTotalWeight());
    }

    @Test
    void testAddParcel_MultipleParcelsExactLimit() {
        // Тестирование добавления нескольких посылок, суммарный вес точно равен лимиту
        standardParcelBox.addParcel(new StandardParcel("Посылка 1", 3, "Адрес 1", 15));
        standardParcelBox.addParcel(new StandardParcel("Посылка 2", 3, "Адрес 2", 15));
        standardParcelBox.addParcel(new StandardParcel("Посылка 3", 4, "Адрес 3", 15));

        assertEquals(3, standardParcelBox.getAllParcels().size());
        assertEquals(10, standardParcelBox.getTotalWeight());

        // Попытка добавить еще одну посылку (даже с весом 1)
        standardParcelBox.addParcel(new StandardParcel("Посылка 4", 1,
                "Адрес 4", 15));
        assertEquals(3, standardParcelBox.getAllParcels().size());
        assertEquals(10, standardParcelBox.getTotalWeight());
    }
}
