package kelompok1.KedaiIceCream.util;

import org.apache.commons.io.FilenameUtils;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.ServletContext;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileUploadUtil {
    public static void saveFile(String uploadDir, String fileName, MultipartFile multipartFile) throws IOException {
        Path uploadPath = Paths.get(uploadDir);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
            System.out.println("Created upload directory: " + uploadPath);
        }
    
        try {
            multipartFile.transferTo(new File(uploadPath.toString() + File.separator + fileName));
            System.out.println("File saved: " + uploadPath.toString() + File.separator + fileName);
        } catch (IOException e) {
            throw new IOException("Could not save file: " + fileName, e);
        }
    }

    public static String generateUniqueFileName(String originalFilename) {
        String ext = FilenameUtils.getExtension(originalFilename);
        long timestamp = System.currentTimeMillis();
        return timestamp + "." + ext;
    }
}