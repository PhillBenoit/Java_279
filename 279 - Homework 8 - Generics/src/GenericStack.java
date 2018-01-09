public class GenericStack<E> {
	
	//new counter for actual stack size
	private int stack_size;
	
	public GenericStack() {
		stack_size = 0;
	}
	
	//private java.util.ArrayList<E> list = new java.util.ArrayList<E>();
	private E[] list = (E[]) new Object[1];

	public int getSize() {
		//return list.size();
		return stack_size;
	}

	public E peek() {
		//return list.get(getSize() - 1);
		if (stack_size > 0) return list[stack_size - 1];
		return null;
	}

	public void push(E o) {
		//increase size of available space for stack
		if (list.length == stack_size) {
			E[] temp_list = (E[]) new Object[stack_size];
			System.arraycopy(list, 0, temp_list, 0, stack_size);
			list = (E[]) new Object[stack_size * 2];
			System.arraycopy(temp_list, 0, list, 0, stack_size);
		}
		
		//list.add(o);
		list[stack_size++] = o;
	}

	public E pop() {
		//E o = list.get(getSize() - 1);
		E o = null;
		
		//list.remove(getSize() - 1);
		if (stack_size > 0) o = list[--stack_size];
			
		return o;
	}

	public boolean isEmpty() {
		//return list.isEmpty();
		return stack_size == 0;
	}

	@Override
	public String toString() {
		//return "stack: " + list.toString();
		String return_string = "stack:\n";
		for (int step = 0; step < stack_size; step++)
			return_string = return_string + list[step].toString() + "\n";
		return return_string;
	}
}