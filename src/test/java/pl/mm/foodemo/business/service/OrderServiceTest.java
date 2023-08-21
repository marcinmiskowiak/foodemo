package pl.mm.foodemo.business.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.mm.foodemo.business.dao.OrderDAO;
import pl.mm.foodemo.domain.*;
import pl.mm.foodemo.util.DomainFixtures;

import java.math.BigDecimal;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    private OrderDAO orderDAO;

    @Mock
    private RestaurantService restaurantService;

    @Mock
    private UserService userService;

    @Mock
    private MealOrderService mealOrderService;

    @InjectMocks
    private OrderService orderService;

    @Test
    void testMakeOrder() {
        Long restaurantId = 1L;
        Long userId = 2L;

        Restaurant restaurant = DomainFixtures.someRestaurant1().withRestaurantId(restaurantId);

        User user = DomainFixtures.someUser1().withAddress(DomainFixtures.someAddress1());

        Meal meal1 = DomainFixtures.someMeal1();
        Meal meal2 = DomainFixtures.someMeal2();

        Map<Meal, String> meals = Map.of(meal1, "10", meal2, "5");

        Order savedOrder = Order.builder()
                .orderNumber("Order Number")
                .status(OrderStatus.init)
                .restaurant(restaurant)
                .build();

        when(restaurantService.findById(restaurantId)).thenReturn(restaurant);
        when(userService.findUserById(userId)).thenReturn(user);
        when(orderDAO.saveOrder(any())).thenReturn(savedOrder);

        Order result = orderService.makeOrder(meals, restaurantId, userId);

        assertEquals(savedOrder.getRestaurant(), result.getRestaurant());
        assertEquals(savedOrder.getAddress(), result.getAddress());
        assertEquals(savedOrder.getStatus(), result.getStatus());
    }


    @Test
    void testCalculateTotalAmount() {
        Meal meal1 = DomainFixtures.someMeal1();
        Meal meal2 = DomainFixtures.someMeal2();

        Map<Meal, String> meals = Map.of(
                meal1, "2",
                meal2, "10"
        );

        BigDecimal expectedTotalAmount = meal1.getPrice().multiply(new BigDecimal(meals.get(meal1)));
        expectedTotalAmount = expectedTotalAmount.add(meal2.getPrice().multiply(new BigDecimal(meals.get(meal2))));

        BigDecimal result = orderService.calculateTotalAmount(meals);

        assertEquals(expectedTotalAmount, result);
    }

}