package tacos.web.api;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import tacos.TacoOrder;
import tacos.data.OrderRepository;

@RestController
@RequestMapping(path = "/api/orders",
        produces = "application/json")
@CrossOrigin(origins = "http://localhost:8080")
public class OrderController {

    private OrderRepository orderRepo;

    public OrderController(OrderRepository orderRepo) {
        this.orderRepo = orderRepo;
    }

    @PostMapping(produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public TacoOrder postOrder(@RequestBody TacoOrder order) {
        return orderRepo.save(order);
    }

    @PutMapping(path = "/{orderId}",
            consumes = "application/json")
    public TacoOrder putOrder(@PathVariable("orderId") Long id,
                              @RequestBody TacoOrder order) {
        order.setId(id);
        return orderRepo.save(order);
    }

    @PatchMapping(path = "/{orderId}",
            consumes = "application/json")
    public TacoOrder patchOrder(@PathVariable("orderId") Long id,
                                @RequestBody TacoOrder patchOrder) {
        TacoOrder order = orderRepo.findById(id).get();
        if (patchOrder.getDeliveryName() != null) {
            order.setDeliveryName(patchOrder.getDeliveryName());
        }
        if (patchOrder.getDeliveryStreet() != null) {
            order.setDeliveryStreet(patchOrder.getDeliveryStreet());
        }
        if (patchOrder.getDeliveryCity() != null) {
            order.setDeliveryCity(patchOrder.getDeliveryCity());
        }
        if (patchOrder.getDeliveryState() != null) {
            order.setDeliveryState(patchOrder.getDeliveryState());
        }
        if (patchOrder.getDeliveryZip() != null) {
            order.setDeliveryZip(patchOrder.getDeliveryZip());
        }
        if (patchOrder.getCcNumber() != null) {
            order.setCcNumber(patchOrder.getCcNumber());
        }
        if (patchOrder.getCcExpiration() != null) {
            order.setCcExpiration(patchOrder.getCcExpiration());
        }
        if (patchOrder.getCcCVV() != null) {
            order.setCcCVV(patchOrder.getCcCVV());
        }
        return orderRepo.save(order);
    }

    @DeleteMapping("/{orderId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteOrder(@PathVariable("orderId") Long id) {
        try {
            orderRepo.deleteById(id);
        } catch (EmptyResultDataAccessException ignored) {}
    }
}