/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import controller.Oyun;
import java.awt.HeadlessException;
import javax.swing.JFrame;

/**
 *
 * @author Mert
 */
public class OyunEkrani extends JFrame {

    public OyunEkrani(String title) throws HeadlessException {
        super(title);
    }

    public static void main(String[] args) {

        OyunEkrani oyunEkrani = new OyunEkrani("Uzay Oyunu");
        Oyun oyun = new Oyun();

        oyunEkrani.setResizable(false);
        oyunEkrani.setFocusable(false);
        oyunEkrani.setSize(1024, 720);
        oyunEkrani.setLocationRelativeTo(null);
        oyunEkrani.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        oyun.requestFocus();
        oyun.addKeyListener(oyun);
        oyun.setFocusable(true);
        oyun.setFocusTraversalKeysEnabled(false);
        
        oyunEkrani.add(oyun);
        oyunEkrani.setVisible(true);
    }
}
