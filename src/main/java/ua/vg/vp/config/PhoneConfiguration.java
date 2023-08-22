package ua.vg.vp.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

@Configuration

public class PhoneConfiguration {
    @Value("${phone.webrtcUser}")
    private String webrtcUser;

    @Value("${phone.webrtcPassword}")
    private String webrtcPassword;

    @Value("${phone.domain}")
    private String domain;

    public String getWebrtcUser() {
        return webrtcUser;
    }

    public void setWebrtcUser(String webrtcUser) {
        this.webrtcUser = webrtcUser;
    }

    public String getWebrtcPassword() {
        return webrtcPassword;
    }

    public void setWebrtcPassword(String webrtcPassword) {
        this.webrtcPassword = webrtcPassword;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }
}
