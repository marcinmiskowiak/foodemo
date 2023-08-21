package pl.mm.foodemo.util;

import pl.mm.foodemo.api.dto.*;
import pl.mm.foodemo.domain.OrderStatus;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DTOFixtures {

    public static final String someString = Stream.generate(() -> "a").limit(33).collect(Collectors.joining());

    public static RestaurantDTO someRestaurant1() {
        return RestaurantDTO.builder()
                .restaurantId(1L)
                .name("name1")
                .address(someAddress1())
                .build();
    }

    public static RestaurantDTO someRestaurant2() {
        return RestaurantDTO.builder()
                .restaurantId(2L)
                .name("name2")
                .build();
    }

    public static RestaurantDTO someRestaurant3() {
        return RestaurantDTO.builder()
                .restaurantId(3L)
                .name("name3")
                .build();
    }

    public static RestaurantDTO someRestaurant4() {
        return RestaurantDTO.builder()
                .restaurantId(3L)
                .name("name3")
                .address(someAddress1())
                .build();
    }

    public static AddressDTO someAddress1() {
        return AddressDTO.builder()
                .country("Poland")
                .city("Warsaw")
                .apartmentNumber("11")
                .postalCode("00-001")
                .street("SomeStreet")
                .buildingNumber("10")
                .build();
    }

    public static UserRegisterDTO someUserRegisterDTO1() {
        return UserRegisterDTO.builder()
                .name("John")
                .surname("Doe")
                .email("john.doe@example.com")
                .password("password123")
                .phone("+48 500 500 500")
                .country("Poland")
                .city("Warsaw")
                .postalCode("00-123")
                .street("Main Street")
                .buildingNumber("123")
                .apartmentNumber("4A")
                .isRestaurantOwner(true)
                .build();
    }


    public static UserDTO someUserDTO1() {
        return UserDTO.builder()
                .userId(1L)
                .name("John")
                .surname("Doe")
                .email("john.doe@example.com")
                .password("password123")
                .phone("+48 123 456 789")
                .address(DomainFixtures.someAddress1())
                .registrationDateTime(OffsetDateTime.now())
                .active(true)
                .build();
    }

    public static OrderDTO someOrderDTO1() {
        return OrderDTO.builder()
                .status(OrderStatus.completed)
                .mealOrders(Set.of())
                .user(someUserDTO1())
                .orderNumber("orderNumber")
                .restaurant(someRestaurant1())
                .receivedDateTime(OffsetDateTime.now())
                .build();
    }

    public static OrderWithDetailsDTO someOrderWithDetails() {
        return OrderWithDetailsDTO.builder()
                .order(someOrderDTO1())
                .canBeCancelled(false)
                .mealOrders(List.of())
                .totalPrice(BigDecimal.TEN)
                .build();
    }

    public static AddingDeliveryStreetsDTO someAddingDeliveryStreetsDTO() {
        return AddingDeliveryStreetsDTO.builder()
                .streets("A, B, C")
                .postalCode("00-000")
                .build();
    }
}
