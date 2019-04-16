package com.sas.custom.common.grid;

import com.sas.analytics.ph.common.jaxb.DataTypes;

/**
 * Created by Y344104 on 2016-08-24.
 */
public class GridColumnDefinition {
    private String name;
    private DataTypes type;
    private boolean coalesce;

    public GridColumnDefinition(String name, DataTypes type) {
        this(name, type, false);
    }

    public GridColumnDefinition(String name, DataTypes type, boolean coalesce) {
        this.name = name;
        this.type = type;
        this.coalesce = coalesce;
    }

    public String getName() {
        return name;
    }

    public DataTypes getType() {
        return type;
    }

    public boolean isCoalesce() {
        return coalesce;
    }

    @Override
    public String toString() {
        return "GridColumnDefinition{" +
                "name='" + name + '\'' +
                ", type=" + type +
                '}';
    }
}
