package  com.interaudit.domain.dao.exc;


/**
 * Cette exception est lancée lorsque la validation d'une donnée ne peut se faire
 */
public class ValidationException extends RuntimeException {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// ======================================
    // =            Constructeurs           =
    // ======================================

    public ValidationException(String message) {
        super(message);
    }
}