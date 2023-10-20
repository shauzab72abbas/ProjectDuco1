package nz.co.twg.erpfisuppliers.kafkaservice.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class DataArea {
    private String process;
    private List<Supplier> suppliers = new ArrayList<Supplier>();
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();
}
