package br.com.fernando.worldtour.model.domain;

import java.io.Serializable;

/**
 * Created by fernando on 04/09/16.
 */
public class User implements Serializable {

    private String idFacebook;
    private String name;
    private String email;
    private String profilePicUrl;


    public String getIdFacebook() {
        return idFacebook;
    }

    public void setIdFacebook(String idFacebook) {
        this.idFacebook = idFacebook;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getProfilePicUrl() {
        return profilePicUrl;
    }

    public void setProfilePicUrl(String profilePicUrl) {
        this.profilePicUrl = profilePicUrl;
    }
}
