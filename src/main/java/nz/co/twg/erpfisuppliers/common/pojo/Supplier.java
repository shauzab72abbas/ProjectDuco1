
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
public class Supplier {

    @JsonProperty("id")
    private String id;

    @JsonProperty("supplierNumber")
    private String supplierNumber;

    @JsonProperty("name")
    private String name;

    @JsonProperty("displayName")
    private String displayName;

    @JsonProperty("status")
    private String status;

    //lines added by Shauzab .
    @JsonProperty("supplierType")
    private String supplierType;


    //lines added by Shauzab .
    @JsonProperty("oneTimeSupplier")
    private String oneTimeSupplier;

    @JsonProperty("primaryContact")
    private PrimaryContact primaryContact;
    @JsonProperty("primaryAddress")
    private PrimaryAddress primaryAddress;

    @JsonProperty("poDetails")
    private PoDetails poDetails;

    @JsonProperty("paymentTerms")
    private String paymentTerms;

    @JsonProperty("invoiceCurrency")
    private String invoiceCurrency;


    //@JsonProperty("nlgSupplierCode")
    //private String nlgSupplierCode;

    @JsonProperty("t7SupplierCode")
    private String t7SupplierCode;

    @JsonProperty("1DaySupplierCode")
    private String _1daySupplierCode;

    @JsonProperty("invoiceMatchingLevel")
    private String invoiceMatchingLevel;

    @JsonProperty("oracleSiteCode")
    private String oracleSiteCode;

    @JsonProperty("multiplePaySite")
    private String multiplePaySite;

    @JsonProperty("taxCode")
    private String taxCode;

    @JsonProperty("twlSiteCode")
    private String twlSiteCode;

    @JsonProperty("nlgSiteCode")
    private String nlgSiteCode;

    @JsonProperty("eldSiteCode")
    private String eldSiteCode;

    @JsonProperty("tmlSiteCode")
    private String tmlSiteCode;

    @JsonProperty("tp7SiteCode")
    private String tp7SiteCode;


    @JsonProperty("coupaSupplierId")
    private String coupaSupplierId;

    @JsonProperty("marketSupplierCode")
    private String marketSupplierCode;

    @JsonProperty("nzbn")
    private String nzbn;

    @JsonProperty("website")
    private String website;

    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

}
