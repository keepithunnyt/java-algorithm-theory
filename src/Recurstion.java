public class Recurstion {
  public static int func(int n) {
    System.out.println(n);
    if (n == 1) {
      return n;
    }
    if (n % 2 == 1) {
      return func(3 * n + 1);
    } else {
      return func(n / 2);
    }
  }

  public static int factorial(int d) {
    if (d == 2) {
      return 2;
    }
    return d * factorial(d - 1);
  }

  public static boolean palindrome(String str) {
    if (str.length() <= 1) {
      return true;
    } else if (str.charAt(0) == str.charAt(str.length() - 1)) {
      return str.length() == 2 ? true : palindrome(str.substring(1, str.length() - 1));
    } else {
      return false;
    }
  }

  public static void main(String[] args) {
    System.out.println(factorial(5));
    System.out.println(palindrome("abba"));
    func(3);
  }
}
