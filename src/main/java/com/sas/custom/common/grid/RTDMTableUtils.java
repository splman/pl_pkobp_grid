package com.sas.custom.common.grid;

import com.sas.analytics.ph.common.RTDMTable;
import com.sas.analytics.ph.common.jaxb.DataTypes;

import java.util.*;

public class RTDMTableUtils {
    public static List<RTDMTable.Row> filterRows(RTDMTable inputGrid, List<RowFilter> rowFilterList)
    {
        List<RTDMTable.Row> rowsToSave = new LinkedList<>();
        Iterator<RTDMTable.Row> iterator = inputGrid.iterator();
        outer: while(iterator.hasNext()) {
            RTDMTable.Row row = iterator.next();
            for(RowFilter rowFilter : rowFilterList)
                if(!rowFilter.shouldBeUsed(row)) {
                    continue outer;
                }
            rowsToSave.add(row);
        }
        return rowsToSave;
    }

    public static RTDMTable.Row filterOneRow(RTDMTable table, String field, Object value) {
        return filterOneRow(table, field, value, false);
    }

    public static RTDMTable.Row filterOneRow(RTDMTable table, String field, Object value, boolean optional) {
        List<RTDMTable.Row> rows = RTDMTableUtils.filterRows(table, Arrays.asList(RTDMTableUtils.createFieldEqualsFilter(field, value)));
        if(!optional && rows.size() > 1)
            throw new RuntimeException("Expected max 1 row, got: " + rows.size());
        return rows.isEmpty() ? null : rows.get(0);
    }

    public static RowFilter createFieldEqualsFilter(final String field, final Object value)
    {
        return new RowFilter(){
            @Override
            public boolean shouldBeUsed(RTDMTable.Row row) {
                return row.columnDataGet(field).equals(value);
            }
        };
    }

    public static Object getFirstColumnValue(RTDMTable rtdmTable, String field)
    {
        return getFirstRow(rtdmTable, false).columnDataGet(field);
    }

    public static RowFilter createFieldNullFilter (final String field){
        return new RowFilter(){
            @Override
            public boolean shouldBeUsed(RTDMTable.Row row) {
                return row.columnDataGet(field) == null;
            }
        };
    }

    public static RowFilter createFieldNotNullFilter (final String field){
        return new RowFilter(){
            @Override
            public boolean shouldBeUsed(RTDMTable.Row row) {
                return row.columnDataGet(field) != null;
            }
        };
    }

    public static RTDMTable.Row getFirstRow(RTDMTable rtdmTable) {
        return getFirstRow(rtdmTable, false);
    }

    public static RTDMTable.Row getFirstRow(RTDMTable rtdmTable, boolean checkIfThereIsOnlyOneRow) {
        if(checkIfThereIsOnlyOneRow && rtdmTable.count() != 1)
            throw new RuntimeException("Input grid should contain exactly 1 row, but it has " + rtdmTable.count() + " rows");
        return rtdmTable.iterator().next();
    }

    public static class RTDMTableColumn
    {
        private DataTypes datatypes;
        private List<? extends Object> values;

        public RTDMTableColumn(DataTypes datatypes, List<? extends Object> values) {
            this.datatypes = datatypes;
            this.values = values;
        }

        public DataTypes getDatatypes() {
            return datatypes;
        }

        public List<? extends Object> getValues() {
            return values;
        }
    }

    public static RTDMTable createTable(Map<String, RTDMTableColumn> values)
    {
        RTDMTable rtdmTable = new RTDMTable();
        for(Map.Entry<String, RTDMTableColumn> entry : values.entrySet())
            if(entry.getValue().getValues() != null && !entry.getValue().getValues().isEmpty())
                rtdmTable.columnAdd(entry.getKey(), entry.getValue().getDatatypes(), entry.getValue().getValues());
        return rtdmTable;
    }

    public static RTDMTable addColumnToGrid(RTDMTable grid, List inputList, String columnName) throws RuntimeException {
        if (inputList == null || inputList.isEmpty())
            throw new RuntimeException("List is empty.");
        if (grid == null || grid.count() == 0)
            throw new RuntimeException("Grid is empty.");
        if (grid.count() != inputList.size())
            throw new RuntimeException("List elements quantity doesn't match the number of rows inside grid.");
        Class type = inputList.get(0).getClass();

        switch (type.getSimpleName()) {
            case "Long":
                grid.columnAdd(columnName, Arrays.copyOf(inputList.toArray(), inputList.size(), Long[].class));
                break;
            case "Double":
                grid.columnAdd(columnName, Arrays.copyOf(inputList.toArray(), inputList.size(), Double[].class));
                break;
            case "Boolean":
                grid.columnAdd(columnName, Arrays.copyOf(inputList.toArray(), inputList.size(), Boolean[].class));
                break;
            case "GregorianCalendar":
                grid.columnAdd(columnName, Arrays.copyOf(inputList.toArray(), inputList.size(), Calendar[].class));
                break;
            case "String":
                grid.columnAdd(columnName, Arrays.copyOf(inputList.toArray(), inputList.size(), String[].class));
                break;
            default:
                throw new RuntimeException("Invalid list type");
        }

        return grid;
    }

    public static RTDMTable addColumnFromScalarToGrid(RTDMTable grid, Object inputValue, String columnName) throws RuntimeException {
        if (inputValue == null)
            throw new RuntimeException("Value is empty.");
        if (grid == null || grid.count() == 0)
            throw new RuntimeException("Grid is empty.");

        Class type = inputValue.getClass();
        Object[] array = new Object[grid.count()];
        Arrays.fill(array, inputValue);

        switch (type.getSimpleName()) {
            case "Long":
                grid.columnAdd(columnName, Arrays.copyOf(array, array.length, Long[].class));
                break;
            case "Double":
                grid.columnAdd(columnName, Arrays.copyOf(array, array.length, Double[].class));
                break;
            case "Boolean":
                grid.columnAdd(columnName, Arrays.copyOf(array, array.length, Boolean[].class));
                break;
            case "GregorianCalendar":
                grid.columnAdd(columnName, Arrays.copyOf(array, array.length, Calendar[].class));
                break;
            case "String":
                grid.columnAdd(columnName, Arrays.copyOf(array, array.length, String[].class));
                break;
            default:
                throw new RuntimeException("Invalid list type");
        }

        return grid;
    }

    public static RTDMTable createEmptyTable(List<GridColumnDefinition> columns) {
        RTDMTable rtdmTable = new RTDMTable();
        for(GridColumnDefinition gcd : columns)
            rtdmTable.columnAdd(gcd.getName(), gcd.getType());
        return rtdmTable;
    }
}
