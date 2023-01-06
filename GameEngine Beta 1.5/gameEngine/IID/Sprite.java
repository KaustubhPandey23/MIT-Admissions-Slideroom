package gameEngine.IID;
import java.awt.Image;

public class Sprite {
    
    private Animation a;
    private float x,y,vx,vy;
    
    public Sprite(Animation a){
        this.a=a;
    }
    
    public void update(long timePassed){
        x+=vx*timePassed;
        y+=vy*timePassed;
        a.update(timePassed);
    }
    
    public float getX(){
        return x;
    }
    
    public float getY(){
        return y;
    }
    
    public void setX(float x){
        this.x=x;
    }
    
    public void setY(float y){
        this.y=y;
    }
    
    public int getWidth(){
        return a.getImage().getWidth(null);
    }
    
    public int getHeigth(){
        return a.getImage().getHeight(null);
    }
    
    public float getVelocityX(){
        return vx;
    }
    
    public float getVelocityY(){
        return vy;
    }
    
    public void setVelocityX(float vx){
        this.vx=vx;
    }
    
    public void setVelocityY(float vy){
        this.vy=vy;
    }
    
    public Image getImage(){
        return a.getImage();
    }
    
}
