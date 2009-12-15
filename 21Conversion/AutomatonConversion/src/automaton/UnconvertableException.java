package automaton;

/**
 * Create by: huangcd
 * Date: 2009-12-7
 * Time: 19:24:44
 */
public class UnconvertableException extends Exception {
    public UnconvertableException() {
    }

    public UnconvertableException(String message) {
        super(message);
    }

    public UnconvertableException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnconvertableException(Throwable cause) {
        super(cause);
    }
}