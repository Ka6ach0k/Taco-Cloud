package tacos.restclient;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import tacos.Ingredient;
import tacos.data.IngredientRepository;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.SimpleTimeZone;

@Slf4j
@Controller
@RequestMapping("/RestClient")
public class RestClientImitation {

    private RestTemplate rest;
    private IngredientRepository ingredientRepo;
    private final String urlApi = "http://localhost:8080/data-api/";

    @Autowired
    public RestClientImitation(RestTemplate rest,
                               IngredientRepository ingredientRepo) {
        this.rest = rest;
        this.ingredientRepo = ingredientRepo;
    }

    @GetMapping()
    public String testRestClient() {
        String testIngredientId = "SPMT";
        Ingredient testIngredient =
                new Ingredient("SPMT", "Super Meat", Ingredient.Type.PROTEIN);

        test_getIngredientById(testIngredientId);
        test_updateIngredient(testIngredient);
        test_getIngredientById(testIngredientId);

        return "Empty";
    }

    public void test_getIngredientById(String ingredientId) {
        log.info("----------Test_getIngredientById----------");

        Ingredient ingredientAPI = getIngredientById(ingredientId).orElse(null);
        Ingredient ingredientFromRepo = ingredientRepo.findById(ingredientId).orElse(null);

        log.info("IngredientRepo = {}", ingredientFromRepo);
        log.info("IngredientAPI = {}", ingredientAPI);
        log.info("------------------------------------------");
    }

    public void test_updateIngredient(Ingredient ingredient) {
        log.info("----------Test_updateIngredient----------");

        updateIngredient(ingredient);

        log.info("Ingredient save/update in database");
        log.info("-----------------------------------------");
    }

    public Optional<Ingredient> getIngredientById(String ingredientId) {
        try {
            Ingredient result = rest.getForObject(urlApi + "ingredients/{id}",
                    Ingredient.class, ingredientId);
            return Optional.ofNullable(result);
        } catch (HttpClientErrorException.NotFound errorException) {
             log.info("Not found ingredient");
             return Optional.empty();
        }
    }

    public void updateIngredient(Ingredient ingredient) {
        rest.put(urlApi + "ingredients/{id}",
                ingredient, ingredient.getId());
    }
}
