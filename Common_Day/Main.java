package Common_Day;
import java.util.*;

class Node{
    int data;
    Node next;
    Node(int val){
        this.data=val;
    }
}

class LinkedList{
    Node head=null;
    void athead(int val){
        Node temp=new Node(val);
        if(head==null){head=temp;}
      else{
          temp.next=head;
        head=temp;
      }
    }

    void display(){
        if(head==null)System.out.println("Hey this is empty linedlist .");
        else{
            Node temp=head;
            while(temp!=null){
                System.out.print(temp.data+" --> ");
                temp=temp.next;
            }
            System.out.println(" ");
        }
    }

}
public class Main{
    public static void main(String[] args) {
        LinkedList l=new LinkedList();
        Scanner sc=new Scanner(System.in);
        System.out.print("what is your name: ");
        String name=sc.nextLine();
        System.out.println("Okay " +name+" Lets continue Sir ...");
        System.out.print("Enter how many time you wanna add element at head ");
        int val=sc.nextInt();
        while(val>0){
            System.out.print("you input: ");
            int data=sc.nextInt();
            System.out.println("Node added sucessfully.");
            l.athead(data);
            val--;
        }
        System.out.println("Your final LinkedList  is  given below : ");
        l.display();
        System.out.println("I am grateful for your efforts . ");

    }

}