package pathfinder;
import processing.core.PApplet;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class nodeCreator extends PApplet{
    public static void main(String[] args) {PApplet.main("pathfinder.nodeCreator");}
    public void settings() {size(800, 800);}
    // -----------------------------------------------------------------------------------------------------------------
    Scanner inputScanner = new Scanner(System.in);
    int numOfClasses;
    ArrayList<node> datas;
    PrintWriter writer;
    public void setup() {
        System.out.println("Number of nodes: ");
        numOfClasses = inputScanner.nextInt();
        onKey=0;
        datas = new ArrayList<>();
        inputScanner.nextLine();
    }
    int onKey;
    public void draw() {
        for(int i=0;i<datas.size();i++) {
            drawNode(datas.get(i), i);
        }
    }
    public void keyPressed() {

        if(datas.size()==numOfClasses && key=='q') {

            datas.get(0).setStartGoal(true, false);
            datas.get(datas.size()-1).setStartGoal(false,true);
            for(int i=0;i<datas.size();i++) {

                System.out.println("Neighbors: ");

                String input=inputScanner.nextLine();

                node[] Neighbors = Arrays.stream(input.split(",")).map(x -> datas.get(Integer.parseInt(x))).toArray(node[]::new);
                datas.get(i).addConnections(Neighbors);
            }
            ArrayList<node> open = new ArrayList<>();
            ArrayList<node> closed = new ArrayList<>();
            node goal = datas.get(datas.size()-1);

            open.add(datas.get(0));
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
                    raceToBottom(currentNode);
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
    }
    public void exit() {
        try {
            writer = new PrintWriter("outputData.txt");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        for(int i=0;i<datas.size();i++){
//            writer.println();
//            for(int j=0;j<datas.get(i).data.length;j++){
//                writer.print(datas.get(i).data[j] + ",");
//            }
//            writer.print(datas.get(i).label);

        }
        writer.flush();
        writer.close();
    }
    public void mousePressed() {
        //datas.add(new dataPoint(new float[]{mouseX, mouseY}, onKey));

        datas.add(new node(mouseX, mouseY));

        for(int i=0;i<datas.size();i++) {
            drawNode(datas.get(i), i);
        }



    }
    private void drawNode(node n, int index) {
        fill(color(100,0,0));
        if(n.isStart()) {
            fill(color(0,255,0));
        }
        if(n.isGoal()) {
            fill(color(255,0,0));
        }
        circle(n.getX(), n.getY(), 16);
        text(index, n.getX()-10, n.getY()-10);
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
    private node raceToBottom(node n) {
        System.out.println("X: "+ n.getX() +"  Y: "+ n.getY());
        if(n.isStart()) {
            return n;
        }
        line(n.getX(),n.getY(),n.getParent().getX(),n.getParent().getY());
        return raceToBottom(n.getParent());
    }
}
