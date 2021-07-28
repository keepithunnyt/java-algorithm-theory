import java.util.Arrays;

public class BinarySearch {
  public static boolean search(int[] dataList, int searchData) {
    if (dataList.length == 1) {
      return dataList[0] == searchData;
    } else if (dataList.length == 0) {
      return false;
    }
    int midIdx = dataList.length / 2;
    int midValue = dataList[midIdx];
    if (midValue == searchData) {
      return true;
    } else {
      boolean isMidValueLessMoreThanSearch = midValue < searchData;
      int start = isMidValueLessMoreThanSearch ? midIdx + 1 : 0;
      int end = isMidValueLessMoreThanSearch ? dataList.length : midIdx;
      return search(Arrays.copyOfRange(dataList, start, end), searchData);
    }
  }

  public static void main(String[] args) {
    int[] dataList = new int[] { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };
    System.out.println(search(dataList, 9));

  }
}
