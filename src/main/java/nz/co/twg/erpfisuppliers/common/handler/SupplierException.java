package nz.co.twg.erpfisuppliers.common.handler;

import lombok.Getter;

/**
 * @author rashim.bala
 * @since 08-10-2021
 * Description:
 * This class represents a base custom exception which handles all types of Exception
 * occurred at different layers of the application (will be used to all unhandled exceptions generated at runtime).
 *
 */

@Getter
public class SupplierException extends RuntimeException
{
    private String message;
    private String debugMessage;

    public SupplierException(String message) {
        super(message);
        this.message = message;
        this.debugMessage = message;
    }

    public SupplierException(String message, RuntimeException exception) {
        super(message);
        this.message = message;
        this.debugMessage = exception.getMessage();
    }

    @Override
    public String toString() {
        return debugMessage;
    }

}
