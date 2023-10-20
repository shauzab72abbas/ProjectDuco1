
package nz.co.twg.erpfisuppliers.common.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
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
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class PrimaryAddress {


    @JsonProperty("street1")
    private String street1;
    /**
     * (Required)
     */
    @JsonProperty("street2")
    private String street2;

    //lines added by Shauzab .
    @JsonProperty("street3")
    private String street3;
    /**
     * (Required)
     */
    @JsonProperty("city")
    private String city;
    /**
     * (Required)
     */
    @JsonProperty("state")
    private String state;
    /**
     * (Required)
     */
    @JsonProperty("postalCode")
    private String postalCode;
    /**
     * (Required)
     */
    @JsonProperty("countryCode")
    private String countryCode;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();


}
