package nz.co.twg.erpfisuppliers.sftpservice.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvBindByPosition;
import lombok.*;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class SupplierCoupaDto {


    @JsonProperty("name")
    @CsvBindByPosition(position = 0)
    @CsvBindByName(column = "Name", required = true)
    private String name;

    @JsonProperty("displayName")
    @CsvBindByPosition(position = 1)
    @CsvBindByName(column = "Display Name", required = true)
    private String displayName;

    @JsonProperty("status")
    @CsvBindByPosition(position = 2)
    @CsvBindByName(column = "Status", required = true)
    private String status;

    @JsonProperty("supplierType")
    @CsvBindByPosition(position = 3)
    @CsvBindByName(column = "Supplier Type", required = true)
    private String supplierType;

    @JsonProperty("oneTimeSupplier")
    @CsvBindByPosition(position = 4)
    @CsvBindByName(column = "One Time Supplier", required = true)
    private String oneTimeSupplier;

    @JsonProperty("supplierNumber")
    @CsvBindByPosition(position = 5)
    @CsvBindByName(column = "Supplier Number", required = true)
    private String supplierNumber;

    //Lines added by Shauzab

    @JsonProperty("primaryContactEmail")
    //@CsvBindByPosition(position = 4)
    //@CsvBindByName(column = "Primary Contact Email", required = true)
    private String primaryContactEmail;

    @JsonProperty("primaryContactMobile")
    //@CsvBindByPosition(position = 5)
    //@CsvBindByName(column = "Primary Contact Phone Work", required = true)
    private String primaryContactMobile;

    @JsonProperty("primaryAddressStreet1")
    @CsvBindByPosition(position = 6)
    @CsvBindByName(column = "Primary Address Street1", required = true)
    private String primaryAddressStreet1;

    @JsonProperty("primaryAddressStreet2")
    @CsvBindByPosition(position = 7)
    @CsvBindByName(column = "Primary Address Street2", required = true)
    private String primaryAddressStreet2;

    @JsonProperty("primaryAddressStreet3")
    @CsvBindByPosition(position = 8)
    @CsvBindByName(column = "Primary Address Street 3", required = true)
    private String primaryAddressStreet3;



    @JsonProperty("primaryAddressCity")
    @CsvBindByPosition(position = 9)
    @CsvBindByName(column = "Primary Address City", required = true)
    private String primaryAddressCity;

    @JsonProperty("primaryAddressState")
    @CsvBindByPosition(position = 10)
    @CsvBindByName(column = "Primary Address State", required = true)
    private String primaryAddressState;

    @JsonProperty("primaryAddressPostalCode")
    @CsvBindByPosition(position = 11)
    @CsvBindByName(column = "Primary Address Postal Code", required = true)
    private String primaryAddressPostalCode;

    @JsonProperty("primaryAddressCountryCode")
    @CsvBindByPosition(position = 12)
    @CsvBindByName(column = "Primary Address Country Code", required = true)
    private String primaryAddressCountryCode;

    @JsonProperty("poEmail")
    @CsvBindByPosition(position = 13)
    @CsvBindByName(column = "PO Email", required = true)
    private String poEmail;

    @JsonProperty("poMethod")
    @CsvBindByPosition(position = 14)
    @CsvBindByName(column = "PO Method", required = true)
    private String poMethod;

    @JsonProperty("poChangeMethod")
    @CsvBindByPosition(position = 15)
    @CsvBindByName(column = "PO Change Method", required = true)
    private String poChangeMethod;

    @JsonProperty("paymentTerms")
    @CsvBindByPosition(position = 16)
    @CsvBindByName(column = "Payment Terms", required = true)
    private String paymentTerms;

    /*
     * @JsonProperty("nlgSupplierCode")
     *
     * @CsvBindByPosition(position = 14)
     *
     * @CsvBindByName(column = "NLG Supplier Code", required = true) private String
     * nlgSupplierCode;
     */
    @JsonProperty("invoiceCurrency")
    @CsvBindByPosition(position = 17)
    @CsvBindByName(column = "Invoice Currency", required = true)
    private String invoiceCurrency;
    @JsonProperty("1daySupplierCode")
    @CsvBindByPosition(position = 18)
    @CsvBindByName(column = "1Day Supplier Code", required = true)
    private String _1daySupplierCode;

    @JsonProperty("t7SupplierCode")
    @CsvBindByPosition(position = 19)
    @CsvBindByName(column = "T7 Supplier Code", required = true)
    private String t7SupplierCode;

    @JsonProperty("invoiceMatchingLevel")
    @CsvBindByPosition(position = 20)
    @CsvBindByName(column = "Invoice Matching Level", required = true)
    private String invoiceMatchingLevel;

    @JsonProperty("oracleSiteCode")
    //@CsvBindByPosition(position = 20)
    //@CsvBindByName(column = "Oracle Site Code", required = true)
    private String oracleSiteCode;

    @JsonProperty("multiplePaySite")
    //@CsvBindByPosition(position = 21)
    //@CsvBindByName(column = "Multiple Pay Site", required = true)
    private String multiplePaySite;

    @JsonProperty("taxCode")
    @CsvBindByPosition(position = 21)
    @CsvBindByName(column = "GST Number", required = true)
    private String taxCode;

    @JsonProperty("tmlSiteCode")
    @CsvBindByPosition(position = 22)
    @CsvBindByName(column = "TML Site Code", required = true)
    private String tmlSiteCode;

    @JsonProperty("tp7SiteCode")
    @CsvBindByPosition(position = 23)
    @CsvBindByName(column = "TP7 Site Code", required = true)
    private String tp7SiteCode;


    @JsonProperty("coupaSupplierId")
    @CsvBindByPosition(position = 24)
    @CsvBindByName(column = "ID", required = true)
    private String id;

    @JsonProperty("twlSiteCode")
    @CsvBindByPosition(position = 25)
    @CsvBindByName(column = "TWL Site Code", required = true)
    private String twlSiteCode;

    @JsonProperty("nlgSiteCode")
    @CsvBindByPosition(position = 26)
    @CsvBindByName(column = "NLG Site Code", required = true)
    private String nlgSiteCode;

    @JsonProperty("eldSiteCode")
    @CsvBindByPosition(position = 27)
    @CsvBindByName(column = "ELD Site Code", required = true)
    private String eldSiteCode;

    @JsonProperty("coupaSupplierId")
    //@CsvBindByPosition(position = 27)
    //@CsvBindByName(column = "Coupa Supplier Id", required = true)
    private String coupaSupplierId;

    @JsonProperty("marketSupplierCode")
    @CsvBindByPosition(position = 28)
    @CsvBindByName(column = "TMC Supplier Code", required = true)
    private String marketSupplierCode;

    @JsonProperty("nzbn")
    @CsvBindByPosition(position = 29)
    @CsvBindByName(column = "NZBN", required = true)
    private String nzbn;

    @JsonProperty("website")
    @CsvBindByPosition(position = 30)
    @CsvBindByName(column = "Website", required = true)
    private String website;


}
