package il.co.ilrd.CRUD;

import java.io.Serializable;

public interface CRUD<K extends Serializable, T extends Serializable> extends AutoCloseable {

	public K create(T data);
	public T read(K key);
	public void update(K key, T data);
	public void delete(K key);
}
