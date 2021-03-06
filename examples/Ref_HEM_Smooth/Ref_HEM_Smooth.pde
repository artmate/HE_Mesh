import wblut.core.*;
import wblut.geom.*;
import wblut.hemesh.*;
import wblut.math.*;
import wblut.processing.*;
import java.util.List;

HE_Mesh mesh;
WB_Render3D render;
int counter;


void setup() {
  size(1000, 1000, P3D);  
  smooth(8);
  textAlign(CENTER);
  render=new WB_Render3D(this);
 counter=0;
  mesh=new HE_Mesh(new HEC_Beethoven());
  mesh.scale(10);

}

void draw() {
  background(0);
  translate(width/2, height/2);
  pointLight(204, 204, 204, 1000, 1000, 1000);
  text("Click for Laplacian smooth ("+counter+").",0,450);
  rotateY(mouseX*1.0f/width*TWO_PI);
  rotateX(mouseY*1.0f/height*TWO_PI);
  noFill();
  stroke(255, 0, 0);
 render.drawEdges(mesh);
  fill(255);
  noStroke();
  render.drawFaces(mesh);
  
}

void mousePressed(){
 HEM_Smooth smooth= new HEM_Smooth();
 mesh.modify(smooth);
 counter++;
}