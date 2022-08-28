package lesson3;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.lessThan;

public class ComplexSearchTest extends AbstractTest {

    @Test
    void getComplexSearchTest() {
        given().spec(getRequestSpecification())
                .queryParam("cuisine", "african")
                .when()
                .get(getBaseUrl()+"recipes/complexSearch")
                .then()
                .spec(responseSpecification);

        given().spec(getRequestSpecification())
                .queryParam("query", "pasta")
                .when()
                .get(getBaseUrl()+"recipes/complexSearch")
                .then()
                .spec(responseSpecification);


        given().spec(getRequestSpecification())
                .queryParam("excludeCuisine", "greek")
                .when()
                .get(getBaseUrl()+"recipes/complexSearch")
                .then()
                .spec(responseSpecification);

        given().spec(getRequestSpecification())
                .queryParam("diet", "diet")
                .when()
                .get(getBaseUrl()+"recipes/complexSearch")
                .then()
                .spec(responseSpecification);

        given().spec(getRequestSpecification())
                .queryParam("intolerances", "gluten")
                .when()
                .get(getBaseUrl()+"recipes/complexSearch")
                .then()
                .spec(responseSpecification);

    }

    @Test
    void postRecipesCuisineTest() {

        given().spec(requestSpecification)
                .when()
                .formParam("title","African Bean Soup")
                .post(getBaseUrl()+"recipes/cuisine")
                .then()
                .spec(responseSpecification);

        given().spec(requestSpecification)
                .when()
                .queryParam("language", "en")
                .formParam("title","Baked Swedish Pancake")
                .post(getBaseUrl()+"recipes/cuisine")
                .then()
                .body(contains("Nordic"))
                .spec(responseSpecification);


        given().spec(requestSpecification)
                .when()
                .formParam("title","Corned Beef and Cabbage")
                .post(getBaseUrl()+"recipes/cuisine")
                .then()
                .extract()
                .response()
                .body(contains("Irish"))
                .spec(responseSpecification);



        given().spec(requestSpecification)
                .when()
                .queryParam("language", "de")
                .formParam("title","African Bean Soup")
                .post(getBaseUrl()+"recipes/cuisine")
                .then()
                .spec(responseSpecification);

        given().spec(requestSpecification)
                .when()
                .formParam("title","Mango Fried Rice")
                .post(getBaseUrl()+"recipes/cuisine")
                .then()
                .body(contains("Chinese"))
                .spec(responseSpecification);
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









