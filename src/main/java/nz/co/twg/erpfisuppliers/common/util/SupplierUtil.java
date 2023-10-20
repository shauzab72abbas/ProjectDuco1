package nz.co.twg.erpfisuppliers.common.util;

public class SupplierUtil {
	
    public static String mergeField(String field1, String field2) {
    	String field=" ";
    	if (field1==null && field2==null) {
    		field=" ";
    	} else	if (field1!=null && field2!=null) {
    		field= field1+ "-" + field2;
    	} else if (field2==null ) {
    		field=field1;
    	} else if (field1==null ) {
    		field=field2;
    	}
    	return field;
    }	
	
}
