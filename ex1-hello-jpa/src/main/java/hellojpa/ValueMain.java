package hellojpa;

public class ValueMain {
  public static void main(String[] args) {

    int a = 10;
    int b = a;

    a = 20;

    System.out.println("a = " + a);
    System.out.println("b = " + b);

    Integer aa = new Integer(10);
    Integer bb = aa;
  }
}
