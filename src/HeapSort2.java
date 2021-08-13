import java.util.Arrays;
import java.util.Comparator;
import java.util.NoSuchElementException;
import java.util.Random;

class Heap2<E> {

  private final Comparator<? super E> comparator;
  private static final int DEFAULT_CAPACITY = 10; // 최소(기본) 용적 크기
  private int size; // 요소 개수
  private Object[] array; // 요소를 담을 배열

  public Heap2() {
    this(null);
  }

  public Heap2(Comparator<? super E> comparator) {
    this.array = new Object[DEFAULT_CAPACITY];
    this.size = 0;
    this.comparator = comparator;
  }

  // 생성자 Type 2 (초기 공간 할당 O)
  public Heap2(int capacity) {
    this(capacity, null);
  }

  public Heap2(int capacity, Comparator<? super E> comparator) {
    this.array = new Object[capacity];
    this.size = 0;
    this.comparator = comparator;
  }

  // 받은 인덱스의 부모 노드 인덱스를 반환
  private int getParent(int index) {
    return index / 2;
  }

  // 받은 인덱스의 왼쪽 자식 노드 인덱스를 반환
  private int getLeftChild(int index) {
    return index * 2;
  }

  // 받은 인덱스의 오른쪽 자식 노드 인덱스를 반환
  private int getRightChild(int index) {
    return index * 2 + 1;
  }

  /**
   * @param newCapacity 새로운 용적 크기
   */
  private void resize(int newCapacity) {

    // 새로 만들 배열
    Object[] newArray = new Object[newCapacity];

    // 새 배열에 기존에 있던 배열의 요소들을 모두 복사해준다.
    for (int i = 1; i <= size; i++) {
      newArray[i] = array[i];
    }

    /*
     * 현재 배열은 GC 처리를 위해 null로 처리한 뒤, 새 배열을 연결해준다.
     */
    this.array = null;
    this.array = newArray;

  }

  public void add(E value) {

    // 배열 용적이 꽉 차있을 경우 용적을 두 배로 늘려준다.
    if (size + 1 == array.length) {
      resize(array.length * 2);
    }

    siftUp(size + 1, value); // 가장 마지막에 추가 되는 위치와 넣을 값(타겟)을 넘겨줌
    size++; // 정상적으로 재배치가 끝나면 사이즈를 증가
  }

  // 상향 선별
  /**
   * @param idx    추가할 노드의 인덱스
   * @param target 재배치 할 노드
   */
  private void siftUp(int idx, E target) {
    // comparator가 존재할 경우 comparator 을 인자로 넘겨준다.
    if (comparator != null) {
      siftUpComparator(idx, target, comparator);
    } else {
      siftUpComparable(idx, target);
    }
  }

  // Comparator을 이용한 sift-up
  @SuppressWarnings("unchecked")
  private void siftUpComparator(int idx, E target, Comparator<? super E> comp) {

    // root노드보다 클 때까지만 탐색한다.
    while (idx > 1) {
      int parent = getParent(idx); // 삽입노드의 부모노드 인덱스 구하기
      Object parentVal = array[parent]; // 부모노드의 값

      // 타겟 노드 값이 부모노드보다 크면 반복문 종료
      if (comp.compare(target, (E) parentVal) >= 0) {
        break;
      }

      /*
       * 부모노드가 타겟노드보다 크므로 현재 삽입 될 위치에 부모노드 값으로 교체해주고 타겟 노드의 위치를 부모노드의 위치로 변경해준다.
       */
      array[idx] = parentVal;
      idx = parent;
    }

    // 최종적으로 삽입될 위치에 타겟 노드 값을 저장해준다.
    array[idx] = target;
  }

  // 삽입 할 객체의 Comparable을 이용한 sift-up
  @SuppressWarnings("unchecked")
  private void siftUpComparable(int idx, E target) {

    // 타겟노드가 비교 될 수 있도록 한 변수를 만든다.
    Comparable<? super E> comp = (Comparable<? super E>) target;

    while (idx > 1) {
      int parent = getParent(idx);
      Object parentVal = array[parent];

      if (comp.compareTo((E) parentVal) >= 0) {
        break;
      }
      array[idx] = parentVal;
      idx = parent;
    }
    array[idx] = comp;
  }

  @SuppressWarnings("unchecked")
  public E remove() {
    if (array[1] == null) { // 만약 root가 비어있을경우 예외를 던지도록 함
      throw new NoSuchElementException();
    }

    E result = (E) array[1]; // 삭제된 요소를 반환하기 위한 임시 변수
    E target = (E) array[size]; // 타겟이 될 요소
    array[size--] = null; // 타겟 노드를 비운다.

    // 삭제할 노드의 인덱스와 이후 재배치 할 타겟 노드를 넘겨준다.
    siftDown(1, target); // 루트 노드가 삭제되므로 1을 넘겨준다.

    return result;
  }

  /**
   * @param idx    삭제할 노드의 인덱스
   * @param target 재배치 할 노드
   */
  private void siftDown(int idx, E target) {
    // comparator가 존재할 경우 comparator 을 인자로 넘겨준다.
    if (comparator != null) {
      siftDownComparator(idx, target, comparator);
    } else {
      siftDownComparable(idx, target);
    }
  }

  // Comparator을 이용한 sift-down
  @SuppressWarnings("unchecked")
  private void siftDownComparator(int idx, E target, Comparator<? super E> comp) {

    int child; // 교환 될 자식을 가리키는 변수

    // 왼쪽 자식 노드의 인덱스가 요소의 개수보다 작을 때 까지 반복
    while ((child = getLeftChild(idx)) <= size) {

      int right = getRightChild(idx); // 오른쪽 자식 인덱스

      Object childVal = array[child]; // 왼쪽 자식의 값 (교환 될 값)

      /*
       * 오른쪽 자식 인덱스가 size를 넘지 않으면서 왼쪽 자식이 오른쪽 자식보다 큰 경우 재배치 할 노드는 작은 자식과 비교해야하므로
       * child와 childVal을 오른쪽 자식으로 바꿔준다.
       */
      if (right <= size && comp.compare((E) childVal, (E) array[right]) > 0) {
        child = right;
        childVal = array[child];
      }

      // 재배치 할 노드가 자식 노드보다 작을경우 반복문을 종료한다.
      if (comp.compare(target, (E) childVal) <= 0) {
        break;
      }

      /*
       * 현재 부모 인덱스에 자식 노드 값을 대체해주고 부모 인덱스를 자식 인덱스로 교체
       */
      array[idx] = childVal;
      idx = child;
    }

    // 최종적으로 재배치 되는 위치에 타겟이 된 값을 넣어준다.
    array[idx] = target;

    /*
     * 용적의 사이즈가 최소 용적보다는 크면서 요소의 개수가 전체 용적의 1/4일경우 용적을 반으로 줄임(단, 최소용적보단 커야함)
     */
    if (array.length > DEFAULT_CAPACITY && size < array.length / 4) {
      resize(Math.max(DEFAULT_CAPACITY, array.length / 2));
    }

  }

  // Comparable을 이용한 sift-down
  @SuppressWarnings("unchecked")
  private void siftDownComparable(int idx, E target) {

    Comparable<? super E> comp = (Comparable<? super E>) target;

    int parent = idx;
    int child;

    while ((child = getLeftChild(parent)) <= size) {

      int right = getRightChild(parent);

      Object childVal = array[child];

      if (right <= size && ((Comparable<? super E>) childVal).compareTo((E) array[right]) > 0) {
        child = right;
        childVal = array[child];
      }

      if (comp.compareTo((E) childVal) <= 0) {
        break;
      }
      array[parent] = childVal;
      parent = child;

    }
    array[parent] = comp;

    if (array.length > DEFAULT_CAPACITY && size < array.length / 4) {
      resize(Math.max(DEFAULT_CAPACITY, array.length / 2));
    }

  }

  public int size() {
    return this.size;
  }

  @SuppressWarnings("unchecked")
  public E peek() {
    if (array[1] == null) {
      throw new NoSuchElementException();
    }

    return (E) array[1];
  }

  public boolean isEmpty() {
    return size == 0;
  }

  public Object[] toArray() {
    return Arrays.copyOf(array, size + 1);
  }
}

class Heap3<E> {
  private final Comparator<? super E> comparator;
  private int DEFAULT_CAPACITY = 10;
  private int size;
  private Object[] array;

  public Heap3() {
    this(null);
  }

  public Heap3(Comparator<? super E> comparator) {
    this.array = new Object[this.DEFAULT_CAPACITY];
    this.size = 0;
    this.comparator = comparator;
  }

  public Heap3(int capacity) {
    this(capacity, null);
  }

  public Heap3(int capacity, Comparator<? super E> comparator) {
    this.array = new Object[capacity];
    this.size = 0;
    this.comparator = comparator;
  }

  private int getParentIdx(int idx) {
    return idx / 2;
  }

  private int getLeftChildIdx(int idx) {
    return idx * 2;
  }

  private int getRightChildIdx(int idx) {
    return idx * 2 + 1;
  }

  public void resize(int newCapacity) {
    Object[] newArray = new Object[newCapacity];
    for (int i = 1; i <= this.size; i++) {
      newArray[i] = this.array[i];
    }
    this.array = newArray;
  }

  public void add(E value) {
    if (size + 1 == this.array.length) {
      this.resize(this.array.length * 2);
    }
    shiftUp(size + 1, value);
    this.size += 1;
  }

  private void shiftUp(int targetIdx, E targetValue) {
    if (this.comparator != null) {
      shiftUpUsingComparator(targetIdx, targetValue);
    } else {
      shiftUpUsingComparable(targetIdx, targetValue);
    }
  }

  @SuppressWarnings("unchecked")
  private void shiftUpUsingComparator(int targetIdx, E targetValue) {
    while (targetIdx > 1) {
      int parentIdx = getParentIdx(targetIdx);
      Object parentValue = this.array[parentIdx];
      if (this.comparator.compare(targetValue, (E) parentValue) >= 0) {
        break;
      }
      this.array[targetIdx] = parentValue;
      targetIdx = parentIdx;
    }
    this.array[targetIdx] = targetValue;
  }

  private void shiftUpUsingComparable(int targetIdx, E targetValue) {
    Comparable<? super E> comp = (Comparable<? super E>) targetValue;
    while (targetIdx > 1) {
      int parentIdx = getParentIdx(targetIdx);
      Object parentValue = this.array[parentIdx];
      if (comp.compareTo((E) parentValue) >= 0) {
        break;
      }
      this.array[targetIdx] = parentValue;
      targetIdx = parentIdx;
    }
    this.array[targetIdx] = targetValue;
  }

  public E remove() {
    if (this.array[1] == null) {
      throw new NoSuchElementException();
    }
    E rootValue = (E) this.array[1];
    E targetValue = (E) this.array[this.size];
    this.array[size--] = null;
    shiftDown(1, targetValue);
    return rootValue;
  }

  private void shiftDown(int targetIdx, E targetValue) {
    if (this.comparator != null) {
      shiftDownUsingComparator(targetIdx, targetValue);
    } else {
      shiftDownUsingComparable(targetIdx, targetValue);
    }
  }

  @SuppressWarnings("unchecked")
  private void shiftDownUsingComparator(int targetIdx, E targetValue) {
    int childIdx;
    while ((childIdx = getLeftChildIdx(targetIdx)) <= this.size) {
      int rightChildIdx = getRightChildIdx(targetIdx);
      E childValue = (E) this.array[childIdx];
      if (rightChildIdx <= this.size && this.comparator.compare(childValue, (E) this.array[rightChildIdx]) > 0) {
        childIdx = rightChildIdx;
        childValue = (E) this.array[rightChildIdx];
      }
      if (this.comparator.compare(targetValue, childValue) <= 0) {
        break;
      }
      this.array[targetIdx] = childValue;
      targetIdx = childIdx;
    }
    this.array[targetIdx] = targetValue;
    if (this.array.length > this.DEFAULT_CAPACITY && this.size < this.array.length / 4) {
      resize(Math.max(this.DEFAULT_CAPACITY, this.array.length / 2));
    }
  }

  @SuppressWarnings("unchecked")
  private void shiftDownUsingComparable(int targetIdx, E targetValue) {
    Comparable<? super E> comp = (Comparable<? super E>) targetValue;
    int rightChildIdx;
    while ((rightChildIdx = getRightChildIdx(targetIdx)) <= this.size) {
      int childIdx = getLeftChildIdx(targetIdx);
      E childValue = (E) this.array[childIdx];
      if (((Comparable<? super E>) childValue).compareTo((E) this.array[rightChildIdx]) > 0) {
        childIdx = rightChildIdx;
        childValue = (E) this.array[rightChildIdx];
      }
      if (comp.compareTo(childValue) <= 0) {
        break;
      }
      this.array[targetIdx] = childValue;
      targetIdx = childIdx;
    }
    this.array[targetIdx] = targetValue;
    if (this.array.length > this.DEFAULT_CAPACITY && this.size < this.array.length / 4) {
      resize(Math.max(this.DEFAULT_CAPACITY, this.array.length / 2));
    }
  }

  public boolean isEmpty() {
    return this.size == 0;
  }

  @SuppressWarnings("unchecked")
  public E peek() {
    if (isEmpty()) {
      throw new NoSuchElementException();
    }
    return (E) this.array[1];
  }

  public Object[] toArray() {
    return Arrays.copyOf(this.array, this.size + 1);
  }

  public int getSize() {
    return size;
  }
}

public class HeapSort2 {
  public static void main(String[] args) {

    Heap2<Student> heap2 = new Heap2<Student>(comparator);
    Heap3<Student> heap3 = new Heap3<Student>(comparator);

    heap2.add(new Student("김자바", 40));
    heap3.add(new Student("김자바", 40));

    heap2.add(new Student("이씨프", 27));
    heap3.add(new Student("이씨프", 27));

    heap2.add(new Student("조파이", 48));
    heap3.add(new Student("조파이", 48));

    heap2.add(new Student("김자바", 18));
    heap3.add(new Student("김자바", 18));

    heap2.add(new Student("상스윕", 32));
    heap3.add(new Student("상스윕", 32));

    heap2.add(new Student("양씨샵", 27));
    heap3.add(new Student("양씨샵", 27));

    System.out.println("[Heap 2]");
    while (!heap2.isEmpty()) {
      System.out.println(heap2.remove());
    }
    System.out.println();

    System.out.println("[Heap 3]");
    while (!heap3.isEmpty()) {
      System.out.println(heap3.remove());
    }
    System.out.println();
  }

  private static Comparator<Student> comparator = (Student o1, Student o2) -> {
    // 나이가 같다면 이름순
    if (o1.age == o2.age) {
      return o1.name.compareTo(o2.name);
    }
    return o2.age - o1.age; // 나이 내림차순
  };

  private static class Student implements Comparable<Student> {

    String name;
    int age;

    public Student(String name, int age) {

      this.name = name;
      this.age = age;
    }

    @Override
    public int compareTo(Student o) {
      // 이름이 같다면 나이순 (오름차순)
      if (this.name.compareTo(o.name) == 0) {
        return this.age - o.age;
      }
      // 이름순
      return this.name.compareTo(o.name);

    }

    public String toString() {
      return "이름 : " + name + "\t나이 : " + age;
    }
  }
}
