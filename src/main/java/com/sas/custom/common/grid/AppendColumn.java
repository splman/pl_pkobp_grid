package com.sas.custom.common.grid;

import com.sas.analytics.ph.common.jaxb.DataTypes;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class AppendColumn {
    private final String name;
    private final DataTypes type;
    private final ValueCreator valueCreator;

    public AppendColumn(String name, DataTypes type, ValueCreator valueCreator) {
        this.name = name;
        this.type = type;
        this.valueCreator = valueCreator;
    }

    public static List<AppendColumn> createList(List<String> names, List<DataTypes> types, List<ValueCreator> values)
    {
        List<AppendColumn> result = new LinkedList<>();
        Iterator<String> namesIterator = names.iterator();
        Iterator<DataTypes> typesIterator = types.iterator();
        Iterator<ValueCreator> valuesIterator = values.iterator();
        while(namesIterator.hasNext())
            result.add(new AppendColumn(namesIterator.next(), typesIterator.next(), valuesIterator.next()));
        return result;
    }

    public String getName() {
        return name;
    }

    public DataTypes getType() {
        return type;
    }

    public ValueCreator getValueCreator() {
        return valueCreator;
    }
}
