package nz.co.twg.erpfisuppliers.common.handler;

import lombok.Getter;

/**
 * @author philip.samuel
 * @since 02-12-2021
 * Description:
 * This class represents a base custom exception which handles retry Exception
 * occurred at micro service layers of the application (will be used to all SFTP File transfer exceptions generated at runtime).
 *
 */

@Getter
public class SupplierRetryException extends RuntimeException
{
    private String message;
    private String debugMessage;

    public SupplierRetryException(String message) {
        super(message);
        this.message = message;
        this.debugMessage = message;
    }

    public SupplierRetryException(String message, RuntimeException exception) {
        super(message);
        this.message = message;
        this.debugMessage = exception.getMessage();
    }

    @Override
    public String toString() {
        return debugMessage;
    }

}
