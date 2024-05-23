package ru.netology.data;

import lombok.SneakyThrows;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLHelper {

    private SQLHelper() {
    }

    // метод который умеет возвращать подключение к БД
    @SneakyThrows
    private static Connection getConn() {
        return DriverManager.getConnection(System.getProperty("db.url"), "app", "pass");
    }

    @SneakyThrows
    public static void deleteTable() {
        var deletePaymentEntity = "DELETE FROM payment_entity ";
        var runner = new QueryRunner();
        try (var conn = getConn()) {
            runner.update(conn, deletePaymentEntity);
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }

    @SneakyThrows
    public static String getPaymentStatus() {
        String statusSQL = "SELECT status FROM payment_entity";
        return getStatus(statusSQL);
    }

    @SneakyThrows
    private static String getStatus(String query) {
        var runner = new QueryRunner();
        try (var conn = getConn()) {
            String status = runner.query(conn, query, new ScalarHandler<String>());
            return status;
        }
    }
}