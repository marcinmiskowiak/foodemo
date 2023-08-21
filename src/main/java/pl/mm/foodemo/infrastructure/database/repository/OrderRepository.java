package pl.mm.foodemo.infrastructure.database.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import pl.mm.foodemo.business.dao.OrderDAO;
import pl.mm.foodemo.domain.Order;
import pl.mm.foodemo.domain.OrderStatus;
import pl.mm.foodemo.infrastructure.database.repository.jpa.OrderJpaRepository;
import pl.mm.foodemo.infrastructure.database.repository.mapper.OrderMapper;

import java.time.OffsetDateTime;
import java.util.List;

@Repository
@AllArgsConstructor
public class OrderRepository implements OrderDAO {

    private final OrderJpaRepository jpaRepository;

    private final OrderMapper mapper;

    @Override
    public Long countAll() {
        return jpaRepository.count();
    }

    @Override
    public Order saveOrder(Order order) {
        return mapper.map(jpaRepository.saveAndFlush(mapper.map(order)));
    }

    @Override
    public void setOrderStatusById(Long id, OrderStatus orderStatus) {
        jpaRepository.setOrderStatusById(id, orderStatus);
    }

    @Override
    public void setOrderCompletedDateTimeById(Long id, OffsetDateTime completedDateTime) {
        jpaRepository.setOrderCompletedDateTimeById(id, completedDateTime);
    }

    @Override
    public List<Order> findAllByUserId(Long userId) {
        return jpaRepository.findAllByUserUserId(userId).stream()
                .map(mapper::map).toList();
    }

    @Override
    public void deleteOrderById(Long id) {
        jpaRepository.deleteById(id);
    }

    @Override
    public List<Order> findAllByRestaurantIdAndStatus(Long id, OrderStatus orderStatus) {
        return jpaRepository.findAllByRestaurantIdAndStatus(id, orderStatus).stream()
                .map(mapper::map).toList();
    }


}
