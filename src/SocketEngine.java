import javax.swing.*;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.List;

public class SocketEngine implements Runnable {
    private String ip1, ip2;
    private int cnnctTimeout;
    private int rspnsTimeout;
    private int slaveId;
    private int strtAddress;
    private int scnQuantity;
    private int ip3_1int;
    private int ip3_2int;
    private int ip4_1int;
    private int ip4_2int;
    private int port_1;
    private int port_2;
    private static boolean stop;
    private boolean cnntnSuccessful;
    private List<Byte> funArray;

    private JFrame frame;
    private JTextArea results;
    private JProgressBar progress;

    SocketEngine(JFrame frame, JTextArea jTextArea, JProgressBar jProgressBar){
        this.frame = frame;
        results = jTextArea;
        progress = jProgressBar;
    }

    public void setIp1(String ip1) {
        this.ip1 = ip1;
    }

    public void setIp2(String ip2) {
        this.ip2 = ip2;
    }

    public void setIp3_1int(int ip3_1int) {
        this.ip3_1int = ip3_1int;
    }

    public void setIp3_2int(int ip3_2int) {
        this.ip3_2int = ip3_2int;
    }

    public void setIp4_1int(int ip4_1int) {
        this.ip4_1int = ip4_1int;
    }

    public void setIp4_2int(int ip4_2int) {
        this.ip4_2int = ip4_2int;
    }

    public void setPort_1(int port_1) {
        this.port_1 = port_1;
    }

    public void setPort_2(int port_2) {
        this.port_2 = port_2;
    }

    public void setCnnctTimeout(int cnnctTimeout) {
        this.cnnctTimeout = cnnctTimeout;
    }

    public void setRspnsTimeout(int rspnsTimeout) {
        this.rspnsTimeout = rspnsTimeout;
    }

    public void setSlaveId(int slaveId) {
        this.slaveId = slaveId;
    }

    public void setStrtAddress(int strtAddress) {
        this.strtAddress = strtAddress;
    }

    public void setScnQuantity(int scnQuantity) {
        this.scnQuantity = scnQuantity;
    }

    public void setFunArray(List<Byte> funArray) {
        this.funArray = funArray;
    }

    public void setStop(boolean stop) {
        this.stop = stop;
    }

    public static void sendRequest(Socket socket, byte[] req) {
        try {
            if (socket != null) {
                DataOutputStream out = new DataOutputStream(socket.getOutputStream());
                out.write(req);
                out.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static byte[] getResponse(Socket socket, int responseTimeout, JFrame frame) {

        byte[] response = null;
        try {
            if (socket != null) {
                socket.setSoTimeout(responseTimeout);
                InputStream in = socket.getInputStream();

                int size;
                byte[] buffer = new byte[1024];
                size = in.read(buffer);

                if (size != -1) {

                    response = new byte[size];
                    System.arraycopy(buffer, 0, response, 0, response.length);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Read time out!");
            stop = true;
        }
        return response;
    }
    
    private void closeSocket(Socket socket){
        if(socket != null){
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void run() {

        results.setText("");
        progress.setValue(0);

        int cntrSocket = 0, cntrFunQuant = 0;

        int prgrsSocket = (ip3_2int - ip3_1int + 1) * (ip4_2int - ip4_1int + 1) * (port_2 - port_1 + 1);
        int prgrsFun = funArray.size() * scnQuantity;

        progress.setMaximum(prgrsSocket);

        Socket socketConnected = null;

//        while (!Thread.currentThread().isInterrupted()) {

            for (int i = ip3_1int; i <= ip3_2int; i++) {
                for (int j = ip4_1int; j <= ip4_2int; j++) {
                    for (int k = port_1; k <= port_2; k++) {

                        cntrSocket++;
                        progress.setValue(cntrSocket);

                        String IP = ip1 + "." + ip2 + "." + i + "." + j;

                        Socket socket = new Socket();
                        try {
                            socket.connect((new InetSocketAddress(IP, k)), cnnctTimeout);
                            if (socket.isConnected()) {
                                cnntnSuccessful = true;
                                stop = true;
                                results.setText("Valid connection:\n" + IP + ":" + k + "\n\n");
                                socketConnected = socket;
                                progress.setValue(prgrsSocket);
                            }

                        } catch (IOException e) {
                            e.printStackTrace();
                            cnntnSuccessful = false;
                        }

                        if (cntrSocket == prgrsSocket && !socket.isConnected()) {
                            JOptionPane.showMessageDialog(frame, "Can't find valid connection!");
                            stop = true;
                        }

                        if (stop) {
                            break;
                        }
                    }
                    if (stop) {
                        break;
                    }
                }
                if (stop) {
                    break;
                }
            }

            if (cnntnSuccessful) {
                stop = false;

                progress.setValue(0);
                progress.setMaximum(prgrsFun);

                for (final byte fun : funArray) {
                    for (int i = 0; i < scnQuantity; i++) {

                        cntrFunQuant++;
                        progress.setValue(cntrFunQuant);

                        byte[] requestFirst = new byte[12];
                        requestFirst[0] = (byte) 0;
                        requestFirst[1] = (byte) 1;
                        requestFirst[2] = (byte) 0;
                        requestFirst[3] = (byte) 0;
                        requestFirst[4] = (byte) 0;
                        requestFirst[5] = (byte) 6;
                        requestFirst[6] = (byte) slaveId;
                        requestFirst[7] = fun;
                        requestFirst[8] = (byte) ((strtAddress + i) >> 8);
                        requestFirst[9] = (byte) (strtAddress + i);
                        requestFirst[10] = (byte) 1 >> 8;
                        requestFirst[11] = (byte) 1;

                        sendRequest(socketConnected, requestFirst);

                        byte[] responseFirst = getResponse(socketConnected, rspnsTimeout, frame);

                        if (responseFirst.length >= 10 && responseFirst.length <= 13) {

                            results.append("Function available: F0" + fun + "\n" + "Start address: " + (strtAddress + i) + "\n");

                            for (int j = 0; j < scnQuantity; j++) {

                                cntrFunQuant++;
                                progress.setValue(cntrFunQuant);

                                byte[] requestLast = new byte[12];
                                requestLast[0] = (byte) 0;
                                requestLast[1] = (byte) 1;
                                requestLast[2] = (byte) 0;
                                requestLast[3] = (byte) 0;
                                requestLast[4] = (byte) 0;
                                requestLast[5] = (byte) 6;
                                requestLast[6] = (byte) 1;
                                requestLast[7] = fun;
                                requestLast[8] = (byte) (((scnQuantity + strtAddress - 1) - j) >> 8);
                                requestLast[9] = (byte) ((scnQuantity + strtAddress - 1) - j);
                                requestLast[10] = (byte) 1 >> 8;
                                requestLast[11] = (byte) 1;

                                sendRequest(socketConnected, requestLast);

                                byte[] responseLast = getResponse(socketConnected, rspnsTimeout, frame);

                                if (responseLast.length >= 10 && responseLast.length <= 13) {

                                    results.append("Last address: " + (scnQuantity + strtAddress - j - 1));
                                    progress.setValue(prgrsFun);

                                    JOptionPane.showMessageDialog(frame, "Complete!");
                                    progress.setValue(0);
                                    stop = true;
                                    break;
                                }
                            }
                            if (stop) {
                                break;
                            }
                            break;
                        }
                        if (stop) {
                            break;
                        }
                    }
                    if (stop) {
                        closeSocket(socketConnected);
                        break;
                    }

                    if (prgrsFun == cntrFunQuant) {
                        JOptionPane.showMessageDialog(frame, "Can't find valid function!");
                        closeSocket(socketConnected);
                        break;
                    }
                }
            }
            Thread.currentThread().interrupt();
//        }
    }
}
