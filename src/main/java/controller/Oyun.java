/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageInputStream;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;
import model.Ates;

/**
 *
 * @author Mert
 */
public class Oyun extends JPanel implements KeyListener, ActionListener {

    private int gecenSure = 0;
    private int harcananAtes = 0;
    private int atesDirY = 1;
    private int topX = 0;
    private int topDirX = 2;
    private int uzayGemisiX = 0;
    private int dirUzayX = 20;
    private BufferedImage image;
    private ArrayList<Ates> atesler = new ArrayList<>();
    Timer timer = new Timer(5, this);

    public Oyun() {

        try {
            image = ImageIO.read(new FileImageInputStream(new File("uzaygemisi.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }

        setBackground(Color.black);

        timer.start();
    }

    public boolean kontrolEt() {
        // çarpışma kontrol etme methodu
        for (Ates ates : atesler) {
            // intersects başka bir Rectangle ile çarpışma var mı diye kontrol eder
            if (new Rectangle(ates.getX(), ates.getY(), 10, 20).intersects(new Rectangle(topX, 0, 20, 20))) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        gecenSure += 5;

        // topun rengini ayarlıyoruz
        g.setColor(Color.red);

        // topu çiziyoruz
        g.fillOval(topX, 0, 20, 20);

        // uzay gemisini çiziyoruz
        g.drawImage(image, uzayGemisiX, 610, image.getWidth() / 10, image.getHeight() / 10, this);

        // jframe dışına çıkan ateşleri siliyoruz
        for (Ates ates : atesler) {
            if (ates.getY() < 0) {
                atesler.remove(ates);
            }
        }

        // ateşleri çiziyoruz
        g.setColor(Color.blue);

        for (Ates ates : atesler) {
            g.fillRect(ates.getX(), ates.getY(), 10, 20);
        }

        // çarpışma kontrol ediyoruz
        if (kontrolEt()) {
            timer.stop();
            String mesaj = "Kazandınız...\n" + "Harcanan Ateş : " + harcananAtes + "\n" + "Geçen Süre : " + gecenSure / 1000.0 + " saniye";
            JOptionPane.showMessageDialog(this, mesaj, "Tebrikler", HEIGHT);
            System.exit(0);
        }
    }

    @Override
    public void repaint() {
        super.repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int c = e.getKeyCode();

        // uzay gemisini sola ve sağa hareket ettirme
        if (c == KeyEvent.VK_LEFT) {
            // uzay gemisinin jframe sınırından çıkıp çıkmadığını kontrol ediyoruz
            if (uzayGemisiX <= 0) {
                uzayGemisiX = 0;
            } else {
                uzayGemisiX -= dirUzayX;
            }
        } else if (c == KeyEvent.VK_RIGHT) {
            // uzay gemisinin jframe sınırından çıkıp çıkmadığını kontrol ediyoruz
            if (uzayGemisiX >= 968) {
                uzayGemisiX = 968;
            } else {
                uzayGemisiX += dirUzayX;
            }
        } else if (c == KeyEvent.VK_CONTROL) {
            // ateş etme
            atesler.add(new Ates(uzayGemisiX + 15, 595));
            harcananAtes++;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        topX += topDirX;
        // topun jframe sınırından çıkıp çıkmadığını kontrol ediyoruz
        if (topX >= 988) {
            topDirX = -topDirX;
        }

        // topun jframe sınırından çıkıp çıkmadığını kontrol ediyoruz
        if (topX <= 0) {
            topDirX = -topDirX;
        }

        for (Ates ates : atesler) {
            ates.setY(ates.getY() - atesDirY);
        }

        repaint();
    }
}
