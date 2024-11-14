import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class RasterizationApp extends JFrame {
    private JPanel drawPanel;
    private JTextField x0Field, y0Field, x1Field, y1Field;
    private JRadioButton stepByStepBtn, ddaBtn, bresenhamBtn, bresenhamCircleBtn;
    private final List<Point> pixels = new ArrayList<>();
    private final JLabel timeLabel;

    public RasterizationApp() {
        setTitle("Растеризация");
        setSize(1256, 713);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        drawPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawGrid(g);
                drawPixels(g);
            }
        };
        drawPanel.setBackground(Color.WHITE);

        x0Field = new JTextField("0", 5);
        y0Field = new JTextField("0", 5);
        x1Field = new JTextField("3", 5);
        y1Field = new JTextField("3", 5);

        stepByStepBtn = new JRadioButton("Пошаговый алгоритм");
        ddaBtn = new JRadioButton("Алгоритм ЦДА");
        bresenhamBtn = new JRadioButton("Алгоритм Брезенхема");
        bresenhamCircleBtn = new JRadioButton("Алгоритм Брезенхема (окружность)");

        ButtonGroup algorithmGroup = new ButtonGroup();
        algorithmGroup.add(stepByStepBtn);
        algorithmGroup.add(ddaBtn);
        algorithmGroup.add(bresenhamBtn);
        algorithmGroup.add(bresenhamCircleBtn);
        stepByStepBtn.setSelected(true);

        JButton drawButton = new JButton("Нарисовать");
        drawButton.addActionListener(new DrawButtonListener());

        JPanel controlPanel = new JPanel();
        controlPanel.add(new JLabel("x0:"));
        controlPanel.add(x0Field);
        controlPanel.add(new JLabel("y0:"));
        controlPanel.add(y0Field);
        controlPanel.add(new JLabel("x1:"));
        controlPanel.add(x1Field);
        controlPanel.add(new JLabel("y1:"));
        controlPanel.add(y1Field);
        controlPanel.add(stepByStepBtn);
        controlPanel.add(ddaBtn);
        controlPanel.add(bresenhamBtn);
        controlPanel.add(bresenhamCircleBtn);
        controlPanel.add(drawButton);

        add(drawPanel, BorderLayout.CENTER);
        add(controlPanel, BorderLayout.SOUTH);
        
        timeLabel = new JLabel("Time: 0 ms");
        controlPanel.add(timeLabel);
    }

    private void drawGrid(Graphics g) {
        g.setColor(Color.LIGHT_GRAY);
        int width = drawPanel.getWidth();
        int height = drawPanel.getHeight();
        for (int i = 0; i < width; i += 20) {
            g.drawLine(i, 0, i, height);
        }
        for (int i = 0; i < height; i += 20) {
            g.drawLine(0, i, width, i);
        }
        g.setColor(Color.BLACK);
        g.drawLine(width / 2, 0, width / 2, height);
        g.drawLine(0, height / 2, width, height / 2);
    }

    private void drawPixels(Graphics g) {
        int offsetX = drawPanel.getWidth() / 2;
        int offsetY = drawPanel.getHeight() / 2;

        for (Point p : pixels) {
            int x = offsetX + p.x * 20;
            int y = offsetY - p.y * 20;
            g.fillRect(x, y - 20, 20, 20);
        }
    }

    private class DrawButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            pixels.clear();
            drawPanel.repaint();

            int x0 = Integer.parseInt(x0Field.getText());
            int y0 = Integer.parseInt(y0Field.getText());
            int x1 = Integer.parseInt(x1Field.getText());
            int y1 = Integer.parseInt(y1Field.getText());

            long startTime = System.nanoTime();

            if (stepByStepBtn.isSelected()) {
                drawStepByStep(x0, y0, x1, y1);
            } else if (ddaBtn.isSelected()) {
                drawDDA(x0, y0, x1, y1);
            } else if (bresenhamBtn.isSelected()) {
                drawBresenham(x0, y0, x1, y1);
            } else if (bresenhamCircleBtn.isSelected()) {
                drawCircleBresenham(x0, y0, x1);
            }

            long endTime = System.nanoTime();
            double elapsedTimeMs = (endTime - startTime) / 1_000_000.0;
            timeLabel.setText(String.format("Time: %.3f ms", elapsedTimeMs));
            drawPanel.repaint();
        }
    }

    private void drawPixel(int x, int y) {
        pixels.add(new Point(x, y));
    }

    public void drawStepByStep(int x0, int y0, int x1, int y1) {
        int dx = x1 - x0;
        int dy = y1 - y0;

        if (Math.abs(dx) > Math.abs(dy)) {
            stepByStepLineXBased(x0, y0, x1, y1);
        } else {
            stepByStepLineYBased(x0, y0, x1, y1);
        }
    }

    private void stepByStepLineXBased(int x0, int y0, int x1, int y1) {
        int dx = x1 - x0;
        int dy = y1 - y0;

        if (dx == 0) {
            for (int y = Math.min(y0, y1); y <= Math.max(y0, y1); y++) {
                drawPixel(x0, y);
            }
            return;
        }

        double K = (double) dy / dx;
        double b = y0 - K * x0;
        int stepX = x1 > x0 ? 1 : -1;

        int x = x0;
        while ((stepX > 0 && x <= x1) || (stepX < 0 && x >= x1)) {
            int y = (int) roundDown(K * x + b);
            drawPixel(x, y);
            x += stepX;
        }
    }

    private void stepByStepLineYBased(int x0, int y0, int x1, int y1) {
        int dx = x1 - x0;
        int dy = y1 - y0;

        if (dy == 0) {
            for (int x = Math.min(x0, x1); x <= Math.max(x0, x1); x++) {
                drawPixel(x, y0);
            }
            return;
        }

        double K = (double) dx / dy;
        double b = x0 - K * y0;
        int stepY = y1 > y0 ? 1 : -1;

        int y = y0;
        while ((stepY > 0 && y <= y1) || (stepY < 0 && y >= y1)) {
            int x = (int) roundDown(K * y + b);
            drawPixel(x, y);
            y += stepY;
        }
    }

    public static int roundDown(double number) {
        return (int) Math.floor(number);
    }

    private void drawDDA(int x0, int y0, int x1, int y1) {
        float dx = x1 - x0;
        float dy = y1 - y0;
        int steps = (int) Math.max(Math.abs(dx), Math.abs(dy));

        float xIncrement = dx / (float) steps;
        float yIncrement = dy / (float) steps;

        float x = x0;
        float y = y0;
        for (int i = 0; i <= steps; i++) {
            drawPixel(roundDown(x), roundDown(y));
            x += xIncrement;
            y += yIncrement;
        }
    }

    private void drawBresenham(int x0, int y0, int x1, int y1) {
        int dx = Math.abs(x1 - x0);
        int dy = Math.abs(y1 - y0);
        int sx = x0 < x1 ? 1 : -1;
        int sy = y0 < y1 ? 1 : -1;
        int err = dx - dy;

        while (true) {
            drawPixel(x0, y0);
            if (x0 == x1 && y0 == y1) break;
            int e2 = 2 * err;
            if (e2 > -dy) {
                err -= dy;
                x0 += sx;
            }
            if (e2 < dx) {
                err += dx;
                y0 += sy;
            }
        }
    }

    private void drawCircleBresenham(int xc, int yc, int r) {
        int x = 0;
        int y = r;
        int d = 3 - 2 * r;
        addCirclePixels(xc, yc, x, y);

        while (y >= x) {
            x++;
            if (d > 0) {
                y--;
                d = d + 4 * (x - y) + 10;
            } else {
                d = d + 4 * x + 6;
            }
            addCirclePixels(xc, yc, x, y);
        }
    }

    private void addCirclePixels(int xc, int yc, int x, int y) {
        drawPixel(xc + x, yc + y);
        drawPixel(xc - x, yc + y);
        drawPixel(xc + x, yc - y);
        drawPixel(xc - x, yc - y);
        drawPixel(xc + y, yc + x);
        drawPixel(xc - y, yc + x);
        drawPixel(xc + y, yc - x);
        drawPixel(xc - y, yc - x);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            RasterizationApp app = new RasterizationApp();
            app.setVisible(true);
        });
    }
}
