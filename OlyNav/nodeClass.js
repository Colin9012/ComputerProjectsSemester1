class node {
  constructor(x,y,start=false,end=false,floor="a") {
    this.x=x;
    this.y=y;
    this.isStart=start;
    this.isGoal=end;
    this.connections=[];
    this.parent;
    this.gscore;
    this.floor;
  }
  distTo(n) {
    if(this.floor!=n.floor) {return 0;}
    return Math.sqrt(Math.pow(n.x-this.x,2)+Math.pow(n.y-this.y,2));
  }
  f_cost(end) {
    if(this.isStart) {return 0;}
    var gCost = end.distTo(this);
    var hCost = h_cost(this,0);
    
    return gCost+hCost;
  }
  equals(n) {
    if(this.x==n.x && this.y==n.y) {return true;}
    return false;
  }
}