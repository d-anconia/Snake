import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.LinkedList;

public class SnakeCanvas extends Canvas implements Runnable, KeyListener{

    private final int BOX_HEIGHT = 10;
    private final int BOX_WIDTH = 10;
    private final int GRID_WIDTH = 50; // number of boxes
    private final int GRID_HEIGHT = 50;// therefore the total area is 500*500

    private LinkedList<Point> snake;
    private Point fruit;
    private int direction = Direction.NO_DIRECTION;

    private Thread runThread;
    private Graphics globalGraphics;

    public void paint(Graphics g){
        this.setPreferredSize(new Dimension(640,480));
        snake  = new LinkedList<Point>();
        snake.add(new Point(3,1));
        snake.add(new Point(3,2));
        snake.add(new Point(3,3));
        fruit = new Point(0,0);
        globalGraphics = g.create();
        this.addKeyListener(this);
        if(runThread == null){
            runThread = new Thread(this);
            runThread.start();
        }
    }

    public void draw(Graphics g) {
        g.clearRect(0,0,BOX_WIDTH*GRID_WIDTH,BOX_HEIGHT*GRID_HEIGHT);
        drawGrid(g);
        drawSnake(g);
        drawFruit(g);
    }
    public void Move(){
        Point head = snake.peekFirst();
        Point newPoint = head;
        switch (direction){
            case Direction.NORTH:
                newPoint = new Point(head.x, head.y-1);
                break;
            case Direction.SOUTH:
                newPoint = new Point(head.x, head.y+1);
                break;
            case Direction.WEST:
                newPoint = new Point(head.x-1, head.y);
                break;
            case Direction.EAST:
                newPoint = new Point(head.x + 1, head.y);
                break;
        }
        snake.remove(snake.peekLast());
        if(newPoint.equals(fruit)){

        }else if(newPoint.x < 0 || newPoint.x > GRID_WIDTH){

        }else if(newPoint.y < 0 || newPoint.y > GRID_HEIGHT){

        }else if(snake.contains(newPoint)){

        }
        snake.push(newPoint);
    }

    public void drawGrid(Graphics g) {
        // drawing an outside rect
        g.drawRect(0, 0, GRID_WIDTH * BOX_WIDTH, GRID_HEIGHT * BOX_HEIGHT);
        // drawing the  vertical lines
        for (int x = BOX_WIDTH; x < GRID_WIDTH * BOX_WIDTH; x += BOX_WIDTH) {
            g.drawLine(x, 0, x, BOX_HEIGHT * GRID_HEIGHT);
        }
        for(int y = BOX_HEIGHT; y < GRID_HEIGHT*BOX_HEIGHT; y+= BOX_HEIGHT){
            g.drawLine(0, y, BOX_WIDTH * GRID_WIDTH, y);
        }
    }
    public void drawSnake(Graphics g){
        g.setColor(Color.GREEN);
        for(Point p : snake){
            g.fillRect(p.x * BOX_WIDTH, p.y*BOX_HEIGHT, BOX_WIDTH, BOX_HEIGHT);
        }
        g.setColor(Color.BLACK);
    }
    public void drawFruit(Graphics g){
        g.setColor(Color.RED);
        g.fillOval(fruit.x*BOX_WIDTH, fruit.y*BOX_HEIGHT, BOX_WIDTH, BOX_HEIGHT);
        g.setColor(Color.BLACK);
    }
    @Override
    public void run(){
        while(true){
            Move();
            draw(globalGraphics);
            try{
                Thread.currentThread();
                Thread.sleep(100);
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()){
            case KeyEvent.VK_UP:
                direction = Direction.NORTH;
                break;
            case KeyEvent.VK_DOWN:
                direction = Direction.SOUTH;
                break;
            case KeyEvent.VK_LEFT:
                direction = Direction.WEST;
                break;
            case KeyEvent.VK_RIGHT:
                direction = Direction.EAST;
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
