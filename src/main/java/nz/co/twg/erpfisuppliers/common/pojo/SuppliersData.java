
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
public class SuppliersData {

    /**
     * (Required)
     */
    @JsonProperty("releaseIdentifier")
    private String releaseIdentifier;
    /**
     * (Required)
     */
    @JsonProperty("applicationArea")
    private ApplicationArea applicationArea;
    /**
     * (Required)
     */
    @JsonProperty("dataArea")
    private DataArea dataArea;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();


}
