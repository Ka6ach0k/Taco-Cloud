package tacos.data;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import tacos.Ingredient.Type;
import tacos.Ingredient;

import java.util.Optional;

@SpringBootTest
@ActiveProfiles("dev")
public class IngredientRepositoryTest {

    @Autowired
    IngredientRepository ingredientRepo;

    @Test
    public void findById() {
        Optional<Ingredient> flto = ingredientRepo.findById("FLTO");
        Assertions.assertThat(flto.isPresent()).isTrue();
        Assertions.assertThat(flto.get()).isEqualTo(new Ingredient("FLTO", "Flour Tortilla", Type.WRAP));

        Optional<Ingredient> none = ingredientRepo.findById("none");
        Assertions.assertThat(none.isEmpty()).isTrue();

    }
}
