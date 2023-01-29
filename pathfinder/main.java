package pathfinder;
import processing.core.PApplet;

import java.util.ArrayList;
public class main extends PApplet {
    public static void main(String[] args) {
        PApplet.main("pathfinder.main");
    }
    public void settings() {
        size(800,800);
    }
    public void setup() {
        ArrayList<node> nodes = new ArrayList<>();
        node start = new node(15,15, true, false);
        node n1 = new node(25,15);
        node n2 = new node(35,15);
        node n3 = new node(15,25);
        node n4 = new node(25,25);
        node n5 = new node(35,25);
        node n6 = new node(15,35);
        node n7 = new node(25,35);
        node goal = new node(35,35, false, true);

        start.addConnections(new node[]{n1,n3,n4});
        n1.addConnections(new node[]{start,n2,n3,n4,n5});
        n2.addConnections(new node[]{n1,n4,n5});
        n3.addConnections(new node[]{start,n1,n4,n6,n7});
        n4.addConnections(new node[]{start,n1,n2,n3,n5,n6,n7});
        n5.addConnections(new node[]{n1,n2,n4,n7,goal});
        n6.addConnections(new node[]{n3,n4,n7});
        n7.addConnections(new node[]{n6,n3,n4,n5,goal});

        nodes.add(start);
        nodes.add(n1);
        nodes.add(n2);
        nodes.add(n3);
        nodes.add(n4);
        nodes.add(n5);
        nodes.add(n6);
        nodes.add(n7);
        nodes.add(goal);
        for(int i=0;i<nodes.size();i++) {
            drawNode(nodes.get(i));
        }
        ArrayList<node> open = new ArrayList<>();
        ArrayList<node> closed = new ArrayList<>();
        open.add(start);
        n1.setParent(start);
        n3.setParent(start);
        n4.setParent(start);
        System.out.println(n1.f_cost(goal));
        System.out.println(n3.f_cost(goal));
        System.out.println(n4.f_cost(goal));

        while(open.size()!=0) {
            float[] f_costs = new float[open.size()];
            for(int i=0;i<f_costs.length;i++) {
                f_costs[i]=open.get(i).f_cost(goal);
            }
            node currentNode = open.get(getSmallestIndex(f_costs));
            open.remove(getSmallestIndex(f_costs));
            closed.add(currentNode);
            if(currentNode.isGoal()) {
                System.out.println("FOUND GOAL!");
                System.out.println(currentNode.getParent().getX());
                System.out.println(currentNode.getParent().getY());
                break;
            }
            for(node n : currentNode.getConnetions()) {
                boolean toSkip=false;
                //if(n.equals(goal)) {System.out.println("FOUND GOAL!");break;}
                if(closed.contains(n)) {continue;}
                if(!open.contains(n)) {
                    n.setParent(currentNode);
                    float f_value = n.f_cost(goal);
                    open.add(n);
                }
            }
            closed.add(currentNode);
        }


    }


    public void draw() {

    }
    private void drawNode(node n) {
        fill(color(0,0,0));
        if(n.isStart()) {
            fill(color(0,255,0));
        }
        if(n.isGoal()) {
            fill(color(255,0,0));
        }
        circle(n.getX()*10, n.getY()*10, 16);

    }
    private int getSmallestIndex(float[] n) {
        int min=0;
        for(int i=0;i<n.length;i++) {
            if(n[i]<n[min]) {
                min=i;
            }
        }
        return min;
    }
}
