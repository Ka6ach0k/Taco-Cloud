package tacos.data;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import tacos.Ingredient;
import tacos.Ingredient.Type;
import tacos.Taco;
import tacos.TacoOrder;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class OrderRepositoryTest {

    @Autowired
    OrderRepository orderRepo;

    @Test
    public void saveOrderWithTwoTacos() {

        TacoOrder order = new TacoOrder();
        order.setDeliveryName("Test McTest");
        order.setDeliveryStreet("1234 Test Lane");
        order.setDeliveryCity("Testville");
        order.setDeliveryState("CO");
        order.setDeliveryZip("80123");
        order.setCcNumber("4111111111111111");
        order.setCcExpiration("10/23");
        order.setCcCVV("123");

        Taco taco1 = new Taco();
        taco1.setName("Taco One");
        taco1.setIngredients(
                Arrays.asList(
                        new Ingredient("FLTO", "Flour Tortilla", Type.WRAP),
                        new Ingredient("GRBF", "Ground Beef", Type.PROTEIN),
                        new Ingredient("CHED", "Shredded Cheddar", Type.CHEESE)
                )
        );
        order.addTaco(taco1);

        Taco taco2 = new Taco();
        taco2.setName("Taco Two");
        taco2.setIngredients(
                Arrays.asList(
                        new Ingredient("COTO", "Corn Tortilla", Type.WRAP),
                        new Ingredient("CARN", "Carnitas", Type.PROTEIN),
                        new Ingredient("JACK", "Monterrey Jack", Type.CHEESE)
                )
        );
        order.addTaco(taco2);

        TacoOrder savedOrder = orderRepo.save(order);
        assertThat(savedOrder.getId()).isNotNull();

        TacoOrder fetchedOrder = orderRepo.findById(savedOrder.getId()).get();
        assertThat(fetchedOrder.getDeliveryName()).isEqualTo("Test McTest");
        assertThat(fetchedOrder.getDeliveryStreet()).isEqualTo("1234 Test Lane");
        assertThat(fetchedOrder.getDeliveryCity()).isEqualTo("Testville");
        assertThat(fetchedOrder.getDeliveryState()).isEqualTo("CO");
        assertThat(fetchedOrder.getDeliveryZip()).isEqualTo("80123");
        assertThat(fetchedOrder.getCcNumber()).isEqualTo("4111111111111111");
        assertThat(fetchedOrder.getCcExpiration()).isEqualTo("10/23");
        assertThat(fetchedOrder.getCcCVV()).isEqualTo("123");
        assertThat(fetchedOrder.getPlacedAt()).isEqualTo(savedOrder.getPlacedAt());
        List<Taco> tacos = fetchedOrder.getTacos();
        assertThat(tacos.size()).isEqualTo(2);
        assertThat(tacos).containsExactlyInAnyOrder(taco1, taco2);
    }
}
