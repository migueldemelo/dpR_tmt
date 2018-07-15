package net.e4commerce.dpR_tmt.dao;

import java.util.List;

import javax.inject.Singleton;

@Singleton
public interface DataAccessInterface<T>
{
	public void create(T subject);
	public void delete(T subject);
	public T get(T subject);
	public List<T> search(T subject);
	public void update(T subject);
}
