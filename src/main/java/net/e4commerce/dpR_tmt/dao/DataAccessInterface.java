package net.e4commerce.dpR_tmt.dao;

import javax.inject.Singleton;

@Singleton
public interface DataAccessInterface<T>
{
	public void create(T subject);
	public void delete(String id);
	public T get(String id) throws Exception;
	public void update(T subject);
}
