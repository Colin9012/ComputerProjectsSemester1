package pathfinder;
import java.util.ArrayList;
import java.util.Arrays;

public class node {
    private float x;
    private float y;
    private boolean isStart=false;
    private boolean isGoal=false;
    private ArrayList<node> connections = new ArrayList<>();
    private node parent;
    public node(int x, int y) {
        this.x=x;
        this.y=y;
    }
    public node(int x, int y, boolean start, boolean end) {
        this.x=x;
        this.y=y;
        isStart=start;
        isGoal=end;
    }
    public float getX() {
        return this.x;
    }
    public void setStartGoal(boolean start, boolean goal) {
        isGoal=goal;
        isStart=start;
    }
    public boolean isStart() {
        return this.isStart;
    }
    public boolean isGoal() {
        return this.isGoal;
    }
    public float getY() {
        return this.y;
    }
    public void addConnections(node m) {
        this.connections.add(m);
    }

    public void addConnections(node[] m) {
        this.connections.addAll(Arrays.asList(m));
    }
    public void setParent(node m) {
        this.parent=m;
    }
    public node getParent() {
        return this.parent;
    }
    public static float h_cost(node n, float sum) {
        if(n.isStart) {return sum;}
        return sum+h_cost(n.parent, n.distTo(n.parent));
    }
    public float distTo(node n) {
        return (float) Math.sqrt(Math.pow(n.getX()-x,2)+Math.pow(n.getY()-y,2));
    }
    public float f_cost(node end) {
        if(isStart) {return 0;}
        float gCost = end.distTo(this);
        float hCost = h_cost(this, 0);

        return gCost+hCost;
    }
    public boolean equals(node n) {
        if(this.x==n.getX() && this.y==n.getY()) {return true;}
        return false;
    }
    public ArrayList<node> getConnetions() {
        return this.connections;
    }
}
