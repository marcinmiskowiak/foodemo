package pl.mm.foodemo.infrastructure.database.repository;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import pl.mm.foodemo.business.dao.RestaurantDAO;
import pl.mm.foodemo.domain.Restaurant;
import pl.mm.foodemo.domain.exception.NotFoundException;
import pl.mm.foodemo.infrastructure.database.entity.RestaurantEntity;
import pl.mm.foodemo.infrastructure.database.repository.jpa.RestaurantJpaRepository;
import pl.mm.foodemo.infrastructure.database.repository.mapper.RestaurantMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
@AllArgsConstructor
public class RestaurantRepository implements RestaurantDAO {

    private final RestaurantJpaRepository jpaRepository;

    private final RestaurantMapper mapper;

    @Override
    public Long countAll() {
        return jpaRepository.count();
    }

    @Override
    public List<Restaurant> findByOwnerId(Long ownerId) {
        List<Restaurant> restaurants = new ArrayList<>();

        jpaRepository.findByOwnerId(ownerId).forEach(restaurantEntity -> restaurants.add(mapper.map(restaurantEntity)));
        return restaurants;
    }

    @Override
    public Restaurant save(Restaurant restaurant) {
        return mapper.map(
                jpaRepository.saveAndFlush(
                        mapper.map(restaurant)
                )
        );
    }

    @Override
    public Restaurant findById(Long id) {
        return mapper.map(jpaRepository.findById(id).orElseThrow(
                () -> new NotFoundException("No restaurant with id: %s found".formatted(id))
        ));
    }

    @Override
    public Page<Restaurant> findRestaurantsByDeliveryAddress(String postalCode, String street, Pageable pageable) {
        Page<RestaurantEntity> restaurantEntityPage = jpaRepository.findRestaurantsByDeliveryAddress(postalCode, street, pageable);

        List<Restaurant> restaurantList = restaurantEntityPage.getContent().stream()
                .map(mapper::map)
                .collect(Collectors.toList());

        return new PageImpl<>(restaurantList, pageable, restaurantEntityPage.getTotalElements());
    }

    @Override
    public List<String> findNewestRestaurantsNames(int howMany) {
        return jpaRepository.findNewestRestaurantsNames(howMany);
    }

    @Override
    public List<Restaurant> findAll() {
        return jpaRepository.findAll().stream().map(mapper::map).collect(Collectors.toList());
    }


}
