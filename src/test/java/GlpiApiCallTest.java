import com.gargoylesoftware.htmlunit.WebResponse;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.*;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.*;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertNotEquals;

public class GlpiApiCallTest {

    private static GlpiApiCall glpiApiCall;
    private static String sessionToken;
    private static int idTicket;
    private static URL url;

    @BeforeClass
    public static void setUpClass() throws IOException {
        System.out.println("**************** SETUP CLASS ****************\n");
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
    public void setUp() throws MalformedURLException {
        url = new URL("http://localhost/glpi_10_0_6/apirest.php");
    }


    @Test
    public void test_Should_create_Ticket() throws IOException {
        String urlTicket = url + "/Ticket/";
        Ticket tic = new Ticket("testTitre", "testDescription", 2, 4);
        WebResponse response = glpiApiCall.createTicket(sessionToken, tic);
        int id = glpiApiCall.getIdTicket(response);
        String location = response.getResponseHeaderValue("location");
        System.out.println("**********la location de la creation du ticket****  " + location);
        WebResponse ticketCreer = glpiApiCall.GetTicket(sessionToken, location);
        System.out.println("le ticket effectivement créer " + ticketCreer);
        String name = glpiApiCall.findJsonString(ticketCreer, "name");
        System.out.println("le nom enfinnnnn *** "+ name);
        String content = glpiApiCall.findJsonString(ticketCreer, "content");
        int status = glpiApiCall.findJsonInt(ticketCreer, "status");
        int urgency = glpiApiCall.findJsonInt(ticketCreer, "urgency");

        assertTrue(response.getWebRequest().getRequestBody().contains(tic.description));
        assertEquals(name, tic.titre);
        assertEquals(content, tic.description);
        assertEquals(status, tic.status);
        assertEquals(urgency, tic.urgency);
    }

    @Test
    public void Test_Should_DeleteATicket() throws IOException {
        glpiApiCall.DeleteATicket(sessionToken, idTicket);
        System.out.println("********* ticket effacé numéro ***** " + idTicket);
        WebResponse responseListeTickets = glpiApiCall.getListTickets(sessionToken);

        String output = responseListeTickets.getContentAsString();
        JSONArray listeTicket = new JSONArray(output);
        for (int i = 0; i < listeTicket.length(); i++) {
            JSONObject ticket = listeTicket.getJSONObject(i);
            int id = ticket.getInt("id");
            System.out.println(ticket.getInt("id"));
            assertNotEquals(idTicket, id);
        }
    }
    @Test
    public void Test_Should_DeleteAllTicket() throws IOException {
        glpiApiCall.DeleteAllTicket(sessionToken);
        WebResponse liste = glpiApiCall.getListTickets(sessionToken);
        String output = liste.getContentAsString();
        JSONArray listeTicket = new JSONArray(output);
        assertTrue(listeTicket.isEmpty());

    }
    @Test
    public void Test_Should_getListTicket() throws IOException {

        WebResponse responseListeTickets = glpiApiCall.getListTickets(sessionToken);
        List<Integer> tableauIdTickets = new ArrayList<>();
        String output = responseListeTickets.getContentAsString();
        JSONArray listeTicket = new JSONArray(output);
        for (int i = 0; i < listeTicket.length(); i++) {
            JSONObject ticket = listeTicket.getJSONObject(i);
            int id = ticket.getInt("id");
            tableauIdTickets.add(id);
        }
        assertEquals(tableauIdTickets.size(), 1);
    }



    @After
    public void tearDown() throws IOException {
        url = new URL("http://localhost/glpi_10_0_6/apirest.php");
        glpiApiCall.DeleteATicket(sessionToken, idTicket );
    }

    @AfterClass
    public static void tearDownClass() throws IOException {
        System.out.println("**************** TEAR DOWN CLASS ****************\n");
        System.out.println("**************** delete ticket ****************\n");
        glpiApiCall.DeleteAllTicket(sessionToken);

    }



}
