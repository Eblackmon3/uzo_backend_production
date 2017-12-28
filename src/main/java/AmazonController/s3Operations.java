package AmazonController;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import Model.Student;
import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;


@Configuration
@PropertySource("classpath:application.properties")
public class s3Operations {

    @Autowired
    private static Environment env;
    private static String accessKeyId=System.getenv("S3_KEY");
    private static String accessSecret=System.getenv("S3_SECRET");
    private static String bucketName = System.getenv("S3_BUCKET");
    private static String region=System.getenv("REGION");
    private static AWSCredentials credentials = new BasicAWSCredentials(accessKeyId, accessSecret);

    private static AmazonS3 s3client = new AmazonS3Client(credentials);

    public static void uploadResume(String resume) throws IOException {


    }

    //use this method to create a new folder on our s3 bucket to store students resumes
    public static  JSONObject createFolder(Student student) {
        JSONObject ret=new JSONObject();
        try {
            // create meta-data for your folder and set content-length to 0
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentLength(0);
            // create empty content
            InputStream emptyContent = new ByteArrayInputStream(new byte[0]);
            // create a PutObjectRequest passing the folder name suffixed by /
            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName,
                    "" + student.getStudent_id()+"/", emptyContent, metadata);
            // send request to S3 to create folder
            PutObjectResult result = s3client.putObject(putObjectRequest);
            ret.put("Student:" + student.getStudent_id(), "Folder created");
            ret.put("Put result", result.toString());
        }catch(Exception e){
            e.printStackTrace();
        }
        return ret;
    }

    public static JSONObject uploadFile(int studentID, MultipartFile file){
        String fileName =  studentID+"/Resume";
        JSONObject ret=new JSONObject();
        try {
            // create a PutObjectRequest passing the folder name suffixed by /
            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, fileName,
                    multipartToFile(file));
            // send request to S3 to create folder
            PutObjectResult result = s3client.putObject(putObjectRequest);
            ret.put("Student:" + studentID, "Resume added to Folder");
            ret.put("Put result", result.toString());
        }catch(Exception e){
            e.printStackTrace();
        }
        return ret;

    }

    public static File multipartToFile(MultipartFile multipart) throws IllegalStateException, IOException
    {
        File convFile = new File(multipart.getOriginalFilename());
        System.out.println(convFile.length());
        multipart.transferTo(convFile);
        return convFile;
    }
}
