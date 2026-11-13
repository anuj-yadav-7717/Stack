import java.util.*;

// here creating blue print of node 
class Node{
    int data;
    Node next;

    Node(int val){
        this.data=val;
    }
}

// creating a stack operation based funtions
class Stack{
    Node head=null;
    void push(int val){
        Node temp=new Node(val);
        if(head==null)head=temp;
        else{
            temp.next=head;
            head=temp;
        }
    }

    int  pop(){
        head=head.next;
        return head.data;
        
    }
    int peek(){
      return head.data;
    }

     // this one print  the stack
    void display(){
        if(head==null)System.out.println("Hey beta your stack is empty now ");
        Node temp=head;
        while(temp!=null){
            System.out.println(temp.data);
            temp=temp.next;
        }
    }
        
}

public class LL_Stack{
    public static  void main(String args[]){
        Scanner sc=new Scanner(System.in);
        sc.close();
        Stack st=new Stack();
        st.push(10);
        st.push(78);
        st.display();
        st.pop();
        st.display();
       System.out.println(st.peek());


    }
}