import java.util.*;
import java.util.Stack;
public class Baseball {

    static int game(String[] arr) {
        int n = arr.length;
        Stack<Integer> st = new Stack<>();

        for (int i = 0; i < n; i++) {
            String s = arr[i];

            if (s.equals("c")) {
                if (!st.isEmpty()) {
                    st.pop();
                }
            } else if (s.equals("D")) {
                if (!st.isEmpty()) {
                    st.push(2 * st.peek());
                }
            } else if (s.equals("+")) {
                if (st.size() >= 2) {
                    int top = st.pop();
                    int secondTop = st.peek();
                    int sum = top + secondTop;
                    st.push(top);
                    st.push(sum);
                }
            } else {
                try {
                    st.push(Integer.parseInt(s));
                } catch (NumberFormatException e) {
                    System.out.println("Invalid score value: " + s);
                }
            }
        }

        int sum = 0;
        while (!st.isEmpty()) {
            sum += st.pop();
        }
        return sum;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter how many numbers in the string: ");
        int number = sc.nextInt();
        sc.nextLine();

        String[] arr = new String[number];
        for (int i = 0; i < number; i++) {
            System.out.print("Enter: ");
            arr[i] = sc.nextLine().trim();
        }
        sc.close();
        System.out.print("Your String is here: ");
        for (int i = 0; i < number; i++) {
            System.out.print(arr[i] + " ");
        }

        System.out.println();
        System.out.println("Now your baseball score is: " + game(arr));
    }
}
