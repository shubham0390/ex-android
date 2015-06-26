package api;

/**
 * Created by styagi on 5/28/2015.
 */
public interface ExpenseBookDataAdapter<T> extends DataAdapter<T> {

    void addMember(T t);
}
