package com.sas.custom.common.grid;

import com.sas.analytics.ph.common.RTDMTable;

public interface ValueCreator {
    Object createValue(RTDMTable.Row row, int rowNum);
}
