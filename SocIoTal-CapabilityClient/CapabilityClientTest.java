import java.security.NoSuchProviderException;
import java.security.UnrecoverableKeyException;

import org.umu.https.capability.CapabilityToken;
import org.umu.https.capabilityclient.HTTPSCapabilityClient;
import org.umu.https.capabilityclient.Settings;
import org.umu.https.contextmanager.client.HTTPSContextManagerClient;
import org.umu.https.contextmanager.client.NGSI_ACTION;
import es.um.security.idm.tokens.Token;
import es.um.security.idm.user.IdMUser;
import es.um.security.idm.user.IdMUserException;
import es.um.security.idm.user.implementation.KeyRockIdMUserClient;
import es.um.security.utilities.Protocols;

public class CapabilityClientTest {
	private static final String CONTEXT_MANAGER_ADDRESS_EXTENDED = "https://193.144.201.50:3501/SocIoTal_CM_REST_V3/EXTENDED/";
	private static final String CERTS_FOLDER = "certs_sociotal/";
	private static final String KEYSTORE_FILE = null;
	private static final String CAPABILITY_TOKENS_FOLDER = "capability_tokens/";	
	private static final String [] TRUSTEDCERTS = {"PrivateRootCA.cer", "ca.cer", "UniversidaddeCantabria.cer", "UC.crt"};
	private static final String KEYSTORE_PASSWORD = null;
	private final static String CAPABILITY_MANAGER_ADDRESS = "https://sociotal.inf.um.es:8443/CapabilityManagerServlet/CapabilityManager";
	private final static boolean CERTAUTHENTICATION = false;
	private final static boolean USEDELEGATION = false;

	public static void main(String[] args) throws InterruptedException, UnrecoverableKeyException, NoSuchProviderException {
		String client_id = "jorge";
		String client_password = "jorgepass";
		Token auth_token = null;
		try {
			IdMUser identityManagerUSer = new KeyRockIdMUserClient(Protocols.HTTPS, null, "sociotalkeyrock.inf.um.es", "443");
			auth_token = identityManagerUSer.authenticateById(client_id, client_password);
		} catch (IdMUserException e1) {
			e1.printStackTrace();
		}
		String token_id = auth_token.getToken_id();
		Settings settings = new Settings(KEYSTORE_FILE, CERTS_FOLDER, TRUSTEDCERTS, CAPABILITY_TOKENS_FOLDER, KEYSTORE_PASSWORD, CERTAUTHENTICATION, USEDELEGATION);
		HTTPSCapabilityClient cc = new HTTPSCapabilityClient(settings);
		CapabilityToken ct = cc.ownToken(NGSI_ACTION.QUERY_CONTEXT_BY_ID, "*");
		if (ct == null || !ct.tokenIsValid()){
			System.out.println("Requesting Capability token...");
			ct = cc.requestCapabilityToken(client_id, token_id, NGSI_ACTION.QUERY_CONTEXT_BY_ID, "*", CAPABILITY_MANAGER_ADDRESS); 
			if (ct!=null)
				System.out.println("Capability token received: " + ct.toString());
			else 
				System.out.println("Capability token could not be obtained");
		}
		else{

			String payload = null;
			System.out.println("Trying to get access to the Context Manager...");
			String action = CONTEXT_MANAGER_ADDRESS_EXTENDED + NGSI_ACTION.QUERY_CONTEXT_BY_ID.getValue()+ "/SocIoTal:UNIS:SmartphoneContext:VirtualSmartphoneContext_001";
			settings = new Settings(KEYSTORE_FILE, CERTS_FOLDER, TRUSTEDCERTS, CAPABILITY_TOKENS_FOLDER, KEYSTORE_PASSWORD, CERTAUTHENTICATION, USEDELEGATION);
			HTTPSContextManagerClient httpsCMclient = new HTTPSContextManagerClient(settings);
			String response = httpsCMclient.getAccess(ct, action, payload, client_id, token_id, null);
			System.out.println("RESPONSE: " + response);
		}
	}
}
