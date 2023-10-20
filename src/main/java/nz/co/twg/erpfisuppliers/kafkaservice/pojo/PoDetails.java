package nz.co.twg.erpfisuppliers.kafkaservice.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.HashMap;
import java.util.Map;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PoDetails {
    private String poEmail;
    private String poMethod;
    private String poChangeMethod;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();
}
