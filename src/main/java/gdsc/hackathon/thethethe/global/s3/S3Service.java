package gdsc.hackathon.thethethe.global.s3;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;


@RequiredArgsConstructor
@Service
public class S3Service {

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    private final AmazonS3 amazonS3;


    @Transactional
    public String saveImage(MultipartFile file) {

        if (file.isEmpty()) {
            throw new RuntimeException("파일이 없습니다.");
        }


        //확장자 제한
        String fileName = file.getOriginalFilename();
        String ext = fileName.substring(fileName.lastIndexOf(".") + 1);
        if (!ext.equals("jpeg") && !ext.equals("jpg") && !ext.equals("png")) {
            throw new RuntimeException("업로드가 불가능한 확장자입니다.");
        }

        String imageName = UUID.randomUUID() + file.getOriginalFilename();


        try {
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentType(file.getContentType());
            amazonS3.putObject(new PutObjectRequest(bucket, imageName, file.getInputStream(), metadata)
                    .withCannedAcl(CannedAccessControlList.PublicRead));

            String newImageUrl = amazonS3.getUrl("thethethe-bucket", imageName).toString();

            return newImageUrl;
        } catch (Exception e) {
            throw new RuntimeException("파일을 저장할 수 없습니다.");

        }

    }


    @Transactional
    public String updateImage(MultipartFile file, String oldImageUrl) {

        if (file.isEmpty()) {
            throw new RuntimeException("파일이 없습니다.");
        }

        //확장자 제한
        String fileName = file.getOriginalFilename();
        String ext = fileName.substring(fileName.lastIndexOf(".") + 1);
        if (!ext.equals("jpeg") && !ext.equals("jpg") && !ext.equals("png")) {
            throw new RuntimeException("업로드가 불가능한 확장자입니다.");
        }


        String newImageName = UUID.randomUUID() + file.getOriginalFilename();

        try {
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentType(file.getContentType());

            if (oldImageUrl != "https://plowithmebucket.s3.ap-northeast-2.amazonaws.com/default-image.png" || oldImageUrl != null) {
                amazonS3.deleteObject(bucket, oldImageUrl.split("/")[3]);
            }

            amazonS3.putObject(new PutObjectRequest(bucket, newImageName, file.getInputStream(), metadata)
                    .withCannedAcl(CannedAccessControlList.PublicRead));

            String newImageUrl = amazonS3.getUrl("plowithmebucket", newImageName).toString();


            return newImageUrl;
        } catch (IOException e) {
            throw new RuntimeException("파일을 수정할 수 없습니다.");

        }
    }


    @Transactional
    public String deleteImage(String imageUrl) {
        try {

            amazonS3.deleteObject(bucket, imageUrl.split("/")[3]);
            return null;
        } catch (AmazonServiceException e) {
            throw new RuntimeException("파일을 삭제할 수 없습니다.");

        }
    }
}
