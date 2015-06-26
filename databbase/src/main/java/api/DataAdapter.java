package api;

import java.util.List;

/**
 * Created by styagi on 5/28/2015.
 */
public interface DataAdapter<T> {
    long create(T t);

    int update(T t);

    int delete(T t);

    int delete(long id);

    int deleteAll();

    T get(long id);

    List<T> getAll();


}
