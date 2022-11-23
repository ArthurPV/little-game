package base.vector;

import java.util.function.Function;
import java.util.Iterator;
import java.util.AbstractCollection;

class VectorIterator<T> implements Iterator<T> {
	Vector<T> v;
	int count = 0;

	public VectorIterator(Vector<T> v) {
		this.v = v;
	}

	@Override
	public boolean hasNext() {
		return count < v.len();
	}

	@Override
	public T next() {
		return count < v.len() ? v.get(count++) : null;
	}

	@Override
	public void remove() {
		throw new RuntimeException("todo: implements remove()");
	}
}

public class Vector<T extends Object> extends AbstractCollection<T> implements Iterable<T> {
	private Object[] items;
	private int len;
	private int capacity;
	private int defaultCapacity;

	public Vector() {
		len = 0;
		capacity = 4;
		defaultCapacity = 4;
		items = new Object[4];
	}

	public Vector(int capacity) {
		assert capacity > 0 : "capacity must be greater to 0";

		len = 0;
		this.capacity = capacity;
		defaultCapacity = capacity;
		items = new Object[capacity];
	}

	public Vector(T[] init) {
		len = 0;
		capacity = 4;
		defaultCapacity = 4;
		items = new Object[4];

		for (int i = 0; i < init.length; i++) {
			push(init[i]);
		}
	}

	public Vector(Vector v) {
		this.items = v.items;
		this.len = v.len;
		this.capacity = v.capacity;
		this.defaultCapacity = v.defaultCapacity;
	}

	public void append(Vector<T> v) {
		for (int i = 0; i < v.len; i++) {
			push(v.get(i));
		}
	}

	public int capacity() {
		return capacity;
	}

	public void clear() {
		items = new Object[defaultCapacity];
		len = 0;
	}

	private void copyToThis(Vector<T> v) {
		len = v.len;
		capacity = v.capacity;
		defaultCapacity = v.defaultCapacity;
		items = v.items;
	}

	public boolean equals(Vector v) {
		if (v.len() == len()) {
			for (int i = 0; i < v.len(); i++) {
				if (get(i) != v.get(i))
					return false;
			}

			return true;
		}

		return false;
	}

	@SuppressWarnings("unchecked")
	public Vector<T> filter(Function<T, Boolean> f) {
		var v = new Vector<T>();

		for (int i = 0; i < len; i++) {
			var item = get(i);

			if (f.apply(item)) {
				v.push(item);
			}
		}

		return v;
	}

	public T first() {
		return len == 0 ? null : get(0);
	}

	// public void forEach(Function<T, void> f) {
	// for (int i = 0; i < len(); i++)
	// f.apply(get(i));
	// }

	@SuppressWarnings("unchecked")
	public T get(int index) {
		return (T) items[index];
	}

	@SuppressWarnings("unchecked")
	public Vector<T> getRange(int start, int end) {
		var temp = new Vector<T>(defaultCapacity);

		for (int i = start; i < end && i < len(); i++) {
			temp.push(get(i));
		}

		return temp;
	}

	@SuppressWarnings("unchecked")
	private Vector<T> getReverseRange(int start, int end) {
		var temp = new Vector<T>(defaultCapacity);

		for (int i = end - 1; i >= start; i--) {
			temp.push(get(i));
		}

		return temp;
	}

	public void insert(int index, T item) {
		var temp = getRange(0, index);

		temp.push(item);

		for (int i = index; i < len; i++) {
			temp.push(get(i));
		}

		copyToThis(temp);
	}

	public boolean is_empty() {
		return len == 0;
	}

	public boolean is_in(T value) {
		for (int i = 0; i < len(); i++) {
			if (((Object) value).equals((Object) get(i)))
				return true;
		}

		return false;
	}

	public boolean is_sorted() {
		if (len > 0) {
			var last = get(0);

			// 1. Verify if T is a Number.
			if (!(last instanceof Number)) {
				throw new RuntimeException("expected T is instance of Number");
			}

			// 2. Verify if the values are sorted.
			for (int i = 0; i + 1 < len; i++) {
				var v = get(i + 1);

				if (((Number) last).doubleValue() > ((Number) v).doubleValue())
					return false;

				last = v;
			}
		}

		return true;
	}

	public Iterator<T> iterator() {
		return new VectorIterator<T>(this);
	}

	public int len() {
		return len;
	}

	@SuppressWarnings("unchecked")
	public Vector<T> map(Function<T, T> f) {
		var v = new Vector<T>();

		for (int i = 0; i < items.length; i++) {
			v.push(f.apply((T) items[i]));
		}

		return v;
	}

	@SuppressWarnings("unchecked")
	public T pop() {
		var v = get(len - 1);
		copyToThis(getRange(0, len - 1));

		return v;
	}

	public void push(T item) {
		// 1. Grow array
		if (len == capacity) {
			Object[] temp = items;

			capacity *= 2;
			items = new Object[capacity];

			// 2. Push in the resized array the oldest items;
			for (int i = 0; i < temp.length; i++) {
				items[i] = temp[i];
			}
		}

		items[len++] = (Object) item;
	}

	public static Integer reduce(Vector<Integer> v) {
		Integer res = 0;

		for (Object i : v) {
			res += (Integer) i;
		}

		return res;
	}

	public void remove(int index) {
		var v1 = getRange(0, index);
		var v2 = getRange(index + 1, len);

		v1.append(v2);
		copyToThis(v1);
	}

	public void reverse() {
		copyToThis(getReverseRange(0, len));
	}

	public void resize(int newLen, T item) {
		if (newLen >= len) {
			int diff = newLen - len;

			for (int i = 0; i < diff; i++) {
				push(item);
			}
		} else {
			copyToThis(getRange(0, newLen));
		}
	}

	// It's the same method of len except is required by AbstractCollection<T>.
	public int size() {
		return len;
	}

	public void swap(int index1, int index2) {
		Object v = get(index1);

		items[index1] = items[index2];
		items[index2] = v;
	}

	@SuppressWarnings("unchecked")
	public T[] toArray() {
		return (T[]) items;
	}
}
