package de.ista.githubresume.model;


import jdk.jfr.DataAmount;
import lombok.Data;

import java.util.List;

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

}
