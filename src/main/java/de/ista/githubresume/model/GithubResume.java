package de.ista.githubresume.model;


import jdk.jfr.DataAmount;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @author mmehrotra
 */

@Data
public class GithubResume {

    String login;
    String url;
    String repos_url;
    List<Repo> repos;
    Integer public_repos;
    Map<String,Double> languagePercentages;

}
