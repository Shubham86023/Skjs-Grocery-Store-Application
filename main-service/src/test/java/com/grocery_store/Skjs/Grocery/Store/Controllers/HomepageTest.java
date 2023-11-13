package com.grocery_store.Skjs.Grocery.Store.Controllers;

import com.grocery_store.Skjs.Grocery.Store.Services.AuthenticationServices.Login_ops;
import com.grocery_store.Skjs.Grocery.Store.Services.DataForHomepages;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.HashMap;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(Homepage.class)
class HomepageTest {

//    private MockMvc mockMvc;
//
//    @MockBean
//    private DataForHomepages dataForHomepages;
//
//    @MockBean
//    private Login_ops loginOps;
//
//    @BeforeEach
//    public void setUp() {
//        // Creating mock
//        mockMvc = MockMvcBuilders.standaloneSetup(new Homepage(dataForHomepages, loginOps)).build();
//    }
//
//    @Test
//    void homepageTest() throws Exception {
//
//        Mockito.when(dataForHomepages.provideHomepageData()).thenReturn(new HashMap<>());
//        Mockito.when(loginOps.isLoggedIn()).thenReturn(false);
//
//        mockMvc.perform(get("/"))
//                .andExpect(status().isOk())
//                .andExpect(view().name("index"));
//
//        // .andExpect(model().attribute("is_login", false))
//        // .andExpect(model().attribute("bis_login", false));
//    }
}
