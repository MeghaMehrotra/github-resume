package de.ista.githubresume.service;

/**
 * @author mmehrotra
 */


import de.ista.githubresume.model.GithubResume;
import de.ista.githubresume.model.Repo;
import de.ista.githubresume.service.impl.GithubResumeServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;


@SpringBootTest
@AutoConfigureMockMvc
public class GithubResumeServiceTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private GithubResumeServiceImpl githubResumeService;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @BeforeEach
    public void setup() {
        GithubResume githubResume = new GithubResume();
        githubResume.setLogin("testuser");
        githubResume.setLogin("Test User");
        githubResume.setUrl("https://github.com/testuser");
        githubResume.setPublic_repos(10);
        githubResume.setRepos_url("https://api.github.com/users/testuser/repos");

        List<Repo> repos = new ArrayList<>();
        Repo repo1 = new Repo();
        repo1.setName("test-repo-1");
        repo1.setHtml_url("https://github.com/testuser/repo1");
        repo1.setDescription("Test repository 1");
        Repo repo2 = new Repo();
        repo2.setName("test-repo-2");
        repo2.setHtml_url("https://github.com/testuser/repo2");
        repo2.setDescription("Test repository 2");
        repos.add(repo1);
        repos.add(repo2);

        ResponseEntity<GithubResume> githubResumeResponse = new ResponseEntity<>(githubResume, HttpStatus.OK);
        ResponseEntity<List<Repo>> repoResponse = new ResponseEntity<>(repos, HttpStatus.OK);
        when(restTemplate.exchange(eq("https://api.github.com/users/testuser"), eq(HttpMethod.GET), any(),
                eq(new ParameterizedTypeReference<GithubResume>() {
                }))).thenReturn(githubResumeResponse);
        when(restTemplate.exchange(eq("https://api.github.com/users/testuser/repos"), eq(HttpMethod.GET), any(),
                eq(new ParameterizedTypeReference<List<Repo>>() {
                }))).thenReturn(repoResponse);

        // Call the service method
        GithubResume result = githubResumeService.getGithubResumeByName("testuser");

        // Verify that the response is correct
        assertEquals("testuser", result.getLogin());
        assertEquals("Test User", result.getLogin());
        assertEquals("https://github.com/testuser", result.getUrl());
        assertEquals(10, result.getPublic_repos());
        assertEquals(2, result.getRepos().size());
        assertEquals("test-repo-1", result.getRepos().get(0).getName());
        assertEquals("https://github.com/testuser/repo1", result.getRepos().get(0).getHtml_url());
        assertEquals("Test repository 1", result.getRepos().get(0).getDescription());
        assertEquals("test-repo-2", result.getRepos().get(1).getName());
        assertEquals("https://github.com/testuser/repo2", result.getRepos().get(1).getHtml_url());
        assertEquals("Test repository 2", result.getRepos().get(1).getDescription());

    }
}