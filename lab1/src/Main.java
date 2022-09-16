import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        StringCalculator stringCalculator = new StringCalculator();
        Scanner in = new Scanner(System.in);
        System.out.print("Input string: ");
        String str = in.nextLine();
        in.close();
        stringCalculator.add(str);

    }
}
