package DAO;

import java.util.HashMap;
import java.util.List;

import Model.Model;

public abstract class Response {

    public interface Result<T extends Model> {
        void result(List<T> list);
    }

    public interface MapToObjectList<T extends Model> {
        T result(HashMap hashMap);
    }

}

