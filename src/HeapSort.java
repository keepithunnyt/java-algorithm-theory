import java.util.Arrays;
import java.util.NoSuchElementException;

class Heap {
  private int[] array;
  private int DEFAULT_CAPACITY = 10;
  private int size = 0;
  private boolean isMax;

  public Heap(int capacity, boolean isMax) {
    this.array = new int[capacity];
    this.isMax = isMax;
  }

  public Heap(int capacity) {
    this(capacity, true);
  }

  public Heap(boolean isMax) {
    this.array = new int[this.DEFAULT_CAPACITY];
    this.isMax = isMax;
  }

  public Heap() {
    this.array = new int[this.DEFAULT_CAPACITY];
    this.isMax = true;
  }

  public void add(int data) {
    if (this.size + 1 == this.array.length) {
      resize(this.array.length * 2);
    }
    //
    this.array[++this.size] = data;
    int targetIdx = this.size;
    while (targetIdx > 1) {
      int parentIdx = getParentIdx(targetIdx);
      int parentValue = this.array[parentIdx];
      if (this.isMax && data <= parentValue) {
        break;
      } else if (!this.isMax && data >= parentValue) {
        break;
      }
      this.array[targetIdx] = parentValue;
      targetIdx = parentIdx;
    }
    this.array[targetIdx] = data;
  }

  public int remove() {
    if (this.isEmpty()) {
      throw new NoSuchElementException();
    }
    //
    int targetIdx = 1;
    int targetValue = this.array[this.size];
    int rootValue = this.array[targetIdx];
    while (getLeftChildIdx(targetIdx) <= size && getRightChildIdx(targetIdx) <= size) {
      int leftChildIdx = getLeftChildIdx(targetIdx);
      int leftChildValue = this.array[leftChildIdx];
      int rightChildIdx = getRightChildIdx(targetIdx);
      int rightChildValue = this.array[rightChildIdx];
      if (this.isMax && leftChildValue < targetValue && rightChildValue < targetValue) {
        break;
      } else if (!this.isMax && leftChildValue > targetValue && rightChildValue > targetValue) {
        break;
      }
      if (this.isMax) {
        boolean isGreaterLeftChild = leftChildValue > rightChildValue;
        this.array[targetIdx] = isGreaterLeftChild ? leftChildValue : rightChildValue;
        targetIdx = isGreaterLeftChild ? leftChildIdx : rightChildIdx;
      } else {
        boolean isLessLeftChild = leftChildValue < rightChildValue;
        this.array[targetIdx] = isLessLeftChild ? leftChildValue : rightChildValue;
        targetIdx = isLessLeftChild ? leftChildIdx : rightChildIdx;
      }
    }
    this.array[targetIdx] = targetValue;
    this.size -= 1;
    return rootValue;
  }

  public int peek() {
    return this.array[1];
  }

  public boolean isEmpty() {
    return this.size == 0;
  }

  // getter/setter
  public int getSize() {
    return size;
  }

  public int[] toArray() {
    return Arrays.copyOf(this.array, this.size + 1);
  }

  // private
  private int getParentIdx(int targetIdx) {
    return targetIdx / 2;
  }

  private int getLeftChildIdx(int targetIdx) {
    return targetIdx * 2;
  }

  private int getRightChildIdx(int targetIdx) {
    return targetIdx * 2 + 1;
  }

  private void resize(int newCapacity) {
    int[] temp = new int[newCapacity];
    for (int i = 0; i < this.array.length; i++) {
      temp[i] = array[i];
    }
    this.array = temp;
  }
}

public class HeapSort {
  public static void main(String[] args) {
    Heap heap = new Heap(true);
    // 3 4 10 26 10 13 27 56 97 47 20 96 84 49 55
    heap.add(3);
    heap.add(4);
    heap.add(10);
    heap.add(26);
    heap.add(11);
    heap.add(13);
    heap.add(27);
    heap.add(56);
    heap.add(97);
    heap.add(47);
    heap.add(20);
    heap.add(96);
    heap.add(84);
    heap.add(49);
    heap.add(55);
    while (!heap.isEmpty()) {
      System.out.print(heap.remove() + " ");
    }
    System.out.println();
  }
}