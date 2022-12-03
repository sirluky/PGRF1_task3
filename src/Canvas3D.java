import objectdata.*;
import rasterdata.Presentable;
import rasterdata.RasterImage;
import rasterdata.RasterImageBI;
import rasterops.Liner;
import rasterops.TrivialLiner;
import rasterops.Wireframe;
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

    double speed = 0.07;
    private int c = 0;
    private int r = 0;
    private int objMode = 1;

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
         Cube cube3D = new Cube();

        Cuboid cuboid3D = new Cuboid();
        Pyramid pyramid3D = new Pyramid();

        // Camera
        cam = new Camera(new Vec3D(-1, -4, 1), 1, -0.5, 1.5, false);
        Mat4PerspRH proj = new Mat4PerspRH(1, (float) this.img.getHeight() / (float) this.img.getWidth(), 0.01, 100.0);
        this.wireframe = new Wireframe(img, 0x66faff, liner, cam.getViewMatrix(), proj);



        scene.add(cube3D);
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
                        if (c2 > c) {
                            cam = cam.addAzimuth(speed);
                        } else if (c2 < c) {
                            cam = cam.addAzimuth(-speed);
                        }
                        if (r2 > r) {
                            cam = cam.addZenith(speed);
                        } else if (r2 < r) {
                            cam = cam.addZenith(-speed);
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
                    scene.add(cube3D);
                }

                if (e.getKeyCode() == KeyEvent.VK_2) {
                    scene.clear();
                    scene.add(cuboid3D);
                }

                if (e.getKeyCode() == KeyEvent.VK_3) {
                    scene.clear();
                    scene.add(pyramid3D);
                }
                // Cam movement WASD
                if (e.getKeyCode() == KeyEvent.VK_W) {
                    cam = cam.forward(speed);
                }
                if (e.getKeyCode() == KeyEvent.VK_S) {
                    cam = cam.backward(speed);
                }
                if (e.getKeyCode() == KeyEvent.VK_A) {
                    cam = cam.left(speed);
                }
                if (e.getKeyCode() == KeyEvent.VK_D) {
                    cam = cam.right(speed);
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
                    cam = cam.forward(speed);
                } else {
                    cam = cam.backward(speed);
                }
                present();
            }
        });


        JLabel label = new JLabel();
        label.setText("Cam [W] [A] [S] [D] | Object [1] [2] [3] | Clear [C] | Zoom [M Wheel]");

        label.setVerticalAlignment(JLabel.CENTER);
        label.setHorizontalAlignment(JLabel.CENTER);

        label.setFont(new Font("Arial", Font.BOLD, 15));

        label.setPreferredSize(new Dimension(width, 50));
        frame.add(label, BorderLayout.NORTH);
        frame.add(panel, BorderLayout.CENTER);
        frame.pack();
        frame.setVisible(true);
        panel.grabFocus();
    }

    public void clear() {
        img.clear(0x000000);
        scene.add(new Cube());
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
