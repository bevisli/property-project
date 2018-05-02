package lib.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author caine
 */
public class ListsTest {
    @Test
    public void page() {
        List<List<Integer>> lists1 = Lists.pages(list(9), 10);
        List<List<Integer>> lists2 = Lists.pages(list(10), 10);
        List<List<Integer>> lists3 = Lists.pages(list(11), 10);
        List<List<Integer>> lists4 = Lists.pages(list(21), 10);

        Assertions.assertEquals(1, lists1.size());
        Assertions.assertEquals(9, lists1.get(0).size());

        Assertions.assertEquals(1, lists2.size());
        Assertions.assertEquals(10, lists2.get(0).size());
        Assertions.assertEquals(10, lists2.get(0).get(9).intValue());

        Assertions.assertEquals(2, lists3.size());
        Assertions.assertEquals(1, lists3.get(1).size());
        Assertions.assertEquals(11, lists3.get(1).get(0).intValue());

        Assertions.assertEquals(3, lists4.size());
        Assertions.assertEquals(1, lists4.get(2).size());
        Assertions.assertEquals(21, lists4.get(2).get(0).intValue());
    }

    private List<Integer> list(int size) {
        List<Integer> list = new ArrayList<>();
        for (int i = 1; i <= size; i++) {
            list.add(i);
        }
        return list;
    }

    @Test
    public void paginate1() {
        SearchResponse response = search(new SearchRequest(0, 10), 10L);
        Assertions.assertEquals(10, response.results.size());
        Assertions.assertEquals(0, response.results.get(0).index);
        Assertions.assertEquals(9, response.results.get(9).index);
    }

    @Test
    public void paginate2() {
        SearchResponse response = search(new SearchRequest(0, 10), 11L);
        Assertions.assertEquals(10, response.results.size());
        Assertions.assertEquals(0, response.results.get(0).index);
        Assertions.assertEquals(9, response.results.get(9).index);
    }

    private SearchResponse search(SearchRequest request, Long total) {
        SearchResponse response = new SearchResponse();
        response.total = total;
        response.results = new ArrayList<>();
        for (int i = 0; i < total; i++) {
            if (i < request.skip) {
                continue;
            }
            if (response.results.size() >= request.limit) {
                break;
            }
            Result result = new Result();
            result.index = i;
            response.results.add(result);
        }
        return response;
    }

    public static class SearchRequest {
        public Integer skip;
        public Integer limit;

        public SearchRequest(Integer skip, Integer limit) {
            this.skip = skip;
            this.limit = limit;
        }
    }

    public static class SearchResponse {
        public Long total;
        public List<Result> results;
    }

    public static class Result {
        public int index;
    }
}
