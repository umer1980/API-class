package com.dec19.maven;

import io.restassured.response.Response;

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
        .body("data.email",is("janet.weaver@reqres.in"))
        .body("data.last_name",is("Weaver"))
        .body("data.first_name",is("Janet"));

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
        .body("data.pantone_value",is("17-2031"))
        .body("data.name",is("fuchsia rose"));
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
        response.then().body("title",hasItems("qui est esse"))
                .body("id",hasItems(3));
        //response.prettyPrint();
    }
    @Test
    public void posts1(){
        Response response = given()
                .when().get("https://jsonplaceholder.typicode.com/posts/1");
        response.then().body("userId",is(1))
                .body("title",is("sunt aut facere repellat provident occaecati excepturi optio reprehenderit"));

        response.prettyPrint();
    }
    @Test
    public void postscomments(){
        Response response = given()
                .when().get("https://jsonplaceholder.typicode.com/posts/1/comments");
        response.then().body("name",hasItems("id labore ex et quam laborum"))
                .body("id",hasItems(441))
                .body("postId",hasItems(89));
        response.prettyPrint();
    }
    @Test
    public void postId(){
        Response response = given()
                .when().get("https://jsonplaceholder.typicode.com/comments?postId=1");
        response.then().body("email",hasItem("Eliseo@gardner.biz"));
        response.prettyPrint();
    }
    @Test
    public void userId(){
        Response response = given()
                .when().get("https://jsonplaceholder.typicode.com/posts?userId=1");
        response.then().body("body",hasItem("repudiandae veniam quaerat sunt sed\nalias aut fugiat sit autem sed est\nvoluptatem omnis possimus esse voluptatibus quis\nest aut tenetur dolor neque"));
        response.prettyPrint();
    }
}

