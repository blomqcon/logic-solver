// This program echoes the arguments that you type in.  To test it
// you do this:
// javac echo.java
// java echo a b c d e f
//
public class Echo{
  public static void main(String argv[]) {
     for (int i = 0; i < argv.length; i++)
        System.out.print(argv[i] + " ");
     System.out.print("\n");
     System.exit(0);
  }
}