package nz.co.twg.erpfisuppliers.kafkaservice.mapper;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import nz.co.twg.erpfisuppliers.common.handler.SupplierException;
import nz.co.twg.erpfisuppliers.common.pojo.Supplier;
import nz.co.twg.erpfisuppliers.common.pojo.SuppliersEntity;

import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CoupaSuppliersMapper {


    /* Json to pojo conversion*/
    public Object convertJsonToPojoDetails(String jsonObject, Object objectType) {
        ObjectMapper objectMapper = new ObjectMapper();
        List<Supplier> supplier = null;
        try {
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            objectType = objectMapper.readValue(jsonObject, objectType.getClass());
        } catch (IOException ex) {
            log.error("payload which is getting failed at pojo generation"+jsonObject);
            log.error("failed to create pojo", ex);
            throw new SupplierException("Failed to create pojo with validated json in CoupaSupplierMapper class convertToCoupaSupplier() " + ex);
        }
        return objectType;
    }
}
