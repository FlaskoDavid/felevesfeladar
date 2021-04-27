package model;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import logic.*;

public class View extends JFrame {
    private JTabbedPane tabbedPane1;
    private JTextField Jtextjob;
    private JTextField JtextRes;
    private JTextField JtextStep;
    private JTextField JtextLoop;
    private JRadioButton IJrbuttonYes;
    private JRadioButton JrbuttonNo;
    private JTextField JtextCmax;
    private JTextField JtextTmax;
    private JTextField JtextSumTi;
    private JTextField JtextSumUi;
    private JTextField cmaxfield;
    private JButton JbtnGenerate;
    private JPanel page1;
    private JPanel page2;
    private JTextArea table;
    private JLabel Cmax;
    private JRadioButton eroforrasGantRadioButton;
    private JRadioButton munkaGantRadioButton;
    private JRadioButton idoIntervalummokRadioButton;
    private JTextField tamxfield;
    private JTextField sumtifield;
    private JTextField sumuifield;
    private JRadioButton csereRadioButton;
    private JRadioButton beszurasRadioButton;
    private JRadioButton inverzRadioButton;
    private JRadioButton keveresRadioButton;
    private JRadioButton orderCrossoverRadioButton;
    private Controller controller = new Controller();
    private final int K = 4;
    private int jn, rn, nen, cn, mode;
    private double[] priority = new double[4], funVal = new double[4];
    private Job[] job;
    private Resource[] res;
    private int[] Vector;
    private int printMode;
    private int type;
    public static void main(String[] arg) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    View frame = new View();
                    frame.setVisible(true);
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        });
    }

    public View() {
        setTitle("Felvesfeladat");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(500, 50, 600, 600);
        tabbedPane1 = new JTabbedPane();
        tabbedPane1.add(page1);
        tabbedPane1.add(page2);

        setContentPane(tabbedPane1);

        JbtnGenerate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (controller.goodField(Jtextjob, "Munkak szama") && (controller.goodField(JtextRes, "Eroforrasok szama") && controller.goodField(JtextLoop, "Szomszedok szama")
                            && controller.goodField(JtextStep, "Ciklusok szama") && controller.goodField(JtextCmax, "Cmax")
                            && controller.goodField(JtextTmax, "Tmax") && controller.goodField(JtextSumTi, "SumTi")
                            && controller.goodField(JtextSumUi, "SumUi"))) {
                        jn = Integer.parseInt(Jtextjob.getText());
                        rn = Integer.parseInt(JtextRes.getText());
                        nen = Integer.parseInt(JtextLoop.getText());
                        cn = Integer.parseInt(JtextStep.getText());
                        priority[0] = Integer.parseInt(JtextCmax.getText());
                        priority[1] = Integer.parseInt(JtextTmax.getText());
                        priority[2] = Integer.parseInt(JtextSumTi.getText());
                        priority[3] = Integer.parseInt(JtextSumUi.getText());
                        if (IJrbuttonYes.isSelected()) {
                            mode = 0;
                        } else if(JrbuttonNo.isSelected()) {
                            mode = 1;
                        }
                        if (csereRadioButton.isSelected()){
                            type=0;
                        } else if (beszurasRadioButton.isSelected()){
                            type=1;
                        } else if (keveresRadioButton.isSelected()){
                            type=2;
                        } else if (inverzRadioButton.isSelected()){
                            type=3;
                        } else if (orderCrossoverRadioButton.isSelected()){
                            type=4;
                        }

                        job = controller.callJobCreator(jn, rn);
                        res = controller.callResourceCreator(jn, rn);
                        Vector = controller.callTimeVector(jn);
                        controller.callSearch(job, res, jn, rn, Vector, priority, K, mode, nen, cn,type);
                        controller.callSimulation(job, res, jn, rn, Vector, 0, mode);
                        controller.callEvaluate(job, jn, rn, Vector, funVal);
                        cmaxfield.setText(controller.intToString(funVal, 0));
                        tamxfield.setText(controller.intToString(funVal, 1));
                        sumtifield.setText(controller.intToString(funVal, 2));
                        sumuifield.setText(controller.intToString(funVal, 3));
                        JOptionPane.showMessageDialog(null, "Sikeres Generalas", "Program uzenet", JOptionPane.INFORMATION_MESSAGE);
                    }
                } catch (Exception exception) {
                    JOptionPane.showMessageDialog(null, "Hibas program", "Program üzenet", JOptionPane.ERROR_MESSAGE);
                }

            }
        });


        eroforrasGantRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                printMode = 1;
                controller.fillArea(job, jn, rn, Vector, res, printMode, table);
            }
        });
        munkaGantRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                printMode = 0;
                controller.fillArea(job, jn, rn, Vector, res, printMode, table);
            }
        });
        idoIntervalummokRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                printMode = 2;
                controller.fillArea(job, jn, rn, Vector, res, printMode, table);
            }
        });
    }

}
