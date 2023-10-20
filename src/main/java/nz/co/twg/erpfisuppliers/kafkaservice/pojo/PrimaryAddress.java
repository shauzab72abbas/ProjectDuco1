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
public class PrimaryAddress {
    private String street1;
    @JsonProperty("street2")
    private String street2;

    private String street3;
    @JsonProperty("city")
    private String city;
    @JsonProperty("state")
    private String state;
    @JsonProperty("postalCode")
    private String postalCode;
    @JsonProperty("countryCode")
    private String countryCode;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

}
