package web;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tacos.Ingredient;
import tacos.data.IngredientRepository;
import tacos.web.IngredientByIdConverter;

import java.util.Optional;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class IngredientByIdConvertTest {

    private IngredientByIdConverter converter;

    @BeforeEach
    public void setup() {
        IngredientRepository ingredientRepo = mock(IngredientRepository.class);
        when(ingredientRepo.findById("AAAA"))
                .thenReturn(Optional.of(new Ingredient("AAAA", "Test Ingredient", Ingredient.Type.CHEESE)));
        when(ingredientRepo.findById("ZZZZ"))
                .thenReturn(Optional.empty());

        this.converter = new IngredientByIdConverter(ingredientRepo);
    }

    @Test
    public void shouldReturnValueWhenPresent() {
        Assertions.assertThat(converter.convert("AAAA"))
                .isEqualTo(new Ingredient("AAAA", "Test Ingredient", Ingredient.Type.CHEESE));
    }

    @Test
    public void shouldReturnNullWhenMissing() {
        Assertions.assertThat(converter.convert("ZZZZ"))
                .isNull();
    }
}
