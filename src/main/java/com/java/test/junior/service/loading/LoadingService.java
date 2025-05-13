package com.java.test.junior.service.loading;

import com.java.test.junior.exception.loading.InvalidFileStructure;
import com.java.test.junior.exception.loading.LoadingFileNotFound;
import com.java.test.junior.mapper.loading.LoadingMapper;
import com.java.test.junior.model.loading.FileSourceDTO;
import com.java.test.junior.model.user.UserPrincipal;
import lombok.AllArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@AllArgsConstructor
public class LoadingService {

    LoadingMapper loadingMapper;
    ResourceLoader resourceLoader;

    public void loadProducts(FileSourceDTO fileSourceDTO, UserPrincipal userPrincipal) {
        Resource resource = resourceLoader.getResource("classpath:" + fileSourceDTO.getFileAddress());
        try {
            String absolutePath = resource.getURL().getPath();
            int rowsChanged = loadingMapper.loadProducts(absolutePath, userPrincipal.getUserId());
            System.out.println(rowsChanged);
            if (rowsChanged == 0){
                throw new InvalidFileStructure("Empty or invalid file content");
            }
        } catch (IOException e) {
            throw new LoadingFileNotFound("No such file at specified location.");
        }
    }

}
