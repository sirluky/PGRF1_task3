package cz.uhk.pgrf1.task3;

import cz.uhk.pgrf1.task3.objectdata.*;
import cz.uhk.pgrf1.task3.rasterdata.Presentable;
import cz.uhk.pgrf1.task3.rasterdata.RasterImage;
import cz.uhk.pgrf1.task3.rasterdata.RasterImageBI;
import cz.uhk.pgrf1.task3.rasterops.Liner;
import cz.uhk.pgrf1.task3.rasterops.TrivialLiner;
import cz.uhk.pgrf1.task3.rasterops.Wireframe;
import transforms.Camera;
import transforms.Mat4PerspRH;
import transforms.Vec3D;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class Canvas3D {
    private final JPanel panel;
    private final RasterImage<Integer> img;
    private final Presentable<Graphics> presenter;
    private final Liner<Integer> liner;
    private final Wireframe wireframe;
    private List<Solid> scene;
    private final double CAM_SPEED = 0.05;
    private int c = 0;
    private int r = 0;
    private int objMode = 1;
    char rotateMode = 'Z';
    private Camera cam;

    public Canvas3D(int width, int height) {
        JFrame frame = new JFrame();
        frame.setLayout(new BorderLayout());
        frame.setResizable(false);
        frame.setTitle("PGRF1 - Task 3 - 3D");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        final RasterImageBI auxRasterImage = new RasterImageBI(width, height);
        img = auxRasterImage;
        presenter = auxRasterImage;
        liner = new TrivialLiner<>();
        scene = new ArrayList<>();

        // 3D objects
        Cube cube = new Cube();
        Cuboid cuboid = new Cuboid();
        Pyramid pyramid = new Pyramid();
        Pyramid3 pyramid3 = new Pyramid3();

        // Axis
        Axis axis3D = new Axis();

        // Camera
        cam = new Camera(new Vec3D(-1, -4, 1), 1, -0.5, 1.5, false);
        Mat4PerspRH perspRH = new Mat4PerspRH(1, (float) this.img.getHeight() / (float) this.img.getWidth(), 0.01, 100.0);
        this.wireframe = new Wireframe(img, 0x66faff, liner, cam.getViewMatrix(), perspRH);


        panel = new JPanel() {
            private static final long serialVersionUID = 1L;

            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                present(g);
            }
        };
        panel.setPreferredSize(new Dimension(width, height));
        panel.addMouseListener(
                new MouseAdapter() {
                    @Override
                    public void mousePressed(MouseEvent e) {
                        clear();
                        c = e.getX();
                        r = e.getY();
                        System.out.println("C = " + c + " R = " + r);
                        present();
                    }
                }
        );

        panel.addMouseMotionListener(
                new MouseMotionAdapter() {
                    @Override
                    public void mouseDragged(MouseEvent e) {
                        clear();
                        int c2 = e.getX();
                        int r2 = e.getY();
                        System.out.println("C2 = " + c2 + " R2 = " + r2);
                        System.out.println(cam.getAzimuth());
                        if (c2 > c) {
                            cam = cam.addAzimuth(CAM_SPEED);
                        } else if (c2 < c) {
                            cam = cam.addAzimuth(-CAM_SPEED);
                        }
                        if (r2 > r) {
                            cam = cam.addZenith(CAM_SPEED);
                        } else if (r2 < r) {
                            cam = cam.addZenith(-CAM_SPEED);
                        }
                        c = c2;
                        r = r2;
                        present();
                    }
                }
        );
        panel.addMouseListener(
                new MouseAdapter() {
                    @Override
                    public void mouseReleased(MouseEvent e) {
                        clear();
                        r = e.getY();
                        c = e.getX();
                        present();
                    }
                }
        );
        panel.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                clear();
                if (e.getKeyCode() == KeyEvent.VK_C) {
                    scene.clear();

                }
                if (e.getKeyCode() == KeyEvent.VK_1) {
                    scene.clear();
                    scene.add(cube);
                    ;
                }

                if (e.getKeyCode() == KeyEvent.VK_2) {
                    scene.clear();
                    scene.add(cuboid);
                }

                if (e.getKeyCode() == KeyEvent.VK_3) {
                    scene.clear();
                    scene.add(pyramid);
                }

                if (e.getKeyCode() == KeyEvent.VK_4) {
                    scene.clear();
                    scene.add(pyramid3);

                }


                 // rotate modes
                if(e.getKeyCode() == KeyEvent.VK_Z) {
                    rotateMode = 'Z';
                }
                if(e.getKeyCode() == KeyEvent.VK_X) {
                    rotateMode = 'X';
                }
                if(e.getKeyCode() == KeyEvent.VK_Y) {
                    rotateMode = 'Y';
                }
                //rotate listener
                // arrow up - positive, arrow down - negative
                if(e.getKeyCode() == KeyEvent.VK_UP){
                    // rotate
                    scene.get(0).rotate(0.1, rotateMode);
                }

                if(e.getKeyCode() == KeyEvent.VK_DOWN){
                    // rotate
                    scene.get(0).rotate(-0.1, rotateMode);
                }

                // Cam movement WASD
                if (e.getKeyCode() == KeyEvent.VK_W) {
                    cam = cam.forward(CAM_SPEED);
                }
                if (e.getKeyCode() == KeyEvent.VK_S) {
                    cam = cam.backward(CAM_SPEED);
                }
                if (e.getKeyCode() == KeyEvent.VK_A) {
                    cam = cam.left(CAM_SPEED);
                }
                if (e.getKeyCode() == KeyEvent.VK_D) {
                    cam = cam.right(CAM_SPEED);
                }

                present();
            }


        });

        // Mouse wheel listener
        panel.addMouseWheelListener(new MouseAdapter() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                clear();
                if (e.getWheelRotation() < 0) {
                    cam = cam.forward(CAM_SPEED);
                } else {
                    cam = cam.backward(CAM_SPEED);
                }
                present();
            }
        });


        JLabel label = new JLabel();
        label.setText("Cam [W] [A] [S] [D] | Solid [1] [2] [3] [4] | Zoom [M-Wheel] |  Rotate [UP] [DOWN] modes [X] [Y] [Z]");
        // 0 = CENTER
        label.setVerticalAlignment(0);
        label.setHorizontalAlignment(0);
        Font f = new Font("Arial", 0, 15);
        label.setFont(f);
        label.setPreferredSize(new Dimension(width, 50));
        frame.add(label, "North");
        frame.add(panel, "Center");
        frame.pack();
        frame.setVisible(true);
        panel.grabFocus();
    }

    public void clear() {
        img.clear(0x000000);
    }


    public void present(Graphics graphics) {
        presenter.present(graphics);
    }

    public void present() {
        wireframe.setView(cam.getViewMatrix());
        wireframe.renderScene(scene);


        final Graphics g = panel.getGraphics();
        if (g != null) {
            presenter.present(g);
        }
    }

    public void start() {
        present();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Canvas3D(800, 600).start());
    }
}
