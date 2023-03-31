import com.gargoylesoftware.htmlunit.HttpMethod;
import com.gargoylesoftware.htmlunit.WebResponse;
import org.json.JSONObject;
import org.junit.*;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

public class EndpointTest {
    private static GlpiApiCall glpiApiCall;
    private static String sessionToken;

    private static int idTicket;

    private static URL url;
    @BeforeClass
    public static void setUpClass() throws IOException {
        System.out.println("**************** SETUP CLASS ENDPOINTTEST****************\n");
        glpiApiCall = new GlpiApiCall();
        System.out.println("**************** Token de session ****************");
        sessionToken = glpiApiCall.getSessionToken();
        System.out.println("Token de session : " + sessionToken + "\n");
        Ticket ticClass = new Ticket("testTitre", "testDescription", 2, 4);
        WebResponse response = glpiApiCall.createTicket(sessionToken, ticClass);
        idTicket = glpiApiCall.getIdTicket(response);
        System.out.println("*******ticket créer dans le setUp class -- ticket numero ***** " + idTicket);

    }

    @Before
    public void setUp() throws IOException {
        url = new URL("http://localhost/glpi_10_0_6/apirest.php");
        sessionToken = glpiApiCall.getSessionToken();
    }
    @Test
    public void TestInitSessionEndpoint_Should_restrieveSessionToken() throws IOException {
         String urlInit = url+  "/initSession/";
         URL urlInitSession = new URL(urlInit);
         WebResponse response = glpiApiCall.sendHttpRequest(urlInitSession, HttpMethod.GET,
                 "9dCPK2HR6Hck118pXe9Kz6xHfh9vuVmF8NxRRMZW",
                "ldCBa7mdyke7xdwEF7D68SLRuxjPIGkMacFwvy1O",
                null, null, null, null,
                "application/json",
                HttpURLConnection.HTTP_OK, null);

        String output = response.getContentAsString();
        assertTrue(output.contains("session_token"));
    }

    @Test
    public void TestKillSessionEndpoint_Should_restrieve200() throws IOException {
        String urlInit = url+  "/killSession/";
        URL urlInitSession = new URL(urlInit);
        String sessionToken2 = glpiApiCall.getSessionToken();
        WebResponse response = glpiApiCall.sendHttpRequest(urlInitSession, HttpMethod.GET,
                "9dCPK2HR6Hck118pXe9Kz6xHfh9vuVmF8NxRRMZW",
                null,
                sessionToken2, null, null, null,
                "application/json",
                HttpURLConnection.HTTP_OK, null);

        assertEquals(response.getStatusCode(), HttpURLConnection.HTTP_OK);
    }
    @Test
    public void TestGetMyProfilesEndpoint_Should_restrieve200() throws IOException {
        String urlInit = url+  "/getMyProfiles/";
        URL urlInitSession = new URL(urlInit);
        WebResponse response = glpiApiCall.sendHttpRequest(urlInitSession, HttpMethod.GET,
                "9dCPK2HR6Hck118pXe9Kz6xHfh9vuVmF8NxRRMZW",
                null,
                sessionToken, null, null, null,
                "application/json",
                HttpURLConnection.HTTP_OK, null);

        assertEquals(response.getStatusCode(), HttpURLConnection.HTTP_OK);
    }

    @Test
    public void TestGetMyEntitiesEndpoint_Should_restrieve200() throws IOException {
        String urlInit = url+  "/getMyEntities/";
        URL urlInitSession = new URL(urlInit);
        WebResponse response = glpiApiCall.sendHttpRequest(urlInitSession, HttpMethod.GET,
                "9dCPK2HR6Hck118pXe9Kz6xHfh9vuVmF8NxRRMZW",
                null,
                sessionToken, null, null, null,
                "application/json",
                HttpURLConnection.HTTP_OK, null);

        assertEquals(response.getStatusCode(), HttpURLConnection.HTTP_OK);
    }

    @Test
    public void TestActiveEntitiesEndpoint_Should_restrieve200() throws IOException {
        String urlInit = url+  "/getActiveEntities/";
        URL urlInitSession = new URL(urlInit);
        WebResponse response = glpiApiCall.sendHttpRequest(urlInitSession, HttpMethod.GET,
                "9dCPK2HR6Hck118pXe9Kz6xHfh9vuVmF8NxRRMZW",
                null,
                sessionToken, null, null, null,
                "application/json",
                HttpURLConnection.HTTP_OK, null);

        assertEquals(response.getStatusCode(), HttpURLConnection.HTTP_OK);
    }

    @Test
    public void TestFullSessionEndpoint_Should_restrieve200() throws IOException {
        String urlInit = url+  "/getFullSession/";
        URL urlInitSession = new URL(urlInit);
        WebResponse response = glpiApiCall.sendHttpRequest(urlInitSession, HttpMethod.GET,
                "9dCPK2HR6Hck118pXe9Kz6xHfh9vuVmF8NxRRMZW",
                null,
                sessionToken, null, null, null,
                "application/json",
                HttpURLConnection.HTTP_OK, null);

        assertEquals(response.getStatusCode(), HttpURLConnection.HTTP_OK);
    }

    @Test
    public void TestGlpiConfigEndpoint_Should_restrieve200() throws IOException {
        String urlInit = url+  "/getGlpiConfig/";
        URL urlInitSession = new URL(urlInit);
        WebResponse response = glpiApiCall.sendHttpRequest(urlInitSession, HttpMethod.GET,
                "9dCPK2HR6Hck118pXe9Kz6xHfh9vuVmF8NxRRMZW",
                null,
                sessionToken, null, null, null,
                "application/json",
                HttpURLConnection.HTTP_OK, null);

        assertEquals(response.getStatusCode(), HttpURLConnection.HTTP_OK);
    }


    @Test
    public void TestGetMassiveActionsEndpoint_Should_restrieve200() throws IOException {
        String urlInit = url+  "/getMassiveActions/Ticket/";
        URL urlInitSession = new URL(urlInit);
        WebResponse response = glpiApiCall.sendHttpRequest(urlInitSession, HttpMethod.GET,
                "9dCPK2HR6Hck118pXe9Kz6xHfh9vuVmF8NxRRMZW",
                null,
                sessionToken, null, null, null,
                "application/json",
                HttpURLConnection.HTTP_OK, null);

        assertEquals(response.getStatusCode(), HttpURLConnection.HTTP_OK);
    }




    @After
    public void tearDown() throws IOException {
        url = new URL("http://localhost/glpi_10_0_6/apirest.php");
        glpiApiCall.DeleteATicket(sessionToken, idTicket );
        sessionToken = glpiApiCall.getSessionToken();
    }

    @AfterClass
    public static void tearDownClass() throws IOException {
        System.out.println("**************** TEAR DOWN ENDPOINTCLASS ****************\n");
        System.out.println("**************** delete ticket ****************\n");
        glpiApiCall.DeleteAllTicket(sessionToken);

    }
}
