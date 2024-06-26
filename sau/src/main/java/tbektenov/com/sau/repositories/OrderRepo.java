package tbektenov.com.sau.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tbektenov.com.sau.models.OrderEntity;

/**
 * Repository interface for OrderEntity.
 *
 * This interface extends JpaRepository to provide CRUD operations
 * for OrderEntity entities, allowing interaction with the database.
 */
public interface OrderRepo
    extends JpaRepository<OrderEntity, Long> {
}
