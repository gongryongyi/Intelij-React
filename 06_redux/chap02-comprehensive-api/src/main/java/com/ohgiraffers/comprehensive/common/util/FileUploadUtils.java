package com.ohgiraffers.comprehensive.common.util;

import com.ohgiraffers.comprehensive.common.exception.ServerInternalException;
import org.apache.commons.io.FilenameUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import static com.ohgiraffers.comprehensive.common.exception.type.ExceptionCode.ExceptionCode.FAIL_TO_DELETE_FILE;
import static com.ohgiraffers.comprehensive.common.exception.type.ExceptionCode.ExceptionCode.FAIL_TO_UPLOAD_FILE;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

public class FileUploadUtils {

    public static String saveFile(String uploadDir, String fileName, MultipartFile multipartFile){
        //  MultipartFile multipartFile 값을 String uploadDir, String fileName 여기다가 저장할꺼임

        try(InputStream inputStream = multipartFile.getInputStream()){

            Path uploadPath = Paths.get(uploadDir);
            /* 업로드 경로가 존재하지 않을 시 경로 먼저 생성 */
            if (!Files.exists(uploadPath))
                Files.createDirectories(uploadPath);

            /* 파일명 생성 */
            String replaceFileName = fileName + "." + FilenameUtils.getExtension(multipartFile.getOriginalFilename());
            //FilenameUtils.getExtension 오리지널 파일 이름에서 뒤에 .jpng, .png 처럼 확장자를 뺴주는 역할이아

            /* 파일 저장 */
            Path filePath = uploadPath.resolve(replaceFileName);  //경로와 파일명을 합쳐서 filePath 변수에 넣은거임
            Files.copy(inputStream, filePath, REPLACE_EXISTING);

            return replaceFileName;
        }catch (IOException e){
             throw new ServerInternalException(FAIL_TO_UPLOAD_FILE);
        }


    }

    public static void deleteFile(String uploadDir, String fileName){
        try {
            Path uploadPath = Paths.get(uploadDir);
            Path filePath = uploadPath.resolve(fileName);
            Files.delete(filePath);
        } catch (IOException e) {
            throw new ServerInternalException(FAIL_TO_DELETE_FILE);
        }
    }
}
