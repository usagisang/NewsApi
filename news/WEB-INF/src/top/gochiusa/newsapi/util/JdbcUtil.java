package top.gochiusa.newsapi.util;


public final class JdbcUtil {
    public static String createLimitSql(String table, int startIndex, int limit, String column) {
        // 注意，此语句LIMIT范围是 (m+2, n)
        return "SELECT * FROM " +
                table + " WHERE " + column +
                " > (SELECT " + column + " FROM " +
                table + " ORDER BY " + column + " LIMIT " +
                startIndex + ", 1) LIMIT " + limit;
    }

    public static String createLimitSqlDesc(String table, int startIndex, int limit, String column) {
        // 注意，此语句LIMIT范围是 (m+2, n)
        return "SELECT * FROM " + table + " WHERE " + column +
                " < (SELECT " + column + " FROM " + table + " ORDER BY " + column + " DESC LIMIT " +
                startIndex + ", 1) ORDER BY " + column + " DESC LIMIT " + limit;
    }
}
