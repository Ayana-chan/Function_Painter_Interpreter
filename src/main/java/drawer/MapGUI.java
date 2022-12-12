package drawer;

import javax.swing.*;
import java.awt.*;

public class MapGUI {

    public MapGUI() {
        init();
    }

    private void init() {
        JFrame window = new JFrame("函数图");
        window.setSize(1000, 500);
        window.setDefaultCloseOperation(window.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setVisible(true);
        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension s = kit.getScreenSize();
        int screenWidth = s.width;
        int screenHeight = s.height;
        window.setLocation(screenWidth / 4, screenHeight / 4);

        Draw draw = new Draw();
        Dimension wh = new Dimension();
        wh.width = Draw.initialX;
        wh.height = Draw.initialY;
        draw.setPreferredSize(wh);

        ScrollPane windowNext = new ScrollPane();
        windowNext.add(draw);

        window.add(windowNext);
    }

}
