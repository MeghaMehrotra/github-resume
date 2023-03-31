package de.ista.githubresume.controller;

import de.ista.githubresume.dto.MessageDTO;
import de.ista.githubresume.model.GithubResume;
import de.ista.githubresume.model.MediaFormat;
import de.ista.githubresume.service.GithubResumeService;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

/**
 * @author mmehrotra
 */

@RestController
@Validated
public class GithubResumeController {

    private static final Logger logger = LogManager.getLogger(GithubResumeController.class);

    private final GithubResumeService githubResumeService;


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
    public ResponseEntity<MessageDTO> getResume(@RequestParam(value = "name") String accountName,
                                                @RequestParam(required = false, defaultValue = "json") String mediaType,
                                                @RequestHeader HttpHeaders headers) {

        logger.info("Request received with accountName {} & mediaType {}", accountName, mediaType);
        if (!headers.containsKey(HttpHeaders.ACCEPT)) {
            MediaFormat format;
            try {
                format = MediaFormat.getMediaFormat(mediaType);
                headers.set(HttpHeaders.CONTENT_TYPE, "application/" + format.name().toLowerCase());
            } catch (IllegalArgumentException exception) {
                logger.debug("Invalid MediaFormat");
                return new ResponseEntity<>(new MessageDTO<>("Invalid MediaFormat", null, false), headers, HttpStatus.BAD_REQUEST);
            }
        }

        Optional<GithubResume> githubResumeOptional = Optional.ofNullable(githubResumeService.getGithubResumeByName(accountName));
        if (githubResumeOptional.isPresent()) {
            return new ResponseEntity<>(
                    new MessageDTO<>("Github Resume fetch success!!", githubResumeOptional.get(), true), headers, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new MessageDTO<>("Not a valid account to create Github Resume", false), headers, HttpStatus.NOT_FOUND);
        }
    }

}
