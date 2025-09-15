package tacos;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import tacos.data.IngredientRepository;
import tacos.data.TacoRepository;
import tacos.data.UserRepository;

import java.util.Arrays;
import java.util.HashMap;

@Profile("dev")
@Configuration
public class DevelopmentConfig {

    @Bean
    public CommandLineRunner dataLoader(IngredientRepository ingredientRepo,
                                        UserRepository userRepo,
                                        TacoRepository tacoRepo,
                                        PasswordEncoder encoder) {
        return new CommandLineRunner() {
            @Override
            public void run(String... args) throws Exception {
                HashMap<String, Ingredient> ingredients = new HashMap<>(10);
                ingredients.put("FLTO", new Ingredient("FLTO", "Flour Tortilla", Ingredient.Type.WRAP));
                ingredients.put("COTO", new Ingredient("COTO", "Corn Tortilla", Ingredient.Type.WRAP));
                ingredients.put("GRBF", new Ingredient("GRBF", "Ground Beef", Ingredient.Type.PROTEIN));
                ingredients.put("CARN", new Ingredient("CARN", "Carnitas", Ingredient.Type.PROTEIN));
                ingredients.put("TMTO", new Ingredient("TMTO", "Diced Tomatoes", Ingredient.Type.VEGGIES));
                ingredients.put("LETC", new Ingredient("LETC", "Lettuce", Ingredient.Type.VEGGIES));
                ingredients.put("CHED", new Ingredient("CHED", "Cheddar", Ingredient.Type.CHEESE));
                ingredients.put("JACK", new Ingredient("JACK", "Monterrey Jack", Ingredient.Type.CHEESE));
                ingredients.put("SLSA", new Ingredient("SLSA", "Salsa", Ingredient.Type.SAUCE));
                ingredients.put("SRCR", new Ingredient("SRCR", "Sour Cream", Ingredient.Type.SAUCE));
                ingredientRepo.saveAll(ingredients.values());

                Taco taco1 = new Taco();
                taco1.setName("Carnivore");
                taco1.setIngredients(Arrays.asList(
                        ingredients.get("FLTO"),
                        ingredients.get("GRBF"),
                        ingredients.get("CARN"),
                        ingredients.get("SRCR"),
                        ingredients.get("SLSA"),
                        ingredients.get("CHED")
                ));
                Taco taco2 = new Taco();
                taco2.setName("Bovine Bounty");
                taco2.setIngredients(Arrays.asList(
                        ingredients.get("COTO"),
                        ingredients.get("GRBF"),
                        ingredients.get("CHED"),
                        ingredients.get("JACK"),
                        ingredients.get("SRCR")
                ));
                Taco taco3 = new Taco();
                taco3.setName("Veg-Out");
                taco3.setIngredients(Arrays.asList(
                        ingredients.get("FLTO"),
                        ingredients.get("COTO"),
                        ingredients.get("TMTO"),
                        ingredients.get("LETC"),
                        ingredients.get("SLSA")
                ));
                tacoRepo.save(taco1);
                tacoRepo.save(taco2);
                tacoRepo.save(taco3);

                userRepo.save(new User(
                        "user",encoder.encode("user"), "Test_User", "Street",
                        "Penza", "Pn", "123456", "+79999999999"
                ));
            }
        };
    }
}
