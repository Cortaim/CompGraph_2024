import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageProcessingApp extends JFrame {

    private JLabel imageLabel;
    private BufferedImage originalImage;
    private BufferedImage processedImage;

    public ImageProcessingApp() {
        setTitle("Image Processing App");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        imageLabel = new JLabel();
        JScrollPane scrollPane = new JScrollPane(imageLabel);
        add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        JButton loadButton = new JButton("Load Image");
        JButton contrastButton = new JButton("Apply Linear Contrast");
        JButton dilationButton = new JButton("Apply Dilation");
        JButton erosionButton = new JButton("Apply Erosion");

        buttonPanel.add(loadButton);
        buttonPanel.add(contrastButton);
        buttonPanel.add(dilationButton);
        buttonPanel.add(erosionButton);

        add(buttonPanel, BorderLayout.SOUTH);

        loadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadImage();
            }
        });

        contrastButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                applyLinearContrast();
            }
        });

        dilationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                applyMorphologicalOperation("dilation");
            }
        });

        erosionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                applyMorphologicalOperation("erosion");
            }
        });
    }


    private void loadImage() {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            try {
                originalImage = ImageIO.read(file);
                processedImage = originalImage;
                imageLabel.setIcon(new ImageIcon(originalImage));
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    private void applyLinearContrast() {
        if (processedImage == null) return;

        int width = processedImage.getWidth();
        int height = processedImage.getHeight();
        int min = 255, max = 0;

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                int pixel = new Color(processedImage.getRGB(i, j)).getRed();
                if (pixel < min) min = pixel;
                if (pixel > max) max = pixel;
            }
        }

        BufferedImage contrastedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                int pixel = new Color(processedImage.getRGB(i, j)).getRed();
                int newPixel = (pixel - min) * 255 / (max - min);
                Color newColor = new Color(newPixel, newPixel, newPixel);
                contrastedImage.setRGB(i, j, newColor.getRGB());
            }
        }

        processedImage = contrastedImage;
        imageLabel.setIcon(new ImageIcon(processedImage));
    }

    private void applyMorphologicalOperation(String operation) {
        if (processedImage == null) return;

        int width = processedImage.getWidth();
        int height = processedImage.getHeight();
        BufferedImage resultImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        int[][] structElem = {
            {0, 1, 0},
            {1, 1, 1},
            {0, 1, 0}
        };

        for (int i = 1; i < width - 1; i++) {
            for (int j = 1; j < height - 1; j++) {
                int resultPixel = operation.equals("dilation") ? 0 : 255;
                for (int x = -1; x <= 1; x++) {
                    for (int y = -1; y <= 1; y++) {
                        int pixel = new Color(processedImage.getRGB(i + x, j + y)).getRed();
                        if (structElem[x + 1][y + 1] == 1) {
                            if (operation.equals("dilation")) {
                                resultPixel = Math.max(resultPixel, pixel);
                            } else if (operation.equals("erosion")) {
                                resultPixel = Math.min(resultPixel, pixel);
                            }
                        }
                    }
                }
                Color newColor = new Color(resultPixel, resultPixel, resultPixel);
                resultImage.setRGB(i, j, newColor.getRGB());
            }
        }

        processedImage = resultImage;
        imageLabel.setIcon(new ImageIcon(processedImage));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ImageProcessingApp().setVisible(true);
            }
        });
    }
}
