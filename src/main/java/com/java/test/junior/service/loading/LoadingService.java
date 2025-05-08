package com.java.test.junior.service.loading;

import com.java.test.junior.exception.loading.EmptyFileException;
import com.java.test.junior.exception.loading.InvalidFileStructureException;
import com.java.test.junior.exception.loading.LoadingFileNotFound;
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

    private final javax.sql.DataSource dataSource;

    public void loadProducts(FileSourceDTO fileSourceDTO, UserPrincipal userPrincipal) {
        try (Connection connection = dataSource.getConnection();
            BufferedReader reader = new BufferedReader(new FileReader(new ClassPathResource(fileSourceDTO.getFileAddress()).getFile()))) {
            connection.setAutoCommit(false);

            PGConnection pgConnection = connection.unwrap(PGConnection.class);
            Statement statement = connection.createStatement();
            statement.execute("CREATE TEMP TABLE temp_product (name TEXT, price NUMERIC, description TEXT) ON COMMIT DROP");

            try {
                pgConnection.getCopyAPI().copyIn(
                        "COPY temp_product (name, price, description) FROM STDIN WITH CSV HEADER",
                        reader
                );
            } catch (SQLException e) {
                throw new InvalidFileStructureException("The file structure is invalid or does not match the expected format.");
            }

            String insertQuery = "INSERT INTO product (name, price, description, user_id) " +
                                 "SELECT name, price, description, " + userPrincipal.getUserId() + " FROM temp_product";
            int rowsChanged = statement.executeUpdate(insertQuery);

            if (rowsChanged == 0) {
                throw new EmptyFileException("Empty file content!");
            }
            connection.commit();
        } catch (IOException e) {
            throw new LoadingFileNotFound("No such file at specified location.");
        } catch (SQLException e) {
            throw new RuntimeException("Database error occurred", e);
        }
    }
}
