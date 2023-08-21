package pl.mm.foodemo.infrastructure.database.repository.jpa;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.mm.foodemo.infrastructure.database.entity.RestaurantEntity;

import java.util.List;

@Repository
public interface RestaurantJpaRepository extends JpaRepository<RestaurantEntity, Long> {

    long count();

    @Query(value = "SELECT res FROM RestaurantEntity res WHERE res.owner.userId=:ownerId")
    List<RestaurantEntity> findByOwnerId(final @Param("ownerId") Long ownerId);


    @Query("""
            SELECT r FROM RestaurantEntity r 
            JOIN r.deliveryAddresses d 
            WHERE d.postalCode = :postalCode 
            AND LOWER(d.street) = LOWER(:street)
            """)
    Page<RestaurantEntity> findRestaurantsByDeliveryAddress(
            @Param("postalCode") String postalCode,
            @Param("street") String street,
            Pageable pageable
    );


    @Query(value = "SELECT r.name FROM restaurant r ORDER BY r.registration_date_time DESC LIMIT ?1", nativeQuery = true)
    List<String> findNewestRestaurantsNames(int limit);
}
