package pl.mm.foodemo.business.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.mm.foodemo.domain.Restaurant;

import java.util.List;

public interface RestaurantDAO {

    Long countAll();

    List<Restaurant> findByOwnerId(Long ownerId);


    Restaurant save(Restaurant restaurant);

    Restaurant findById(Long id);


    Page<Restaurant> findRestaurantsByDeliveryAddress(String postalCode, String street, Pageable pageable);

    List<String> findNewestRestaurantsNames(int howMany);

    List<Restaurant> findAll();
}
