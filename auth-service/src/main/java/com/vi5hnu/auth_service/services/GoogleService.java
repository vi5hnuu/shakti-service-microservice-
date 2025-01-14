package com.vi5hnu.auth_service.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

import java.io.ByteArrayInputStream;
import java.net.URL;
import java.security.PublicKey;
import java.security.SignatureException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class GoogleService {
    private static final String GOOGLE_REVOKE_ENDPOINT = "https://oauth2.googleapis.com/revoke";
    private static final String GOOGLE_CERT_ENDPOINT = "https://www.googleapis.com/oauth2/v3/certs";
    private final WebClient webClient;
    private final JwtService jwtService;
    private final ObjectMapper objectMapper;


    public Mono<Boolean> revokeAccessToken(String accessToken) {
        return webClient
                .post()
                .uri(GOOGLE_REVOKE_ENDPOINT,uriBuilder -> uriBuilder.queryParam("token", accessToken).build())
                .retrieve()
                .toBodilessEntity()
                .map(response -> response.getStatusCode().is2xxSuccessful())
                .onErrorResume(e -> {
                    log.error(e.getMessage());
                    return Mono.just(false);
                });
    }

    public Map<String, Object> extractIdTokenData(String idToken) throws SignatureException {
        PublicKey publicKey = getGooglePublicKey(idToken);
        return jwtService.getClaims(idToken, publicKey);
    }

    private PublicKey getGooglePublicKey(String idToken) throws SignatureException {
        try {
            List<Map<String, Object>> certs = fetchGooglePublicCerts();

            // Extract the "kid" (Key ID) from the JWT header to match with the correct public key
            String kid = Jwts.parserBuilder().build().parseClaimsJws(idToken).getHeader().getKeyId();

            Optional<Map<String, Object>> matchingCert = certs.stream()
                    .filter(cert -> cert.get("kid").equals(kid))
                    .findFirst();

            if (matchingCert.isEmpty()) {
                throw new SignatureException("Unable to find a matching public key for the provided JWT.");
            }

            // Extract the public key from the matching certificate
            Map<String, Object> cert = matchingCert.get();
            String publicKeyPem = (String) cert.get("x5c"); // Get the "x5c" field containing the public key
            return getPublicKeyFromPem(publicKeyPem);
        } catch (Exception e) {
            throw new SignatureException("Failed to fetch or parse Google's public keys.", e);
        }
    }

    // Method to convert PEM string to PublicKey object
    private PublicKey getPublicKeyFromPem(String pem) throws Exception {
        try {
            // Remove the first and last lines (-----BEGIN CERTIFICATE----- and -----END CERTIFICATE-----)
            String cleanedPem = pem.replace("-----BEGIN CERTIFICATE-----", "")
                    .replace("-----END CERTIFICATE-----", "")
                    .replaceAll("\\s", ""); // Remove any whitespace characters

            // Decode the Base64-encoded PEM string
            byte[] encoded = Base64.getDecoder().decode(cleanedPem);

            // Use the CertificateFactory to generate an X509 certificate from the encoded bytes
            CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(encoded);
            X509Certificate certificate = (X509Certificate) certificateFactory.generateCertificate(byteArrayInputStream);
            return certificate.getPublicKey();
        } catch (Exception e) {
            throw new Exception("Failed to convert PEM to PublicKey", e);
        }
    }

    private List<Map<String, Object>> fetchGooglePublicCerts() throws Exception {
        try {
            String response = webClient.get()
                    .uri(GOOGLE_CERT_ENDPOINT)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();
            return parseCertsJson(response);

        } catch (WebClientResponseException e) {
            throw new Exception("Failed to fetch public certs from Google: " + e.getMessage(), e);
        }
    }

    private List<Map<String, Object>> parseCertsJson(String response) {
        // Example using Jackson (you can replace with your JSON parsing library)
        try {
            Map<String, Object> responseMap = objectMapper.readValue(response, Map.class);
            return (List<Map<String, Object>>) responseMap.get("keys");
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse Google's public certs JSON: " + e.getMessage(), e);
        }
    }
}
