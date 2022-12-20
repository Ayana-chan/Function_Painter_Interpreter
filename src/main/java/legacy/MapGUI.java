package legacy;

import javax.swing.*;
import java.awt.*;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.*;

public class MapGUI {
    public MapGUI() {
        init();
    }

    public void init() {

        JFrame window = new JFrame("函数图");
        window.setSize(1000, 800);
        window.setDefaultCloseOperation(window.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setVisible(true);
        final String[] check = {""};


        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension s = kit.getScreenSize();
        int screenWidth = s.width/2;
        int screenHeight = s.height/2;

        window.setLocation(screenWidth / 4, screenHeight / 4);
        class mycanvas extends Canvas{
            void drawAL(int sx, int sy, int ex, int ey,Graphics2D g2) {
                double H = 10; // 箭头高度
                double L = 4; // 底边的一半
                int x3 = 0;
                int y3 = 0;
                int x4 = 0;
                int y4 = 0;
                double awrad = Math.atan(0); // 箭头角度
                double arraow_len = Math.sqrt(L * L + H * H); // 箭头的长度
                double[] arrXY_1 = rotateVec(ex - sx, ey - sy, awrad, true, arraow_len);
                double[] arrXY_2 = rotateVec(ex - sx, ey - sy, -awrad, true, arraow_len);
                double x_3 = ex - arrXY_1[0]; // (x3,y3)是第一端点
                double y_3 = ey - arrXY_1[1];
                double x_4 = ex - arrXY_2[0]; // (x4,y4)是第二端点
                double y_4 = ey - arrXY_2[1];

                Double X3 = new Double(x_3);
                x3 = X3.intValue();
                Double Y3 = new Double(y_3);
                y3 = Y3.intValue();
                Double X4 = new Double(x_4);
                x4 = X4.intValue();
                Double Y4 = new Double(y_4);
                y4 = Y4.intValue();
                // 画线
                g2.drawLine(sx, sy, ex, ey);
                //
                GeneralPath triangle = new GeneralPath();
                triangle.moveTo(ex, ey);
                triangle.lineTo(x3, y3);
                triangle.lineTo(x4, y4);
                triangle.closePath();
                //实心箭头
                g2.fill(triangle);
                //非实心箭头
                //g2.draw(triangle);
            }
            public  double[] rotateVec(int px, int py, double ang, boolean isChLen, double newLen) {
                double mathstr[] = new double[2];
                // 矢量旋转函数，参数含义分别是x分量、y分量、旋转角、是否改变长度、新长度
                double vx = px * Math.cos(ang) - py * Math.sin(ang);
                double vy = px * Math.sin(ang) + py * Math.cos(ang);
                if (isChLen) {
                    double d = Math.sqrt(vx * vx + vy * vy);
                    vx = vx / d * newLen;
                    vy = vy / d * newLen;
                    mathstr[0] = vx;
                    mathstr[1] = vy;
                }
                return mathstr;
            }
            @Override
            public void paint(Graphics g){
                //绘制图形

                /*
                //test
                  g.setColor(Color.BLACK);
                    g.drawRect(100,100,150,100);
                 */
                super.paint(g);
                Graphics2D g2=(Graphics2D) g;
                g2.setPaint(Color.black);
                drawAL(10, 10, 10, 10, g2);

            }
        }

        mycanvas drawArea =new mycanvas();
        drawArea.setPreferredSize(new Dimension(100,100));
        window.add(drawArea);





        while(window.isVisible()){
            try{
                Thread.sleep(1000);//死循环中降低CPU占用
            } catch(Exception e){
                e.printStackTrace();
            }
        }
    }
}
