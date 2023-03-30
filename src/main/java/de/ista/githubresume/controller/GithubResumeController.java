package de.ista.githubresume.controller;

import de.ista.githubresume.model.GithubResume;
import de.ista.githubresume.service.GithubResumeService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

/**
 * @author mmehrotra
 */

@RestController
public class GithubResumeController {

    private static final Logger logger = LogManager.getLogger(GithubResumeController.class);

    private GithubResumeService githubResumeService;


    public GithubResumeController(GithubResumeService githubResumeService) {
        this.githubResumeService = githubResumeService;
    }

    /**
     * This method takes accountName and mediaType and returns github resume of accountName
     *
     * @param accountName
     * @param mediaType
     * @param headers
     * @return
     */
    @GetMapping(value = "/resume", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<GithubResume> getResume(@RequestParam(value = "name", required = true) String accountName, @RequestParam(required = false, defaultValue = "JSON") de.ista.githubresume.model.MediaType mediaType,
                                                  @RequestHeader HttpHeaders headers) {

        if (!headers.containsKey(HttpHeaders.ACCEPT)) {
            headers.set(HttpHeaders.CONTENT_TYPE, "application/" + mediaType.name().toLowerCase());
        }
        logger.info("Request received with accountName {} & mediaType {}", accountName, mediaType.name());
        Optional<GithubResume> githubResumeOptional = Optional.ofNullable(githubResumeService.getGithubResumeByName(accountName));
        if (githubResumeOptional.isPresent()) {
            return new ResponseEntity<>(githubResumeOptional.get(), headers, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }


    }

}
