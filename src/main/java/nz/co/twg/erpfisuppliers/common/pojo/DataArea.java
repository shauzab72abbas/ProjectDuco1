
package nz.co.twg.erpfisuppliers.common.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class DataArea {

    /**
     * (Required)
     */
    @JsonProperty("process")
    private String process;
    /**
     * (Required)
     */
    @JsonProperty("suppliers")
    private List<Supplier> suppliers = new ArrayList<Supplier>();
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();


}
