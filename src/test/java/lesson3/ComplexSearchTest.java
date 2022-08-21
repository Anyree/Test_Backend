package lesson3;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.lessThan;

public class ComplexSearchTest extends AbstractTest {

    @Test
    void getComplexSearchTest() {
        given()
                .queryParam("apiKey", getApiKey())
                .queryParam("cuisine", "african")
                .when()
                .get(getBaseUrl()+"recipes/complexSearch")
                .then()
                .statusCode(200);

        given()
                .queryParam("apiKey", getApiKey())
                .queryParam("query", "pasta")
                .when()
                .get(getBaseUrl()+"recipes/complexSearch")
                .then()
                .statusLine("HTTP/1.1 200 OK");


        given()
                .queryParam("apiKey", getApiKey())
                .queryParam("excludeCuisine", "greek")
                .when()
                .get(getBaseUrl()+"recipes/complexSearch")
                .then()
                .contentType(ContentType.JSON);

        given()
                .queryParam("apiKey", getApiKey())
                .queryParam("diet", "diet")
                .when()
                .get(getBaseUrl()+"recipes/complexSearch")
                .then()
                .time(lessThan(2000L));

        given()
                .queryParam("apiKey", getApiKey())
                .queryParam("intolerances", "gluten")
                .when()
                .get(getBaseUrl()+"recipes/complexSearch")
                .then()
                .body(contains("Cauliflower"));

    }

    @Test
    void postRecipesCuisineTest() {

        given()
                .queryParam("apiKey", getApiKey())
                .contentType("application/x-www-form-urlencoded")
                .formParam("title","African Bean Soup")
                .when()
                .post(getBaseUrl()+"recipes/cuisine")
                .then()
                .time(lessThan(2000L))
                .statusCode(200);

        given()
                .queryParam("apiKey", getApiKey())
                .queryParam("language", "en")
                .contentType("application/x-www-form-urlencoded")
                .formParam("title","Baked Swedish Pancake")
                .when()
                .post(getBaseUrl()+"recipes/cuisine")
                .then()
                .statusCode(200)
                .body(contains("Nordic"));


        given()
                .queryParam("apiKey", getApiKey())
                .contentType("application/x-www-form-urlencoded")
                .formParam("title","Corned Beef and Cabbage")
                .when()
                .post(getBaseUrl()+"recipes/cuisine")
                .then()
                .body(contains("Irish"))
                .statusCode(200);

        given()
                .queryParam("apiKey", getApiKey())
                .queryParam("language", "de")
                .contentType("application/x-www-form-urlencoded")
                .formParam("title","African Bean Soup")
                .when()
                .post(getBaseUrl()+"recipes/cuisine")
                .then()
                .statusCode(200);

        given()
                .queryParam("apiKey", getApiKey())
                .contentType("application/x-www-form-urlencoded")
                .formParam("title","Mango Fried Rice")
                .when()
                .post(getBaseUrl()+"recipes/cuisine")
                .then()
                .body(contains("Chinese"))
                .statusCode(200);
    }

    @Test
    void addDeleteMealTest () {
        String id = given()
                .queryParam("hash", "a3da66460bfb7e62ea1c96cfa0b7a634a346ccbf")
                .queryParam("apiKey", getApiKey())
                .body("{\n"
                        + " \"date\": 1644881179,\n"
                        + " \"slot\": 1,\n"
                        + " \"position\": 0,\n"
                        + " \"type\": \"INGREDIENTS\",\n"
                        + " \"value\": {\n"
                        + " \"ingredients\": [\n"
                        + " {\n"
                        + " \"name\": \"1 banana\"\n"
                        + " }\n"
                        + " ]\n"
                        + " }\n"
                        + "}")
                .when()
                .post("https://api.spoonacular.com/mealplanner/geekbrains/items")
                .then()
                .statusCode(200)
                .extract()
                .jsonPath()
                .get("id")
                .toString();

        given()
                .queryParam("hash", "a3da66460bfb7e62ea1c96cfa0b7a634a346ccbf")
                .queryParam("apiKey", getApiKey())
                .delete("https://api.spoonacular.com/mealplanner/geekbrains/items/" + id)
                .then()
                .statusCode(200);
    }

    }









