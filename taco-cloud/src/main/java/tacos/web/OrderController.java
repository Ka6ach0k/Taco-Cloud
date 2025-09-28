package tacos.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import tacos.TacoOrder;
import tacos.User;
import tacos.data.OrderRepository;

import javax.validation.Valid;

@Controller
@RequestMapping("/orders")
@SessionAttributes("tacoOrder")
public class OrderController {

    private OrderRepository orderRep;

    private OrdersProps props;

    @Autowired
    public OrderController(OrderRepository orderRep,
                           OrdersProps props) {
        this.orderRep = orderRep;
        this.props = props;
    }

    @GetMapping("/current")
    public String orderForm(@AuthenticationPrincipal User user,
                            @ModelAttribute TacoOrder tacoOrder) {
        if (tacoOrder.getDeliveryName() == null) {
            tacoOrder.setDeliveryName(user.getFullname());
        }
        if (tacoOrder.getDeliveryStreet() == null) {
            tacoOrder.setDeliveryStreet(user.getStreet());
        }
        if (tacoOrder.getDeliveryCity() == null) {
            tacoOrder.setDeliveryCity(user.getCity());
        }
        if (tacoOrder.getDeliveryState() == null) {
            tacoOrder.setDeliveryState(user.getState());
        }
        if (tacoOrder.getDeliveryZip() == null) {
            tacoOrder.setDeliveryZip(user.getZip());
        }

        return "orderForm";
    }

    @PostMapping
    public String processOrder(@Valid TacoOrder tacoOrder,
                               Errors errors,
                               @AuthenticationPrincipal User user,
                               SessionStatus sessionStatus) {
        if (errors.hasErrors())
            return "orderForm";

        tacoOrder.setUser(user);
        orderRep.save(tacoOrder);

        sessionStatus.setComplete();
        return "redirect:/";
    }

    @GetMapping
    public String orderForUser(
            @AuthenticationPrincipal User user, Model model) {

        Pageable pageable = PageRequest.of(0, props.getPageSize());
        model.addAttribute("orders",
                orderRep.findByUserOrderByPlacedAtDesc(user, pageable));
        return "orderList";
    }
}
