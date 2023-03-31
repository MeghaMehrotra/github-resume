package de.ista.githubresume.controller;

import de.ista.githubresume.model.GithubResume;
import de.ista.githubresume.service.GithubResumeService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * @author mmehrotra
 */

public class GithubResumeControllerTest {

    private MockMvc mockMvc;

    @Mock
    private GithubResumeService githubResumeService;

    private static final Logger logger = LogManager.getLogger(GithubResumeControllerTest.class);


    @BeforeEach
    void setup(){
        MockitoAnnotations.openMocks(this);
        GithubResumeController githubResumeController = new GithubResumeController(githubResumeService);
        mockMvc = MockMvcBuilders.standaloneSetup(githubResumeController).build();
    }

    @Test
    void testGetGithubResume() throws Exception{
        String testAccountName = "testAcc";
        Optional<GithubResume> githubResumeOptional = Optional.of(new GithubResume());
        when(githubResumeService.getGithubResumeByName(any(String.class))).thenReturn(githubResumeOptional.get());

        mockMvc.perform(get("/resume").param("name", testAccountName))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value(true));
    }

    @Test
    void testGetGithubResume_NotFound() throws Exception {
        String testAccountName = "test";
        Optional<GithubResume> githubResumeOptional = Optional.empty();
        when(githubResumeService.getGithubResumeByName(any(String.class))).thenReturn(githubResumeOptional.orElse(null));

        mockMvc.perform(get("/resume").param("name", testAccountName))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value(false));
    }

    @Test
    void testGetGithubResume_ContentType_XML() throws Exception {
        String testAccountName = "test";
        Optional<GithubResume> githubResumeOptional = Optional.empty();
        when(githubResumeService.getGithubResumeByName(any(String.class))).thenReturn(githubResumeOptional.orElse(null));

        mockMvc.perform(get("/resume").param("name", testAccountName))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value(false));
    }
}
