import javax.swing.*;
import java.awt.*;

public class main
{
    public void frame(int w, int h)
    {
        JFrame f = new JFrame("ray caster");
        start obj = new start();


        f.add(obj);
        f.setDefaultCloseOperation(f.EXIT_ON_CLOSE);
        f.pack();
        f.setSize(obj.win_w, obj.win_h);

        f.setLocationRelativeTo(null);
        f.setVisible(true);
        f.addKeyListener(obj);


    }

    public static void main(String[] args)
    {
        //frame width and height.

        start s = new start();
        main obj = new main();
        obj.frame(s.win_w, s.win_h);
        


    }

}
