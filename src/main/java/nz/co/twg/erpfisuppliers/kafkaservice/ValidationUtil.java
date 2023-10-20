package nz.co.twg.erpfisuppliers.kafkaservice;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.networknt.schema.JsonSchema;
import com.networknt.schema.JsonSchemaFactory;
import com.networknt.schema.SpecVersion;
import com.networknt.schema.ValidationMessage;
import java.io.InputStream;
import java.util.Set;

import lombok.extern.slf4j.Slf4j;
import nz.co.twg.erpfisuppliers.common.handler.SupplierException;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ValidationUtil {

    @Value("${poEmailValidationRequired}")
    private String poEmailValidationRequired;

    /* JSON and Schema Validation */
    public boolean validateJson(JSONObject jsonObject, String schemaPath) {
        log.info("schemaPath : " + schemaPath);
        log.info("schemaPath : " + jsonObject.toString());
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            InputStream inputStream = ValidationUtil.class.getResourceAsStream(schemaPath);
            JsonSchemaFactory jsonSchemaFactory = JsonSchemaFactory.getInstance(SpecVersion.VersionFlag.V4);

            JsonNode json = objectMapper.readTree(jsonObject.toString());
            log.info("inputStream : " + inputStream);
            JsonSchema schema = jsonSchemaFactory.getSchema(inputStream);
            Set<ValidationMessage> validateResult = schema.validate(json);
            
            if("false".equals(poEmailValidationRequired)) {       
                validateResult.removeIf(item -> item.getMessage().contains("pattern email"));
            }

            if (validateResult.isEmpty()) {
                log.info("Validation Success");
                return true;
            } else {
                log.error("payload which is getting failed at validation level" + jsonObject.toString());
                log.error("Validation failed " + validateResult);
                throw new SupplierException(
                        "Error while validating the Json in ValidationUtil class validateJson() ");
            }
        } catch (Exception ex) {
            log.error("payload which is getting failed at validation level Exception" + jsonObject.toString());
            log.error("Validation failed ex ", ex);
        }
        return false;
    }
}
