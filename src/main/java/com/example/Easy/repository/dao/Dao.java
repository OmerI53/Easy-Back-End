package com.example.Easy.repository.dao;

import java.util.List;
import java.util.UUID;

public interface Dao<T> {
    T get(UUID id);
    List<T> getAll();
    T save(T t);
    T update(T t);
    T delete(T t);
}
