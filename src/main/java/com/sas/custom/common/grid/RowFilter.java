package com.sas.custom.common.grid;


        import com.sas.analytics.ph.common.RTDMTable;

public interface RowFilter {
    /*
        returns true if filter should not remove a row
        otherwise returns false
     */
    boolean shouldBeUsed(RTDMTable.Row row);
}
