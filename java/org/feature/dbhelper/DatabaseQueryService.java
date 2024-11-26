package org.feature.dbhelper;

import org.feature.prefs.Prefs;
import org.feature.query_into_object.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DatabaseQueryService {
    private final Database database;

    public DatabaseQueryService(Database database) {
        this.database = database;
    }

    public List<MaxProjectCountClient> findMaxProjectsClient(){
        CreateAnObject<MaxProjectCountClient> executedRequestIntoObject = rs ->
            new MaxProjectCountClient(
                    rs.getString("name"),
                    rs.getInt("project_count")
            );
        ExecuteQuery<MaxProjectCountClient> executeQuery = new ExecuteQuery<>();
        return executeQuery.getListResultQuery(
                Prefs.FIND_MAX_PROJECTS_CLIENT_FILE_PATH,
                executedRequestIntoObject
        );
    }

    public List<MaxSalaryWorker> findMaxSalaryWorker(){
        CreateAnObject<MaxSalaryWorker> executedRequestIntoObject = rs ->
                new MaxSalaryWorker(
                    rs.getString("name"),
                    rs.getInt("salary")
            );
        ExecuteQuery<MaxSalaryWorker> executeQuery = new ExecuteQuery<>();
        return executeQuery.getListResultQuery(
                Prefs.FIND_MAX_SALARY_WORKER_FILE_PATH,
                executedRequestIntoObject
        );
    }

    public List<LongestProject> findLongestProject(){
        CreateAnObject<LongestProject> executedRequestIntoObject = rs ->
                new LongestProject(
                    rs.getString("name"),
                    rs.getInt("monthCount")
            );
        ExecuteQuery<LongestProject> executeQuery = new ExecuteQuery<>();
        return executeQuery.getListResultQuery(
                Prefs.FIND_LONGEST_PROJECT_FILE_PATH,
                executedRequestIntoObject
        );
    }

    public List<YoungestEldestWorkers> findYoungestEldestWorkers(){
        CreateAnObject<YoungestEldestWorkers> executedRequestIntoObject = rs->
                new YoungestEldestWorkers(
                    rs.getString("type"),
                    rs.getString("name"),
                    rs.getString("birthday")
            );
        ExecuteQuery<YoungestEldestWorkers> executeQuery = new ExecuteQuery<>();
        return executeQuery.getListResultQuery(
                Prefs.FIND_YOUNGEST_ELDEST_WORKERS_FILE_PATH,
                executedRequestIntoObject
        );
    }

    public List<ProjectPrices> printProjectPrices(){
        CreateAnObject<ProjectPrices> executedRequestIntoObject = rs->
                new ProjectPrices(
                        rs.getString("name"),
                        rs.getInt("price")
                );
        ExecuteQuery<ProjectPrices> executeQuery = new ExecuteQuery<>();
        return executeQuery.getListResultQuery(
                Prefs.PRINT_PROJECT_PRICES_FILE_PATH,
                executedRequestIntoObject
        );
    }

    private class ExecuteQuery<T>{
        public List<T> getListResultQuery(String filePath, CreateAnObject<T> createAnObject){
            String sql = getQueryWithFile(filePath);
            if (sql.isEmpty()) return new ArrayList<>();
            return getConnectionDB(sql, createAnObject);
        }
        private String getQueryWithFile(String filePath){
            String initDbFilename = new Prefs().getString(filePath);
            try {
                return String.join(
                        "\n",
                        Files.readAllLines(Paths.get(initDbFilename))
                );
            } catch (IOException e) {
                e.printStackTrace();
            }
            return "";
        }

        private List<T> getConnectionDB(String sql, CreateAnObject<T> createAnObject){
            try(Statement st = database.getConnection().createStatement()){
                return executeQuery(st, sql, createAnObject);
            }catch (Exception e){
                e.printStackTrace();
            }
            return new ArrayList<>();
        }

        private List<T> executeQuery(Statement st, String sql, CreateAnObject<T> createAnObject){
            List<T> listResult = new ArrayList<>();
            try(ResultSet rs = st.executeQuery(sql)) {
                while (rs.next()) {
                    listResult.add(createAnObject.executedRequestIntoObject(rs));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return listResult;
        }
    }
}
