import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Rectangle2D;


// i have made the "3d view". before continuing, please review the code and math to fully understand how it works. i believe i am doing a good job so far.
class start extends JComponent implements KeyListener {

    //frame width and height.
    public int win_w = 512 * 2;
    public int win_h = 512;

    public int map_w = 16;
    public int map_h = 16;



    //walls represent 1 and 0 represents a space. this is slightly different from the tutorial.
    int[][] map = {
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
            {1, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 0, 0, 0, 1},
            {1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1},
            {1, 0, 0, 0, 0, 0, 1, 0, 0, 1, 1, 1, 1, 1, 1, 1},
            {1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1},
            {1, 0, 0, 0, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 1},
            {1, 0, 0, 0, 1, 0, 0, 0, 1, 1, 1, 1, 1, 0, 0, 1},
            {1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 1},
            {1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 1, 1, 1, 1, 1},
            {1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 1},
            {1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 1},
            {1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 1},
            {1, 0, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 1},
            {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}};

    private float player_x = 3.456F;
    private float player_y = 2.345F;
    public float player_a = 1.523F;
    private float fov = (float) (Math.PI / 3);
    //player_a is the direction of the player's gaze.
    //fov is the players field of view(i believe it is a radian measure equivalent to 60 degrees).

    @Override
    public void keyTyped(KeyEvent e) {

    }

    public void draw_rectangle(Graphics g, int x, int y, int w, int h, Color c) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2d.setColor(c);
        g2d.fillRect(x, y, w, h);
        repaint();





    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;


        //the rectangle width and height is proportional to the dimensions of the screen and the map.
        //we multiply map width by two to compensate for the increased width from 512 to 1024.

        int rect_w = win_w / (map_w * 2);
        int rect_h = win_h / map_h;



    //double nested for loop.
    for (int j = 0; j < map_h; j++) {
        for (int i = 0; i < map_w; i++) {
            if (map[j][i] == 0) {
                //continue if it is a space. we do not want to draw a rectangle if it is a space.
                continue;
            }

            //i think i figured this out in my brain.
            int rect_x = i * rect_w;
            int rect_y = j * rect_h;
            draw_rectangle(g, rect_x, rect_y, rect_w, rect_h, Color.GRAY);
        }
    }

    //draw player position on the screen.
    draw_rectangle(g, (int) (player_x * rect_w), (int) (player_y * rect_h), 5, 5, Color.WHITE);

    //work in progress
        /*if you see the first for loop, i; it goes on the x axis(width). we divide by 2 to compensate for the multiplication above.
            we draw an i amount of rays in the horizontal direction.
         */

    for (int i = 0; i < win_w / 2; i++) {
        float angle = player_a - fov / 2 + fov * i / (float) (win_w / 2);


        //i believe t is our distance.
        for (float t = 0; t < 20; t += .05) {
            //these are our angles. cos is for x as sin is for y.
            double cx = player_x + t * Math.cos(angle);
            double cy = player_y + t * Math.sin(angle);


            int pix_x = (int) (cx * rect_w);
            int pix_y = (int) (cy * rect_h);

            g.setColor(Color.GRAY);
            //this draws our visibility cone. if we don't draw it, it will still work. this just gives a visible view of our cone.
            g.drawLine(pix_x, pix_y, pix_x, pix_y);

                /*
                if our ray touches a wall, we will draw a vertical segment to create the illusion of 3D.

                 */
            if (map[(int) (cy)][(int) (cx)] != 0) {
                //this is inversely proportional. when t gets bigger, the column height gets smaller.
                int column_height = (int) (win_h / t * Math.cos(angle - player_a));
                draw_rectangle(g2d, win_w / 2 + i, win_h / 2 - column_height / 2, 1, column_height, Color.CYAN);

                break;
            }


        }

    }

}




    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if(key == KeyEvent.VK_A)
        {
            player_a += (2 * Math.PI / 360);
            System.out.println(player_a);

        }

        if(key == KeyEvent.VK_W)
        {
            //yes!!!! i did it by myself!!
            player_x +=  Math.cos(player_a);
            player_y += Math.sin(player_a);
        }
        repaint();

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}




