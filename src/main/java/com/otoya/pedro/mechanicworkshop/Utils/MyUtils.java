package com.otoya.pedro.mechanicworkshop.Utils;

import java.util.Collection;
import java.util.Iterator;
import java.util.StringJoiner;

public class MyUtils {
    public static String stringify(Collection collection) {
        Iterator iter = collection.iterator();
        StringJoiner sj = new StringJoiner(",");
        while (iter.hasNext()) {
            sj.add(String.valueOf(iter.next()));
        }
        return new StringBuilder("[")
                .append(sj.toString())
                .append("]")
                .toString();
    }
}
