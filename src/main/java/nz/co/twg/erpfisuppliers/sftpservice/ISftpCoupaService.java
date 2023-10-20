package nz.co.twg.erpfisuppliers.sftpservice;
import java.io.ByteArrayOutputStream;

import nz.co.twg.erpfisuppliers.common.handler.SupplierRetryException;

public interface ISftpCoupaService {
    public boolean uploadCSVFile(ByteArrayOutputStream stream) throws Exception;
    public String uploadCSVFileRetry(ByteArrayOutputStream stream) throws SupplierRetryException;
}
