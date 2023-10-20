package nz.co.twg.erpfisuppliers.kafkaservice.impl;

import java.util.List;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import nz.co.twg.erpfisuppliers.common.handler.SupplierException;
import nz.co.twg.erpfisuppliers.common.pojo.Supplier;
import nz.co.twg.erpfisuppliers.common.pojo.SuppliersEntity;
import nz.co.twg.erpfisuppliers.kafkaservice.ValidationUtil;
import nz.co.twg.erpfisuppliers.kafkaservice.mapper.CoupaSuppliersMapper;
import nz.co.twg.erpfisuppliers.sftpservice.ISupplierCoupaService;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Setter
@Slf4j
public class KafkaConsumerDataReaderImpl implements IKafkaConsumerDataReader {

    @Autowired
    private  ISupplierCoupaService supplierCoupaService;



    @Autowired private CoupaSuppliersMapper coupaSuppliersMapper;

    @Autowired private ValidationUtil validationUtil;

    @Override
    public void dataReader(String data) {
        log.info("******Data Reading started*******" + data);
        try {
            JSONObject jsonObject = new JSONObject(data.toString());
            log.info("json object******" + jsonObject);
            Boolean isValidated = validationUtil.validateJson(jsonObject, "/sdem/suppliers.json");
            if (isValidated) {
                SuppliersEntity suppliersEntity = new SuppliersEntity();
                suppliersEntity = (SuppliersEntity) coupaSuppliersMapper.convertJsonToPojoDetails(jsonObject.toString(), suppliersEntity);
                log.info("readValue = " + suppliersEntity);
                if (suppliersEntity != null && suppliersEntity.getSuppliersData().getDataArea().getSuppliers() != null) {
                    List<Supplier> coupaSupplier = suppliersEntity.getSuppliersData().getDataArea().getSuppliers();
                    /* Call service to generate CSV and push on Coupa SFTP server */
                    log.info("Sending coupaSupplier details to generate csv: KafkaConsumerDataReaderImpl");
                    log.info("suppliers Details>>>>>>>>" + coupaSupplier);
                    supplierCoupaService.createCSV(coupaSupplier);
                    log.info("CSV generation completed: KafkaConsumerDataReaderImpl");
                }
            }
        } catch (Exception ex) {
            log.error("payload getting failed at jsonobject creation" +data);
            log.error("Invalid schema in KafkaConsumerDataReaderImpl:dataReader", ex);
            throw new SupplierException(ex.getMessage());

        }
    }
}
