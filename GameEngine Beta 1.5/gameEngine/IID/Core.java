package gameEngine.IID;
import java.awt.Color;
import java.awt.DisplayMode;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Window;

public abstract class Core{
    
    private static DisplayMode[]modes={
        new DisplayMode(1500,900,32,0),
        new DisplayMode(1500,900,16,0),
        new DisplayMode(1500,900,8,0),
        new DisplayMode(800,600,32,0),
        new DisplayMode(800,600,24,0),
        new DisplayMode(800,600,16,0),
        new DisplayMode(640,480,32,0),
        new DisplayMode(640,480,24,0),
        new DisplayMode(640,480,16,0)
    };
    private static String fString="Arial";
    private static int fStyle=Font.PLAIN;
    private static int fSize=20;
    private static Color bC=Color.WHITE;
    private static Color fC=Color.BLACK;
    private boolean running;
    protected ScreenManager s;
    
    public void setDisModes(DisplayMode[]dm){
        modes=dm;
    }
    
    public void setFString(String fS){
        fString=fS;
    }
    
    public void setFStyle(int fS){
        fStyle=fS;
    }
    
    public void setFSize(int fS){
        fSize=fS;
    }
    
    public void stop(){
        running=false;
    }

    public void run() {
        try{
            init();
            gameloop();
        }finally{
            s.restoreScreen();
        }
    }
    
    public void init(){
        s=new ScreenManager();
        DisplayMode dm=s.findFirstCompatibleMode(modes);
        s.setFullScreen(dm);
        Window w=s.getFullScreenWindow();
        w.setFont(new Font(fString,fStyle,fSize));
        w.setBackground(bC);
        w.setForeground(fC);
        running=true;
    }

    private void gameloop() {
        long startingTime=System.currentTimeMillis();
        long cumTime=startingTime;
        while(running){
            long timePassed=System.currentTimeMillis()-cumTime;
            cumTime+=timePassed;
            update(timePassed);
            Graphics2D g=s.getGraphics();
            draw(g);
            g.dispose();
            s.update();
            try{
                Thread.sleep(20);
            }catch(Exception ex){}
        }
    }
    
    public void loadImages(Animation a,Image[]images,long t){
        for(Image x:images)
            a.addScene(x, t);
    }
    
    public void loadImages(Animation a,Image[]images,long[]t){
        int i=images.length;
        for(int j=0;j<i;j++)
            a.addScene(images[j], t[j]);
    }
    
    public void update(long timePassed){
    }
    
    public abstract void draw(Graphics2D g);
}