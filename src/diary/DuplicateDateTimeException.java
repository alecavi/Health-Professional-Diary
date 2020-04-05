package diary;

/**
 * Thrown to indicate that a diary has been passed an appointment datetime that already exists
 * @author Alessandro Cavicchioli
 * @version 1.0
 */
public class DuplicateDateTimeException extends IllegalArgumentException
{
	private static final long serialVersionUID = 1L;

	/**
     * Constructs a <code>DuplicateDateTimeException</code> with no
     * detail message.
     */
    public DuplicateDateTimeException() {
        super();
    }

    /**
     * Constructs a <code>DuplicateDateTimeException</code> with the
     * specified detail message.
     *
     * @param   s   the detail message.
     */
    public DuplicateDateTimeException(String s) {
        super(s);
    }

    /**
     * Constructs a new exception with the specified detail message and
     * cause.
     *
     * <p>Note that the detail message associated with <code>cause</code> is
     * <i>not</i> automatically incorporated in this exception's detail
     * message.
     *
     * @param  message the detail message (which is saved for later retrieval
     *         by the {@link Throwable#getMessage()} method).
     * @param  cause the cause (which is saved for later retrieval by the
     *         {@link Throwable#getCause()} method).  (A {@code null} value
     *         is permitted, and indicates that the cause is nonexistent or
     *         unknown.)
     * @since 1.5
     */
    public DuplicateDateTimeException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a new exception with the specified cause and a detail
     * message of {@code (cause==null ? null : cause.toString())} (which
     * typically contains the class and detail message of {@code cause}).
     * This constructor is useful for exceptions that are little more than
     * wrappers for other throwables (for example, {@link
     * java.security.PrivilegedActionException}).
     *
     * @param  cause the cause (which is saved for later retrieval by the
     *         {@link Throwable#getCause()} method).  (A {@code null} value is
     *         permitted, and indicates that the cause is nonexistent or
     *         unknown.)
     * @since  1.5
     */
    public DuplicateDateTimeException(Throwable cause) {
        super(cause);
    }

}
