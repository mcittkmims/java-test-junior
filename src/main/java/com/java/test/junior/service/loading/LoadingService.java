package com.java.test.junior.service.loading;

import com.java.test.junior.exception.loading.EmptyFileException;
import com.java.test.junior.exception.loading.InvalidFileStructureException;
import com.java.test.junior.exception.loading.LoadingFileNotFound;
import com.java.test.junior.mapper.loading.LoadingMapper;
import com.java.test.junior.model.loading.FileSourceDTO;
import com.java.test.junior.model.user.UserPrincipal;
import lombok.AllArgsConstructor;
import org.postgresql.PGConnection;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

@Service
@AllArgsConstructor
public class LoadingService {

    private LoadingMapper loadingMapper;

    public void loadProducts(FileSourceDTO fileSourceDTO, UserPrincipal userPrincipal) {
        loadingMapper.copyFromFile(fileSourceDTO.getFileAddress(), userPrincipal.getUserId());
    }
}
