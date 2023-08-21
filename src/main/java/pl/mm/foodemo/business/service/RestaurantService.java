package pl.mm.foodemo.business.service;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.mm.foodemo.business.dao.RestaurantDAO;
import pl.mm.foodemo.domain.Address;
import pl.mm.foodemo.domain.Restaurant;

import java.util.List;

@Service
@AllArgsConstructor
public class RestaurantService {

    private static final Integer RESTAURANTS_ON_PAGE = 4;

    private final RestaurantDAO repository;
    private final AddressService addressService;


    @Transactional
    public Long countAll() {
        return repository.countAll();
    }

    @Transactional
    public List<Restaurant> findByOwnerId(Long ownerId) {
        return repository.findByOwnerId(ownerId);
    }

    @Transactional
    public Restaurant save(Restaurant restaurant) {
        Address address = addressService.saveAddress(restaurant.getAddress());
        return repository.save(restaurant.withAddress(address));
    }

    public Restaurant findById(Long id) {
        return repository.findById(id);
    }

    public Address updateAddress(Long id, Address address) {
        Restaurant restaurant = findById(id);

        Address restaurantUpdatedAddress = address.withAddressId(restaurant.getAddress().getAddressId());

        return addressService.saveAddress(restaurantUpdatedAddress);
    }

    public Page<Restaurant> findRestaurantsByDeliveryAddress(String postalCode, String street, Integer pageNumber) {
        Sort sort = Sort.by("name").ascending();
        Pageable pageable = PageRequest.of(pageNumber, RESTAURANTS_ON_PAGE , sort);

        return repository.findRestaurantsByDeliveryAddress(postalCode, street, pageable);
    }

    public List<String> findNewestRestaurantsNames(int howMany) {

        return repository.findNewestRestaurantsNames(howMany);
    }

    public List<Restaurant> findAll() {
        return repository.findAll();
    }
}
