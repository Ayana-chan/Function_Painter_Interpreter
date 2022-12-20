package drawer;

import javafx.util.Pair;

import javax.swing.*;
import java.awt.*;
import java.util.Set;

public class GUIPainter {
    public static void paintGUI(Set<Pair<Integer,Integer>> points){
        MyFrame window = new MyFrame(points);
        window.setSize(1000, 800);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setVisible(true);

        //维持窗口
        while(window.isVisible()){
            try{
                Thread.sleep(500);
            } catch(Exception e){
                e.printStackTrace();
            }
        }
    }

    static class MyFrame extends JFrame{
        public int pointSize=4;
        private Set<Pair<Integer,Integer>> points;

        public MyFrame(Set<Pair<Integer,Integer>> points){
            super("Function Painter");
            this.points=points;
        }
        @Override
        public void paint(Graphics g){
            super.paint(g);
            g.setColor(Color.RED);
            for(Pair<Integer,Integer> p:points) {
                g.fillOval(p.getKey(),p.getValue(),pointSize,pointSize);
            }
        }
    }

}
