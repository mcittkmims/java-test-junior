package com.java.test.junior.interceptor;

import com.java.test.junior.exception.loading.EmptyFileException;
import com.java.test.junior.exception.loading.InvalidFileStructureException;
import com.java.test.junior.exception.loading.LoadingFileNotFound;
import lombok.AllArgsConstructor;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;
import org.postgresql.PGConnection;
import org.springframework.core.io.ClassPathResource;

import javax.sql.DataSource;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;
import java.util.Properties;


@Intercepts({
        @Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class})
})
@AllArgsConstructor
public class CopyInterceptor implements Interceptor {


    private final DataSource dataSource;

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        MappedStatement ms = (MappedStatement) invocation.getArgs()[0];
        Object parameter = invocation.getArgs()[1];

        String methodId = ms.getId();

        if (methodId.endsWith("copyFromFile")) {
            Map<?, ?> paramMap = (Map<?, ?>) parameter;
            String fileAddress = (String) paramMap.get("fileAddress");
            Long userId = (Long) paramMap.get("id");

            performCopyOperation(fileAddress, userId);
        }

        return invocation.proceed();
    }

    private void performCopyOperation(String fileAddress, Long userId) {
        try (Connection connection = dataSource.getConnection();
             BufferedReader reader = new BufferedReader(new FileReader(new ClassPathResource(fileAddress).getFile()))) {
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
                    "SELECT name, price, description, " + userId + " FROM temp_product";
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

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {
    }
}
