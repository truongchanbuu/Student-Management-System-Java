package com.midterm.studentmanagementsystem.dao;

import java.util.List;

public interface DAO<T> {
	public boolean add(T obj);
	public boolean update(T obj);
	public boolean delete(T obj);
	public T getById(String id);
	public List<T> getAll();
}
