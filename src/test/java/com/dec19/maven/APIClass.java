package com.dec19.maven;

import io.restassured.response.Response;

import org.junit.Ignore;
import org.junit.Test;
import org.hamcrest.Matchers;
import static  io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.requestSpecification;
import static org.hamcrest.Matchers.*;

public class APIClass {
    @Test
    public void listusers() {
        Response response = given()
                .when().get("https://reqres.in/api/users?page=2");
        // response.prettyPrint();
        response.then().statusCode(200)
                .body("page", is(2))
                .body("data.first_name", hasItem("Michael"));
    }

    @Test
    public void singleuser() {
        Response response = given()
                .when().get("https://reqres.in/api/users/2");
        response.then().statusCode(200)
                .body("data.id", is(2))
                .body("data.email", is("janet.weaver@reqres.in"))
                .body("data.last_name", is("Weaver"))
                .body("data.first_name", is("Janet"));

        //response.prettyPrint();
    }

    @Test
    public void singleusernotfound() {
        Response response = given()
                .when().get("https://reqres.in/api/users/23");
        response.then().statusCode(404);
        //response.prettyPrint();

    }

    @Test
    public void listresource() {
        Response response = given()
                .when().get("https://reqres.in/api/unknown");
        response.then().statusCode(200)
                .body("page", is(1))
                .body("total_pages", is(2))
                .body("data.year", hasItem(2000))
                .body("data.color", hasItem("#98B2D1"))
                .body("data.id", hasItems(1, 2, 3, 4, 6));
        response.prettyPrint();

    }

    @Test
    public void singleresource() {
        Response response = given()
                .when().get("https://reqres.in/api/unknown/2");
        response.then().statusCode(200)
                .body("data.id", is(2))
                .body("data.pantone_value", is("17-2031"))
                .body("data.name", is("fuchsia rose"));
    }

    @Test
    public void delayedresponse() {
        Response response = given()
                .when().get("https://reqres.in/api/users?delay=3");
        response.then().statusCode(200)
                .body("data.id", hasItems(1))
                .body("data.first_name", hasItems("Charles"))
                .body("data.first_name", hasItems("Tracey"))
                .body("data.last_name", hasItems("Ramos", "Morris"))
                .body("data.email", hasItems("tracey.ramos@reqres.in"));


    }

    @Test
    public void posts() {
        Response response = given()
                .when().get("https://jsonplaceholder.typicode.com/posts");
        response.then().body("title", hasItems("qui est esse"))
                .body("id", hasItems(3));
        //response.prettyPrint();
    }

    @Test
    public void posts1() {
        Response response = given()
                .when().get("https://jsonplaceholder.typicode.com/posts/1");
        response.then().body("userId", is(1))
                .body("title", is("sunt aut facere repellat provident occaecati excepturi optio reprehenderit"));

        response.prettyPrint();
    }

    @Test
    public void postscomments() {
        Response response = given()
                .when().get("https://jsonplaceholder.typicode.com/posts/1/comments");
        response.then().body("name", hasItems("id labore ex et quam laborum"))
                .body("id", hasItems(441))
                .body("postId", hasItems(89));
        response.prettyPrint();
    }

    @Test
    public void postId() {
        Response response = given()
                .when().get("https://jsonplaceholder.typicode.com/comments?postId=1");
        response.then().body("email", hasItem("Eliseo@gardner.biz"));
        response.prettyPrint();
    }

    @Test
    public void userId() {
        Response response = given()
                .when().get("https://jsonplaceholder.typicode.com/posts?userId=1");
        response.then().body("body", hasItem("repudiandae veniam quaerat sunt sed\nalias aut fugiat sit autem sed est\nvoluptatem omnis possimus esse voluptatibus quis\nest aut tenetur dolor neque"));
        response.prettyPrint();
    }

    @Test
    public void delayed() {
        Response response = given()
                .with().get("https://reqres.in/api/users?delay=3");
        response.then().statusCode(200)

                .body("data.first_name", hasItems("Emma"))
                .body("data.id", hasItems(3))
                .body("data.last_name", hasItems("Wong"))
                .body("page", is(1));
        //   response.prettyPrint();
    }

    @Test
    public void SingleUser() {
        Response response = given()
                .when().get("https://reqres.in/api/users/2");
        response.then().statusCode(200)
                .body("data.id", is(2));
    }

    //post techniques
    @Test
    public void RegSuccessful() {
        Response res = given().contentType("application/json")
                .when().body("{\n" +
                        "    \"email\": \"eve.holt@reqres.in\",\n" +
                        "    \"password\": \"pistol\"\n" +
                        "}")
                .post("https://reqres.in/api/register");
        //assertion part
        res.then().body("id", is(4))
                .statusCode(200);
        res.prettyPrint();
    }

    @Test
    public void Unsuccessful() {
        Response res = given().contentType("application/json")
                .when().body("{\n" +
                        "    \"email\": \"sydney@fife\"\n" +
                        "}")
                .post("https://reqres.in/api/register");
        res.then().statusCode(400)
                .body("error", is("Missing password"));
        res.prettyPrint();
    }

    @Test
    public void LoginSuccessful() {
//storing payload message in string value
        String payload = "{\n" +
                "    \"email\": \"eve.holt@reqres.in\",\n" +
                "    \"password\": \"cityslicka\"\n" +
                "}";
        Response res = given().contentType("application/json")
                .when().body(payload)
                .post("https://reqres.in/api/login");
        res.then().body("token", is("QpwL5tke4Pnpja7X4"))
                .statusCode(200);
        res.prettyPrint();
    }

    @Test
    public void LoginUnsucessful() {
        String payload = "{\n" +
                "    \"email\": \"peter@klaven\"\n" +
                "}";
        Response res = given().contentType("application/json")
                .when().body(payload)
                .post("https://reqres.in/api/login");
        res.then().statusCode(400)
                .body("error", equalToIgnoringCase("missing password"));
        res.prettyPrint();
    }

    @Test
    public void create() {
        Response res = given().contentType("application/json")
                .when().body("{\n" +
                        "    \"name\": \"morpheus\",\n" +
                        "    \"job\": \"leader\"\n" +
                        "}")
                .post("https://reqres.in/api/users");
        res.then().statusCode(201)
                .body("job", is("leader"));

        res.prettyPrint();
    }

    @Test
    public void BestBuyGet() {
        Response response = given()
                .when().get("http://localhost:3030/products");
        response.then().statusCode(200)
                .body("data.name", hasItems("Duracell - AAA Batteries (4-Pack)"))
                .body("data.type", hasItem("HardGood"))
                .body("limit", is(10));
        //  .body("data.categories.name",hasItems("Alkaline Batteries"));
        // .body("data.price",hasItems(7.49,144.99));
        // .body("data.categories.name",hasItems("Alkaline Batteries"));
        response.prettyPrint();

    }

    @Test
    public void GetTesting() {
        Response res = given()
                .when().get("http://localhost:3030/products/9999679");
        res.then().statusCode(404);
        res.prettyPrint();
    }

    @Test
    public void PostTesting() {
        Response res = given().contentType("application/json")
                .when().body("ilford")

                .post("http://localhost:3030/docs/#!/stores/addStore");
        res.then().statusCode(500);
        res.prettyPrint();
    }

    @Test
    public void PostStore() {
        Response res = given().contentType("application/json")
                .when().body("testng1i")

                .post("http://localhost:3030/docs/#!/stores/addStore");
        res.then().body("name", is("GeneralError"));
        //.statusCode(500);
        res.prettyPrint();
    }

    @Test
    public void StoreGet() {
        Response res = given()
                .when().get("http://localhost:3030/stores");
        res.then().statusCode(200)
                .body("data.city", hasItems("Hopkins"));
        //    .body("services.serviceId",equalTo(1));
        res.prettyPrint();
    }

    @Test
    public void StorePost() {
        Response res = given().contentType("application/json")
                .when().body("Maven IT Solutions")
                .post("http://localhost:3030/stores");
        res.then().statusCode(500)
                .body("message", is("Unexpected token M in JSON at position 0"));
        res.prettyPrint();

    }

    @Test
    public void StorePost1() {
        String payload;
        payload = "hello";
        Response res = given().contentType("application/json")
                .when().body(payload)
                .post("http://localhost:3030/stores");
        res.then().statusCode(500);
        // .body("message",is("Unexpected token M in JSON at position 0"));
        res.prettyPrint();
    }

    @Test
    public void StorePost2() {
        Response res = given().contentType("application/json")
                .when().body("{\n" +
                        "  \"name\": \"umer\",\n" +
                        "  \"type\": \"abcd\",\n" +
                        "  \"address\": \"forestgate\",\n" +
                        "  \"address2\": \"string\",\n" +
                        "  \"city\": \"string\",\n" +
                        "  \"state\": \"string\",\n" +
                        "  \"zip\": \"string\",\n" +
                        "  \"lat\": 0,\n" +
                        "  \"lng\": 0,\n" +
                        "  \"hours\": \"string\",\n" +
                        "  \"services\": {}\n" +
                        "}")

                .post("http://localhost:3030/stores");
        res.then().statusCode(201)
                .body("name", is("umer"));
        res.prettyPrint();
    }

    @Test
    public void ServicesGet() {
        Response res = given()
                .when().get("http://localhost:3030/services");
        res.then().statusCode(200)
                .body("total", is(21))
                .body("data.name", hasItems("Best Buy Mobile"));
        res.prettyPrint();
    }

    @Test
    public void ServicesPost() {
        Response res = given().contentType("application/json")
                .when().body("{\n" +
                        "  \"name\": \"Maven IT Solutions\"\n" +
                        "}")
                .post("http://localhost:3030/services");
        res.then().statusCode(201)
                .body("name", is("Maven IT Solutions"));
        res.prettyPrint();
    }

    @Test
    public void CategoriesGet() {
        Response res = given()
                .when().get("http://localhost:3030/categories");
        res.then().statusCode(200)
                .body("limit", is(10))
                .body("data.name", hasItems("Gift Ideas"));
        res.prettyPrint();


    }

    @Ignore
    public void CategoriesPost() {
        Response res = given().contentType("application/json")
                .when().body("{\n" +
                        "  \"name\": \"umer\",\n" +
                        "  \"id\": \"1\"\n" +
                        "}")
                .post("http://localhost:3030/categories");
        res.then().statusCode(201)
                .body("name", is("umer"));
        res.prettyPrint();
        //best buy intentionally failed this test as the response code meant to be 400
    }

    @Test
    public void UtilitiesGet() {
        Response res = given()
                .when().get("http://localhost:3030/version");
        res.then().statusCode(200)
                .body("version", is("1.1.0"));
        res.prettyPrint();
    }
    @Test
    public void CategoriesGet1(){
        Response res = given()
                .when().get("http://localhost:3030/healthcheck");
        res.then().statusCode(200)

              //  .body("categories",is(4308))
              //  .body("products",is(51960));
        .body("readonly",is(false));
        res.prettyPrint();
    }
}

