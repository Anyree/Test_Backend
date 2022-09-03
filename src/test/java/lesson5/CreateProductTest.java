package lesson5;

import com.github.javafaker.Faker;
import lesson5.api.ProductService;
import lesson5.dto.Product;
import lesson5.utils.RetrofitUtils;
import lombok.SneakyThrows;
import okhttp3.ResponseBody;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import retrofit2.Call;
import retrofit2.Response;

import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.comparesEqualTo;
import static org.hamcrest.Matchers.equalTo;


public class CreateProductTest {

    static ProductService productService;
    Product product = null;
    Faker faker = new Faker();
    int id;

    @BeforeAll
    static void beforeAll() {
        productService = RetrofitUtils.getRetrofit()
                .create(ProductService.class);
    }

    @BeforeEach
    void setUp() {
        product = new Product()
                .withTitle(faker.food().ingredient())
                .withCategoryTitle("Food")
                .withPrice((int) (Math.random() * 10000));
    }

    @Test
    void createProductInFoodCategoryTest() throws IOException {
        Response<Product> response = productService.createProduct(product)
                .execute();
        id =  response.body().getId();
        assertThat(response.isSuccessful(), CoreMatchers.is(true));
    }

    @Test
    void getProductByIdPositiveTest() throws IOException {
        Response<Product> response = productService.getProductById(3).execute();

        assertThat(response.body().getId(), equalTo(3));
        assertThat(response.body().getTitle(), equalTo("Cheese"));
        assertThat(response.body().getPrice(), equalTo("360"));
        assertThat(response.body().getCategoryTitle(), equalTo("Food"));
    }

    @Test
    void getProductsPositiveTest() throws IOException {
        Response<ResponseBody> response = productService.getProducts().execute();

        assertThat(response.isSuccessful(), CoreMatchers.is(true));
    }

    @Test
    void modifyProductPositiveTest() throws IOException {
        Response<Product> response = productService.modifyProduct(product).execute();

        assert response.body() != null;
        assertThat(response.body().getId(), equalTo(2));
        assertThat(response.body().getTitle(), equalTo("Bread"));
        assertThat(response.body().getPrice(), equalTo("25"));
        assertThat(response.body().getCategoryTitle(), equalTo("Food"));
    }


    @SneakyThrows
    @AfterEach
    void tearDown() {
        Response<ResponseBody> response = productService.deleteProduct(id).execute();
        assertThat(response.isSuccessful(), CoreMatchers.is(true));
    }



}
