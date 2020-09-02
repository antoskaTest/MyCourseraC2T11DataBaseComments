package com.courseraandroid.myfirstappcoursera.comments;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class CompareComments {
    /**
     * Сравиннение двух коллекций
     */
    public static <T> boolean isCollectionEquals(Collection<T> first, Collection<T> second) {
        if (first == second || (first == null && second.isEmpty()) || (second == null && first.isEmpty()))
            return true;

        if (first.size() != second.size())
            return false;

        final List<T> listSecond = new ArrayList<>(second);

        for (T item : first) {
            Iterator<T> it = listSecond.iterator();
            boolean flag = false;
            while (it.hasNext()) {
                if (item.equals(it.next())) {
                    it.remove();
                    flag = true;
                    break;
                }
            }
            if (!flag) return false;
        }
        return true;
    }
}
