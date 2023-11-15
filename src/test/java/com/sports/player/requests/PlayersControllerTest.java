package com.sports.player.requests;

import com.hackerrank.test.utility.Order;
import com.hackerrank.test.utility.OrderedTestRunner;
import com.hackerrank.test.utility.TestWatchman;
import com.sports.player.repository.model.Players;

import java.util.ArrayList;
import java.util.List;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestWatcher;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.rules.SpringClassRule;
import org.springframework.test.context.junit4.rules.SpringMethodRule;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@RunWith(OrderedTestRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class PlayersControllerTest {
    @ClassRule
    public static final SpringClassRule springClassRule = new SpringClassRule();

    @Rule
    public final SpringMethodRule springMethodRule = new SpringMethodRule();

    @Rule
    public TestWatcher watchman = TestWatchman.watchman;

    @Autowired
    private MockMvc mockMvc;

    @BeforeClass
    public static void setUpClass() {
        TestWatchman.watchman.registerClass(PlayersControllerTest.class);
    }

    @AfterClass
    public static void tearDownClass() {
        TestWatchman.watchman.createReport(PlayersControllerTest.class);
    }

    /**
     *
     * @throws Exception
     *
     * It tests response to be "{"id":[1,"name":"basketball"},{"id":2,"name":"tennis"},{"id":3],"name":"soccer"}"
     */
    @Test
    @Order(1)
    public void getSportsByEmail() throws Exception {
        String response = mockMvc.perform(MockMvcRequestBuilders.get("/api/sports?email=a@gmail.com"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andReturn()
            .getResponse()
            .getContentAsString();

        Assert.assertEquals(response.toString(), "[{\"id\":2,\"name\":\"soccer\"},{\"id\":1,\"name\":\"tennis\"},{\"id\":3,\"name\":\"basketball\"}]");
    }

    /**
     *
     * @throws Exception
     *
     * It tests response to be ""
     */
    @Test
    @Order(2)
    public void getNoSportsByEmail() throws Exception {
        String response = mockMvc.perform(MockMvcRequestBuilders.get("/api/sports?email=a123@gmail.com"))
            .andExpect(MockMvcResultMatchers.status().isNoContent())
            .andReturn()
            .getResponse()
            .getContentAsString();
        Assert.assertEquals(response, "");
    }

    /**
     *
     * @throws Exception
     *
     * It tests response to be Player JSON object
     */
    @Test
    @Order(3)
    public void getNoPlayers() throws Exception {
        String response = mockMvc.perform(MockMvcRequestBuilders.get("/api/noplayers"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andReturn()
            .getResponse()
            .getContentAsString();

        Assert.assertEquals(response.toString(), "[{\"id\":1,\"email\":\"a1@gmail.com\",\"age\":11,\"level\":3,\"gender\":\"F\",\"sports\":[]}]");
    }
    /**
    *
    * @throws Exception
    *
    * It tests response to be Player JSON object
    */
    @Test
    @Order(4)
    public void getPlayersById() throws Exception {
        String response = mockMvc.perform(MockMvcRequestBuilders.delete("/api/player/1"))
            .andExpect(MockMvcResultMatchers.status().isNoContent())
            .andReturn()
            .getResponse()
            .getContentAsString();

        Assert.assertEquals(response.toString(), "");
    }
    
    /**
    *
    * @throws Exception
    *
    * It tests response to be Player JSON object with pagenation
    */
    @Test
    @Order(5)
    public void getPlayersByPagenation() throws Exception {
        String response = mockMvc.perform(MockMvcRequestBuilders.get("/api/players/pagination?email=a@gmail.com"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andReturn()
            .getResponse()
            .getContentAsString();

        Assert.assertNotNull(response);
    }
}
