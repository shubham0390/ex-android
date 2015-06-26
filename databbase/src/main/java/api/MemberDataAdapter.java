package api;

import java.util.List;

/**
 * Created by styagi on 5/28/2015.
 */
public interface MemberDataAdapter <T> extends DataAdapter<T>{

    long create(List<T> list);
}
