package br.com.draju.templateapi.service;

/**
 * Error generation the action file
 * @author victor
 *
 */
public class ActionGenerationException extends Exception {
	public ActionGenerationException(String message, Exception ex) {
		super(message, ex);
	}
	
	public ActionGenerationException(String message) {
		super(message);
	}
}
