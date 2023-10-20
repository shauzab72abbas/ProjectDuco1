 package nz.co.twg.erpfisuppliers.common.handler;

 import lombok.Getter;

/**
 * @author Satya G
 * @since 08-10-2021
 * Description:
 * This class represents a base custom exception which handles all types of Exception
 * occurred at different layers of the application (will be used to all unhandled exceptions
 generated at runtime).
 *
 **/

 @Getter
 public class CoupaSupplierException extends RuntimeException
 {
    private String message;
    private String debugMessage;

    public CoupaSupplierException(String message) {
        super(message);
        this.message = message;
        this.debugMessage = message;
    }

    public CoupaSupplierException(String message, RuntimeException exception) {
        super(message);
        this.message = message;
        this.debugMessage = exception.getMessage();
    }

    @Override
    public String toString() {
        return debugMessage;
    }

 }
