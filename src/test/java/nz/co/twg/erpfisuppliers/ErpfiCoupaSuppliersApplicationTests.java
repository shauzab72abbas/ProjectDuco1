package nz.co.twg.erpfisuppliers;

import nz.co.twg.erpfisuppliers.common.pojo.Supplier;
import nz.co.twg.erpfisuppliers.common.pojo.SuppliersEntity;
import nz.co.twg.erpfisuppliers.common.util.SupplierMappingStrategy;
import nz.co.twg.erpfisuppliers.kafkaservice.ValidationUtil;
import nz.co.twg.erpfisuppliers.kafkaservice.mapper.CoupaSuppliersMapper;
import nz.co.twg.erpfisuppliers.sftpservice.impl.SftpCoupaServiceImpl;
import nz.co.twg.erpfisuppliers.sftpservice.impl.SupplierCoupaServiceImpl;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.ArrayList;
import java.util.List;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;


@RunWith(SpringRunner.class)
@SpringBootTest
public class ErpfiCoupaSuppliersApplicationTests {

	private final Logger log = LoggerFactory.getLogger(ErpfiCoupaSuppliersApplicationTests.class);

    @Autowired
    private SupplierCoupaServiceImpl supplierCoupaService;

    @MockBean
    private SftpCoupaServiceImpl sftpCoupaService;

	@Test
	public void contextLoads() {
	}

	@MockBean
    ValidationUtil validationUtil;


	@MockBean
    CoupaSuppliersMapper jsonToPojoMapper;

	List<Supplier> supplierList = new ArrayList<>();


	String data;
	String data2;
	String data3;
	String wrongData;
	public String sftpHost = null;
	public String userName = null;
	public Integer sftpPort = null;
	public String remoteDir = null;
	public String password = null;

	@Before
	public void setUp() throws JSONException {

        data = "{\"suppliers\":{\"applicationArea\":{\"creationDateTime\":\"2022-01-25T03:49:01\"},\"dataArea\":{\"process\":\"ProcessCoupaSuppliers\",\"suppliers\":[{\"oracleSiteCode\":\"ELDNTREG\",\"multiplePaySite\":\"Y\",\"displayName\":\"ARNSuppliers\",\"supplierNumber\":\"15645\",\"poDetails\":{\"poMethod\":\"prompt\",\"poChangeMethod\":\"prompt\"},\"nlgSiteCode\":\"NLGNTREG\",\"taxCode\":\"753421869\",\"nlgSupplierCode\":\"121407\",\"primaryContact\":{\"email\":null,\"mobile\":\"9876540\"},\"name\":\"BlueBucketCleaningLimited\",\"invoiceMatchingLevel\":\"3-way\",\"id\":\"300000003097384\",\"primaryAddress\":{\"city\":\"Masterton\",\"countryCode\":\"NZ\",\"postalCode\":\"5810\",\"street1\":\"3RogersLane\"},\"coupaSupplierId\":\"8362\",\"status\":\"Active\"},{\"oracleSiteCode\":\"TWL55\",\"multiplePaySite\":\"\",\"displayName\":null,\"supplierNumber\":\"18944\",\"poDetails\":{\"poEmail\":null,\"poMethod\":\"prompt\",\"poChangeMethod\":\"prompt\"},\"twlSiteCode\":\"TWLNTREG\",\"nlgSiteCode\":null,\"eldSiteCode\":null,\"taxCode\":null,\"primaryContact\":{\"mobile\":\"9876540\"},\"name\":\"ABCEquipmentLtd\",\"invoiceMatchingLevel\":\"3-way\",\"id\":\"300000006979353\",\"primaryAddress\":{\"city\":\"Auckland\",\"countryCode\":\"NZ\",\"postalCode\":\"1740\",\"street1\":\"POBox25294\",\"street2\":null},\"paymentTerms\":\"20thMonth1%\",\"status\":\"Active\"}]}}}";

       data2 = "{\"suppliers\":{\"applicationArea\":{\"creationDateTime\":\"2022-03-01T04:09:35\"},\"dataArea\":{\"process\":\"ProcessCoupaSuppliers\",\"suppliers\":[{\"id\":\"300000003547124\",\"supplierNumber\":\"115095\",\"name\":\"GreenCabsLimited\",\"displayName\":null,\"status\":\"Active\",\"primaryContact\":{\"email\":\"somnath.g.ghosh@oracle.com\",\"mobile\":null},\"primaryAddress\":{\"street1\":\"POBox6061\",\"street2\":\"MarionSquare\",\"city\":\"Wellington\",\"state\":null,\"postalCode\":\"6141\",\"countryCode\":\"NZ\"},\"poDetails\":{\"poEmail\":null,\"poMethod\":\"prompt\",\"poChangeMethod\":\"prompt\"},\"paymentTerms\":\"30thMonth\",\"nlgSupplierCode\":\"108930\",\"t7SupplierCode\":null,\"1DaySupplierCode\":null,\"invoiceMatchingLevel\":\"3-way\",\"multiplePaySite\":\"Y\",\"taxCode\":\"096-065-590\",\"twlSiteCode\":null,\"nlgSiteCode\":null,\"eldSiteCode\":null,\"coupaSupplierId\":\"1353\",\"marketSupplierCode\":null},{\"id\":\"300000003547124\",\"supplierNumber\":\"115095\",\"name\":\"GreenCabsLimited\",\"displayName\":null,\"status\":\"Active\",\"primaryContact\":{\"email\":\"somnath.g.ghosh@oracle.com\",\"mobile\":null},\"primaryAddress\":{\"street1\":\"POBox6061\",\"street2\":\"MarionSquare\",\"city\":\"Wellington\",\"state\":null,\"postalCode\":\"6141\",\"countryCode\":\"NZ\"},\"poDetails\":{\"poEmail\":\"Accounts@greencabs.co.nz\",\"poMethod\":\"email\",\"poChangeMethod\":\"email\"},\"paymentTerms\":\"20thMonth\",\"nlgSupplierCode\":\"108930\",\"t7SupplierCode\":null,\"1DaySupplierCode\":null,\"invoiceMatchingLevel\":\"3-way\",\"multiplePaySite\":\"Y\",\"taxCode\":\"096-065-590\",\"twlSiteCode\":\"TWLNTREG\",\"nlgSiteCode\":null,\"eldSiteCode\":null,\"coupaSupplierId\":\"1353\",\"marketSupplierCode\":null},{\"id\":\"300000003547124\",\"supplierNumber\":\"115095\",\"name\":\"GreenCabsLimited\",\"displayName\":null,\"status\":\"Active\",\"primaryContact\":{\"email\":\"Remit@wherever.net\",\"mobile\":null},\"primaryAddress\":{\"street1\":\"Level999\",\"street2\":\"SkyscraperBuilding\",\"city\":\"Wellington\",\"state\":null,\"postalCode\":\"1010\",\"countryCode\":\"NZ\"},\"poDetails\":{\"poEmail\":null,\"poMethod\":\"prompt\",\"poChangeMethod\":\"prompt\"},\"paymentTerms\":\"20thMonth\",\"nlgSupplierCode\":\"108930\",\"t7SupplierCode\":null,\"1DaySupplierCode\":null,\"invoiceMatchingLevel\":\"3-way\",\"multiplePaySite\":\"Y\",\"taxCode\":\"096-065-590\",\"twlSiteCode\":\"TRADE20\",\"nlgSiteCode\":null,\"eldSiteCode\":null,\"coupaSupplierId\":\"1353\",\"marketSupplierCode\":null},{\"id\":\"300000003547124\",\"supplierNumber\":\"115095\",\"name\":\"GreenCabsLimited\",\"displayName\":null,\"status\":\"Active\",\"primaryContact\":{\"email\":\"somnath.g.ghosh@oracle.com\",\"mobile\":null},\"primaryAddress\":{\"street1\":\"POBox6061\",\"street2\":\"MarionSquare\",\"city\":\"Wellington\",\"state\":null,\"postalCode\":\"6141\",\"countryCode\":\"NZ\"},\"poDetails\":{\"poEmail\":\"AccountsStuff@greencabs.co.nz\",\"poMethod\":\"email\",\"poChangeMethod\":\"email\"},\"paymentTerms\":null,\"nlgSupplierCode\":\"108930\",\"t7SupplierCode\":null,\"1DaySupplierCode\":null,\"invoiceMatchingLevel\":\"3-way\",\"multiplePaySite\":\"Y\",\"taxCode\":\"096-065-590\",\"twlSiteCode\":null,\"nlgSiteCode\":null,\"eldSiteCode\":\"ELDNTREG\",\"coupaSupplierId\":\"1353\",\"marketSupplierCode\":null},{\"id\":\"300000003547124\",\"supplierNumber\":\"115095\",\"name\":\"GreenCabsLimited\",\"displayName\":null,\"status\":\"Active\",\"primaryContact\":{\"email\":\"Remit@greencabs.net.co.nz\",\"mobile\":null},\"primaryAddress\":{\"street1\":\"Level999\",\"street2\":\"SkyscraperBuilding\",\"city\":\"Wellington\",\"state\":null,\"postalCode\":\"1010\",\"countryCode\":\"NZ\"},\"poDetails\":{\"poEmail\":null,\"poMethod\":\"prompt\",\"poChangeMethod\":\"prompt\"},\"paymentTerms\":\"20thMonth\",\"nlgSupplierCode\":\"108930\",\"t7SupplierCode\":null,\"1DaySupplierCode\":null,\"invoiceMatchingLevel\":\"3-way\",\"multiplePaySite\":\"Y\",\"taxCode\":\"096-065-590\",\"twlSiteCode\":null,\"nlgSiteCode\":\"NLGNTREG\",\"eldSiteCode\":null,\"coupaSupplierId\":\"1353\",\"marketSupplierCode\":null}]}}}";

       data3 = "{\"suppliers\":{\"applicationArea\":{\"creationDateTime\":\"2022-03-01T04:09:35\"},\"dataArea\":{\"process\":\"ProcessCoupaSuppliers\",\"suppliers\":[{\"id\":\"300000003547124\",\"supplierNumber\":\"115095\",\"name\":\"GreenCabsLimited\",\"displayName\":null,\"status\":\"Active\",\"primaryContact\":{\"email\":\"somnath.g.ghosh@oracle.com\",\"mobile\":null},\"primaryAddress\":{\"street1\":\"POBox6061\",\"street2\":\"MarionSquare\",\"city\":\"Wellington\",\"state\":null,\"postalCode\":\"6141\",\"countryCode\":\"NZ\"},\"poDetails\":{\"poEmail\":null,\"poMethod\":\"prompt\",\"poChangeMethod\":\"prompt\"},\"paymentTerms\":\"30thMonth\",\"nlgSupplierCode\":\"108930\",\"t7SupplierCode\":null,\"1DaySupplierCode\":null,\"invoiceMatchingLevel\":\"3-way\",\"multiplePaySite\":\"Y\",\"taxCode\":\"096-065-590\",\"twlSiteCode\":null,\"nlgSiteCode\":\"NLGT20\",\"eldSiteCode\":null,\"coupaSupplierId\":\"1353\",\"marketSupplierCode\":null},{\"id\":\"300000003547124\",\"supplierNumber\":\"115095\",\"name\":\"GreenCabsLimited\",\"displayName\":null,\"status\":\"Active\",\"primaryContact\":{\"email\":\"somnath.g.ghosh@oracle.com\",\"mobile\":null},\"primaryAddress\":{\"street1\":\"POBox6061\",\"street2\":\"MarionSquare\",\"city\":\"Wellington\",\"state\":null,\"postalCode\":\"6141\",\"countryCode\":\"NZ\"},\"poDetails\":{\"poEmail\":\"Accounts@greencabs.co.nz\",\"poMethod\":\"email\",\"poChangeMethod\":\"email\"},\"paymentTerms\":\"20thMonth\",\"nlgSupplierCode\":\"108930\",\"t7SupplierCode\":null,\"1DaySupplierCode\":null,\"invoiceMatchingLevel\":\"3-way\",\"multiplePaySite\":\"Y\",\"taxCode\":\"096-065-590\",\"twlSiteCode\":\"TWLNTREG\",\"nlgSiteCode\":null,\"eldSiteCode\":null,\"coupaSupplierId\":\"1353\",\"marketSupplierCode\":null},{\"id\":\"300000003547124\",\"supplierNumber\":\"115095\",\"name\":\"GreenCabsLimited\",\"displayName\":null,\"status\":\"Active\",\"primaryContact\":{\"email\":\"Remit@wherever.net\",\"mobile\":null},\"primaryAddress\":{\"street1\":\"Level999\",\"street2\":\"SkyscraperBuilding\",\"city\":\"Wellington\",\"state\":null,\"postalCode\":\"1010\",\"countryCode\":\"NZ\"},\"poDetails\":{\"poEmail\":null,\"poMethod\":\"prompt\",\"poChangeMethod\":\"prompt\"},\"paymentTerms\":\"20thMonth\",\"nlgSupplierCode\":\"108930\",\"t7SupplierCode\":null,\"1DaySupplierCode\":null,\"invoiceMatchingLevel\":\"3-way\",\"multiplePaySite\":\"Y\",\"taxCode\":\"096-065-590\",\"twlSiteCode\":\"TRADE20\",\"nlgSiteCode\":null,\"eldSiteCode\":null,\"coupaSupplierId\":\"1353\",\"marketSupplierCode\":null},{\"id\":\"300000003547124\",\"supplierNumber\":\"115095\",\"name\":\"GreenCabsLimited\",\"displayName\":null,\"status\":\"Active\",\"primaryContact\":{\"email\":\"somnath.g.ghosh@oracle.com\",\"mobile\":null},\"primaryAddress\":{\"street1\":\"POBox6061\",\"street2\":\"MarionSquare\",\"city\":\"Wellington\",\"state\":null,\"postalCode\":\"6141\",\"countryCode\":\"NZ\"},\"poDetails\":{\"poEmail\":\"AccountsStuff@greencabs.co.nz\",\"poMethod\":\"email\",\"poChangeMethod\":\"email\"},\"paymentTerms\":null,\"nlgSupplierCode\":\"108930\",\"t7SupplierCode\":null,\"1DaySupplierCode\":null,\"invoiceMatchingLevel\":\"3-way\",\"multiplePaySite\":\"Y\",\"taxCode\":\"096-065-590\",\"twlSiteCode\":null,\"nlgSiteCode\":null,\"eldSiteCode\":\"ELDNTREG\",\"coupaSupplierId\":\"1353\",\"marketSupplierCode\":null},{\"id\":\"300000003547125\",\"supplierNumber\":\"115097\",\"name\":\"GreenCabsLimited\",\"displayName\":null,\"status\":\"Active\",\"primaryContact\":{\"email\":\"Remit@greencabs.net.co.nz\",\"mobile\":null},\"primaryAddress\":{\"street1\":\"Level999\",\"street2\":\"SkyscraperBuilding\",\"city\":\"Wellington\",\"state\":null,\"postalCode\":\"1010\",\"countryCode\":\"NZ\"},\"poDetails\":{\"poEmail\":null,\"poMethod\":\"prompt\",\"poChangeMethod\":\"prompt\"},\"paymentTerms\":\"20thMonth\",\"nlgSupplierCode\":\"108930\",\"t7SupplierCode\":null,\"1DaySupplierCode\":null,\"invoiceMatchingLevel\":\"3-way\",\"multiplePaySite\":\"Y\",\"taxCode\":\"096-065-590\",\"twlSiteCode\":null,\"nlgSiteCode\":\"NLGSECSUP\",\"eldSiteCode\":null,\"coupaSupplierId\":\"1353\",\"marketSupplierCode\":null},{\"id\":\"300000003547126\",\"supplierNumber\":\"115096\",\"name\":\"GreenCabsLimited\",\"displayName\":null,\"status\":\"Active\",\"primaryContact\":{\"email\":\"Remit@greencabs.net.co.nz\",\"mobile\":null},\"primaryAddress\":{\"street1\":\"Level999\",\"street2\":\"SkyscraperBuilding\",\"city\":\"Wellington\",\"state\":null,\"postalCode\":\"1010\",\"countryCode\":\"NZ\"},\"poDetails\":{\"poEmail\":null,\"poMethod\":\"prompt\",\"poChangeMethod\":\"prompt\"},\"paymentTerms\":\"20thMonth\",\"nlgSupplierCode\":\"108930\",\"t7SupplierCode\":null,\"1DaySupplierCode\":null,\"invoiceMatchingLevel\":\"3-way\",\"multiplePaySite\":\"Y\",\"taxCode\":\"096-065-590\",\"twlSiteCode\":null,\"nlgSiteCode\":null,\"eldSiteCode\":null,\"coupaSupplierId\":\"1353\",\"marketSupplierCode\":null}]}}}";

       wrongData="{\n" +
            "  \"suppliers\": {\n" +
            "    \"releaseIdentifier\": \"ABCDEFGHIJKLMNOPQRSTUVWXYZA\",\n" +
            "    \"applicationArea\": {\n" +
            "      \"creationDateTime\": \"2016\"\n" +
            "    },\n" +
            "    \"dataArea\": {\n" +
            "      \"process\": \"ABCDEFGHIJKLMNOPQRSTUVWXYZABC\",\n" +
            "      \"suppliers\": [\n" +
            "        {\n" +
            "          \"id\": \"ABCDEFGHIJKLMNOPQRSTUVWXYZAB\",\n" +
            "          \"supplierNumber\": \"ABCDEFGHIJK\",\n" +
            "          \"name\": \"ABCDEFGHIJKLMNOPQRSTUVWXYZAB\",\n" +
            "          \"displayName\": \"ABCDEFGHIJK\",\n" +
            "          \"status\": \"ABCDEFGHIJKLMNOPQRSTUVWX\",\n" +
            "          \"primaryContact\": {\n" +
            "            \"email\": \"jay@gmail.com\",\n" +
            "            \"mobile\": \"ABCDEFGHIJKLMNOPQRSTUVWXYZ\"\n" +
            "          },\n" +
            "          \"primaryAddress\": {\n" +
            "            \"street1\": \"ABCDEF\",\n" +
            "            \"street2\": \"ABCDEFGHIJKLMNOPQRSTUVW\",\n" +
            "            \"city\": \"ABCDEFGHIJK\",\n" +
            "            \"state\": \"ABCDEFGHIJKLMNOPQRSTUVWXYZA\",\n" +
            "            \"postalCode\": \"ABCDEFGHIJKLMNOPQRSTUVWXY\",\n" +
            "            \"countryCode\": \"ABCDEFGHIJKLMNOP\"\n" +
            "          },\n" +
            "          \"poDetails\": {\n" +
            "            \"poEmail\": \"asdf@gmail.com\",\n" +
            "            \"poMethod\": \"ABCDEFGHIJKLMNOPQRSTUVWXYZA\",\n" +
            "            \"poChangeMethod\": \"ABCDEFG\"\n" +
            "          },\n" +
            "          \"paymentTerms\": \"ABCDEFGHIJKLMN\",\n" +
            "          \"nlgSupplierCode\": \"ABCDEFGHIJKLMNOPQRS\",\n" +
            "          \"t7SupplierCode\": {},\n" +
            "          \"1daySupplierCode\": \"ABCDEFG\",\n" +
            "          \"invoiceMatchingLevel\": \"ABCDEFGHIJKLMNOPQRSTUVWXYZ\",\n" +
            "          \"oracleSiteCode\": \"ABCDEFGHIJKLMNOPQRS\",\n" +
            "          \"multiplePaySite\": \"ABCDEFGHIJKLMNOPQRSTU\",\n" +
            "          \"taxCode\": \"ABCDEF\",\n" +
            "          \"coupaSupplierId\": \"ABCDEFGHIJKLMNOPQRSTU\",\n" +
            "          \"marketSupplierCode\": \"ABCDEFGHIJK\"\n" +
            "        }\n" +
            "      ]\n" +
            "    }\n" +
            "  }\n" +
            "}";
	}

    @Test
    public void testObjectMapperWithData(){
        SuppliersEntity suppliersEntity = new SuppliersEntity();
        CoupaSuppliersMapper coupaSuppliersMapper = new CoupaSuppliersMapper();
        suppliersEntity = (SuppliersEntity)coupaSuppliersMapper.convertJsonToPojoDetails(data3,suppliersEntity);
        assertEquals(null, suppliersEntity.getSuppliersData().getDataArea().getSuppliers().get(0).getDisplayName());
    }

    @Test
    public void testObjectMapperWithException(){
        SuppliersEntity suppliersEntity = new SuppliersEntity();
        List<Supplier> supplierList = new ArrayList<>();
        CoupaSuppliersMapper coupaSuppliersMapper = new CoupaSuppliersMapper();
        boolean isValidData = false;
        try {
            suppliersEntity = (SuppliersEntity) coupaSuppliersMapper.convertJsonToPojoDetails(null, suppliersEntity);
            isValidData = true;
        }catch (Exception e){
            log.error("failed to convert json to pojo : "+e.getMessage());
        }
        assertEquals(false, isValidData);
    }


    @Test
    public void testValidationUtilClass() throws JSONException {
        JSONObject jsonObject = new JSONObject(data3.toString());
        Boolean isvalidated =validationUtil.validateJson(jsonObject,"/sdem/suppliers.json");
        assertEquals(false,isvalidated);
    }
    @Test
    public void testValidationUtilClassFail() throws JSONException {
        JSONObject jsonObject = new JSONObject(data3.toString());
        //assertEquals(true,validationUtil.validateJson(jsonObject,"/sdem/suppliers.json"));
        assertFalse(validationUtil.validateJson(jsonObject,"/sdem/suppliers.json"));
    }

    @Test
    public void testValidationUtilClassWrongData() throws JSONException {
        JSONObject jsonObject = new JSONObject(wrongData.toString());
        //assertEquals(true,validationUtil.validateJson(jsonObject,"/sdem/suppliers.json"));
        assertFalse(validationUtil.validateJson(jsonObject,"/sdem/suppliers.json"));
    }

    //@Test
    public void testUploadCSV(){
        SuppliersEntity suppliersEntity = new SuppliersEntity();
        CoupaSuppliersMapper coupaSuppliersMapper = new CoupaSuppliersMapper();
        suppliersEntity = (SuppliersEntity)coupaSuppliersMapper.convertJsonToPojoDetails(data3,suppliersEntity);
        List<Supplier> supplierList = suppliersEntity.getSuppliersData().getDataArea().getSuppliers();
        sftpCoupaService = getCoupaService();
        supplierCoupaService.setProjectCodeMappingStrategy(new SupplierMappingStrategy());
        supplierCoupaService.setSftpCoupaServiceImpl(sftpCoupaService);
        boolean status = supplierCoupaService.createCSV(supplierList);
        assertEquals(true,status);
    }

    private SftpCoupaServiceImpl getCoupaService() {
        SftpCoupaServiceImpl sftpService = new SftpCoupaServiceImpl(
            "fileshare-au-test.coupahost.com",
            "thewarehouse-dev", 22,
            "/sftp-test-bucket-poc/Suppliers",
            "7BGF4EsowEyB",
            "_Supplier_OFI.csv", "yyMMddHHmmss_SSS" );
        return sftpService;
    }

}
