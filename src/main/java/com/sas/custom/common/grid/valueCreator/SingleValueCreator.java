package com.sas.custom.common.grid.valueCreator;

import com.sas.analytics.ph.common.RTDMTable;
import com.sas.custom.common.grid.ValueCreator;

/**
 * Created by y333325 on 2016-06-23.
 */
public class SingleValueCreator implements ValueCreator {
    private Object value;

    public SingleValueCreator(Object value)
    {
        this.value = value;
    }

    @Override
    public Object createValue(RTDMTable.Row row, int rowNum) {
        return value;
    }
}
