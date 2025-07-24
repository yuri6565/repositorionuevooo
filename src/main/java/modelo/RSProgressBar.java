/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author Personal
 */


import java.awt.Color;
import javax.swing.JProgressBar;
import javax.swing.border.Border;

import java.awt.Color;
import javax.swing.JProgressBar;
import javax.swing.border.Border;

public class RSProgressBar extends JProgressBar {

    private Color colorForeground;
    private Color colorBackground;
    private Color colorSelBackground;
    private Color colorSelForeground;
    private int grosorBorde;
    private Color colorBorde;
    private Border borde;
    private boolean horizontalOrientacion;

    public RSProgressBar() {
        // <editor-fold defaultstate="collapsed" desc="Compiled Code">
        /* 0: aload_0
         * 1: invokespecial #3                  // Method javax/swing/JProgressBar."<init>":()V
         * 4: aload_0
         * 5: new           #4                  // class java/awt/Color
         * 8: dup
         * 9: iconst_0
         * 10: bipush        112
         * 12: sipush        192
         * 15: invokespecial #5                  // Method java/awt/Color."<init>":(III)V
         * 18: putfield      #6                  // Field colorForeground:Ljava/awt/Color;
         * 21: aload_0
         * 22: new           #4                  // class java/awt/Color
         * 25: dup
         * 26: sipush        255
         * 29: sipush        255
         * 32: sipush        255
         * 35: invokespecial #5                  // Method java/awt/Color."<init>":(III)V
         * 38: putfield      #7                  // Field colorBackground:Ljava/awt/Color;
         * 41: aload_0
         * 42: new           #4                  // class java/awt/Color
         * 45: dup
         * 46: iconst_0
         * 47: iconst_0
         * 48: iconst_0
         * 49: invokespecial #5                  // Method java/awt/Color."<init>":(III)V
         * 52: putfield      #2                  // Field colorSelBackground:Ljava/awt/Color;
         * 55: aload_0
         * 56: new           #4                  // class java/awt/Color
         * 59: dup
         * 60: sipush        255
         * 63: sipush        255
         * 66: sipush        255
         * 69: invokespecial #5                  // Method java/awt/Color."<init>":(III)V
         * 72: putfield      #1                  // Field colorSelForeground:Ljava/awt/Color;
         * 75: aload_0
         * 76: iconst_2
         * 77: putfield      #8                  // Field grosorBorde:I
         * 80: aload_0
         * 81: new           #4                  // class java/awt/Color
         * 84: dup
         * 85: iconst_0
         * 86: iconst_0
         * 87: iconst_0
         * 88: invokespecial #5                  // Method java/awt/Color."<init>":(III)V
         * 91: putfield      #9                  // Field colorBorde:Ljava/awt/Color;
         * 94: aload_0
         * 95: aload_0
         * 96: getfield      #8                  // Field grosorBorde:I
         * 99: aload_0
         * 100: getfield      #8                  // Field grosorBorde:I
         * 103: aload_0
         * 104: getfield      #8                  // Field grosorBorde:I
         * 107: aload_0
         * 108: getfield      #8                  // Field grosorBorde:I
         * 111: aload_0
         * 112: getfield      #9                  // Field colorBorde:Ljava/awt/Color;
         * 115: invokestatic  #10                 // Method javax/swing/BorderFactory.createMatteBorder:(IIIILjava/awt/Color;)Ljavax/swing/border/MatteBorder;
         * 118: putfield      #11                 // Field borde:Ljavax/swing/border/Border;
         * 121: aload_0
         * 122: iconst_1
         * 123: putfield      #12                 // Field horizontalOrientacion:Z
         * 126: aload_0
         * 127: aload_0
         * 128: getfield      #11                 // Field borde:Ljavax/swing/border/Border;
         * 131: invokevirtual #13                 // Method setBorder:(Ljavax/swing/border/Border;)V
         * 134: aload_0
         * 135: bipush        50
         * 137: invokevirtual #14                 // Method setValue:(I)V
         * 140: aload_0
         * 141: iconst_1
         * 142: invokevirtual #15                 // Method setStringPainted:(Z)V
         * 145: aload_0
         * 146: getstatic     #16                 // Field rojeru_san/efectos/Roboto.BOLD:Ljava/awt/Font;
         * 149: ldc           #17                 // float 18.0f
         * 151: invokevirtual #18                 // Method java/awt/Font.deriveFont:(F)Ljava/awt/Font;
         * 154: invokevirtual #19                 // Method setFont:(Ljava/awt/Font;)V
         * 157: aload_0
         * 158: aload_0
         * 159: getfield      #6                  // Field colorForeground:Ljava/awt/Color;
         * 162: invokevirtual #20                 // Method setForeground:(Ljava/awt/Color;)V
         * 165: aload_0
         * 166: aload_0
         * 167: getfield      #7                  // Field colorBackground:Ljava/awt/Color;
         * 170: invokevirtual #21                 // Method setBackground:(Ljava/awt/Color;)V
         * 173: aload_0
         * 174: new           #22                 // class java/awt/Dimension
         * 177: dup
         * 178: sipush        200
         * 181: bipush        30
         * 183: invokespecial #23                 // Method java/awt/Dimension."<init>":(II)V
         * 186: invokevirtual #24                 // Method setPreferredSize:(Ljava/awt/Dimension;)V
         * 189: aload_0
         * 190: new           #22                 // class java/awt/Dimension
         * 193: dup
         * 194: sipush        200
         * 197: bipush        30
         * 199: invokespecial #23                 // Method java/awt/Dimension."<init>":(II)V
         * 202: invokevirtual #25                 // Method setSize:(Ljava/awt/Dimension;)V
         * 205: aload_0
         * 206: iconst_0
         * 207: invokevirtual #26                 // Method setOrientation:(I)V
         * 210: aload_0
         * 211: new           #27                 // class rojeru_san/rsprogress/RSProgressBar$1
         * 214: dup
         * 215: aload_0
         * 216: invokespecial #28                 // Method rojeru_san/rsprogress/RSProgressBar$1."<init>":(Lrojeru_san/rsprogress/RSProgressBar;)V
         * 219: invokevirtual #29                 // Method setUI:(Ljavax/swing/plaf/ProgressBarUI;)V
         * 222: return
         *  */
        // </editor-fold>
    }

    public Color getColorSelBackground() {
        // <editor-fold defaultstate="collapsed" desc="Compiled Code">
        /* 0: aload_0
         * 1: getfield      #2                  // Field colorSelBackground:Ljava/awt/Color;
         * 4: areturn
         *  */
        // </editor-fold>
        return null;
        // <editor-fold defaultstate="collapsed" desc="Compiled Code">
        /* 0: aload_0
         * 1: getfield      #2                  // Field colorSelBackground:Ljava/awt/Color;
         * 4: areturn
         *  */
        // </editor-fold>
    }

    public void setColorSelBackground(Color colorSelBackground) {
        // <editor-fold defaultstate="collapsed" desc="Compiled Code">
        /* 0: aload_0
         * 1: aload_1
         * 2: putfield      #2                  // Field colorSelBackground:Ljava/awt/Color;
         * 5: aload_0
         * 6: new           #30                 // class rojeru_san/rsprogress/RSProgressBar$2
         * 9: dup
         * 10: aload_0
         * 11: aload_1
         * 12: invokespecial #31                 // Method rojeru_san/rsprogress/RSProgressBar$2."<init>":(Lrojeru_san/rsprogress/RSProgressBar;Ljava/awt/Color;)V
         * 15: invokevirtual #29                 // Method setUI:(Ljavax/swing/plaf/ProgressBarUI;)V
         * 18: return
         *  */
        // </editor-fold>
    }

    public Color getColorSelForeground() {
        // <editor-fold defaultstate="collapsed" desc="Compiled Code">
        /* 0: aload_0
         * 1: getfield      #1                  // Field colorSelForeground:Ljava/awt/Color;
         * 4: areturn
         *  */
        // </editor-fold>
        return null;
        // <editor-fold defaultstate="collapsed" desc="Compiled Code">
        /* 0: aload_0
         * 1: getfield      #1                  // Field colorSelForeground:Ljava/awt/Color;
         * 4: areturn
         *  */
        // </editor-fold>
    }

    public void setColorSelForeground(Color colorSelForeground) {
        // <editor-fold defaultstate="collapsed" desc="Compiled Code">
        /* 0: aload_0
         * 1: aload_1
         * 2: putfield      #1                  // Field colorSelForeground:Ljava/awt/Color;
         * 5: aload_0
         * 6: new           #32                 // class rojeru_san/rsprogress/RSProgressBar$3
         * 9: dup
         * 10: aload_0
         * 11: aload_1
         * 12: invokespecial #33                 // Method rojeru_san/rsprogress/RSProgressBar$3."<init>":(Lrojeru_san/rsprogress/RSProgressBar;Ljava/awt/Color;)V
         * 15: invokevirtual #29                 // Method setUI:(Ljavax/swing/plaf/ProgressBarUI;)V
         * 18: return
         *  */
        // </editor-fold>
    }

    public int getGrosorBorde() {
        // <editor-fold defaultstate="collapsed" desc="Compiled Code">
        /* 0: aload_0
         * 1: getfield      #8                  // Field grosorBorde:I
         * 4: ireturn
         *  */
        // </editor-fold>
        return 0;
        // <editor-fold defaultstate="collapsed" desc="Compiled Code">
        /* 0: aload_0
         * 1: getfield      #8                  // Field grosorBorde:I
         * 4: ireturn
         *  */
        // </editor-fold>
    }

    public void setGrosorBorde(int grosorBorde) {
        // <editor-fold defaultstate="collapsed" desc="Compiled Code">
        /* 0: aload_0
         * 1: iload_1
         * 2: putfield      #8                  // Field grosorBorde:I
         * 5: aload_0
         * 6: iload_1
         * 7: iload_1
         * 8: iload_1
         * 9: iload_1
         * 10: aload_0
         * 11: getfield      #9                  // Field colorBorde:Ljava/awt/Color;
         * 14: invokestatic  #10                 // Method javax/swing/BorderFactory.createMatteBorder:(IIIILjava/awt/Color;)Ljavax/swing/border/MatteBorder;
         * 17: invokevirtual #13                 // Method setBorder:(Ljavax/swing/border/Border;)V
         * 20: return
         *  */
        // </editor-fold>
    }

    public void getColorBorde() {
        // <editor-fold defaultstate="collapsed" desc="Compiled Code">
        /* 0: aload_0
         * 1: getfield      #9                  // Field colorBorde:Ljava/awt/Color;
         * 4: areturn
         *  */
        // </editor-fold>
    }

    public void setColorBorde(Color colorBorde) {
        // <editor-fold defaultstate="collapsed" desc="Compiled Code">
        /* 0: aload_0
         * 1: aload_1
         * 2: putfield      #9                  // Field colorBorde:Ljava/awt/Color;
         * 5: aload_0
         * 6: aload_0
         * 7: getfield      #8                  // Field grosorBorde:I
         * 10: aload_0
         * 11: getfield      #8                  // Field grosorBorde:I
         * 14: aload_0
         * 15: getfield      #8                  // Field grosorBorde:I
         * 18: aload_0
         * 19: getfield      #8                  // Field grosorBorde:I
         * 22: aload_1
         * 23: invokestatic  #10                 // Method javax/swing/BorderFactory.createMatteBorder:(IIIILjava/awt/Color;)Ljavax/swing/border/MatteBorder;
         * 26: invokevirtual #13                 // Method setBorder:(Ljavax/swing/border/Border;)V
         * 29: return
         *  */
        // </editor-fold>
    }

    public void getHorizontalOrientacion() {
        // <editor-fold defaultstate="collapsed" desc="Compiled Code">
        /* 0: aload_0
         * 1: getfield      #12                 // Field horizontalOrientacion:Z
         * 4: ireturn
         *  */
        // </editor-fold>
    }

    public void setHorizontalOrientacion(boolean horizontalOrientacion) {
        // <editor-fold defaultstate="collapsed" desc="Compiled Code">
        /* 0: aload_0
         * 1: iload_1
         * 2: putfield      #12                 // Field horizontalOrientacion:Z
         * 5: aload_0
         * 6: getfield      #12                 // Field horizontalOrientacion:Z
         * 9: ifeq          52
         * 12: aload_0
         * 13: iconst_0
         * 14: invokevirtual #26                 // Method setOrientation:(I)V
         * 17: aload_0
         * 18: new           #22                 // class java/awt/Dimension
         * 21: dup
         * 22: sipush        200
         * 25: bipush        30
         * 27: invokespecial #23                 // Method java/awt/Dimension."<init>":(II)V
         * 30: invokevirtual #24                 // Method setPreferredSize:(Ljava/awt/Dimension;)V
         * 33: aload_0
         * 34: new           #22                 // class java/awt/Dimension
         * 37: dup
         * 38: sipush        200
         * 41: bipush        30
         * 43: invokespecial #23                 // Method java/awt/Dimension."<init>":(II)V
         * 46: invokevirtual #25                 // Method setSize:(Ljava/awt/Dimension;)V
         * 49: goto          89
         * 52: aload_0
         * 53: iconst_1
         * 54: invokevirtual #26                 // Method setOrientation:(I)V
         * 57: aload_0
         * 58: new           #22                 // class java/awt/Dimension
         * 61: dup
         * 62: bipush        30
         * 64: sipush        200
         * 67: invokespecial #23                 // Method java/awt/Dimension."<init>":(II)V
         * 70: invokevirtual #24                 // Method setPreferredSize:(Ljava/awt/Dimension;)V
         * 73: aload_0
         * 74: new           #22                 // class java/awt/Dimension
         * 77: dup
         * 78: bipush        30
         * 80: sipush        200
         * 83: invokespecial #23                 // Method java/awt/Dimension."<init>":(II)V
         * 86: invokevirtual #25                 // Method setSize:(Ljava/awt/Dimension;)V
         * 89: return
         *  */
        // </editor-fold>
    }
}

