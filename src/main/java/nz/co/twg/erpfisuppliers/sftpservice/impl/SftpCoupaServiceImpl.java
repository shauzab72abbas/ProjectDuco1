package nz.co.twg.erpfisuppliers.sftpservice.impl;
import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import lombok.extern.slf4j.Slf4j;
import nz.co.twg.erpfisuppliers.common.handler.SupplierException;
import nz.co.twg.erpfisuppliers.common.handler.SupplierRetryException;
import nz.co.twg.erpfisuppliers.common.Constants;
import nz.co.twg.erpfisuppliers.sftpservice.ISftpCoupaService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;

@Slf4j
@Component
public class  SftpCoupaServiceImpl implements ISftpCoupaService {

    @Value("${spring.application.sftpHost}")
    private String sftpHost;

    @Value("${spring.application.userName}")
    private String userName;

    @Value("${spring.application.sftpCoupaPassword}")
    private String sftpCoupaPassword;

    @Value("${spring.application.sftpPort}")
    private Integer sftpPort;

    @Value("${spring.application.remoteDir}")
    private String remoteDir;

    @Value("${spring.application.privateKey}")
    private String privateKey;

    @Value("${spring.application.sftpCoupaFile}")
    private String sftpCoupaFile;

    @Value("${spring.application.coupaDateFormat}")
    private String coupaDateFormat;

    private  static int  COUNTER = 0;

    SftpCoupaServiceImpl(){}

    public SftpCoupaServiceImpl(String sftpHost, String userName,
    		Integer sftpPort, String remoteDir, String privateKey,
    		String sftpCoupaFile, String coupaDateFormat){
        this.sftpHost = sftpHost;
        this.userName = userName;
        this.sftpCoupaPassword = privateKey;
        this.sftpPort = sftpPort;
        this.remoteDir = remoteDir;
        this.privateKey = privateKey;
        this.sftpCoupaFile= sftpCoupaFile;
        this.coupaDateFormat= coupaDateFormat;
    }

    public boolean uploadCSVFile(ByteArrayOutputStream stream)  {
        log.info("Uploading CSV file to Coupa SFTP Server.");
        Session session = null;
        Channel channel = null;
        ChannelSftp channelSftp = null;
        JSch jSch = null;

        try {
            if (stream == null){
                log.info("Byte Array doesn't contain any data");
                throw new SupplierException( "Byte Array doesn't contain any data");
            }
        	DateTimeFormatter formatter = DateTimeFormatter.ofPattern(coupaDateFormat);
            ZoneId zone = ZoneId.of(Constants.ZONE_NAME);
            byte[] bytes = stream.toByteArray();
            InputStream inputStream = new ByteArrayInputStream(bytes);

            jSch = new JSch();
            session = jSch.getSession(userName, sftpHost, sftpPort);
            session.setPassword(sftpCoupaPassword);
            java.util.Properties config = new java.util.Properties();
            config.put("StrictHostKeyChecking", "no");
            session.setConfig(config);
            session.connect();
            if(!session.isConnected()) throw new SupplierRetryException( "Session or Host is not connected");
            log.info("Host connected "+sftpHost);
            channel = session.openChannel("sftp");
            channel.connect();
            if(!channel.isConnected()) throw new SupplierRetryException( "Channel is not opened or connected");
            log.info("sftp channel opened and connected.");
            channelSftp = (ChannelSftp) channel;
            channelSftp.cd(remoteDir);
            channelSftp.put(inputStream, formatter.format(ZonedDateTime.now(zone))  + sftpCoupaFile, ChannelSftp.OVERWRITE);
            log.info("File Uploaded Successfully to HOST :" + sftpHost + " to DIR: " + remoteDir);
        } catch (JSchException jSchException) {
            if(jSchException.getMessage().equals("Auth fail")){
            	log.error("Retry, Counter : " + COUNTER);
                log.error("Auth fail exception  occured in uploadCSVFile():: SftpCoupaServiceImpl.", jSchException);
                throw new SupplierRetryException("Auth fail exception  occured in uploadCSVFile():: SftpCoupaServiceImpl." + jSchException.getMessage());
            }else{
            	log.error("Retry, Counter : " + COUNTER);
                log.error("InvalidHostName exception occured in uploadCSVFile():: SftpCoupaServiceImpl.", jSchException);
                throw new SupplierRetryException("InvalidHostName exception occured in uploadCSVFile():: SftpCoupaServiceImpl " + jSchException.getMessage());
            }
        } catch (SupplierRetryException supplierRetryException) {
        	log.error("Retry, Counter : " + COUNTER);
            log.error("SupplierRetryException in uploadCSVFile():: SftpCoupaServiceImpl.", supplierRetryException);
            throw new SupplierRetryException( "SupplierRetryException in uploadCSVFile():: SftpCoupaServiceImpl " + supplierRetryException.getMessage());
        } catch (Exception exception) {
            log.error("Invalid Folder or some Generic Exception occured in uploadCSVFile():: SftpCoupaServiceImpl.", exception);
            throw new SupplierException( "Invalid Folder or some Generic Exception occured in uploadCSVFile():: SftpCoupaServiceImpl " + exception.getMessage());
        } finally {
            if(channelSftp != null) {
                channelSftp.exit();
                log.info("sftp Channel exited.");
            }
            if(channel != null) {
                channel.disconnect();
                log.info("Channel disconnected.");
            }
            if(session != null) {
                session.disconnect();
                log.info("Host Session disconnected.");
            }
        }
        return true;
    }

    @Retryable(value = SupplierRetryException.class,
    maxAttemptsExpression = "${spring.application.maxAttempts}",
    backoff = @Backoff(delayExpression = "${spring.application.delayRetry}"))
    public String uploadCSVFileRetry(ByteArrayOutputStream stream) throws SupplierRetryException {
        COUNTER++;
        log.info("Upload CSV File Retry, Counter : " + COUNTER );
        if (uploadCSVFile(stream)) {
        	log.info("SFTP Server Connected, Counter : " +  COUNTER);
            COUNTER=0;
            return "Success";
        }
        log.info("SFTP Server not  Connected, Counter: " +  COUNTER);
        return "Fails";
     }

     @Recover
     public String sftpRecover(SupplierException supplierException) {
    	COUNTER=0;
    	if(true) {
            log.error("SupplierException in sftpRecover():: SftpCoupaServiceImpl ", supplierException);
            throw new SupplierException( "SupplierException in sftpRecover():: SftpCoupaServiceImpl " + supplierException.getMessage());
    	}
        return "fails";
    }

}
