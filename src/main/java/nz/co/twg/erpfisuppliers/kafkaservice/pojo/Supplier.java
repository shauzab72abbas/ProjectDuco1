package nz.co.twg.erpfisuppliers.kafkaservice.pojo;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.*;

import java.util.HashMap;
import java.util.Map;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Supplier {
    private String id;
    private String supplierNumber;
    private String name;
    private String displayName;
    private String status;

    //lines added by Shauzab
    private String supplierType;

    private String oneTimeSupplier;

    private PrimaryContact primaryContact;
    private PrimaryAddress primaryAddress;
    private PoDetails poDetails;
    private String paymentTerms;

    private String invoiceCurrency;
    private String nlgSupplierCode;
    private String _1daySupplierCode;
    private String invoiceMatchingLevel;
    private String oracleSiteCode;
    private String multiplePaySite;
    private String taxCode;

    private String tmlSiteCode;

    private String tp7siteCode;
    private String coupaSupplierId;
    private String marketSupplierCode;

    private String nzbn;

    private String website;
    @JsonIgnore private Map<String, Object> additionalProperties = new HashMap<String, Object>();


}
