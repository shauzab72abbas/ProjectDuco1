 package nz.co.twg.erpfisuppliers.common.handler;

 import lombok.Getter;

/**
 * @author Satya G
 * @since 08-10-2021
 * Description :
 * This custom exception is thrown when system is unable to create schema for an organization.
 * It extends Custom Generic Exception - PiqnicException.
 */

 @Getter
 public class SchemaValidationException extends CoupaSupplierException
 {
    public SchemaValidationException(String message) {
        super(message);
    }
 }
