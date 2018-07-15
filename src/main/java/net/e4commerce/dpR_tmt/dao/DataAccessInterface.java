package net.e4commerce.dpR_tmt.dao;

public interface DataAccessInterface<T>
{
	public void create(T subject);
	public void delete(T subject);
	public T get(T subject);
	public T search(T subject);
	public void update(T subject);
}
