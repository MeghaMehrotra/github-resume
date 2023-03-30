package de.ista.githubresume.service;

import de.ista.githubresume.model.GithubResume;

/**
 * @author mmehrotra
 */

public interface GithubResumeService {

    /**
     * gets github account's details by accountName
     * @param accountName
     */
   GithubResume  getGithubResumeByName(String accountName);
}
