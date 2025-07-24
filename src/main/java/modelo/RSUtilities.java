/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;
import java.awt.Component;
import java.awt.Window;

import java.awt.Component;
import java.awt.Window;
/**
 *
 * @author Personal
 */




public class RSUtilities {

    public RSUtilities() {
        // <editor-fold defaultstate="collapsed" desc="Compiled Code">
        /* 0: aload_0
         * 1: invokespecial #1                  // Method java/lang/Object."<init>":()V
         * 4: return
         *  */
        // </editor-fold>
    }

    public static void setCentrarVentana(Window ventana) {
        // <editor-fold defaultstate="collapsed" desc="Compiled Code">
        /* 0: invokestatic  #2                  // Method java/awt/Toolkit.getDefaultToolkit:()Ljava/awt/Toolkit;
         * 3: invokevirtual #3                  // Method java/awt/Toolkit.getScreenSize:()Ljava/awt/Dimension;
         * 6: astore_1
         * 7: aload_0
         * 8: invokevirtual #4                  // Method java/awt/Window.getSize:()Ljava/awt/Dimension;
         * 11: astore_2
         * 12: aload_1
         * 13: getfield      #5                  // Field java/awt/Dimension.width:I
         * 16: aload_2
         * 17: getfield      #5                  // Field java/awt/Dimension.width:I
         * 20: isub
         * 21: iconst_2
         * 22: idiv
         * 23: istore_3
         * 24: aload_1
         * 25: getfield      #6                  // Field java/awt/Dimension.height:I
         * 28: aload_2
         * 29: getfield      #6                  // Field java/awt/Dimension.height:I
         * 32: isub
         * 33: iconst_2
         * 34: idiv
         * 35: istore        4
         * 37: aload_0
         * 38: iload_3
         * 39: iload         4
         * 41: invokevirtual #7                  // Method java/awt/Window.setLocation:(II)V
         * 44: return
         *  */
        // </editor-fold>
    }

    public static void setVentanaCompleta(Window ventana) {
        // <editor-fold defaultstate="collapsed" desc="Compiled Code">
        /* 0: invokestatic  #2                  // Method java/awt/Toolkit.getDefaultToolkit:()Ljava/awt/Toolkit;
         * 3: invokevirtual #3                  // Method java/awt/Toolkit.getScreenSize:()Ljava/awt/Dimension;
         * 6: astore_1
         * 7: aload_0
         * 8: invokevirtual #4                  // Method java/awt/Window.getSize:()Ljava/awt/Dimension;
         * 11: astore_2
         * 12: aload_1
         * 13: getfield      #5                  // Field java/awt/Dimension.width:I
         * 16: aload_2
         * 17: getfield      #5                  // Field java/awt/Dimension.width:I
         * 20: isub
         * 21: iconst_2
         * 22: idiv
         * 23: istore_3
         * 24: aload_1
         * 25: getfield      #6                  // Field java/awt/Dimension.height:I
         * 28: aload_2
         * 29: getfield      #6                  // Field java/awt/Dimension.height:I
         * 32: isub
         * 33: iconst_2
         * 34: idiv
         * 35: istore        4
         * 37: aload_0
         * 38: iload_3
         * 39: iload         4
         * 41: invokevirtual #7                  // Method java/awt/Window.setLocation:(II)V
         * 44: return
         *  */
        // </editor-fold>
    }

    public static void setIconoVentana(Window ventana, String rutaIcono) {
        // <editor-fold defaultstate="collapsed" desc="Compiled Code">
        /* 0: aload_0
         * 1: new           #8                  // class javax/swing/ImageIcon
         * 4: dup
         * 5: aload_0
         * 6: invokevirtual #9                  // Method java/lang/Object.getClass:()Ljava/lang/Class;
         * 9: aload_1
         * 10: invokevirtual #10                 // Method java/lang/Class.getResource:(Ljava/lang/String;)Ljava/net/URL;
         * 13: invokespecial #11                 // Method javax/swing/ImageIcon."<init>":(Ljava/net/URL;)V
         * 16: invokevirtual #12                 // Method javax/swing/ImageIcon.getImage:()Ljava/awt/Image;
         * 19: invokevirtual #13                 // Method java/awt/Window.setIconImage:(Ljava/awt/Image;)V
         * 22: return
         *  */
        // </editor-fold>
    }

    public static void setOpaqueVentana(Window ventana, boolean opaque) {
        // <editor-fold defaultstate="collapsed" desc="Compiled Code">
        /* 0: ldc           #14                 // String com.sun.awt.AWTUtilities
         * 2: invokestatic  #15                 // Method java/lang/Class.forName:(Ljava/lang/String;)Ljava/lang/Class;
         * 5: astore_2
         * 6: aload_2
         * 7: ifnull        53
         * 10: aload_2
         * 11: ldc           #16                 // String setWindowOpaque
         * 13: iconst_2
         * 14: anewarray     #17                 // class java/lang/Class
         * 17: dup
         * 18: iconst_0
         * 19: ldc           #18                 // class java/awt/Window
         * 21: aastore
         * 22: dup
         * 23: iconst_1
         * 24: getstatic     #19                 // Field java/lang/Boolean.TYPE:Ljava/lang/Class;
         * 27: aastore
         * 28: invokevirtual #20                 // Method java/lang/Class.getMethod:(Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;
         * 31: astore_3
         * 32: aload_3
         * 33: aconst_null
         * 34: iconst_2
         * 35: anewarray     #21                 // class java/lang/Object
         * 38: dup
         * 39: iconst_0
         * 40: aload_0
         * 41: aastore
         * 42: dup
         * 43: iconst_1
         * 44: iload_1
         * 45: invokestatic  #22                 // Method java/lang/Boolean.valueOf:(Z)Ljava/lang/Boolean;
         * 48: aastore
         * 49: invokevirtual #23                 // Method java/lang/reflect/Method.invoke:(Ljava/lang/Object;[Ljava/lang/Object;)Ljava/lang/Object;
         * 52: pop
         * 53: goto          57
         * 56: astore_2
         * 57: return
         * Exception table:
         * from    to  target type
         * 0    53    56   Class java/lang/ClassNotFoundException
         * 0    53    56   Class java/lang/IllegalAccessException
         * 0    53    56   Class java/lang/IllegalArgumentException
         * 0    53    56   Class java/lang/NoSuchMethodException
         * 0    53    56   Class java/lang/SecurityException
         * 0    53    56   Class java/lang/reflect/InvocationTargetException
         *  */
        // </editor-fold>
    }

    public static void setOpacityVentana(Window ventana, float opacidad) {
        // <editor-fold defaultstate="collapsed" desc="Compiled Code">
        /* 0: ldc           #14                 // String com.sun.awt.AWTUtilities
         * 2: invokestatic  #15                 // Method java/lang/Class.forName:(Ljava/lang/String;)Ljava/lang/Class;
         * 5: astore_2
         * 6: aload_2
         * 7: ifnull        53
         * 10: aload_2
         * 11: ldc           #30                 // String setWindowOpacity
         * 13: iconst_2
         * 14: anewarray     #17                 // class java/lang/Class
         * 17: dup
         * 18: iconst_0
         * 19: ldc           #18                 // class java/awt/Window
         * 21: aastore
         * 22: dup
         * 23: iconst_1
         * 24: getstatic     #31                 // Field java/lang/Float.TYPE:Ljava/lang/Class;
         * 27: aastore
         * 28: invokevirtual #20                 // Method java/lang/Class.getMethod:(Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;
         * 31: astore_3
         * 32: aload_3
         * 33: aconst_null
         * 34: iconst_2
         * 35: anewarray     #21                 // class java/lang/Object
         * 38: dup
         * 39: iconst_0
         * 40: aload_0
         * 41: aastore
         * 42: dup
         * 43: iconst_1
         * 44: fload_1
         * 45: invokestatic  #32                 // Method java/lang/Float.valueOf:(F)Ljava/lang/Float;
         * 48: aastore
         * 49: invokevirtual #23                 // Method java/lang/reflect/Method.invoke:(Ljava/lang/Object;[Ljava/lang/Object;)Ljava/lang/Object;
         * 52: pop
         * 53: goto          57
         * 56: astore_2
         * 57: return
         * Exception table:
         * from    to  target type
         * 0    53    56   Class java/lang/ClassNotFoundException
         * 0    53    56   Class java/lang/IllegalAccessException
         * 0    53    56   Class java/lang/IllegalArgumentException
         * 0    53    56   Class java/lang/NoSuchMethodException
         * 0    53    56   Class java/lang/SecurityException
         * 0    53    56   Class java/lang/reflect/InvocationTargetException
         *  */
        // </editor-fold>
    }

    public static void setOpacityComponente(Component componente, int opacidad) {
        // <editor-fold defaultstate="collapsed" desc="Compiled Code">
        /* 0: new           #33                 // class java/awt/Color
         * 3: dup
         * 4: aload_0
         * 5: invokevirtual #34                 // Method java/awt/Component.getBackground:()Ljava/awt/Color;
         * 8: invokevirtual #35                 // Method java/awt/Color.getRed:()I
         * 11: aload_0
         * 12: invokevirtual #34                 // Method java/awt/Component.getBackground:()Ljava/awt/Color;
         * 15: invokevirtual #36                 // Method java/awt/Color.getGreen:()I
         * 18: aload_0
         * 19: invokevirtual #34                 // Method java/awt/Component.getBackground:()Ljava/awt/Color;
         * 22: invokevirtual #37                 // Method java/awt/Color.getBlue:()I
         * 25: iload_1
         * 26: invokespecial #38                 // Method java/awt/Color."<init>":(IIII)V
         * 29: astore_2
         * 30: aload_0
         * 31: aload_2
         * 32: invokevirtual #39                 // Method java/awt/Component.setBackground:(Ljava/awt/Color;)V
         * 35: goto          48
         * 38: astore_2
         * 39: aload_0
         * 40: ldc           #40                 // String Parámetro de color fuera del rango esperado.\nEl valor máximo admitido es 255 y el valor mínimo\nadmitido es 0.
         * 42: ldc           #41                 // String ¡Error!
         * 44: iconst_0
         * 45: invokestatic  #43                 // Method javax/swing/JOptionPane.showMessageDialog:(Ljava/awt/Component;Ljava/lang/Object;Ljava/lang/String;I)V
         * 48: return
         * Exception table:
         * from    to  target type
         * 0    35    38   Class java/lang/IllegalArgumentException
         *  */
        // </editor-fold>
    }
}
