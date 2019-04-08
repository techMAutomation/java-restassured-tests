import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.path.json.JsonPath;
import com.jayway.restassured.response.Response;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;

import static com.jayway.restassured.RestAssured.given;

public class ApiTests {

    private static String apiUrl = "https://jsonplaceholder.typicode.com";

    // POST METHOD

    @Test
    public void postMethodApiCallForPostsRequest() {

        Response postsResponse = given().
                contentType(ContentType.JSON).
                body("{\"title\":\"foo\", \"body\":\"bar\", \"userId\":\"9\"}").
                when().
                    post(apiUrl + "/posts").
                then().
                    contentType(ContentType.JSON).
                    statusCode(201).
                extract().
                    response();

        System.out.println("- Posts Response :: " + postsResponse.getBody().prettyPrint());
        JsonPath jsonPath = postsResponse.getBody().jsonPath();
        String userId = jsonPath.get("userId");
        Assert.assertEquals(userId, "9");
    }

    // PUT METHOD

    @Test
    public void putMethodApiCallForPostsRequest() {
        String newBodyName = "CAR PARK";
        Response postsResponse = given().
                contentType(ContentType.JSON).
                body("{\"id\":1, \"title\":\"foo\", \"body\":\"" + newBodyName + "\", \"userId\":\"9\"}").
                when().
                    put(apiUrl + "/posts/1").
                then().
                    contentType(ContentType.JSON).
                    statusCode(200).
                extract().
                    response();

        System.out.println("- Posts Response :: " + postsResponse.getBody().prettyPrint());
        JsonPath jsonPath = postsResponse.getBody().jsonPath();
        String bodyNameFromResponse = jsonPath.get("body");
        Assert.assertEquals(bodyNameFromResponse, newBodyName);
    }

    // PATCH METHOD

    @Test
    public void patchMethodApiCallForPostsRequest() {
        Response postsResponse = given().
                contentType(ContentType.JSON).
                body("{\"title\":\"foo\"}").
                when().
                    patch(apiUrl + "/posts/1").
                then().
                    contentType(ContentType.JSON).
                    statusCode(200).
                extract().
                    response();

        System.out.println("- Posts Response :: " + postsResponse.getBody().prettyPrint());
        JsonPath jsonPath = postsResponse.getBody().jsonPath();
        String titleNameFromResponse = jsonPath.get("title");
        Assert.assertEquals(titleNameFromResponse, "foo");
    }

    // DELETE METHOD

    @Test
    public void deleteMethodApiCallForPostsRequest() {
        given().
                when().
                    delete(apiUrl + "/posts/1").
                then().
                    contentType(ContentType.JSON).
                    statusCode(200).
                    assertThat().body("isEmpty()", Matchers.is(true));
    }

    // GET METHOD for Filtering UserId

    @Test
    public void getMethodApiCallForPostsRequest() {
        Response postsResponse = given().
                when().
                    get(apiUrl + "/posts?userId=9").
                then().
                    contentType(ContentType.JSON).
                    statusCode(200).
                extract().
                    response();

        int userIDList = postsResponse.jsonPath().getList("userId").size();
        Assert.assertTrue(userIDList > 0);
    }

    // GET METHOD for posts Comments

    @Test
    public void getMethodApiCallForCommentsRequest() {
        Response postsResponse = given().
                when().
                    get(apiUrl + "/posts/1/comments").
                then().
                    contentType(ContentType.JSON).
                    statusCode(200).
                extract().
                    response();

        int emailList = postsResponse.jsonPath().getList("email").size();
        Assert.assertTrue(emailList > 0);
    }

    // GET METHOD for Filtering PostIds

    @Test
    public void getMethodApiCallForCommentsPostIdsRequest() {
        Response postsResponse = given().
                when().
                    get(apiUrl + "/comments?postId=1").
                then().
                    contentType(ContentType.JSON).
                    statusCode(200).
                extract().
                    response();

        int postIdsList = postsResponse.jsonPath().getList("postId").size();
        System.out.println("- PostIds count :: " + postIdsList);
        Assert.assertTrue(postIdsList > 0);
    }

}