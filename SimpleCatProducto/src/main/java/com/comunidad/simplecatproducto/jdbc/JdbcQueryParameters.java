package com.comunidad.simplecatproducto.jdbc;

import java.util.Properties;

public class JdbcQueryParameters {

    private String sqlBase;
    private String sqlLast;
    private Properties optionalParameters;
    private Properties optionalOrder;

    private String sqlCount;
    private String sqlAllInit;
    private String sqlMain;
    private String sqlFrom;
    private String sqlAllEnd;
    private String sqlOrderDefault;

    public String getSqlBase() {
        return sqlBase;
    }

    public void setSqlBase(String sqlBase) {
        this.sqlBase = sqlBase;
    }

    public Properties getOptionalParameters() {
        return optionalParameters;
    }

    public void setOptionalParameters(Properties optionalParameters) {
        this.optionalParameters = optionalParameters;
    }

    public String getSqlLast() {
        return sqlLast;
    }

    public void setSqlLast(String sqlLast) {
        this.sqlLast = sqlLast;
    }

    public final String getSqlCount() {
        return sqlCount;
    }

    public final void setSqlCount(String sqlCount) {
        this.sqlCount = sqlCount;
    }

    public final String getSqlAllInit() {
        return sqlAllInit;
    }

    public final void setSqlAllInit(String sqlAllInit) {
        this.sqlAllInit = sqlAllInit;
    }

    public final String getSqlMain() {
        return sqlMain;
    }

    public final void setSqlMain(String sqlMain) {
        this.sqlMain = sqlMain;
    }

    public final String getSqlFrom() {
        return sqlFrom;
    }

    public final void setSqlFrom(String sqlFrom) {
        this.sqlFrom = sqlFrom;
    }

    public final String getSqlAllEnd() {
        return sqlAllEnd;
    }

    public final void setSqlAllEnd(String sqlAllEnd) {
        this.sqlAllEnd = sqlAllEnd;
    }

    public final String getSqlOrderDefault() {
        return sqlOrderDefault;
    }

    public final void setSqlOrderDefault(String sqlOrderDefault) {
        this.sqlOrderDefault = sqlOrderDefault;
    }

    public final Properties getOptionalOrder() {
        return optionalOrder;
    }

    public final void setOptionalOrder(Properties optionalOrder) {
        this.optionalOrder = optionalOrder;
    }

}
