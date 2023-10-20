package nz.co.twg.erpfisuppliers.sftpservice;
import java.util.List;
import nz.co.twg.erpfisuppliers.common.pojo.Supplier;

public interface ISupplierCoupaService {
    public boolean createCSV(List<Supplier> projectCodes);
}
