console.log("hello");
params = new URLSearchParams(window.location.search);
class node {
  constructor(x,y,start=false,end=false) {
    this.x=x;
    this.y=y;
    this.isStart=start;
    this.isGoal=end;
    this.connections=[];
    this.parent;
  }
  distTo(n) {
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
function drawNode(n, index) {
  pen.fillStyle="rgb(100,0,0)";
  if(n.isStart) {
    pen.fillStyle="rgb(0,255,0)";
  }
  if(n.isGoal) {
    pen.fillStyle="rgb(255,0,0)";
  }
  pen.beginPath();
  pen.ellipse(n.x,n.y,4,4,0,0,Math.PI*2);
    pen.font="30px serif"
  pen.fillText(index,n.x+10,n.y);
  pen.fill();
}
function h_cost(n,sum) {
  if(n.isStart) {return sum;}
  return sum+h_cost(n.parent,n.distTo(n.parent));
}
var canvas = document.getElementById('canvas');
var pen = canvas.getContext('2d');


const width=760;
const height=475;
pen.strokeStyle="rgb(0,255,0)";
pen.lineWidth=5;
var datas = [];


startingLocation=params.get("start");
var goingTo = prompt("Going to: ");
var onKey=0;
var start=0;
var goal=0;
for(var i=0;i<nodes.size;i++) {
    var coords=new node(nodes.get("a"+i)[0],nodes.get("a"+i)[1]);
    //drawNode(coords,i);
    
    if(i==startingLocation.substring(1)) {coords.isStart=true;var start =i;}
    if(i==goingTo.substring(1)) {coords.isGoal=true;var goal = i;}

    datas.push(coords);
}
for(var i=0;i<datas.length;i++) {
    var neighbor=neighbors.get("a"+i);
    for(var j=0;j<neighbor.length;j++) {
        datas[i].connections.push(datas[neighbor[j].substring(1)]);
    }
}
var open = [];
var closedd = new Array();

open.push(datas[start]);

while(open.length!=0) {

  var f_costs = [];
  for(var i=0;i<open.length;i++) {
    f_costs[i]=open[i].f_cost(datas[goal]);
  }
  var currentNode=open[getSmallestIndex(f_costs)];
  open.splice(getSmallestIndex(f_costs));
  console.log(currentNode);
  closedd.push(currentNode);
  if(currentNode.isGoal) {
    console.log("Found goal!");
    raceToBottom(currentNode);
    break;

  }

  for(var i=0;i<currentNode.connections.length;i++) {
    var n = currentNode.connections[i];
    if(closedd.filter(function(e){return e.x==n.x&&e.y==n.y;}).length>0) {
      continue;
    }
    if(open.filter(function(ee){return ee.x==n.x&&ee.y==n.y;}).length<1) {
      n.parent=currentNode;
      open.push(n);

    }
  }
}



function getSmallestIndex(n) {
  var min=0;
  for(var i=0;i<n.length;i++) {
    if(n[i]<n[min]) {
      min=i;
    }
  }
  return min;
}
function raceToBottom(n) {
  if(n.isStart) {return n;}
  pen.beginPath();
    pen.setLineDash([10,10]);
  pen.moveTo(n.x,n.y);
  pen.lineTo(n.parent.x,n.parent.y);
  pen.stroke();
  return raceToBottom(n.parent);
}