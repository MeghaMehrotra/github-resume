package de.ista.githubresume.service.impl;

import de.ista.githubresume.model.GithubResume;
import de.ista.githubresume.model.Repo;
import de.ista.githubresume.service.GithubResumeService;
import org.apache.coyote.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * @author mmehrotra
 */

@Service
public class GithubResumeServiceImpl implements GithubResumeService {


    @Value("${github.api.url}")
    private String githubApiUrl;

    private static final Logger logger = LogManager.getLogger(GithubResumeServiceImpl.class);
    private  RestTemplate restTemplate;

    @Autowired
    public GithubResumeServiceImpl(RestTemplateBuilder restTemplateBuilder){
        this.restTemplate = restTemplateBuilder.build();
    }
    public GithubResume getGithubResumeByName(String accountName){

        StringBuilder stringBuilder = new StringBuilder(githubApiUrl);
        stringBuilder.append(accountName);

        try {
            ResponseEntity<GithubResume> githubResumeResponse = restTemplate.exchange(stringBuilder.toString()
                    ,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<GithubResume>() {}
            );
            if (githubResumeResponse.getStatusCode().is2xxSuccessful()) {
                Optional<GithubResume> optionalGithubResume = Optional.ofNullable(githubResumeResponse.getBody());
                if(optionalGithubResume.isPresent()){
                    GithubResume githubResume = optionalGithubResume.get();
                    if(!githubResume.getRepos_url().isBlank())
                    {
                        ResponseEntity<List<Repo>> repoResponse = restTemplate.exchange(githubResume.getRepos_url()
                                ,
                                HttpMethod.GET,
                                null,
                                new ParameterizedTypeReference<List<Repo>>() {}
                        );
                        if (repoResponse.getStatusCode().is2xxSuccessful()){
                            if(repoResponse.getBody() != null){
                                githubResume.setRepos(repoResponse.getBody());
                            }
                            return githubResume;
                        }else{
                            logger.debug("Failed to fetch the repos with status code {}" ,repoResponse.getStatusCode());
                        }
                    }
                }

            } else {
                logger.debug("Failed to fetch details repositories with status code {}", githubResumeResponse.getStatusCode());

            }
        } catch (RestClientException e) {
            logger.debug("Failed to fetch popular repositories: {}", e.getMessage());
        }
        return null;
    }
}
