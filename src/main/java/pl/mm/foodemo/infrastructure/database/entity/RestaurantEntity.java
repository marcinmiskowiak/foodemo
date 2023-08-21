package pl.mm.foodemo.infrastructure.database.entity;


import jakarta.persistence.*;
import lombok.*;
import pl.mm.foodemo.infrastructure.security.UserEntity;

import java.time.OffsetDateTime;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
@EqualsAndHashCode
@Entity
@With
@Table(name = "restaurant")
public class RestaurantEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "restaurant_id")
    private Long restaurantId;

    @Column(name = "name")
    private String name;

    @Column(name = "phone")
    private String phone;

    @Column(name = "registration_date_time")
    private OffsetDateTime registrationDateTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id")
    private UserEntity owner;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "address_id")
    private AddressEntity address;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "restaurant")
    private Set<DeliveryAddressEntity> deliveryAddresses;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "restaurant")
    private Set<MealEntity> meals;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "restaurant")
    private Set<OrderEntity> orders;

}