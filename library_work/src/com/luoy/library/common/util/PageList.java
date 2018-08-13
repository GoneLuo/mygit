package com.luoy.library.common.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * 包含查询结果resultList以及分页信息pageConfig
 * 不实现List接口，因为当前dubbo版本对实现了集合接口的类的序列化/反序列化有bug，会丢失其它自定义属性（如pageConfig）
 * @author zhl
 *
 * @param <E>
 */
public final class PageList<E> implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3074520819462291346L;

	private List<E> resulstList;

	private PageConfig pageConfig;
	
	public PageList(List<E> list, PageConfig pageConfig) {
		resulstList = new ArrayList<E>();
		if(list!=null){
			resulstList.addAll(list);
		}
		this.pageConfig = pageConfig;
	}

	public int size() {
		return resulstList.size();
	}

	public boolean isEmpty() {

		if (resulstList == null) {
			return true;
		}
		return resulstList.isEmpty();
	}

	public boolean contains(Object o) {
		if (resulstList == null) {
			return false;
		}
		return resulstList.contains(o);
	}

	public Iterator<E> iterator() {
		return resulstList.iterator();
	}

	public Object[] toArray() {
		return resulstList.toArray();
	}

	public <T> T[] toArray(T[] a) {
		return resulstList.toArray(a);
	}

	public boolean add(E e) {
		return resulstList.add(e);
	}

	public boolean remove(Object o) {
		return resulstList.remove(o);
	}

	public boolean containsAll(Collection<?> c) {
		return resulstList.containsAll(c);
	}

	public boolean addAll(Collection<? extends E> c) {
		return resulstList.addAll(c);
	}

	public boolean addAll(int index, Collection<? extends E> c) {
		return resulstList.addAll(index, c);
	}

	public boolean removeAll(Collection<?> c) {
		return resulstList.removeAll(c);
	}

	public boolean retainAll(Collection<?> c) {
		return c.retainAll(c);
	}

	public void clear() {
		resulstList.clear();
	}

	public E get(int index) {
		return resulstList.get(index);
	}

	public E set(int index, E element) {
		return resulstList.set(index, element);
	}

	public void add(int index, E element) {
		resulstList.set(index, element);
	}

	public E remove(int index) {
		return resulstList.remove(index);
	}

	public int indexOf(Object o) {
		return resulstList.indexOf(o);
	}

	public int lastIndexOf(Object o) {
		return resulstList.lastIndexOf(o);
	}

	public ListIterator<E> listIterator() {
		return resulstList.listIterator();
	}

	public ListIterator<E> listIterator(int index) {
		return resulstList.listIterator(index);
	}

	public List<E> subList(int fromIndex, int toIndex) {
		return resulstList.subList(fromIndex, toIndex);
	}

    public PageConfig getPageConfig() {
        return pageConfig;
    }

    public void setPageConfig(PageConfig pageConfig) {
        this.pageConfig = pageConfig;
    }

	public List<E> getResulstList() {
		return resulstList;
	}

	public void setResulstList(List<E> resulstList) {
		this.resulstList = resulstList;
	}
}

