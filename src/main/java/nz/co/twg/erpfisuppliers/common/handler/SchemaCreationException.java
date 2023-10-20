package nz.co.twg.erpfisuppliers.common.handler;

import lombok.Getter;

/**
 * @author rashim.bala
 * @since 08-10-2021
 * Description :
 * This custom exception is thrown when system is unable to create schema for an organization.
 * It extends Custom Generic Exception - PiqnicException.
 */

@Getter
public class SchemaCreationException extends SupplierException
{
    public SchemaCreationException(String message) {
        super(message);
    }
}
