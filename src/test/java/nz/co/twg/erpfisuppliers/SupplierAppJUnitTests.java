package nz.co.twg.erpfisuppliers;
import lombok.extern.slf4j.Slf4j;
import nz.co.twg.erpfisuppliers.common.handler.SupplierException;
import nz.co.twg.erpfisuppliers.common.pojo.PoDetails;
import nz.co.twg.erpfisuppliers.common.pojo.PrimaryAddress;
import nz.co.twg.erpfisuppliers.common.pojo.PrimaryContact;
import nz.co.twg.erpfisuppliers.common.pojo.Supplier;
import nz.co.twg.erpfisuppliers.common.util.SupplierMappingStrategy;
import nz.co.twg.erpfisuppliers.sftpservice.impl.SftpCoupaServiceImpl;
import nz.co.twg.erpfisuppliers.sftpservice.impl.SupplierCoupaServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class SupplierAppJUnitTests {

	@Test
	public void contextLoads() {
	}

	@Autowired
	private SupplierCoupaServiceImpl supplierCoupaService;

	@MockBean
	private SftpCoupaServiceImpl sftpCoupaService;

	List<Supplier> supplierList = new ArrayList<>();
	@Before
	public void setup() {
		log.info("Setup Done.... " );
	}

    @Test
	public void createCsvAll()  {
		log.info("CSV Creation Started All...");
		ByteArrayOutputStream stream=new ByteArrayOutputStream();
		when(sftpCoupaService.uploadCSVFile(any(ByteArrayOutputStream.class))).thenReturn(true);
		supplierCoupaService.setProjectCodeMappingStrategy(new SupplierMappingStrategy());
		supplierCoupaService.setSftpCoupaServiceImpl(sftpCoupaService);
		boolean status = supplierCoupaService.createCSV(getSuppliersAll());
		assertEquals(true, status);
	    log.info("CSV Creation Success All...");
	}

	//@Test
	public void createCsvPartial() {
		log.info("CSV Creation Started Partial...");
		sftpCoupaService = getCoupaService();
		//when(sftpCoupaService.uploadCSVFile(any(ByteArrayOutputStream.class))).thenReturn(true);
		supplierCoupaService.setProjectCodeMappingStrategy(new SupplierMappingStrategy());
		supplierCoupaService.setSftpCoupaServiceImpl(sftpCoupaService);
		boolean status = supplierCoupaService.createCSV(getSuppliersAll());
		assertEquals(true, status);
	    log.info("CSV Creation Success Partial...");
	}

	@Test
	public void uploadCSVFileNonNull() {
		boolean status=false;
		try {
			sftpCoupaService.uploadCSVFile(new ByteArrayOutputStream());
			status=true;
		}catch(SupplierException exception) {
			status=false;
		}
		assertEquals(true,status);
	}

	@Test
	public void uploadCSVFile() {
		when(sftpCoupaService.uploadCSVFile(null)).thenCallRealMethod();
		boolean status=true;
		try {
			sftpCoupaService.uploadCSVFile(null);
		}catch(SupplierException exception) {
			status=false;
		}
		assertEquals(false,status);
	}

	@Test
	public void createCsvNoValues() {
		List<Supplier> suppliers= new ArrayList<Supplier>();
		assertEquals(false,supplierCoupaService.createCSV(suppliers));
	}

	@Test
	public void createCsvPartialError(){
		log.info("createCsvPartialError() started...");
		boolean status = true;
		try {
			sftpCoupaService = getCoupaService();
			sftpCoupaService = getCoupaServiceError();
		    supplierCoupaService.setProjectCodeMappingStrategy(new SupplierMappingStrategy());
		    supplierCoupaService.setSftpCoupaServiceImpl(sftpCoupaService);
		    status = supplierCoupaService.createCSV(getSuppliersAll());
		}catch (Exception ex){
			status = false;
		}
		assertEquals(false, status);
	    log.info("createCsvPartialError() finished...");
	}

	@Test
	public void createCsvPartialFolder(){
		log.info("createCsvPartialFolder() started...");
		boolean status = true;
		try {
			sftpCoupaService = getCoupaServiceFolder();
		    supplierCoupaService.setProjectCodeMappingStrategy(new SupplierMappingStrategy());
		    supplierCoupaService.setSftpCoupaServiceImpl(sftpCoupaService);
		    status = supplierCoupaService.createCSV(getSuppliersAll());
		}catch (Exception ex){
			status = false;
		}
		assertEquals(false, status);
	    log.info("createCsvPartialFolder() finished...");
	}

	@Test
	public void createCsvPartialFolderAuth(){
		log.info("createCsvPartialFolderAuth() started...");
		boolean status = true;
		try {
			sftpCoupaService = getCoupaServiceAuth();
		    supplierCoupaService.setProjectCodeMappingStrategy(new SupplierMappingStrategy());
		    supplierCoupaService.setSftpCoupaServiceImpl(sftpCoupaService);
		    status = supplierCoupaService.createCSV(getSuppliersAll());
		}catch (Exception ex){
			status = false;
		}
		assertEquals(false, status);
	    log.info("createCsvPartialFolderAuth() finished...");
	}

	//@Test
	public void createCsvConnectionError(){
		log.info("createCsvConnectionError() started...");
		boolean status = true;
		try {
		    supplierCoupaService.setProjectCodeMappingStrategy(new SupplierMappingStrategy());
		    status = supplierCoupaService.createCSV(getSuppliersAll());
		}catch (Exception ex){
			status = false;
		}
		assertEquals(true, status);
	    log.info("createCsvConnectionError() finished...");
	}

	private List<Supplier> getSuppliersAll() {
		supplierList = new ArrayList<>();
		supplierList.add(getSupplier("A"));
		supplierList.add(getSupplier("B"));
		supplierList.add(getSupplier("C"));
		return supplierList;
	}

	private Supplier getSupplier(String label) {
		Supplier supplier = new Supplier();
		supplier.setName("Name " + label);
		supplier.setDisplayName("DisplayName " + label);
		supplier.setStatus("Status " + label);
		supplier.setSupplierNumber("SupplierNumber " + label);
		supplier.setPrimaryContact(getPrimaryContact(label));
		supplier.setPrimaryAddress(getPrimaryAddress(label));
		supplier.setPoDetails(getPoDetail(label));
		supplier.setPaymentTerms("PaymentTerms  " + label);
		//supplier.setNlgSupplierCode("NlgSupplierCode  " + label);
		supplier.setT7SupplierCode("T7SupplierCode " + label);
		supplier.set_1daySupplierCode("1daySupplierCode " + label);
		supplier.setInvoiceMatchingLevel("InvoiceMatchingLevel  " + label);
		supplier.setTwlSiteCode("TWLSITE003");
        supplier.setNlgSiteCode("NLGSDSDG");
        supplier.setEldSiteCode("ELD000456");
		supplier.setOracleSiteCode("OracleSiteCode " + label);
		supplier.setMultiplePaySite("tMultiplePaySite  " + label);
		supplier.setTaxCode("TaxCode " + label);
		supplier.setCoupaSupplierId("CoupaSupplierId " + label);
		return supplier;
	}

	private PrimaryAddress getPrimaryAddress(String label) {
		PrimaryAddress primaryAddress = new PrimaryAddress();
		primaryAddress.setStreet1("1234, flat 4, Street 7 " + label);
		primaryAddress.setStreet2("44455566, north Street4 " + label);
		primaryAddress.setCity("City ");
		primaryAddress.setState("State " + label);
		primaryAddress.setPostalCode(label);
		primaryAddress.setCountryCode(label);
		return primaryAddress;
	}

	private PrimaryContact getPrimaryContact(String label) {
		PrimaryContact primaryContact= new PrimaryContact();
		primaryContact.setEmail("Email " + label);
		primaryContact.setMobile("Mobile " + label);
		return primaryContact;
	}

	private PoDetails getPoDetail(String label) {
		PoDetails poDetails= new PoDetails();
		poDetails.setPoEmail("PoEmail " +label);
		poDetails.setPoMethod("PoMethod " +label);
		poDetails.setPoChangeMethod("PoChangeMethod " +label);
		return poDetails;
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

	private SftpCoupaServiceImpl getCoupaServiceError() {
		sftpCoupaService = new SftpCoupaServiceImpl(
				"s-test-server-fileshare-au-test.coupahost.com",
				"public-sftp", 22, "/sftp-test-bucket-poc/Suppliers",
				"7BGF4EsowEyB",
				"_Supplier_OFI.csv", "yyMMddHHmmss_SSS" );
		return sftpCoupaService;
	}

	private SftpCoupaServiceImpl getCoupaServiceFolder() {
		SftpCoupaServiceImpl sftpService = new SftpCoupaServiceImpl(
				"fileshare-au-test.coupahost.com",
				"thewarehouse-dev", 22,
				"/sftp-test-bucket-poc/Supplier5555",
				"7BGF4EsowEyB",
				"_Supplier_OFI.csv", "yyMMddHHmmss_SSS" );
		return sftpService;
	}

	private SftpCoupaServiceImpl getCoupaServiceAuth() {
		SftpCoupaServiceImpl sftpService = new SftpCoupaServiceImpl(
				"fileshare-au-test.coupahost.com",
				"thewarehouse-dev", 22,
				"/sftp-test-bucket-poc/Suppliers",
				"7BGF4EsowEyBTTTT",
				"_Supplier_OFI.csv", "yyMMddHHmmss_SSS" );
		return sftpService;
	}
}
