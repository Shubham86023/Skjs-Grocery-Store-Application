package com.skjsgrocerystore.productservice.Services.Implementations;

import com.skjsgrocerystore.productservice.DAO.ImagesRepository;
import com.skjsgrocerystore.productservice.DTO.Images.ImagesResponse;
import com.skjsgrocerystore.productservice.Entities.Images;
import com.skjsgrocerystore.productservice.Services.Images_ops;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

@Service
public class ImagesImpl implements Images_ops {

    private final ImagesRepository imagesRepo;

    @Value("${images.directory}")
    private String directory;

    public ImagesImpl(ImagesRepository imagesRepo) {
        this.imagesRepo = imagesRepo;
    }

    @Override
    public ImagesResponse getImagesDB(int productId) {

        Images image = imagesRepo.findByProId(productId);

        ImagesResponse response = null;
        if (image != null) {
            response = ImagesResponse.builder()
                    .id(image.getId())
                    .proId(image.getProId())
                    .mainImage(image.getMainImage())
                    .image2(image.getImage2())
                    .image3(image.getImage3())
                    .image4(image.getImage4())
                    .image5(image.getImage5()).build();
        }
        return response;
    }

    @Override
    public Integer saveImagesToDB(ImagesResponse imageData) {

        if (imageData != null) {
            Images images = Images.builder()
                    .id(imageData.getId())
                    .proId(imageData.getProId())
                    .mainImage(imageData.getMainImage())
                    .image2(imageData.getImage2())
                    .image3(imageData.getImage3())
                    .image4(imageData.getImage4())
                    .image5(imageData.getImage5()).build();

            imagesRepo.save(images);
            return 1;
        }
        return -1;

    }

    @Override
    public void saveImagesToFolder(List<MultipartFile> files, int proId) throws IOException {

        // setting path
        String uploadDir = directory + proId;
        Path uploadPath = Paths.get(uploadDir);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        // looping
        int i = 1;
        for (MultipartFile f : files) {
            if (!f.isEmpty() && f.getOriginalFilename() != null) {
                File folder = new File(directory + proId);
                File[] listOfFiles = folder.listFiles();
                if (listOfFiles != null) {
                    for (File old_file : listOfFiles) {
                        String old_name = old_file.getName().substring(0, old_file.getName().lastIndexOf("."));
                        if (old_name.equals(String.valueOf(i))) {
                            // delete old file,
                            deleteImageFromFolder(proId, old_file.getName());
                        }
                    }
                }

                String fileName = i + ".";
                String onefile = StringUtils.cleanPath(f.getOriginalFilename());
                int j = onefile.lastIndexOf('.');
                if (j > 0) {
                    fileName += onefile.substring(j + 1);
                }
                try (InputStream inputStream = f.getInputStream()) {
                    Path filePath = uploadPath.resolve(fileName);
                    Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
                } catch (IOException ioe) {
                    throw new IOException("Could not save image file: " + onefile, ioe);
                }
                i++;
            }
        }

    }

    private boolean deleteImageFromFolder(int pro, String old_image_name) {
        String filePath = directory + pro + "/" + old_image_name;
        File fileToDelete = new File(filePath);
        if (fileToDelete.exists()) {
            fileToDelete.delete();
        }
        return true;
    }

    @Override
    public boolean updateImages(int proId, String imageName, Images images) {
        try {
            imagesRepo.save(images);
            return deleteImageFromFolder(proId, imageName);
        } catch (Exception ex) {
            System.out.println("Images not updated :- updateImages");
            System.out.println(ex);
            return false;
        }
    }

}
