import org.umu.capability.CapabilityToken;
import org.umu.capability.evaluator.CapabilityEvaluator;
import org.umu.capability.evaluator.CapabilityVerifierCode;

public class CapabilityEvaluatorTest {

	public static void main(String[] args) {
		/* Path where certificates are */
		String certsFolder = "certs_sociotal/";
		/* Name of the KeyRock's certificate */
		String certKeyRock = "cert.cer";
		/* Password to access the KeyRock instance */
		String keyrockpassword = "uhd4378ry34d3";
		/* KeyRock's IP address */
		String keyRockIP = "https://192.168.1.128";
		/* KeyRock's port */
		String keyRockPort = "8443";
		
		CapabilityEvaluator cev = new CapabilityEvaluator(certsFolder, certKeyRock, keyrockpassword, keyRockIP, keyRockPort);
		
		String actionNGSI = "queryContext";
		String requestPayload = null;
		/* Replace with obtained token from request */
		CapabilityToken ct = null;
		/* Replace with obtained signature from request (if present, null otherwise) */
		String signature = null;
		/* Replace with obtained auth_TokenIDtoken from request (if present, null otherwise) */
		String auth_tokenID = null;
		/* Replace with obtained client_ID from request (if present, null otherwise) */
		String clientID = null;
		CapabilityVerifierCode cevc = cev.evaluateCapabilityToken(ct.toString(), signature, actionNGSI, requestPayload, auth_tokenID, clientID);
		System.out.println(cevc);
		
	}

}
