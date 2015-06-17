package org.acactown.clickchat.cache.impl

import com.google.common.base.Optional
import groovy.util.logging.Slf4j
import org.acactown.clickchat.cache.TokenEncoder
import org.acactown.clickchat.commons.Token
import org.acactown.clickchat.domain.User
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

import java.security.MessageDigest
import java.security.SecureRandom

/**
 * @author Andrés Amado
 * @since 2015-06-17
 */
@Slf4j
@Service
class TokenEncoderImpl implements TokenEncoder {

    private final SecureRandom random;
    private final MessageDigest md5digest

    private static final String CHARSET = "UTF-8"
    private static final String TYPE = "Bearer"
    private static final char[] HEX = ['0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f']

    @Autowired
    TokenEncoderImpl(MessageDigest md5digest) {
        this.md5digest = md5digest
        random = new SecureRandom()
    }

    @Override
    Optional<Token> generateToken(User user) {
        try {
            String random = new BigInteger(130, random).toString(32),
                   keySource = "${user.email}-${random}-${user.id}"

            return Optional.of(new Token(tokenType: TYPE, accessToken: encode(keySource)))
        } catch (Exception ex) {
            log.error("Error generating token!", ex)
        }

        return Optional.absent()
    }

    private String encode(CharSequence sequence) {
        byte[] hash = md5digest.digest(sequence.toString().getBytes(CHARSET))

        return String.valueOf(encodeBytes(hash))
    }

    private char[] encodeBytes(byte[] bytes) {
        final int nBytes = bytes.length
        char[] result = new char[2 * nBytes]

        int j = 0
        for (int i = 0; i < nBytes; i++) {
            // Char for top 4 bits
            result[j++] = HEX[(0xF0 & bytes[i]) >>> 4]
            // Bottom 4
            result[j++] = HEX[(0x0F & bytes[i])]
        }

        return result
    }

}
