
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
public class PrimaryContact {

    /**
     * Email Address
     * <p>
     *
     * (Required)
     *
     */
    @JsonProperty("email")
    private String email;
    /**
     *
     * (Required)
     *
     */
    @JsonProperty("mobile")
    private String mobile;

    //lines added by Shauzab
    @JsonProperty("phone")
    private String phone;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();



}
