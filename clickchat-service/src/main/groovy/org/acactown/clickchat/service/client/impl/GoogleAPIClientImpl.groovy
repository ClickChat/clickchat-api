package org.acactown.clickchat.service.client.impl

import com.google.common.base.Optional
import groovy.util.logging.Slf4j
import org.acactown.clickchat.commons.Environment
import org.acactown.clickchat.service.client.GoogleAPIClient
import org.acactown.clickchat.service.model.GoogleUserInfo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import org.springframework.http.ResponseEntity

import static org.springframework.http.HttpMethod.GET
import static org.springframework.http.HttpStatus.OK

/**
 * @author Andrés Amado
 * @since 2015-06-17
 */
@Slf4j
@Service
class GoogleAPIClientImpl implements GoogleAPIClient {

    private final RestTemplate restTemplate
    private final Environment environment
    private final String endpoint

    private static final String AUTHORIZATION_HEADER = "Authorization"
    private static final String GOOGLE_ENDPOINT = "GOOGLE_ENDPOINT"
    private static final String DEFAULT_GOOGLE_ENDPOINT = "https://www.googleapis.com/oauth2/v2/userinfo"

    @Autowired
    GoogleAPIClientImpl(@Qualifier("googleRestTemplate") RestTemplate restTemplate, Environment environment) {
        this.restTemplate = restTemplate

        endpoint = environment.getPropertyOr(GOOGLE_ENDPOINT, DEFAULT_GOOGLE_ENDPOINT)
    }

    @Override
    Optional<GoogleUserInfo> getUserInfo(String accessToken, String tokenType) {
        HttpHeaders headers = new HttpHeaders()
        headers.add(AUTHORIZATION_HEADER, "${tokenType} ${accessToken}")

        HttpEntity<String> request = new HttpEntity<>(headers)
        ResponseEntity<GoogleUserInfo> userInfo = restTemplate.exchange(endpoint, GET, request, GoogleUserInfo)
        if (userInfo && OK.equals(userInfo.statusCode)) {
            return Optional.of(userInfo.body)
        }

        return Optional.absent()
    }

}
