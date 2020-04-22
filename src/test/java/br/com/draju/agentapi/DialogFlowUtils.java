package br.com.draju.agentapi;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.UUID;

import com.google.api.client.util.Maps;
import com.google.api.gax.core.FixedCredentialsProvider;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.dialogflow.v2.DetectIntentResponse;
import com.google.cloud.dialogflow.v2.QueryInput;
import com.google.cloud.dialogflow.v2.QueryResult;
import com.google.cloud.dialogflow.v2.SessionName;
import com.google.cloud.dialogflow.v2.SessionsClient;
import com.google.cloud.dialogflow.v2.SessionsSettings;
import com.google.cloud.dialogflow.v2.TextInput;
import com.google.common.collect.Lists;

//TODO: Test two instance scenario, two call with same UUID and check chat is reusable
public class DialogFlowUtils {
	
	private static SessionsClient sessionClient;
	
	//TODO: Refactor to handle differents site ID for tennants
	//TODO: Create a diferent flow for DialogFlow conversation
	//TODO Create unit tests for google CCAI / Dialogflow
	//API: HandleStartConversation, HandleFinish (close resource)
	//Map<String, SessionClient> = put(1L, cientXPTO);
	
	private static String projectId;
	
	//TODO: Testing reusing the same session
//	private static SessionName session;

	/**
	 * Returns the result of detect intent with texts as inputs.
	 *
	 * Using the same `session_id` between requests allows continuation of the
	 * conversation.
	 *
	 * @param projectId    Project/Agent Id.
	 * @param texts        The text intents to be detected based on what a user
	 *                     says.
	 * @param sessionId    Identifier of the DetectIntent session.
	 * @param languageCode Language code of the query.
	 * @return The QueryResult for each input text.
	 */
	public static Map<String, QueryResult> detectIntentTexts(String projectId, List<String> texts, String sessionId,
			String languageCode) throws Exception {
		Map<String, QueryResult> queryResults = Maps.newHashMap();
		// Instantiates a client
		try (SessionsClient sessionsClient = getSession()) {		
			if(sessionsClient != null) {
				// Set the session name using the sessionId (UUID) and projectID (my-project-id)
				SessionName session = SessionName.of(projectId, sessionId);
				System.out.println("Session Path: " + session.toString());
	
				// Detect intents for each text input
				for (String text : texts) {
					QueryResult queryResult = getIntentFromText(languageCode, sessionsClient, session, text);
					queryResults.put(text, queryResult);
				}
			}
		}
		
		return queryResults;
	}
	

	public static void initializeConversation(String projectId, String conversationID) throws Exception {
		// Instantiates a client
		DialogFlowUtils.sessionClient = getSession();		
		if(DialogFlowUtils.sessionClient != null) {
			// Set the session name using the sessionId (UUID) and projectID (my-project-id)
			//TODO: Test reusing session or project ID
//			DialogFlowUtils.session = SessionName.of(projectId, conversationID);
			DialogFlowUtils.projectId = projectId;
		}

	}
	

	public static void finishConversation(String projectId, String sessionId) throws Exception {
		if(DialogFlowUtils.sessionClient != null) {
			sessionClient.close();
		}
	}
	
	public static QueryResult sendNewTextToConversation(String conversationID, String text, String languageCode) {
		//TODO: Testing not reusing the same session
		SessionName session = SessionName.of(projectId, conversationID);
		if(DialogFlowUtils.sessionClient != null) {
			return getIntentFromText(languageCode, sessionClient, session, text);	
		} else {
			System.out.println("ERROR, conversation not started yet!");
		}
		return null;
	}
	
	/**
	 * Extract a intent and a response from text
	 * @param languageCode
	 * @param sessionsClient
	 * @param session
	 * @param text
	 * @return
	 */
	private static QueryResult getIntentFromText(String languageCode, SessionsClient sessionsClient, SessionName session,
			String text) {
		// Set the text (hello) and language code (en-US) for the query
		TextInput.Builder textInput = TextInput.newBuilder().setText(text).setLanguageCode(languageCode);

		// Build the query with the TextInput
		QueryInput queryInput = QueryInput.newBuilder().setText(textInput).build();

		// Performs the detect intent request
		DetectIntentResponse response = sessionsClient.detectIntent(session, queryInput);

		// Display the query result
		QueryResult queryResult = response.getQueryResult();

		System.out.println("====================");
		System.out.format("Query Text: '%s'\n", queryResult.getQueryText());
		System.out.format("Detected Intent: %s (confidence: %f)\n", queryResult.getIntent().getDisplayName(),
				queryResult.getIntentDetectionConfidence());
		System.out.format("Fulfillment Text: '%s'\n", queryResult.getFulfillmentText());
		System.out.format("Is fallback intent: %b\n", queryResult.getIntent().getIsFallback());
		//TODO: Check how figure intent has "end of conversation"
		return queryResult;
	}


	/**
	 * Get Dialogflow session
	 * @return
	 */
	private static SessionsClient getSession() {
		try {
			SessionsSettings.Builder settingsBuilder = SessionsSettings.newBuilder();
			SessionsSettings sessionsSettings = settingsBuilder
					.setCredentialsProvider(FixedCredentialsProvider.create(createGoogleCredentials())).build();
			return SessionsClient.create(sessionsSettings);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * How to is here:
	 * https://dialogflow.com/docs/reference/v2-auth-setup#curl
	 * @return
	 */
	private static GoogleCredentials createGoogleCredentials() {
		GoogleCredentials credentials = null;
		try {
			credentials = GoogleCredentials.fromStream(new FileInputStream("C:\\Users\\vhrodriguesv\\Downloads\\draju-gccai.json"))
					.createScoped(Lists.newArrayList("https://www.googleapis.com/auth/cloud-platform"));

		} catch (Exception e) {
			e.printStackTrace();
		}
		return credentials;
	}
	
	/**
	 * agent-name: draju-v1 console:
	 * https://dialogflow.cloud.google.com/#/agent/drajuproject/intents Notes:
	 * https://cloud.google.com/dialogflow/docs/quick/api
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
//		startSimpleIntentDetection();
		startInteractiveChat();
	}
	
	private static void startSimpleIntentDetection() {
		ArrayList<String> texts = new ArrayList<>();
		String projectID = "drajuproject";
		String sessionID = UUID.randomUUID().toString();
		String languageCode = "pt-BR";
		
		try (Scanner scanIn = new Scanner(System.in)) {
			String text = null;
			System.out.println("Send text here or type exit to finish");
			// read text from console
			while (text == null || !text.equals("exit")) {
				text = scanIn.nextLine();
				if (text != null && !text.isEmpty() && !text.equals("exit")) {
					texts.add(text);
				}
			}
			Map<String, QueryResult> results = detectIntentTexts(projectID, texts, sessionID, languageCode);
			System.out.println(results.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static void startInteractiveChat() {
		String projectID = "drajuproject";
		String conversationID = UUID.randomUUID().toString();
		String languageCode = "pt-BR";
		
		try (Scanner scanIn = new Scanner(System.in)) {
			initializeConversation(projectID, conversationID);
			String text = null;
			System.out.println("Send text here or type exit to finish");
			// read text from console
			while (text == null || !text.equals("exit")) {
				text = scanIn.nextLine();
				if (text != null && !text.isEmpty() && !text.equals("exit")) {
					QueryResult queryResult = sendNewTextToConversation(conversationID, text, languageCode);
					System.out.println("All parameters required present: " + queryResult.getAllRequiredParamsPresent());
					if(queryResult.getFulfillmentText() != null && !queryResult.getFulfillmentText().isEmpty())
						System.out.println(queryResult.getFulfillmentText());
				}
			}
			finishConversation(projectID, conversationID);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
