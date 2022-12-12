package drawer;

import javax.swing.*;
import java.awt.*;

class Draw extends JPanel {

    private final long NUMBER = 100000;    // 十万
    private final int INTERVAL = 2;
    public static final int initialX = 3000;
    public static final int initialY = 1500;

    @Override
    public void paint(Graphics g) {
        line(g);
    }

    private void line(Graphics graphics) {

        char direction = 'W';

        int x = initialX / 2;
        int y = initialY / 2;


    }

}

