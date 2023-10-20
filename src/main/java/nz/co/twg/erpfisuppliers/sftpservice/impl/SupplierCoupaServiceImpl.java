package nz.co.twg.erpfisuppliers.sftpservice.impl;
import com.opencsv.CSVWriter;
import lombok.extern.slf4j.Slf4j;
import nz.co.twg.erpfisuppliers.common.handler.SupplierException;
import nz.co.twg.erpfisuppliers.common.pojo.Supplier;
import nz.co.twg.erpfisuppliers.sftpservice.model.SupplierCoupaDto;
import nz.co.twg.erpfisuppliers.sftpservice.ISupplierCoupaService;
import nz.co.twg.erpfisuppliers.common.util.SupplierMappingStrategy;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.*;

@Slf4j
@Service
public class SupplierCoupaServiceImpl implements ISupplierCoupaService {

	private SftpCoupaServiceImpl sftpCoupaServiceImpl;
    private SupplierMappingStrategy supplierMappingStrategy;
    private String nullValueString = "--NULL--";
    private String empty = "";

    @Autowired SupplierCoupaServiceImpl(
    		 SupplierMappingStrategy supplierMappingStrategy,
    		 SftpCoupaServiceImpl sftpCoupaServiceImpl ){
    	this.supplierMappingStrategy = supplierMappingStrategy;
	    this.sftpCoupaServiceImpl = sftpCoupaServiceImpl;
	}

    public List<SupplierCoupaDto> filterSupplier(List<Supplier> supplierList) {
        List<SupplierCoupaDto> coupaDataList = new ArrayList<SupplierCoupaDto>();
        SupplierCoupaDto coupaDto = new SupplierCoupaDto();
        for (Supplier supplier : supplierList) {
            coupaDto = new SupplierCoupaDto();
            //mandatory Field
            coupaDto.setName(replaceField(supplier.getName()));
            coupaDto.setDisplayName(StringUtils.isNotEmpty(supplier.getDisplayName()) ? replaceField(supplier.getDisplayName()) : nullValueString);
            coupaDto.setStatus(StringUtils.isNotEmpty(supplier.getStatus()) ? replaceField(supplier.getStatus()) : nullValueString);
            //mandatory Field
            // Added by Shauzab
            coupaDto.setSupplierType(replaceField(supplier.getSupplierType()));
            coupaDto.setSupplierNumber(replaceField(supplier.getSupplierNumber()));
            coupaDto.setOneTimeSupplier(replaceField(supplier.getOneTimeSupplier()));
            //if (supplier.getPrimaryContact() != null) {
                //coupaDto.setPrimaryContactEmail(StringUtils.isNotEmpty(supplier.getPrimaryContact().getEmail()) ? replaceField(supplier.getPrimaryContact().getEmail()) : nullValueString);
                //coupaDto.setPrimaryContactMobile(StringUtils.isNotEmpty(supplier.getPrimaryContact().getMobile()) ? replaceField(supplier.getPrimaryContact().getMobile()) : nullValueString);
            //}
            if (supplier.getPrimaryAddress() != null) {
                coupaDto.setPrimaryAddressStreet1(StringUtils.isNotEmpty(supplier.getPrimaryAddress().getStreet1()) ? replaceField(supplier.getPrimaryAddress().getStreet1()) : nullValueString);
                coupaDto.setPrimaryAddressStreet2(StringUtils.isNotEmpty(supplier.getPrimaryAddress().getStreet2()) ? replaceField(supplier.getPrimaryAddress().getStreet2()) : nullValueString);
                coupaDto.setPrimaryAddressStreet3(StringUtils.isNotEmpty(supplier.getPrimaryAddress().getStreet3()) ? replaceField(supplier.getPrimaryAddress().getStreet3()) : nullValueString);
                coupaDto.setPrimaryAddressCity(StringUtils.isNotEmpty(supplier.getPrimaryAddress().getCity()) ? replaceField(supplier.getPrimaryAddress().getCity()) : nullValueString);
                coupaDto.setPrimaryAddressState(StringUtils.isNotEmpty(supplier.getPrimaryAddress().getState()) ? replaceField(supplier.getPrimaryAddress().getState()) : nullValueString);
                coupaDto.setPrimaryAddressPostalCode(StringUtils.isNotEmpty(supplier.getPrimaryAddress().getPostalCode()) ? replaceField(supplier.getPrimaryAddress().getPostalCode()) : nullValueString);
                coupaDto.setPrimaryAddressCountryCode(StringUtils.isNotEmpty(supplier.getPrimaryAddress().getCountryCode()) ? replaceField(supplier.getPrimaryAddress().getCountryCode()) : nullValueString);
            }
            coupaDto.setPoEmail(StringUtils.isNotEmpty(supplier.getPoDetails().getPoEmail()) ? replaceField(supplier.getPoDetails().getPoEmail()) : nullValueString);
            //mandatory field
            coupaDto.setPoMethod(replaceField(supplier.getPoDetails().getPoMethod()));
            coupaDto.setPoChangeMethod(StringUtils.isNotEmpty(supplier.getPoDetails().getPoChangeMethod()) ? replaceField(supplier.getPoDetails().getPoChangeMethod()) : nullValueString);
            coupaDto.setPaymentTerms(StringUtils.isNotEmpty(supplier.getPaymentTerms()) ? replaceField(supplier.getPaymentTerms()) : "");
            coupaDto.setInvoiceCurrency(StringUtils.isNotEmpty(supplier.getInvoiceCurrency()) ? replaceField(supplier.getInvoiceCurrency()) : "");
            // coupaDto.setPaymentTerms(StringUtils.isNotEmpty(supplier.getPaymentTerms()) ? "Test" : "");
            //coupaDto.setNlgSupplierCode(StringUtils.isNotEmpty(supplier.getNlgSupplierCode()) ? replaceField(supplier.getNlgSupplierCode()) : nullValueString);

            coupaDto.setT7SupplierCode(StringUtils.isNotEmpty(supplier.getT7SupplierCode()) ? replaceField(supplier.getT7SupplierCode()) : nullValueString);
            coupaDto.set_1daySupplierCode(StringUtils.isNotEmpty(supplier.get_1daySupplierCode()) ? replaceField(supplier.get_1daySupplierCode()) : nullValueString);
            //mandatory Field
            coupaDto.setInvoiceMatchingLevel(replaceField(supplier.getInvoiceMatchingLevel()));
            //coupaDto.setOracleSiteCode(StringUtils.isNotEmpty(supplier.getOracleSiteCode()) ? replaceField(supplier.getOracleSiteCode()) : nullValueString);
            //coupaDto.setMultiplePaySite(StringUtils.isNotEmpty(supplier.getMultiplePaySite()) ? replaceField(supplier.getMultiplePaySite()) : nullValueString);
            coupaDto.setTaxCode(StringUtils.isNotEmpty(supplier.getTaxCode()) ? replaceField(supplier.getTaxCode()) : nullValueString);
            coupaDto.setTmlSiteCode(StringUtils.isNotEmpty(supplier.getTmlSiteCode()) ? replaceField(supplier.getTmlSiteCode()) : nullValueString);
          //  coupaDto.setTp7SiteCode(StringUtils.isNotEmpty(supplier.getTp7SiteCode()) ? replaceField(supplier.getTp7SiteCode()) : nullValueString);
            if (StringUtils.isNotEmpty(supplier.getTp7SiteCode())) {
                coupaDto.setTp7SiteCode(replaceField(supplier.getTp7SiteCode()));
            } else {
                coupaDto.setTp7SiteCode(replaceField(nullValueString));
            }
            coupaDto.setId(StringUtils.isNotEmpty(supplier.getCoupaSupplierId()) ? replaceField(supplier.getCoupaSupplierId()) : nullValueString);
            //fix for twl/nlg/eld site codes
            if (StringUtils.isNotEmpty(supplier.getTwlSiteCode())) {
                coupaDto.setTwlSiteCode(replaceField(supplier.getTwlSiteCode()));
            } else {
                coupaDto.setTwlSiteCode(replaceField(nullValueString));
            }
            if (StringUtils.isNotEmpty(supplier.getNlgSiteCode())) {
                coupaDto.setNlgSiteCode(replaceField(supplier.getNlgSiteCode()));
            } else {
                coupaDto.setNlgSiteCode(replaceField(nullValueString));
            }
            if (StringUtils.isNotEmpty(supplier.getEldSiteCode())) {
                coupaDto.setEldSiteCode(replaceField(supplier.getEldSiteCode()));
            } else {
                coupaDto.setEldSiteCode(replaceField(nullValueString));
            }
            //coupaDto.setCoupaSupplierId(StringUtils.isNotEmpty(supplier.getId()) ? replaceField(supplier.getId()) : nullValueString);
            coupaDto.setMarketSupplierCode(StringUtils.isNotEmpty(supplier.getMarketSupplierCode()) ? replaceField(supplier.getMarketSupplierCode()) : nullValueString);
            coupaDto.setNzbn(replaceField(supplier.getNzbn()));
            coupaDto.setWebsite(replaceField(supplier.getWebsite()));
            coupaDataList.add(coupaDto);
        }
        return coupaDataList;
    }

    @Override
    public boolean createCSV (List<Supplier> supplierList) {
        log.info("Getting list of all PaymentDetail List to Process csv");
    	//List<SupplierCoupaDto>  coupaDataList= filterSupplier (supplierList);
        List<SupplierCoupaDto>  filteredCoupaDataSet = filterSuppliersDataSet(supplierList);
        List<String[]> inputData = new LinkedList<>();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        OutputStreamWriter streamWriter = new OutputStreamWriter(stream);
        try {
            if (CollectionUtils.isEmpty(filteredCoupaDataSet)){
                log.info("No Data Found in POJO object");
                return false;
            }
            CSVWriter writer =new CSVWriter(streamWriter, ',', CSVWriter.NO_QUOTE_CHARACTER);
            supplierMappingStrategy = new SupplierMappingStrategy();
            supplierMappingStrategy.setType(SupplierCoupaDto.class);
            String[] header = supplierMappingStrategy.generateHeader();
            writer.writeNext(header);
            for (SupplierCoupaDto supplierCoupaDto : filteredCoupaDataSet) {
                 String[] row = {
                	 supplierCoupaDto.getName(),
                	 supplierCoupaDto.getDisplayName(),
                	 supplierCoupaDto.getStatus(),
                     supplierCoupaDto.getSupplierType(),
                     supplierCoupaDto.getOneTimeSupplier(),

                	 supplierCoupaDto.getSupplierNumber(),
                     //added by Shauzab

                	 //supplierCoupaDto.getPrimaryContactEmail(),
                	 //supplierCoupaDto.getPrimaryContactMobile(),
                	 supplierCoupaDto.getPrimaryAddressStreet1(),
                	 supplierCoupaDto.getPrimaryAddressStreet2(),
                     supplierCoupaDto.getPrimaryAddressStreet3(),
                	 supplierCoupaDto.getPrimaryAddressCity(),
                	 supplierCoupaDto.getPrimaryAddressState(),
                	 supplierCoupaDto.getPrimaryAddressPostalCode(),
                	 supplierCoupaDto.getPrimaryAddressCountryCode(),
                	 supplierCoupaDto.getPoEmail(),
                	 supplierCoupaDto.getPoMethod(),
                	 supplierCoupaDto.getPoChangeMethod(),
                	 supplierCoupaDto.getPaymentTerms(),

                     supplierCoupaDto.getInvoiceCurrency(),
                	 //supplierCoupaDto.getNlgSupplierCode(),
                	 supplierCoupaDto.get_1daySupplierCode(),
                	 supplierCoupaDto.getT7SupplierCode(),
                	 supplierCoupaDto.getInvoiceMatchingLevel(),
                	 //supplierCoupaDto.getOracleSiteCode(),
                	 //supplierCoupaDto.getMultiplePaySite(),
                	 supplierCoupaDto.getTaxCode(),
                     supplierCoupaDto.getTmlSiteCode(),
                     supplierCoupaDto.getTp7SiteCode(),
                	 supplierCoupaDto.getId(),
                     supplierCoupaDto.getTwlSiteCode(),
                     supplierCoupaDto.getNlgSiteCode(),
                     supplierCoupaDto.getEldSiteCode(),
                     //supplierCoupaDto.getCoupaSupplierId(),
                     supplierCoupaDto.getMarketSupplierCode(),
                     supplierCoupaDto.getNzbn(),
                     supplierCoupaDto.getWebsite()

                 };
                inputData.add(row);
            }
            writer.writeAll(inputData);
            log.info("size : " + inputData.size());
            writer.close();
            log.info("Successfully created CSV file : ");
            //sftpCoupaServiceImpl.uploadCSVFile(stream);
            sftpCoupaServiceImpl.uploadCSVFileRetry(stream);
            log.info("Successfully uploaded CSV file : ");
        }  catch (IOException ioException) {
            log.error("IOException occured while writing CSV file in createCSV::SupplierCoupaServiceImpl "+ ioException.getMessage());
            throw new SupplierException("IOException occured while writing CSV file in createCSV::SupplierCoupaServiceImpl "+ ioException.getMessage());
        } catch (SupplierException supplierException) {
			log.error("SupplierException occured while writing CSV file in createCSV::SupplierCoupaServiceImpl " + supplierException.getMessage());
			throw new SupplierException("SupplierException occured while writing in CSV file in createCSV::SupplierCoupaServiceImpl " + supplierException.getMessage());
        }
        return true;
    }

    public void setProjectCodeMappingStrategy(SupplierMappingStrategy supplierMappingStrategy) {
    	this.supplierMappingStrategy=supplierMappingStrategy;
    }

    public void setSftpCoupaServiceImpl(SftpCoupaServiceImpl sftpCoupaServiceImpl) {
    	this.sftpCoupaServiceImpl=sftpCoupaServiceImpl;
    }

    private static String replaceField(String fieldValue) {
        if (fieldValue != null && fieldValue.contains(", ")) {
            fieldValue = fieldValue.replaceAll(",", "");
        } else if (fieldValue != null && (fieldValue.startsWith(",") || fieldValue.endsWith(","))) {
            fieldValue = fieldValue.replaceAll(",", "");
        } else {
            fieldValue = fieldValue.replaceAll(",", " ");
        }
        return fieldValue;
    }

    public List<SupplierCoupaDto> filterSuppliersDataSet(List<Supplier> supplierList){

        Set<String> supplierNumSet = new LinkedHashSet<>();
        Map<String, SupplierCoupaDto> supplierCoupaDtoMap = new LinkedHashMap<>();
        Set<String> twlSiteSet = null;
        Set<String> nlgSiteSet = null;
        Set<String> eldSiteSet = null;
        SupplierCoupaDto coupaDto;

        for(Supplier supplier:supplierList){
            if(supplierNumSet.add(supplier.getSupplierNumber())){

                twlSiteSet = new HashSet<>();
                nlgSiteSet = new HashSet<>();
                eldSiteSet = new HashSet<>();

                if(StringUtils.isNotEmpty(supplier.getTwlSiteCode())){
                twlSiteSet.add(supplier.getTwlSiteCode());
                }
                if(StringUtils.isNotEmpty(supplier.getNlgSiteCode())){
                    nlgSiteSet.add(supplier.getNlgSiteCode());
                }
                if(StringUtils.isNotEmpty(supplier.getEldSiteCode())){
                    eldSiteSet.add(supplier.getEldSiteCode());
                }

                coupaDto = new SupplierCoupaDto();
                coupaDto.setName(replaceField(supplier.getName()));
                coupaDto.setDisplayName(StringUtils.isNotEmpty(supplier.getDisplayName()) ? replaceField(supplier.getDisplayName()) : nullValueString);
                coupaDto.setStatus(StringUtils.isNotEmpty(supplier.getStatus()) ? replaceField(supplier.getStatus()) : nullValueString);
                //added by Shauzab Abbas
                coupaDto.setSupplierType(replaceField(supplier.getSupplierType()));
                //mandatory Field
                coupaDto.setSupplierNumber(replaceField(supplier.getSupplierNumber()));
                coupaDto.setOneTimeSupplier(replaceField(supplier.getOneTimeSupplier()));
                if (supplier.getPrimaryContact() != null) {
                    coupaDto.setPrimaryContactEmail(StringUtils.isNotEmpty(supplier.getPrimaryContact().getEmail()) ? replaceField(supplier.getPrimaryContact().getEmail()) : nullValueString);
                    coupaDto.setPrimaryContactMobile(StringUtils.isNotEmpty(supplier.getPrimaryContact().getMobile()) ? replaceField(supplier.getPrimaryContact().getMobile()) : nullValueString);
                }
                if (supplier.getPrimaryAddress() != null) {
                    coupaDto.setPrimaryAddressStreet1(StringUtils.isNotEmpty(supplier.getPrimaryAddress().getStreet1()) ? replaceField(supplier.getPrimaryAddress().getStreet1()) : nullValueString);
                    coupaDto.setPrimaryAddressStreet2(StringUtils.isNotEmpty(supplier.getPrimaryAddress().getStreet2()) ? replaceField(supplier.getPrimaryAddress().getStreet2()) : nullValueString);
                    coupaDto.setPrimaryAddressStreet3(StringUtils.isNotEmpty(supplier.getPrimaryAddress().getStreet3()) ? replaceField(supplier.getPrimaryAddress().getStreet3()) : nullValueString);
                    coupaDto.setPrimaryAddressCity(StringUtils.isNotEmpty(supplier.getPrimaryAddress().getCity()) ? replaceField(supplier.getPrimaryAddress().getCity()) : nullValueString);
                    coupaDto.setPrimaryAddressState(StringUtils.isNotEmpty(supplier.getPrimaryAddress().getState()) ? replaceField(supplier.getPrimaryAddress().getState()) : nullValueString);
                    coupaDto.setPrimaryAddressPostalCode(StringUtils.isNotEmpty(supplier.getPrimaryAddress().getPostalCode()) ? replaceField(supplier.getPrimaryAddress().getPostalCode()) : nullValueString);
                    coupaDto.setPrimaryAddressCountryCode(StringUtils.isNotEmpty(supplier.getPrimaryAddress().getCountryCode()) ? replaceField(supplier.getPrimaryAddress().getCountryCode()) : nullValueString);
                }
                coupaDto.setPoEmail(StringUtils.isNotEmpty(supplier.getPoDetails().getPoEmail()) ? replaceField(supplier.getPoDetails().getPoEmail()) : nullValueString);
                //mandatory field
                coupaDto.setPoMethod(replaceField(supplier.getPoDetails().getPoMethod()));
                coupaDto.setPoChangeMethod(StringUtils.isNotEmpty(supplier.getPoDetails().getPoChangeMethod()) ? replaceField(supplier.getPoDetails().getPoChangeMethod()) : nullValueString);
                //coupaDto.setPaymentTerms(empty);
                //coupaDto.setPaymentTerms(StringUtils.isNotEmpty(supplier.getPaymentTerms()) ? replaceField(supplier.getPaymentTerms()) : "");
                // coupaDto.setPaymentTerms(StringUtils.isNotEmpty(supplier.getPaymentTerms()) ? "Test" : "");
                //coupaDto.setNlgSupplierCode(StringUtils.isNotEmpty(supplier.getNlgSupplierCode()) ? replaceField(supplier.getNlgSupplierCode()) : nullValueString);
                coupaDto.setInvoiceCurrency(StringUtils.isNotEmpty(supplier.getInvoiceCurrency()) ? replaceField(supplier.getInvoiceCurrency()) : "");

                coupaDto.setT7SupplierCode(StringUtils.isNotEmpty(supplier.getT7SupplierCode()) ? replaceField(supplier.getT7SupplierCode()) : nullValueString);
                coupaDto.set_1daySupplierCode(StringUtils.isNotEmpty(supplier.get_1daySupplierCode()) ? replaceField(supplier.get_1daySupplierCode()) : nullValueString);
                //mandatory Field
                coupaDto.setInvoiceMatchingLevel(replaceField(supplier.getInvoiceMatchingLevel()));
                coupaDto.setTaxCode(StringUtils.isNotEmpty(supplier.getTaxCode()) ? replaceField(supplier.getTaxCode()) : nullValueString);
                coupaDto.setTmlSiteCode(StringUtils.isNotEmpty(supplier.getTmlSiteCode()) ? replaceField(supplier.getTmlSiteCode()) : nullValueString);
            //   coupaDto.setTp7siteCode(StringUtils.isNotEmpty(supplier.getTp7siteCode()) ? replaceField(supplier.getTp7siteCode()) : nullValueString);
                if (StringUtils.isNotEmpty(supplier.getTp7SiteCode())) {
                    coupaDto.setTp7SiteCode(replaceField(supplier.getTp7SiteCode()));
                } else {
                    coupaDto.setTp7SiteCode(replaceField(nullValueString));
                }
                coupaDto.setNzbn(replaceField(supplier.getNzbn()));
                coupaDto.setWebsite(replaceField(supplier.getWebsite()));
                coupaDto.setId(StringUtils.isNotEmpty(supplier.getCoupaSupplierId()) ? replaceField(supplier.getCoupaSupplierId()) : empty);
                //fix for twl/nlg/eld site codes
                if (StringUtils.isNotEmpty(supplier.getTwlSiteCode())) {
                    coupaDto.setTwlSiteCode(replaceField(supplier.getTwlSiteCode()));
                } else {
                    coupaDto.setTwlSiteCode(replaceField(nullValueString));
                }
                if (StringUtils.isNotEmpty(supplier.getNlgSiteCode())) {
                    coupaDto.setNlgSiteCode(replaceField(supplier.getNlgSiteCode()));
                } else {
                    coupaDto.setNlgSiteCode(replaceField(nullValueString));
                }
                if (StringUtils.isNotEmpty(supplier.getEldSiteCode())) {
                    coupaDto.setEldSiteCode(replaceField(supplier.getEldSiteCode()));
                } else {
                    coupaDto.setEldSiteCode(replaceField(nullValueString));
                }
                coupaDto.setMarketSupplierCode(StringUtils.isNotEmpty(supplier.getMarketSupplierCode()) ? replaceField(supplier.getMarketSupplierCode()) : nullValueString);

                supplierCoupaDtoMap.put(supplier.getSupplierNumber(), coupaDto);
            } else {
                if (StringUtils.isNotEmpty(supplier.getTwlSiteCode()) || StringUtils.isNotEmpty(supplier.getNlgSiteCode()) || StringUtils.isNotEmpty(supplier.getEldSiteCode())) {
                    if (twlSiteSet.add(supplier.getTwlSiteCode())) {
                        coupaDto = supplierCoupaDtoMap.get(supplier.getSupplierNumber());
                        coupaDto.setTwlSiteCode(StringUtils.isNotEmpty(supplier.getTwlSiteCode())?supplier.getTwlSiteCode():coupaDto.getTwlSiteCode());
                    }
                    if (nlgSiteSet.add(supplier.getNlgSiteCode())) {
                        coupaDto = supplierCoupaDtoMap.get(supplier.getSupplierNumber());
                        coupaDto.setNlgSiteCode(StringUtils.isNotEmpty(supplier.getNlgSiteCode())?supplier.getNlgSiteCode():coupaDto.getNlgSiteCode());
                    }
                    if (eldSiteSet.add(supplier.getEldSiteCode())) {
                        coupaDto = supplierCoupaDtoMap.get(supplier.getSupplierNumber());
                        coupaDto.setEldSiteCode(StringUtils.isNotEmpty(supplier.getEldSiteCode())?supplier.getEldSiteCode():coupaDto.getEldSiteCode());
                    }
                }
            }
        }
        return new ArrayList<>(supplierCoupaDtoMap.values());
    }
}
