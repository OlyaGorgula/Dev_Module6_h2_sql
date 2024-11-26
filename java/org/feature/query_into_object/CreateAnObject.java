package org.feature.query_into_object;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface CreateAnObject<T> {
    T executedRequestIntoObject(ResultSet rs) throws SQLException;
}
