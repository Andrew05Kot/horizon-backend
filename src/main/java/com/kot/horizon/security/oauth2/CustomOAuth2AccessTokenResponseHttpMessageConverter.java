package com.kot.horizon.security.oauth2;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.endpoint.OAuth2AccessTokenResponse;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.security.oauth2.core.http.converter.OAuth2AccessTokenResponseHttpMessageConverter;
import org.springframework.util.StringUtils;

public class CustomOAuth2AccessTokenResponseHttpMessageConverter extends OAuth2AccessTokenResponseHttpMessageConverter {

    public CustomOAuth2AccessTokenResponseHttpMessageConverter() {
        tokenResponseConverter = new CustomOAuth2AccessTokenResponseConverter();
    }

    private static class CustomOAuth2AccessTokenResponseConverter implements Converter<Map<String, String>, OAuth2AccessTokenResponse> {
        private static final String USER_ID_PARAMETER = "user_id";

        private static final Set<String> TOKEN_RESPONSE_PARAMETER_NAMES = new HashSet<>(Arrays.asList(
                OAuth2ParameterNames.ACCESS_TOKEN,
                OAuth2ParameterNames.TOKEN_TYPE,
                OAuth2ParameterNames.EXPIRES_IN,
                OAuth2ParameterNames.REFRESH_TOKEN,
                OAuth2ParameterNames.SCOPE
        ));

        @Override
        public OAuth2AccessTokenResponse convert(Map<String, String> tokenResponseParameters) {
            String accessToken = tokenResponseParameters.get(OAuth2ParameterNames.ACCESS_TOKEN);

            OAuth2AccessToken.TokenType accessTokenType = null;
            if (OAuth2AccessToken.TokenType.BEARER.getValue().equalsIgnoreCase(
                    tokenResponseParameters.get(OAuth2ParameterNames.TOKEN_TYPE))) {
                accessTokenType = OAuth2AccessToken.TokenType.BEARER;
            }

            long expiresIn = 0;
            if (tokenResponseParameters.containsKey(OAuth2ParameterNames.EXPIRES_IN)) {
                try {
                    expiresIn = Long.parseLong(tokenResponseParameters.get(OAuth2ParameterNames.EXPIRES_IN));
                } catch (NumberFormatException ex) { }
            }

            if ( isInstagramShortLivedToken( tokenResponseParameters ) ){
                accessTokenType = OAuth2AccessToken.TokenType.BEARER;
                expiresIn = 3600L;
            }

            Set<String> scopes = Collections.emptySet();
            if (tokenResponseParameters.containsKey(OAuth2ParameterNames.SCOPE)) {
                String scope = tokenResponseParameters.get(OAuth2ParameterNames.SCOPE);
                scopes = new HashSet<>(Arrays.asList(StringUtils.delimitedListToStringArray(scope, " ")));
            }

            String refreshToken = tokenResponseParameters.get(OAuth2ParameterNames.REFRESH_TOKEN);

            Map<String, Object> additionalParameters = new LinkedHashMap<>();
            for (Map.Entry<String, String> entry : tokenResponseParameters.entrySet()) {
                if (!TOKEN_RESPONSE_PARAMETER_NAMES.contains(entry.getKey())) {
                    additionalParameters.put(entry.getKey(), entry.getValue());
                }
            }

            return OAuth2AccessTokenResponse.withToken(accessToken)
                    .tokenType(accessTokenType)
                    .expiresIn(expiresIn)
                    .scopes(scopes)
                    .refreshToken(refreshToken)
                    .additionalParameters(additionalParameters)
                    .build();
        }

        private boolean isInstagramShortLivedToken( Map<String, String> tokenResponseParameters ){
            return tokenResponseParameters.containsKey( OAuth2ParameterNames.ACCESS_TOKEN )
                    && tokenResponseParameters.containsKey( USER_ID_PARAMETER )
                    && !tokenResponseParameters.containsKey( OAuth2ParameterNames.TOKEN_TYPE )
                    && !tokenResponseParameters.containsKey( OAuth2ParameterNames.EXPIRES_IN );
        }
    }
}
