package pl.mm.foodemo.business.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.mm.foodemo.business.dao.OrderDAO;
import pl.mm.foodemo.domain.*;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.*;

@AllArgsConstructor
@Service
public class OrderService {

    private final OrderDAO repository;

    private final RestaurantService restaurantService;
    private final UserService userService;
    private final MealOrderService mealOrderService;

    @Transactional
    public Long countAll() {
        return repository.countAll();
    }


    @Transactional
    public Order makeOrder(Map<Meal, String> meals, Long restaurantId, Long userId) {

        Restaurant restaurant = restaurantService.findById(restaurantId);
        User user = userService.findUserById(userId);


        Order order = Order.builder()
                .orderNumber(UUID.randomUUID().toString())
                .status(OrderStatus.init)
                .receivedDateTime(OffsetDateTime.now())
                .restaurant(restaurant)
                .user(user)
                .address(user.getAddress())
                .build();


        Order savedOrder = repository.saveOrder(order);

        Set<MealOrder> mealOrders = new HashSet<>();

        meals.forEach(
                (key, value) -> mealOrders.add(
                        MealOrder.builder()
                                .price(key.getPrice())
                                .quantity(Integer.valueOf(value))
                                .meal(key)
                                .order(savedOrder)
                                .build()

                )
        );

        mealOrderService.saveAll(mealOrders);

        return savedOrder;
    }


    @Transactional
    public void setOrderStatusById(OrderStatus orderStatus, Long id) {
        repository.setOrderStatusById(id, orderStatus);

        if (orderStatus.equals(OrderStatus.completed)) {
            repository.setOrderCompletedDateTimeById(id, OffsetDateTime.now());
        }
    }

    @Transactional
    public List<Order> getAllOrdersByUserId(Long userId) {
        return repository.findAllByUserId(userId);
    }

    @Transactional
    public void deleteOrderById(Long id) {
        mealOrderService.deleteMealOrderByOrderId(id);

        repository.deleteOrderById(id);
    }


    @Transactional
    public List<Order> getAllOrdersByRestaurantIdAndStatus(Long id, OrderStatus orderStatus) {
        return repository.findAllByRestaurantIdAndStatus(id, orderStatus);
    }

    public BigDecimal calculateTotalAmount(Map<Meal, String> filteredSelectedMeals) {
        return filteredSelectedMeals.entrySet().stream()
                .map(entry -> {
                    BigDecimal price = entry.getKey().getPrice();
                    int quantity = Integer.parseInt(entry.getValue());
                    return price.multiply(BigDecimal.valueOf(quantity));
                })
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

}
