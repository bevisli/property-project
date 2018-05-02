package lib.util;

import java.util.ArrayList;
import java.util.List;

/**
 * @author caine
 */
public class Lists {
    public static <T> List<List<T>> pages(List<T> full, int pageSize) {
        List<List<T>> pages = new ArrayList<>();
        int start = 0;
        int end = Math.min(pageSize, full.size());
        do {
            pages.add(full.subList(start, end));
            start = end;
            end = Math.min(start + pageSize, full.size());
        } while (end > start);
        return pages;
    }
}
