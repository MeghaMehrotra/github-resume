package de.ista.githubresume;


import com.fasterxml.jackson.databind.ObjectMapper;
import de.ista.githubresume.dto.MessageDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author mmehrotra
 */
@SpringBootTest
@AutoConfigureMockMvc
public class GithubResumeApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testGetResume() throws Exception {
        String accountName = "mmehrotra";
        String mediaType = "json";

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/resume")
                .param("name", accountName)
                .param("mediaType", mediaType)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String content = result.getResponse().getContentAsString();
        MessageDTO messageDto = new ObjectMapper().readValue(content, MessageDTO.class);
        assertThat(messageDto.getStatus()).isTrue();
        assertThat(messageDto.getResponseMessage()).isEqualTo("Github Resume fetch success!!");
        assertThat(messageDto.getBody()).isNotNull();
    }

    @Test
    public void getResume_shouldReturnXml() throws Exception {
        String accountName = "test";
        MediaType mediaType = MediaType.APPLICATION_XML;
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.ACCEPT, mediaType.toString());

         mockMvc.perform(MockMvcRequestBuilders.get("/resume")
                .param("name", accountName)
                .headers(headers))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_XML));

    }
}
