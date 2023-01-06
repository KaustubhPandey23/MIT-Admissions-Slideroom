package gameEngine.IID;
import java.awt.Image;
import java.util.ArrayList;

public class Animation {
    private ArrayList scenes;
    private long totalTime;
    private static int sceneIndex;
    private static long movieTime;
    
    public Animation(){
        scenes=new ArrayList();
        totalTime=0;
        start();
    }
    
    public synchronized void addScene(Image i,long t){
        totalTime+=t;
        scenes.add(new OneScene(i,totalTime));
    }
    
    public synchronized void start(){
        movieTime=0;
        sceneIndex=0;
    }
    
    public synchronized void update(long timePassed){
        if(scenes.size()>1){
            movieTime+=timePassed;
            if(movieTime>=totalTime){
                movieTime=0;
                sceneIndex=0;
            }
            while(movieTime>=getScene(sceneIndex).endTime){
                sceneIndex++;
            }
        }
    }
    
    public void setSceneIndex(int i){
        sceneIndex=i;
    }
    
    public void setMovieTime(long i){
        movieTime=i;
    }
    
    public synchronized Image getImage(){
        if(scenes.size()==0)
            return null;
        else
            return getScene(sceneIndex).pic;
    }
    
    private OneScene getScene(int x){
        return (OneScene)scenes.get(x);
    }
    
    private class OneScene{
        Image pic;
        long endTime;
        
        public OneScene(Image pic, long endTime){
            this.pic=pic;
            this.endTime=endTime;
            
        }
        
    }
    
}
