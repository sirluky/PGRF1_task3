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
    private final Wireframe wireframe2;
    private List<Solid> scene;
    private List<Solid> axisScene;
    // Camera
    private final double CAM_SPEED = 0.05;
    private Camera cam;
    // Coords
    private int c = 0, r = 0;
    // Object mode
    private int objMode = 1;
    // Rotation axis X, Y, Z
    char rotateMode = 'Z';


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
        axisScene = new ArrayList<>();

        // 3D objects
        Cube cube = new Cube();
        Cuboid cuboid = new Cuboid();
        Pyramid pyramid = new Pyramid();
        Pyramid3 pyramid3 = new Pyramid3();
        Axis axis = new Axis();

        // Camera
        Vec3D vec3D =  new Vec3D(-1, -3, 1)  ;
        cam = new Camera(vec3D, 1, -0.5, 1.5, false);

        Mat4PerspRH perspRH = new Mat4PerspRH(1, (float) this.img.getHeight() / (float) this.img.getWidth(), 0.01, 100.0);
        this.wireframe = new Wireframe(img, 0x66faff, liner, cam.getViewMatrix(), perspRH);
        this.wireframe2 = new Wireframe(img, 0xff0000, liner, cam.getViewMatrix(), perspRH);

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

        // Keyboard listener
        panel.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                clear();
                if (e.getKeyCode() == KeyEvent.VK_C) {
                     // reset cam to default
                    cam = new Camera(vec3D, 1, -0.5, 1.5, false);

                    scene.removeAll(scene);
                    scene.add(cube);
                    axisScene.removeAll(axisScene);
                    axisScene.add(axis);

                }
                if (e.getKeyCode() == KeyEvent.VK_1) {
                    scene.clear();
                    scene.add(cube);
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

                // random color
                if (e.getKeyCode() == KeyEvent.VK_R) {
                    wireframe.setColor((int) (Math.random() * 0xFFFFFF));
                }
                // rotate modes
                if (e.getKeyCode() == KeyEvent.VK_Z) {
                    rotateMode = 'Z';
                }
                if (e.getKeyCode() == KeyEvent.VK_X) {
                    rotateMode = 'X';
                }
                if (e.getKeyCode() == KeyEvent.VK_Y) {
                    rotateMode = 'Y';
                }
                // rotate listener
                // arrow up - positive, arrow down - negative
                if (e.getKeyCode() == KeyEvent.VK_UP) {
                    // rotate
                    scene.get(0).rotate(0.1, rotateMode);
                    axisScene.get(0).rotate(0.1,rotateMode);
                }

                if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                    // rotate
                    scene.get(0).rotate(-0.1, rotateMode);
                    axisScene.get(0).rotate(-0.1,rotateMode);
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

        // Mouse wheel listener - zoom
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
        // GUI
        // Label and settings
        JLabel label = new JLabel();
        label.setText("Cam [W] [A] [S] [D] | Solid [1] [2] [3] [4] | Zoom [M-Wheel] |  Rotate [UP] [DOWN] modes [X] [Y] [Z] | Reset [C]");
        // 0 = CENTER
        label.setVerticalAlignment(0);
        label.setHorizontalAlignment(0);

        label.setFont(new Font("Arial", 0, 15) );
        label.setPreferredSize(new Dimension(width, 50));

        frame.add(label, "North");
        frame.add(panel, "Center");
        frame.pack();
        frame.setVisible(true);
        panel.grabFocus();

    }

    public void changeColor(int color) {
        this.wireframe.setColor(color);
    }

    // Clear
    public void clear() {
        img.clear(0x000000);
    }

    // Present
    public void present(Graphics graphics) {

        presenter.present(graphics);
    }

    // Present
    public void present() {
        wireframe.setView(cam.getViewMatrix());

        wireframe.renderScene(scene);
        wireframe2.setView(cam.getViewMatrix());
        wireframe2.renderScene(axisScene);
        final Graphics g = panel.getGraphics();
        if (g != null) {
            presenter.present(g);
        }
    }

    // Start
    public void start() {
        axisScene.add(new Axis());
        scene.add(new Cube());
        present();
    }

    // PSVM
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Canvas3D(1024, 600).start());

    }
}
