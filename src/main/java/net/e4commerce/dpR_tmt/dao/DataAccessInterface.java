/*
 * 
 */
package net.e4commerce.dpR_tmt.dao;

import javax.inject.Singleton;

// TODO: Auto-generated Javadoc
/**
 * The Interface DataAccessInterface.
 *
 * @param <T> the generic type
 */
@Singleton
public interface DataAccessInterface<T>
{
	
	/**
	 * Creates the.
	 *
	 * @param subject the subject
	 */
	public void create(T subject);
	
	/**
	 * Delete.
	 *
	 * @param id the id
	 */
	public void delete(String id);
	
	/**
	 * Gets the.
	 *
	 * @param id the id
	 * @return the t
	 * @throws Exception the exception
	 */
	public T get(String id) throws Exception;
	
	/**
	 * Update.
	 *
	 * @param subject the subject
	 */
	public void update(T subject);
}
