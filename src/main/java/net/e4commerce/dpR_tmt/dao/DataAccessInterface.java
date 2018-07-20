package net.e4commerce.dpR_tmt.dao;

/**
 * The Interface DataAccessInterface defines standard data access objects operations
 *
 * @param <T> the generic type
 * 
 * @author      Miguel de Melo
 * @version     1.0
 * @since       1.0
 */
public interface DataAccessInterface<T>
{
	
	/**
	 * Creates an entity being presisted
	 *
	 * @param subject the subject entity
	 */
	public void create(T subject);
	
	/**
	 * Deletes an entity.
	 *
	 * @param id the id of the entity
	 */
	public void delete(String id);
	
	/**
	 * Gets an instance of an entity
	 *
	 * @param id the entity identifier
	 * @return the implementation entity type
	 * @throws Exception the exception
	 */
	public T get(String id) throws Exception;
	
	/**
	 * Update aspects of an entity.
	 *
	 * @param subject the subject
	 */
	public void update(T subject);
}
