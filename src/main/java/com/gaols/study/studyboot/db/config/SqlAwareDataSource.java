package com.gaols.study.studyboot.db.config;

import com.jfinal.plugin.activerecord.SqlReporter;

import javax.sql.DataSource;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.logging.Logger;

public class SqlAwareDataSource implements DataSource {

    private DataSource ds;
    private boolean showSql;

    public SqlAwareDataSource(DataSource dataSource) {
        this(dataSource, false);
    }

    public SqlAwareDataSource(DataSource dataSource, boolean showSql) {
        this.ds = dataSource;
        this.showSql = showSql;
    }

    @Override
    public Connection getConnection() throws SQLException {
        return showSql ? new SqlReporter(ds.getConnection()).getConnection() : ds.getConnection();
    }

    @Override
    public Connection getConnection(String username, String password) throws SQLException {
        return showSql ? new SqlReporter(ds.getConnection(username, password)).getConnection() : ds.getConnection(username, password);
    }

    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        return ds.unwrap(iface);
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        return ds.isWrapperFor(iface);
    }

    @Override
    public PrintWriter getLogWriter() throws SQLException {
        return ds.getLogWriter();
    }

    @Override
    public void setLogWriter(PrintWriter out) throws SQLException {
        ds.setLogWriter(out);
    }

    @Override
    public void setLoginTimeout(int seconds) throws SQLException {
        ds.setLoginTimeout(seconds);
    }

    @Override
    public int getLoginTimeout() throws SQLException {
        return ds.getLoginTimeout();
    }

    @Override
    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        return ds.getParentLogger();
    }
}
