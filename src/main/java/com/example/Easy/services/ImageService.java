package com.example.Easy.services;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ImageService {
    private final static String DOWNLOAD_URL ="https://firebasestorage.googleapis.com/v0/b/easy-newss.appspot.com/o/%s?alt=media&";

    //-----Upload image to firebase storage-----
    public String uploadImage(MultipartFile file) throws IOException {
        String imageName = generateFileName(file.getOriginalFilename());
        BlobId blobId = BlobId.of("easy-newss.appspot.com", imageName);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId)
                .setContentType(file.getContentType())
                .build();
        Storage storage = StorageOptions.newBuilder()
                .setCredentials(GoogleCredentials.fromStream(new ClassPathResource("serviceAccountKey.json").getInputStream()))
                .build().getService();

        storage.create(blobInfo, file.getInputStream());
        return String.format(DOWNLOAD_URL,imageName);
    }
    private String generateFileName(String originalFileName) {
        return UUID.randomUUID().toString() + "." + getExtension(originalFileName);
    }
    private String getExtension(String originalFileName) {
        return StringUtils.getFilenameExtension(originalFileName);
    }
}
