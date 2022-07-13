package com.kot.horizon.security.jwt;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.PostConstruct;
import com.nimbusds.jose.EncryptionMethod;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWEAlgorithm;
import com.nimbusds.jose.JWEHeader;
import com.nimbusds.jose.JWEObject;
import com.nimbusds.jose.KeyLengthException;
import com.nimbusds.jose.Payload;
import com.nimbusds.jose.crypto.DirectEncrypter;
import com.nimbusds.jose.jwk.source.ImmutableSecret;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.BadJOSEException;
import com.nimbusds.jose.proc.JWEDecryptionKeySelector;
import com.nimbusds.jose.proc.JWEKeySelector;
import com.nimbusds.jose.proc.SimpleSecurityContext;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.proc.DefaultJWTClaimsVerifier;
import com.nimbusds.jwt.proc.DefaultJWTProcessor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;
@Service
public class JwtService {

	@Value("${horizon.oauth2.refreshSec}")
	private Integer refreshTimeSec;

	private static final String JWT_SECRET = "841D4G6C80CB0QFCAW89D5367C18C53B";
	public static final String HORIZON_IAT = "horizon-iat";
	public static final String AUTH_AUDIENCE = "auth";

	private DirectEncrypter encrypter;
	private JWEHeader header = new JWEHeader(JWEAlgorithm.DIR, EncryptionMethod.A128CBC_HS256);
	private DefaultJWTProcessor<SimpleSecurityContext> jwtProcessor;


	@PostConstruct
	public void init() throws KeyLengthException {
		byte[] secretKey = JWT_SECRET.getBytes();
		encrypter = new DirectEncrypter(secretKey);
		jwtProcessor = new DefaultJWTProcessor<>();
		DefaultJWTClaimsVerifier claimsVerifier = new DefaultJWTClaimsVerifier<>();
		claimsVerifier.setMaxClockSkew( refreshTimeSec );
		jwtProcessor.setJWTClaimsSetVerifier( claimsVerifier );
		JWKSource<SimpleSecurityContext> jweKeySource = new ImmutableSecret<>(secretKey);

		JWEKeySelector<SimpleSecurityContext> jweKeySelector =
				new JWEDecryptionKeySelector<>(JWEAlgorithm.DIR, EncryptionMethod.A128CBC_HS256, jweKeySource);

		jwtProcessor.setJWEKeySelector(jweKeySelector);
	}

	public String createToken(String aud, String subject, Long expirationMillis, Map<String, Object> claimMap) {

		JWTClaimsSet.Builder builder = new JWTClaimsSet.Builder();

		builder
				.expirationTime(new Date(System.currentTimeMillis() + expirationMillis))
				.audience(aud)
				.subject(subject)
				.claim(HORIZON_IAT, System.currentTimeMillis());

		claimMap.forEach(builder::claim);

		JWTClaimsSet claims = builder.build();

		Payload payload = new Payload(claims.toJSONObject());

		JWEObject jweObject = new JWEObject(header, payload);

		try {
			jweObject.encrypt(encrypter);
		} catch (JOSEException e) {
			throw new RuntimeException(e);
		}

		return jweObject.serialize();
	}

	public String createToken(String audience, String subject, Long expirationMillis) {
		return createToken(audience, subject, expirationMillis, new HashMap<>());
	}

	public JWTClaimsSet parseToken(String token) {
		try {
			return jwtProcessor.process(token, null);
		} catch (ParseException | BadJOSEException | JOSEException e) {
			throw new BadCredentialsException(e.getMessage());
		}
	}
}
