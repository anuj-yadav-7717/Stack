import  java.util.*;
public class Practice{

   static void addAtbottom(Stack<Integer>st,int element){
       if(st.size()==0){
        st.push(element);
        return;
       }

       int top=st.pop();
       addAtbottom(st, element);
       st.push(top);
    }
    public static  void main(String args[]){
    
    Scanner sc=new Scanner(System.in);
    Stack<Integer> st=new Stack<>();
   
    while(true){
        System.out.println("Enter Value : ");
        int val=sc.nextInt();
        if(val==-1){
            break;
        }
        st.push(val);
    }
    System.out.println(st.peek());
    System.out.println(st);
   addAtbottom(st,90);
   System.out.println(st);

    }
}