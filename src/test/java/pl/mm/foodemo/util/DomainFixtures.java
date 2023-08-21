package pl.mm.foodemo.util;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import pl.mm.foodemo.domain.*;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Collection;
import java.util.Set;

public class DomainFixtures {

    public static Restaurant someRestaurant1() {
        return Restaurant.builder()
                .restaurantId(1L)
                .name("name1")
                .build();
    }

    public static Restaurant someRestaurant2() {
        return Restaurant.builder()
                .restaurantId(2L)
                .name("name2")
                .build();
    }

    public static Restaurant someRestaurant3() {
        return Restaurant.builder()
                .restaurantId(3L)
                .name("name3")
                .build();
    }

    public static Restaurant someRestaurant4() {
        return Restaurant.builder()
                .restaurantId(3L)
                .name("name3")
                .address(someAddress1())
                .build();
    }


    public static Address someAddress1() {
        return Address.builder()
                .country("Poland")
                .city("Warsaw")
                .apartmentNumber("11")
                .postalCode("00-001")
                .street("SomeStreet")
                .buildingNumber("10")
                .build();
    }

    public static Address someAddress2() {
        return Address.builder()
                .country("Poland")
                .city("Warsaw")
                .apartmentNumber("22")
                .postalCode("00-002")
                .street("SomeStreet2")
                .buildingNumber("20")
                .build();
    }

    public static User someUser1() {
        return User.builder()
                .userId(1L)
                .name("John")
                .surname("Doe")
                .email("john.doe@example.com")
                .password("password123")
                .phone("+48 123 456 789")
                .address(someAddress1())
                .registrationDateTime(OffsetDateTime.now())
                .active(true)
                .build();
    }

    public static User someUser2() {
        return User.builder()
                .userId(2L)
                .name("John")
                .surname("Doe")
                .email("john.doe@example.com")
                .password("password123")
                .phone("+48 123 555 789")
                .address(someAddress1())
                .registrationDateTime(OffsetDateTime.now())
                .active(true)
                .build();
    }

    public static DeliveryAddress someDeliveryAddress() {
        return DeliveryAddress.builder()
                .deliveryAddressId(1L)
                .street("Street 1")
                .postalCode("00-001")
                .build();
    }

    public static DeliveryAddress someDeliveryAddress2() {
        return DeliveryAddress.builder()
                .deliveryAddressId(2L)
                .street("Street 2")
                .postalCode("00-001")
                .build();
    }

    public static DeliveryAddress someDeliveryAddress3() {
        return DeliveryAddress.builder()
                .deliveryAddressId(3L)
                .street("Street 3")
                .postalCode("00-003")
                .build();
    }

    public static Meal someMeal1() {
        return Meal.builder()
                .mealId(1L)
                .price(BigDecimal.TEN)
                .category(MealCategory.APPETIZER)
                .name("Meal1")
                .build();
    }

    public static Meal someMeal2() {
        return Meal.builder()
                .mealId(2L)
                .price(BigDecimal.TEN)
                .category(MealCategory.APPETIZER)
                .name("Meal1")
                .build();
    }

    public static Collection<? extends GrantedAuthority> userAuthorities() {
        return Set.of(new SimpleGrantedAuthority("user"), new SimpleGrantedAuthority("owner"));
    }

    public static Order someOrder() {
        return Order.builder()
                .status(OrderStatus.completed)
                .mealOrders(Set.of())
                .user(someUser1())
                .orderNumber("orderNumber")
                .user(someUser1())
                .address(someAddress2())
                .restaurant(someRestaurant1())
                .receivedDateTime(OffsetDateTime.now())
                .build();
    }
}
