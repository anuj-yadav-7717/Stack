import  java.util.*;
// import java.util.Stack;
class Node{
    int data;
    Node next;
    Node(int data){
        this.data=data;
    }
}
class Linkedlist{
    Node head=null;
    void addLast(int val){
        Node newNode=new Node(val);
        if(head==null){
            head=newNode;
        }
        else{
            Node temp=head;
            while(temp.next!=null){
                temp=temp.next;
            }
            temp.next=newNode;
        }

        System.out.println("Node added successfully at end of Linked List");
    }

    void display(){
        if(head==null){
            System.out.println("Linkedlist is empty ......");
        }
        Node temp=head;
        while(temp!=null){
            System.out.print(temp.data+" -> ");
            temp=temp.next;
        }
        System.out.println(" ");
    }
}


public class Remove_ListNode {

    // method to reverse linkedlist...
    static Node reverse(Node head){
        Node f=head;
        Node c=head;
        Node p=null;
        while(c!=null){
            f=c.next;
            c.next=p;
            p=c;
            c=f;
        }
        return p;
    }

     // method to remove the nodes whoes right nodes are greater .

   static  Node remove_Nodes(Node head){
        if(head==null)System.out.println("Nothing in this LinkedList..");

        Node temp=reverse(head);
        Node j=temp;
        while(j!=null && j.next!=null){
            if(j.next.data<j.data){
                j.next=j.next.next;
            }
            else{
                j=j.next;
            }
        }
        return reverse(temp);
    }
    public static void main(String[] args) {
        Scanner sc=new Scanner(System.in);
        Linkedlist l=new Linkedlist();
        System.out.println("Welcome in Linked List world Beta : ");
        while(true){
            System.out.print("Enter Value of node : ");
            int val=sc.nextInt();
            if(val==-1){
                break;
            }
            else {
                l.addLast(val);
            }
        }
        sc.close();
        l.display();

        l.head=remove_Nodes(l.head);
        System.out.println(" ");
        System.out.println("Hey now nodes are deleted from your nodes ... ");
        l.display();
    }
}
