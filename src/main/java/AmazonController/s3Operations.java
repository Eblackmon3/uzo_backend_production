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

public class s3Operations {
    private static String bucketName = "${uzo-s3-bucket}";
    private static AWSCredentials credentials = new BasicAWSCredentials("${jsa.aws.access_key_id}", "${jsa.aws.secret_access_key}");

    private static AmazonS3 s3client = new AmazonS3Client(credentials);

    public static void uploadResume(String resume) throws IOException {


    }

    //use this method to create a new folder on our s3 bucket to store students resumes
    public static  JSONObject createFolder(Student student) {
        for (Bucket bucket : s3client.listBuckets()) {
            System.out.println(" - " + bucket.getName());
        }
        JSONObject ret=new JSONObject();
        try {
            // create meta-data for your folder and set content-length to 0
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentLength(0);
            // create empty content
            InputStream emptyContent = new ByteArrayInputStream(new byte[0]);
            // create a PutObjectRequest passing the folder name suffixed by /
            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName,
                    "" + student.getStudent_id(), emptyContent, metadata);
            // send request to S3 to create folder
            PutObjectResult result = s3client.putObject(putObjectRequest);
            ret.put("Student:" + student.getStudent_id(), "Folder created");
            ret.put("Put result", result.toString());
        }catch(Exception e){
            e.printStackTrace();
        }
        return ret;
    }
}