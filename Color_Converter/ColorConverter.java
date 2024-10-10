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

        JButton convertButton = new JButton("Конвертировать");
        JButton colorChooserButton = new JButton("Выбрать цвет");

        colorPanel = new JPanel();
        colorPanel.setBackground(Color.WHITE);
        colorPanel.setPreferredSize(new Dimension(100, 50));
        
        setupSliderListeners();
        
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
        
        gbc.gridx = 0; gbc.gridy = 14;
        gbc.gridwidth = 2;
        add(convertButton, gbc);

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

        convertButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!rgbInput.getText().isEmpty()) {
                    convertFromRGB();
                } else if (!cmykInput.getText().isEmpty()) {
                    convertFromCMYK();
                } else if (!hslInput.getText().isEmpty()) {
                    convertFromHSL();
                } else {
                    JOptionPane.showMessageDialog(null, "Заполните одно из полей для конвертации!");
                }
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
        updateColorFromSliders();
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
        updateColorFromSliders();
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
            updateColorFromSliders();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Неправильный формат RGB! Введите три числа, разделенных запятыми.");
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
            updateColorFromCMYKSliders();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Неправильный формат CMYK! Введите четыре числа, разделенных запятыми.");
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
            updateColorFromHSLSliders();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Неправильный формат HSL! Введите три числа, разделенных запятыми.");
        }
    }

    private int[] RGBtoCMYK(int r, int g, int b) {
        double rNorm = r / 255.0;
        double gNorm = g / 255.0;
        double bNorm = b / 255.0;

        double k = 1 - Math.max(rNorm, Math.max(gNorm, bNorm));
        double c = (1 - rNorm - k) / (1 - k);
        double m = (1 - gNorm - k) / (1 - k);
        double y = (1 - bNorm - k) / (1 - k);

        return new int[]{(int) (c * 100), (int) (m * 100), (int) (y * 100), (int) (k * 100)};
    }

    private int[] RGBtoHSL(int r, int g, int b) {
        double rNorm = r / 255.0;
        double gNorm = g / 255.0;
        double bNorm = b / 255.0;

        double max = Math.max(rNorm, Math.max(gNorm, bNorm));
        double min = Math.min(rNorm, Math.min(gNorm, bNorm));
        double delta = max - min;

        double h = 0, s, l = (max + min) / 2.0;

        if (delta == 0) {
            h = 0;
        } else if (max == rNorm) {
            h = 60 * (((gNorm - bNorm) / delta) % 6);
        } else if (max == gNorm) {
            h = 60 * (((bNorm - rNorm) / delta) + 2);
        } else if (max == bNorm) {
            h = 60 * (((rNorm - gNorm) / delta) + 4);
        }

        if (delta == 0) {
            s = 0;
        } else {
            s = delta / (1 - Math.abs(2 * l - 1));
        }

        return new int[]{(int) h, (int) (s * 100), (int) (l * 100)};
    }

    private int[] CMYKtoRGB(int c, int m, int y, int k) {
        double cNorm = c / 100.0;
        double mNorm = m / 100.0;
        double yNorm = y / 100.0;
        double kNorm = k / 100.0;

        int r = (int) (255 * (1 - cNorm) * (1 - kNorm));
        int g = (int) (255 * (1 - mNorm) * (1 - kNorm));
        int b = (int) (255 * (1 - yNorm) * (1 - kNorm));

        return new int[]{r, g, b};
    }

    private int[] HSLtoRGB(int h, int s, int l) {
        double sNorm = s / 100.0;
        double lNorm = l / 100.0;

        double c = (1 - Math.abs(2 * lNorm - 1)) * sNorm;
        double x = c * (1 - Math.abs((h / 60.0) % 2 - 1));
        double m = lNorm - c / 2.0;

        double r = 0, g = 0, b = 0;

        if (h >= 0 && h < 60) {
            r = c;
            g = x;
            b = 0;
        } else if (h >= 60 && h < 120) {
            r = x;
            g = c;
            b = 0;
        } else if (h >= 120 && h < 180) {
            r = 0;
            g = c;
            b = x;
        } else if (h >= 180 && h < 240) {
            r = 0;
            g = x;
            b = c;
        } else if (h >= 240 && h < 300) {
            r = x;
            g = 0;
            b = c;
        } else if (h >= 300 && h < 360) {
            r = c;
            g = 0;
            b = x;
        }

        int rFinal = (int) ((r + m) * 255);
        int gFinal = (int) ((g + m) * 255);
        int bFinal = (int) ((b + m) * 255);

        return new int[]{rFinal, gFinal, bFinal};
    }

    public static void main(String[] args) {
        ColorConverter converter = new ColorConverter();
        converter.setVisible(true);
    }
}
