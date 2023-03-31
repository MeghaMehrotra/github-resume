package de.ista.githubresume.service.impl;

import de.ista.githubresume.model.GithubResume;
import de.ista.githubresume.model.Repo;
import de.ista.githubresume.service.GithubResumeService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * This Service implementation interacts with github api to retrieve the account details
 *
 * @author mmehrotra
 */

@Service
public class GithubResumeServiceImpl implements GithubResumeService {


    private static final Logger logger = LogManager.getLogger(GithubResumeServiceImpl.class);
    private final RestTemplate restTemplate;
    @Value("${github.api.url}")
    private String githubApiUrl;

    @Autowired
    public GithubResumeServiceImpl(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    public GithubResume getGithubResumeByName(String accountName) {

        StringBuilder stringBuilder = new StringBuilder(githubApiUrl);
        stringBuilder.append(accountName);
        GithubResume githubResume = new GithubResume();

        try {
            ResponseEntity<GithubResume> githubResumeResponse = restTemplate.exchange(stringBuilder.toString(),
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<GithubResume>() {
                    }
            );
            if (githubResumeResponse.getStatusCode().is2xxSuccessful()) {
                Optional<GithubResume> optionalGithubResume = Optional.ofNullable(githubResumeResponse.getBody());
                if (optionalGithubResume.isPresent()) {
                    githubResume = optionalGithubResume.get();
                    if (!githubResume.getRepos_url().isBlank()) {
                        ResponseEntity<List<Repo>> repoResponse = restTemplate.exchange(githubResume.getRepos_url()
                                ,
                                HttpMethod.GET,
                                null,
                                new ParameterizedTypeReference<List<Repo>>() {
                                }
                        );
                        if (repoResponse.getStatusCode().is2xxSuccessful()) {
                            if (repoResponse.getBody() != null) {
                                githubResume.setRepos(repoResponse.getBody());
                                githubResume.setLanguagePercentages(getLanguagePercentages(repoResponse.getBody()));
                            }

                            return githubResume;
                        } else {
                            logger.debug("Failed to fetch the repos with status code {}", repoResponse.getStatusCode());
                        }
                    }
                }

            } else {
                logger.debug("Failed to fetch the github account details", githubResumeResponse.getStatusCode());

            }
        } catch (RestClientException e) {
            logger.debug("Error while calling REST API: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return githubResume;
    }

    public Map<String, Double> getLanguagePercentages(List<Repo> repos) {

        double totalSize = repos.stream().mapToDouble(Repo::getSize).sum();

        Map<String, Double> languagePercentages = new HashMap<>();
        for (Repo repo : repos) {
            String language = repo.getLanguage();
            if (language != null) {
                double size = repo.getSize();
                double percentage = size / totalSize * 100;
                languagePercentages.merge(language, percentage, Double::sum);
            }
        }

        return languagePercentages;
    }
}
