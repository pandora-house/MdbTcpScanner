import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

public class MdbTcpScanner {
    private static JFrame frame;
    private JPanel panel;
    private JTextField tIp;
    private JTextField tPort;
    private JTextField tConnectTimeout;
    private JRadioButton rF01;
    private JProgressBar pResults;
    private JTextArea tResults;
    private JLabel lIp;
    private JLabel lPort;
    private JSeparator separator1;
    private JSeparator separator2;
    private JSeparator separator3;
    private JLabel lConnectTimeout;
    private JLabel lResponseTimeout;
    private JTextField tResponseTimeout;
    private JLabel lStartAddress;
    private JTextField tStartAddress;
    private JLabel lQuantity;
    private JTextField tQuantity;
    private JLabel lSelectFunctions;
    private JRadioButton rF02;
    private JRadioButton rF03;
    private JRadioButton rF04;
    private JLabel lResults;
    private JButton btnStart;
    private JButton btnStop;
    private JTextField tSlaveId;
    private JLabel lSlaveId;

    private boolean invalidPar = false;

    private boolean tFieldEmpty() {
        return tIp.getText().isEmpty() || tPort.getText().isEmpty() || tConnectTimeout.getText().isEmpty() || tSlaveId.getText().isEmpty()
                || tResponseTimeout.getText().isEmpty() || tStartAddress.getText().isEmpty() || tQuantity.getText().isEmpty()
                || (!rF01.isSelected() && !rF02.isSelected() && !rF03.isSelected() && !rF04.isSelected());
    }

    private void initSocketEngine(SocketEngine socketEngine) {
        List<Byte> funArray = new ArrayList<>();

        if (rF01.isSelected()) {
            funArray.add((byte) 1);
        } else {
            funArray.remove(Byte.valueOf((byte) 1));
        }
        if (rF02.isSelected()) {
            funArray.add((byte) 2);
        } else {
            funArray.remove(Byte.valueOf((byte) 2));
        }
        if (rF03.isSelected()) {
            funArray.add((byte) 3);
        } else {
            funArray.remove(Byte.valueOf((byte) 3));
        }
        if (rF04.isSelected()) {
            funArray.add((byte) 4);
        } else {
            funArray.remove(Byte.valueOf((byte) 4));
        }
        socketEngine.setFunArray(funArray);

        String[] tmpIp = tIp.getText().split("\\.");

        String ip1 = "192", ip2 = "168", ip3_1 = "1", ip3_2 = "255", ip4_1 = "1", ip4_2 = "255";
        if (tmpIp.length == 4) {
            ip1 = tmpIp[0];
            ip2 = tmpIp[1];

            String[] ip3Tmp = tmpIp[2].split("-");
            if (ip3Tmp.length == 2) {
                ip3_1 = ip3Tmp[0];
                ip3_2 = ip3Tmp[1];
            } else {
                ip3_1 = ip3Tmp[0];
                ip3_2 = ip3Tmp[0];
            }

            String[] ip4Tmp = tmpIp[3].split("-");
            if (ip4Tmp.length == 2) {
                ip4_1 = ip4Tmp[0];
                ip4_2 = ip4Tmp[1];
            } else {
                ip4_1 = ip4Tmp[0];
                ip4_2 = ip4Tmp[0];
            }

            socketEngine.setIp1(ip1);
            socketEngine.setIp2(ip2);

        } else {
            JOptionPane.showMessageDialog(frame, "Wrong IP!");
        }

        int port_1 = 502, port_2 = 502;

        String[] tmpPort = tPort.getText().split("-");
        try {
            if (tmpPort.length == 2) {
                port_1 = Integer.valueOf(tmpPort[0]);
                port_2 = Integer.valueOf(tmpPort[1]);
            } else {
                port_1 = Integer.valueOf(tmpPort[0]);
                port_2 = Integer.valueOf(tmpPort[0]);
            }

            socketEngine.setPort_1(port_1);
            socketEngine.setPort_2(port_2);

            int ip3_1int = Integer.valueOf(ip3_1);
            int ip3_2int = Integer.valueOf(ip3_2);
            int ip4_1int = Integer.valueOf(ip4_1);
            int ip4_2int = Integer.valueOf(ip4_2);

            socketEngine.setIp3_1int(ip3_1int);
            socketEngine.setIp3_2int(ip3_2int);
            socketEngine.setIp4_1int(ip4_1int);
            socketEngine.setIp4_2int(ip4_2int);

            int connectTimeout = Integer.valueOf(tConnectTimeout.getText());
            int responseTimeout = Integer.valueOf(tResponseTimeout.getText());
            int slaveId = Integer.valueOf(tSlaveId.getText());
            int startAddr = Integer.valueOf(tStartAddress.getText());
            int scanQuant = Integer.valueOf(tQuantity.getText());

            socketEngine.setCnnctTimeout(connectTimeout);
            socketEngine.setRspnsTimeout(responseTimeout);
            socketEngine.setSlaveId(slaveId);
            socketEngine.setStrtAddress(startAddr);
            socketEngine.setScnQuantity(scanQuant);

            invalidPar = false;
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(frame, "Invalid parameter!");
            invalidPar = true;
        }
    }

    private MdbTcpScanner() {
        SocketEngine socketEngine = new SocketEngine(frame, tResults, pResults);
        btnStart.addActionListener(e -> {

            socketEngine.setStop(false);

            if (!tFieldEmpty()) {
                initSocketEngine(socketEngine);
                if (!invalidPar) {
                    Thread thread = new Thread(socketEngine);
                    if (!thread.isAlive()) {
                        thread.start();
                    }
                }
            } else {
                JOptionPane.showMessageDialog(frame, "Empty field!");
            }
        });
        btnStop.addActionListener(e -> socketEngine.setStop(true));
    }

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        panel = new JPanel();
        panel.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(17, 6, new Insets(0, 0, 0, 0), -1, -1));
        lIp = new JLabel();
        lIp.setText("IP");
        panel.add(lIp, new com.intellij.uiDesigner.core.GridConstraints(1, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(-1, 20), null, 0, false));
        lPort = new JLabel();
        lPort.setText("Port");
        panel.add(lPort, new com.intellij.uiDesigner.core.GridConstraints(1, 2, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(-1, 20), null, 0, false));
        tIp = new JTextField();
        tIp.setText("192.168.1-255.1-255");
        panel.add(tIp, new com.intellij.uiDesigner.core.GridConstraints(2, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(170, 22), null, 0, false));
        tPort = new JTextField();
        tPort.setText("1-10000");
        panel.add(tPort, new com.intellij.uiDesigner.core.GridConstraints(2, 2, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(90, 22), new Dimension(90, -1), 0, false));
        lConnectTimeout = new JLabel();
        lConnectTimeout.setText("Connect Timeout, ms");
        panel.add(lConnectTimeout, new com.intellij.uiDesigner.core.GridConstraints(4, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(-1, 20), null, 0, false));
        tConnectTimeout = new JTextField();
        tConnectTimeout.setText("1000");
        panel.add(tConnectTimeout, new com.intellij.uiDesigner.core.GridConstraints(4, 2, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(70, 22), new Dimension(70, -1), 0, false));
        lResults = new JLabel();
        lResults.setText("Results");
        panel.add(lResults, new com.intellij.uiDesigner.core.GridConstraints(1, 3, 1, 2, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(-1, 20), null, 0, false));
        tResponseTimeout = new JTextField();
        tResponseTimeout.setText("1000");
        panel.add(tResponseTimeout, new com.intellij.uiDesigner.core.GridConstraints(5, 2, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(70, 22), new Dimension(70, -1), 0, false));
        lResponseTimeout = new JLabel();
        lResponseTimeout.setText("Response Timeout, ms");
        panel.add(lResponseTimeout, new com.intellij.uiDesigner.core.GridConstraints(5, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(-1, 20), null, 0, false));
        separator1 = new JSeparator();
        panel.add(separator1, new com.intellij.uiDesigner.core.GridConstraints(3, 1, 1, 2, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(-1, 2), null, 0, false));
        separator2 = new JSeparator();
        panel.add(separator2, new com.intellij.uiDesigner.core.GridConstraints(6, 1, 1, 2, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(-1, 2), null, 0, false));
        lStartAddress = new JLabel();
        lStartAddress.setText("Start Address");
        panel.add(lStartAddress, new com.intellij.uiDesigner.core.GridConstraints(8, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(-1, 20), null, 0, false));
        tStartAddress = new JTextField();
        tStartAddress.setText("1");
        panel.add(tStartAddress, new com.intellij.uiDesigner.core.GridConstraints(8, 2, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(70, 22), new Dimension(70, -1), 0, false));
        lQuantity = new JLabel();
        lQuantity.setText("Quantity");
        panel.add(lQuantity, new com.intellij.uiDesigner.core.GridConstraints(9, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(-1, 20), null, 0, false));
        tQuantity = new JTextField();
        tQuantity.setText("1");
        panel.add(tQuantity, new com.intellij.uiDesigner.core.GridConstraints(9, 2, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(70, 22), new Dimension(70, -1), 0, false));
        lSelectFunctions = new JLabel();
        lSelectFunctions.setText("Select Functions");
        panel.add(lSelectFunctions, new com.intellij.uiDesigner.core.GridConstraints(11, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(-1, 20), null, 0, false));
        rF01 = new JRadioButton();
        rF01.setSelected(true);
        rF01.setText("F01 coil status (0x)");
        panel.add(rF01, new com.intellij.uiDesigner.core.GridConstraints(12, 1, 1, 2, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(-1, 22), null, 0, false));
        separator3 = new JSeparator();
        panel.add(separator3, new com.intellij.uiDesigner.core.GridConstraints(10, 1, 1, 2, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(-1, 2), null, 0, false));
        tResults = new JTextArea();
        tResults.setAlignmentX(0.5f);
        tResults.setEditable(false);
        panel.add(tResults, new com.intellij.uiDesigner.core.GridConstraints(2, 3, 12, 2, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final com.intellij.uiDesigner.core.Spacer spacer1 = new com.intellij.uiDesigner.core.Spacer();
        panel.add(spacer1, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 15, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, new Dimension(5, -1), null, 0, false));
        final com.intellij.uiDesigner.core.Spacer spacer2 = new com.intellij.uiDesigner.core.Spacer();
        panel.add(spacer2, new com.intellij.uiDesigner.core.GridConstraints(1, 5, 15, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, new Dimension(5, -1), null, 0, false));
        final com.intellij.uiDesigner.core.Spacer spacer3 = new com.intellij.uiDesigner.core.Spacer();
        panel.add(spacer3, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 6, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_VERTICAL, 1, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(-1, 5), null, 0, false));
        final com.intellij.uiDesigner.core.Spacer spacer4 = new com.intellij.uiDesigner.core.Spacer();
        panel.add(spacer4, new com.intellij.uiDesigner.core.GridConstraints(16, 0, 1, 6, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_VERTICAL, 1, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(-1, 5), null, 0, false));
        lSlaveId = new JLabel();
        lSlaveId.setText("Slave ID");
        panel.add(lSlaveId, new com.intellij.uiDesigner.core.GridConstraints(7, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        tSlaveId = new JTextField();
        tSlaveId.setText("1");
        panel.add(tSlaveId, new com.intellij.uiDesigner.core.GridConstraints(7, 2, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(70, -1), new Dimension(70, -1), 0, false));
        rF02 = new JRadioButton();
        rF02.setSelected(true);
        rF02.setText("F02 input status (1x)");
        panel.add(rF02, new com.intellij.uiDesigner.core.GridConstraints(13, 1, 1, 2, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(-1, 22), null, 0, false));
        rF03 = new JRadioButton();
        rF03.setSelected(true);
        rF03.setText("F03 holding registers (4x)");
        panel.add(rF03, new com.intellij.uiDesigner.core.GridConstraints(14, 1, 1, 2, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(170, 22), null, 0, false));
        rF04 = new JRadioButton();
        rF04.setSelected(true);
        rF04.setText("F04 input registers (3x)");
        panel.add(rF04, new com.intellij.uiDesigner.core.GridConstraints(15, 1, 1, 2, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(170, 22), null, 0, false));
        btnStart = new JButton();
        btnStart.setText("start");
        panel.add(btnStart, new com.intellij.uiDesigner.core.GridConstraints(15, 3, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(90, 22), null, 0, false));
        btnStop = new JButton();
        btnStop.setText("stop");
        panel.add(btnStop, new com.intellij.uiDesigner.core.GridConstraints(15, 4, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(90, 22), null, 0, false));
        pResults = new JProgressBar();
        panel.add(pResults, new com.intellij.uiDesigner.core.GridConstraints(14, 3, 1, 2, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return panel;
    }

    public static void main(String[] args) {
        frame = new JFrame("Modbus Tcp Scanner");
        frame.setContentPane(new MdbTcpScanner().panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
