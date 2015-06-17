package org.acactown.clickchat.service.client

import groovy.util.logging.Slf4j
import org.acactown.clickchat.service.util.RestUtil
import org.springframework.http.client.ClientHttpResponse
import org.springframework.web.client.ResponseErrorHandler

/**
 * @author Andrés Amado
 * @since 2015-06-17
 */
@Slf4j
class ApiClientResponseHandler implements ResponseErrorHandler {

    @Override
    boolean hasError(ClientHttpResponse response) throws IOException {
        return RestUtil.isError(response.statusCode)
    }

    @Override
    void handleError(ClientHttpResponse response) throws IOException {
        StringBuilder body = new StringBuilder()
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(response.body))
            String line
            while ((line = reader.readLine()) != null) {
                body.append(line);
            }
        } catch (IOException e) {
            log.error("Error while reading body from REST response.", e)
        }

        log.error("Error in REST call. Response body is: [${body}]")
    }

}

