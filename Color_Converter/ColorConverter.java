import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class ColorConverter extends JFrame {

    private JTextField rgbInput;
    private JTextField cmykInput;
    private JTextField hslInput;

    private JSlider rSlider, gSlider, bSlider;
    private JSlider cSlider, mSlider, ySlider, kSlider;
    private JSlider hSlider, sSlider, lSlider;

    private JPanel colorPanel;

    public ColorConverter() {
        setTitle("RGB <-> CMYK <-> HSL Converter");
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        JLabel rgbLabel = new JLabel("RGB (например, 255,0,0):");
        rgbInput = new JTextField();
        rgbInput.setColumns(10);

        rSlider = new JSlider(0, 255);
        gSlider = new JSlider(0, 255);
        bSlider = new JSlider(0, 255);

        JLabel cmykLabel = new JLabel("CMYK (например, 0,100,100,0):");
        cmykInput = new JTextField();
        cmykInput.setColumns(10);

        cSlider = new JSlider(0, 100);
        mSlider = new JSlider(0, 100);
        ySlider = new JSlider(0, 100);
        kSlider = new JSlider(0, 100);

        JLabel hslLabel = new JLabel("HSL (например, 0,100,50):");
        hslInput = new JTextField();
        hslInput.setColumns(10);

        hSlider = new JSlider(0, 360);
        sSlider = new JSlider(0, 100);
        lSlider = new JSlider(0, 100);

        JButton colorChooserButton = new JButton("Выбрать цвет");

        colorPanel = new JPanel();
        colorPanel.setBackground(Color.WHITE);
        colorPanel.setPreferredSize(new Dimension(100, 50));

        setupSliderListeners();
        setupTextFieldListeners(); 

        gbc.gridx = 0; gbc.gridy = 0;
        add(rgbLabel, gbc);
        gbc.gridx = 1; gbc.gridy = 0;
        add(rgbInput, gbc);
        gbc.gridx = 0; gbc.gridy = 1;
        add(new JLabel("R:"), gbc);
        gbc.gridx = 1; gbc.gridy = 1;
        add(rSlider, gbc);
        gbc.gridx = 0; gbc.gridy = 2;
        add(new JLabel("G:"), gbc);
        gbc.gridx = 1; gbc.gridy = 2;
        add(gSlider, gbc);
        gbc.gridx = 0; gbc.gridy = 3;
        add(new JLabel("B:"), gbc);
        gbc.gridx = 1; gbc.gridy = 3;
        add(bSlider, gbc);

        gbc.gridx = 0; gbc.gridy = 4;
        add(cmykLabel, gbc);
        gbc.gridx = 1; gbc.gridy = 4;
        add(cmykInput, gbc);
        gbc.gridx = 0; gbc.gridy = 5;
        add(new JLabel("C:"), gbc);
        gbc.gridx = 1; gbc.gridy = 5;
        add(cSlider, gbc);
        gbc.gridx = 0; gbc.gridy = 6;
        add(new JLabel("M:"), gbc);
        gbc.gridx = 1; gbc.gridy = 6;
        add(mSlider, gbc);
        gbc.gridx = 0; gbc.gridy = 7;
        add(new JLabel("Y:"), gbc);
        gbc.gridx = 1; gbc.gridy = 7;
        add(ySlider, gbc);
        gbc.gridx = 0; gbc.gridy = 8;
        add(new JLabel("K:"), gbc);
        gbc.gridx = 1; gbc.gridy = 8;
        add(kSlider, gbc);

        gbc.gridx = 0; gbc.gridy = 9;
        add(hslLabel, gbc);
        gbc.gridx = 1; gbc.gridy = 9;
        add(hslInput, gbc);
        gbc.gridx = 0; gbc.gridy = 10;
        add(new JLabel("H:"), gbc);
        gbc.gridx = 1; gbc.gridy = 10;
        add(hSlider, gbc);
        gbc.gridx = 0; gbc.gridy = 11;
        add(new JLabel("S:"), gbc);
        gbc.gridx = 1; gbc.gridy = 11;
        add(sSlider, gbc);
        gbc.gridx = 0; gbc.gridy = 12;
        add(new JLabel("L:"), gbc);
        gbc.gridx = 1; gbc.gridy = 12;
        add(lSlider, gbc);

        gbc.gridx = 0; gbc.gridy = 13;
        add(colorChooserButton, gbc);
        gbc.gridx = 1; gbc.gridy = 13;
        add(colorPanel, gbc);

        colorChooserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Color selectedColor = JColorChooser.showDialog(null, "Выберите цвет", Color.WHITE);
                if (selectedColor != null) {
                    rSlider.setValue(selectedColor.getRed());
                    gSlider.setValue(selectedColor.getGreen());
                    bSlider.setValue(selectedColor.getBlue());
                    updateColorFromSliders();
                }
            }
        });
    }

    private void setupTextFieldListeners() {
        rgbInput.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                convertFromRGB();
            }
        });

        cmykInput.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                convertFromCMYK();
            }
        });

        hslInput.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                convertFromHSL();
            }
        });
    }

    private void setupSliderListeners() {
        rSlider.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                updateColorFromSliders();
            }
        });
        gSlider.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                updateColorFromSliders();
            }
        });
        bSlider.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                updateColorFromSliders();
            }
        });

        cSlider.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                updateColorFromCMYKSliders();
            }
        });
        mSlider.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                updateColorFromCMYKSliders();
            }
        });
        ySlider.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                updateColorFromCMYKSliders();
            }
        });
        kSlider.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                updateColorFromCMYKSliders();
            }
        });

        hSlider.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                updateColorFromHSLSliders();
            }
        });
        sSlider.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                updateColorFromHSLSliders();
            }
        });
        lSlider.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                updateColorFromHSLSliders();
            }
        });
    }

 
    private void updateColorFromSliders() {
        int r = rSlider.getValue();
        int g = gSlider.getValue();
        int b = bSlider.getValue();
        rgbInput.setText(r + "," + g + "," + b);
        colorPanel.setBackground(new Color(r, g, b));

        int[] cmyk = RGBtoCMYK(r, g, b);
        cmykInput.setText(cmyk[0] + "," + cmyk[1] + "," + cmyk[2] + "," + cmyk[3]);

        int[] hsl = RGBtoHSL(r, g, b);
        hslInput.setText(hsl[0] + "," + hsl[1] + "," + hsl[2]);
    }

    private void updateColorFromCMYKSliders() {
        int c = cSlider.getValue();
        int m = mSlider.getValue();
        int y = ySlider.getValue();
        int k = kSlider.getValue();
        cmykInput.setText(c + "," + m + "," + y + "," + k);

        int[] rgb = CMYKtoRGB(c, m, y, k);
        rSlider.setValue(rgb[0]);
        gSlider.setValue(rgb[1]);
        bSlider.setValue(rgb[2]);
        colorPanel.setBackground(new Color(rgb[0], rgb[1], rgb[2]));

        int[] hsl = RGBtoHSL(rgb[0], rgb[1], rgb[2]);
        hslInput.setText(hsl[0] + "," + hsl[1] + "," + hsl[2]);
    }

    private void updateColorFromHSLSliders() {
        int h = hSlider.getValue();
        int s = sSlider.getValue();
        int l = lSlider.getValue();
        hslInput.setText(h + "," + s + "," + l);

        int[] rgb = HSLtoRGB(h, s, l);
        rSlider.setValue(rgb[0]);
        gSlider.setValue(rgb[1]);
        bSlider.setValue(rgb[2]);
        colorPanel.setBackground(new Color(rgb[0], rgb[1], rgb[2]));

        int[] cmyk = RGBtoCMYK(rgb[0], rgb[1], rgb[2]);
        cmykInput.setText(cmyk[0] + "," + cmyk[1] + "," + cmyk[2] + "," + cmyk[3]);
    }

    private void convertFromRGB() {
        try {
            String[] rgbValues = rgbInput.getText().split(",");
            int r = Integer.parseInt(rgbValues[0].trim());
            int g = Integer.parseInt(rgbValues[1].trim());
            int b = Integer.parseInt(rgbValues[2].trim());

            rSlider.setValue(r);
            gSlider.setValue(g);
            bSlider.setValue(b);
            colorPanel.setBackground(new Color(r, g, b));

            int[] cmyk = RGBtoCMYK(r, g, b);
            cmykInput.setText(cmyk[0] + "," + cmyk[1] + "," + cmyk[2] + "," + cmyk[3]);

            int[] hsl = RGBtoHSL(r, g, b);
            hslInput.setText(hsl[0] + "," + hsl[1] + "," + hsl[2]);

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Некорректный ввод RGB!");
        }
    }

    private void convertFromCMYK() {
        try {
            String[] cmykValues = cmykInput.getText().split(",");
            int c = Integer.parseInt(cmykValues[0].trim());
            int m = Integer.parseInt(cmykValues[1].trim());
            int y = Integer.parseInt(cmykValues[2].trim());
            int k = Integer.parseInt(cmykValues[3].trim());

            cSlider.setValue(c);
            mSlider.setValue(m);
            ySlider.setValue(y);
            kSlider.setValue(k);

            int[] rgb = CMYKtoRGB(c, m, y, k);
            rSlider.setValue(rgb[0]);
            gSlider.setValue(rgb[1]);
            bSlider.setValue(rgb[2]);
            colorPanel.setBackground(new Color(rgb[0], rgb[1], rgb[2]));

            int[] hsl = RGBtoHSL(rgb[0], rgb[1], rgb[2]);
            hslInput.setText(hsl[0] + "," + hsl[1] + "," + hsl[2]);

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Некорректный ввод CMYK!");
        }
    }

    private void convertFromHSL() {
        try {
            String[] hslValues = hslInput.getText().split(",");
            int h = Integer.parseInt(hslValues[0].trim());
            int s = Integer.parseInt(hslValues[1].trim());
            int l = Integer.parseInt(hslValues[2].trim());

            hSlider.setValue(h);
            sSlider.setValue(s);
            lSlider.setValue(l);

            int[] rgb = HSLtoRGB(h, s, l);
            rSlider.setValue(rgb[0]);
            gSlider.setValue(rgb[1]);
            bSlider.setValue(rgb[2]);
            colorPanel.setBackground(new Color(rgb[0], rgb[1], rgb[2]));

            int[] cmyk = RGBtoCMYK(rgb[0], rgb[1], rgb[2]);
            cmykInput.setText(cmyk[0] + "," + cmyk[1] + "," + cmyk[2] + "," + cmyk[3]);

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Некорректный ввод HSL!");
        }
    }

    private void clearOtherFields(String source) {
        if (!source.equals("RGB")) {
            rgbInput.setText("");
            rSlider.setValue(0);
            gSlider.setValue(0);
            bSlider.setValue(0);
        }
        if (!source.equals("CMYK")) {
            cmykInput.setText("");
            cSlider.setValue(0);
            mSlider.setValue(0);
            ySlider.setValue(0);
            kSlider.setValue(0);
        }
        if (!source.equals("HSL")) {
            hslInput.setText("");
            hSlider.setValue(0);
            sSlider.setValue(0);
            lSlider.setValue(0);
        }
    }

    private int[] RGBtoCMYK(int r, int g, int b) {
        float rPercent = r / 255f;
        float gPercent = g / 255f;
        float bPercent = b / 255f;

        float k = 1 - Math.max(rPercent, Math.max(gPercent, bPercent));
        float c = (1 - rPercent - k) / (1 - k);
        float m = (1 - gPercent - k) / (1 - k);
        float y = (1 - bPercent - k) / (1 - k);

        return new int[]{Math.round(c * 100), Math.round(m * 100), Math.round(y * 100), Math.round(k * 100)};
    }

    private int[] RGBtoHSL(int r, int g, int b) {
        float rPercent = r / 255f;
        float gPercent = g / 255f;
        float bPercent = b / 255f;

        float max = Math.max(rPercent, Math.max(gPercent, bPercent));
        float min = Math.min(rPercent, Math.min(gPercent, bPercent));
        float delta = max - min;

        float h = 0;
        if (delta != 0) {
            if (max == rPercent) {
                h = (gPercent - bPercent) / delta + (gPercent < bPercent ? 6 : 0);
            } else if (max == gPercent) {
                h = (bPercent - rPercent) / delta + 2;
            } else {
                h = (rPercent - gPercent) / delta + 4;
            }
            h *= 60;
        }

        float l = (max + min) / 2;
        float s = delta == 0 ? 0 : delta / (1 - Math.abs(2 * l - 1));

        return new int[]{Math.round(h), Math.round(s * 100), Math.round(l * 100)};
    }

    private int[] CMYKtoRGB(int c, int m, int y, int k) {
        float cPercent = c / 100f;
        float mPercent = m / 100f;
        float yPercent = y / 100f;
        float kPercent = k / 100f;

        int r = Math.round(255 * (1 - cPercent) * (1 - kPercent));
        int g = Math.round(255 * (1 - mPercent) * (1 - kPercent));
        int b = Math.round(255 * (1 - yPercent) * (1 - kPercent));

        return new int[]{r, g, b};
    }

    private int[] HSLtoRGB(int h, int s, int l) {
        float sPercent = s / 100f;
        float lPercent = l / 100f;

        float c = (1 - Math.abs(2 * lPercent - 1)) * sPercent;
        float x = c * (1 - Math.abs((h / 60f) % 2 - 1));
        float m = lPercent - c / 2;

        float rPrime = 0, gPrime = 0, bPrime = 0;

        if (h >= 0 && h < 60) {
            rPrime = c;
            gPrime = x;
        } else if (h >= 60 && h < 120) {
            rPrime = x;
            gPrime = c;
        } else if (h >= 120 && h < 180) {
            gPrime = c;
            bPrime = x;
        } else if (h >= 180 && h < 240) {
            gPrime = x;
            bPrime = c;
        } else if (h >= 240 && h < 300) {
            rPrime = x;
            bPrime = c;
        } else if (h >= 300 && h < 360) {
            rPrime = c;
            bPrime = x;
        }

        int r = Math.round((rPrime + m) * 255);
        int g = Math.round((gPrime + m) * 255);
        int b = Math.round((bPrime + m) * 255);

        return new int[]{r, g, b};
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ColorConverter().setVisible(true);
            }
        });
    }
}
