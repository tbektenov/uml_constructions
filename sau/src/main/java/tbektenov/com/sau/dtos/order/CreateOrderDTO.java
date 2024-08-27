package tbektenov.com.sau.dtos.order;

import lombok.Data;

/**
 * DTO for creating an order.
 *
 * <p>Contains the order details.</p>
 */
@Data
public class CreateOrderDTO {
    private String orderBody;
}
