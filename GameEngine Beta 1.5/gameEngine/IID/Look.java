package gameEngine.IID;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Robot;
import java.awt.Window;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import javax.swing.ImageIcon;
import javax.swing.SwingUtilities;

public class Look extends Core implements MouseMotionListener,KeyListener {
    public static void main(String[]args){
        new Look().run();
    }
    
    private Image bg;
    private Robot robot;
    private Point mouse,center,image;
    private boolean centering;
    
    
    public void init(){
        super.init();
        mouse=new Point();
        center=new Point();
        image=new Point();
        centering=false;
        
        try{
            robot=new Robot();
            recenterMouse();
            mouse.x=center.x;
            mouse.y=center.y;
        }catch(Exception e){
            System.out.println("Exception 1");
        }
        Window w=s.getFullScreenWindow();
        w.addMouseMotionListener(this);
        w.addKeyListener(this);
        bg=new ImageIcon("C:\\Users\\ACER\\Desktop\\images\\background.png").getImage();
        }
    
    public synchronized void draw(Graphics2D g){
        int w=s.getWidth();
        int h=s.getHeight();
        image.x%=w;
        image.y%=h;
        if(image.x<0){
            image.x+=w;
        }
        if(image.y<0){
            image.y+=h;
        }
        int x=image.x;
        int y=image.y;
        g.setColor(s.getFullScreenWindow().getBackground());
        g.fillRect(0, 0, s.getWidth(), s.getHeight());
        g.drawImage(bg,x,y,null);
        g.drawImage(bg,x-w,y,null);
        g.drawImage(bg,x,y-h,null);
        g.drawImage(bg,x-w,y-h,null);
    }
    
    private synchronized void recenterMouse(){
        Window w=s.getFullScreenWindow();
        if(robot!=null&&w.isShowing()){
            center.x=w.getWidth()/2;
            center.y=w.getHeight()/2;
            SwingUtilities.convertPointToScreen(center, w);
            centering=true;
            robot.mouseMove(center.x,center.y);
        }
    }

    public void mouseDragged(MouseEvent e) {
        mouseMoved(e);
    }
    
    public synchronized void mouseMoved(MouseEvent e){
        if(centering&&center.x==e.getX()&&center.y==e.getY()){
            centering=false;
        }else{
            int dx=e.getX()-mouse.x;
            int dy=e.getY()-mouse.y;
            image.x+=dx;
            image.y+=dy;
            recenterMouse();
        }
        mouse.x=e.getX();
        mouse.y=e.getY();
    }
    
    public void keyPressed(KeyEvent e){
        int keyCode=e.getKeyCode();
        if(keyCode==KeyEvent.VK_ESCAPE){
             stop();
        }
    }
    
    public void keyReleased(KeyEvent e){}
    
    public void keyTyped(KeyEvent e){}
}
