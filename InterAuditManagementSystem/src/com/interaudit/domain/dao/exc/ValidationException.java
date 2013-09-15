package  com.interaudit.domain.dao.exc;


/**
 * Cette exception est lanc�e lorsque la validation d'une donn�e ne peut se faire
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