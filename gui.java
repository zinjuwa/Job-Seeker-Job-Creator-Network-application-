import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class gui extends JPanel {
    public JComboBox<String> jobOptions, options;
    public JButton start, ok;
    public JLabel label1, label2, label3, res;
    public JTextField textfield1, textfield2;
    public JPanel panel1, panel2, panel3;
    boolean actOptions = false;
    JobCreator jobcreator;

    public gui() {
        super();

        jobOptions = new JComboBox<>();
        options = new JComboBox<>();
        start = new JButton("Start Job");
        ok = new JButton("OK");
        label1 = new JLabel("");
        label2 = new JLabel("");
        label3 = new JLabel("");
        res = new JLabel("");
        textfield1 = new JTextField("", 25);
        textfield2 = new JTextField("", 25);
        panel1 = new JPanel();
        panel2 = new JPanel();
        panel3 = new JPanel();
        jobcreator = new JobCreator();

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        createjobOptions();

        jobOptions.addActionListener(new jobOptionsListener());
        options.addActionListener(new optionListener());
        start.addActionListener(new startListener());
        ok.addActionListener(new okListener());

        panel1.add(jobOptions);

        setInvisible();

        panel3.add(res);
        panel3.add(ok);
        res.setVisible(false);
        ok.setVisible(false);

        this.add(panel1);
        this.add(panel2);
        this.add(panel3);
    }

    public void createjobOptions() {
        jobOptions.addItem("Job Options");
        jobOptions.addItem("1. Detect if a given IP Address or Host Name is online or not.");
        jobOptions.addItem("2. Detect the status of a given port at a given IP Address.");
        jobOptions.addItem("3. ICMP flood attack against a given IP or subnet");
        jobOptions.addItem("4. TCP flood attack against a given port on a given IP");
    }

    public void j1() {
        String result = jobcreator(options.getSelectedIndex(), textfield1.getText());
        res.setText("Output: " + result);
        res.setVisible(true);
        ok.setVisible(true);
        panel3.revalidate();
    }

    public void j2() {
        String result = jobcreator(textfield1.getText(), textfield2.getText());
        res.setText("Output: " + result);
        res.setVisible(true);
        ok.setVisible(true);
        panel3.revalidate();
    }

    public void j3() {
        j1.multiJob = 3;
        j1.mode = options.getSelectedIndex();
        j1.IP = textfield1.getText();
        j2.multiJob = 3;
        j2.mode = options.getSelectedIndex();
        j2.IP = textfield1.getText();
        Thread t1 = new Thread(j1);
        Thread t2 = new Thread(j2);
        t1.start();
        t2.start();
        res.setText("Output: " + j1.result);
        res.setVisible(true);
        ok.setVisible(true);
        panel3.revalidate();
    }

    public void j4() {
        res.setText("Output: Port: " + textfield1.getText() + ", Who: " + textfield2.getText());
        res.setVisible(true);
        ok.setVisible(true);
        panel3.revalidate();
    }

    public void j1Add() {
        label1.setText("Detect by: ");
        options.removeAllItems();
        options.addItem("");
        options.addItem("IP Address");
        options.addItem("Host Name");
        options.setSelectedIndex(0);
        label1.setVisible(true);
        options.setVisible(true);
        panel2.add(label1);
        panel2.add(options);
        panel2.add(label2);
        panel2.add(textfield1);
        panel2.add(start);
        panel2.revalidate();
        actOptions = true;
    }

    public void j2Add() {
        label1.setText("Port : ");
        label2.setText("IP Address: ");
        label1.setVisible(true);
        label2.setVisible(true);
        textfield1.setVisible(true);
        textfield2.setVisible(true);
        start.setVisible(true);
        panel2.add(label1);
        panel2.add(textfield1);
        panel2.add(label2);
        panel2.add(textfield2);
        panel2.add(start);
        panel2.revalidate();
    }

    public void j3Add() {
        label1.setText("Flood against: ");
        options.removeAllItems();
        options.addItem("");
        options.addItem("IP Address");
        options.addItem("Subnet");
        options.addItem("Unknown");
        options.setSelectedIndex(0);
        label1.setVisible(true);
        options.setVisible(true);
        panel2.add(label1);
        panel2.add(options);
        panel2.add(label2);
        panel2.add(textfield1);
        panel2.add(start);
        panel2.revalidate();
        actOptions = true;
    }

    public void j4Add() {
        label1.setText("Port : ");
        label2.setText("IP Address: ");
        label1.setVisible(true);
        label2.setVisible(true);
        textfield1.setVisible(true);
        textfield2.setVisible(true);
        start.setVisible(true);
        panel2.add(label1);
        panel2.add(textfield1);
        panel2.add(label2);
        panel2.add(textfield2);
        panel2.add(start);
        panel2.revalidate();
    }

    public void clearInputs() {
        textfield1.setText("");
        textfield2.setText("");
        panel2.revalidate();
    }

    public void setInvisible() {
        options.setVisible(false);
        label1.setVisible(false);
        label2.setVisible(false);
        label3.setVisible(false);
        textfield1.setVisible(false);
        textfield2.setVisible(false);
        start.setVisible(false);
        panel2.revalidate();
    }

    public void createDialog(String title, String content) {
        JFrame dialogBox = new JFrame(title);
        JLabel dialog = new JLabel(content);
        dialogBox.setLayout(new BorderLayout());
        dialogBox.add(dialog, BorderLayout.CENTER);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        dialogBox.setSize(screenSize.width / 4, screenSize.height / 4);
        dialogBox.setLocation(screenSize.width / 2 - screenSize.width / 8, screenSize.height / 2 - screenSize.height / 8);
        dialogBox.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        dialogBox.setVisible(true);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("JobCreator");
        frame.add(new GUI());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setSize(Toolkit.getDefaultToolkit().getScreenSize().width / 2, Toolkit.getDefaultToolkit().getScreenSize().height);
    }

    class jobOptionsListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            setInvisible();
            panel2.removeAll();
            clearInputs();
            switch (jobOptions.getSelectedIndex()) {
                case 0:
                default:
                    break;
                case 2:
                    j1Add();
                    break;
                case 3:
                    j2Add();
                    break;
                case 4:
                    j3Add();
                    break;
                case 5:
                    j4Add();
                    break;
            }
        }
    }

    class optionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if(options.getSelectedIndex() == 0 && actOptions) {
                label2.setVisible(false);
                textfield1.setVisible(false);
                textfield1.setText("");
                start.setVisible(false);
                panel2.revalidate();
            }

            else if(options.getSelectedIndex() == 1 && actOptions) {
                label2.setText("IP Address: ");
                label2.setVisible(true);
                textfield1.setText("");
                textfield1.setVisible(true);
                start.setVisible(true);
                panel2.revalidate();
            }

            else if(options.getSelectedIndex() == 2 && actOptions) {
                if(jobOptions.getSelectedIndex() == 2)
                    label2.setText("Host Name: ");
                else
                    label2.setText("Subnet: ");
                label2.setVisible(true);
                textfield1.setVisible(true);
                textfield1.setText("");
                start.setVisible(true);
                panel2.revalidate();
            }

            else if(options.getSelectedIndex() == 3 && actOptions) {
                label2.setText("Unknown: ");
                label2.setVisible(true);
                textfield1.setVisible(true);
                textfield1.setText("");
                start.setVisible(true);
                panel2.revalidate();
            }
        }
    }

    class startListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            switch(jobOptions.getSelectedIndex()) {
                case 0:
                case 1:
                case 4:
                default:
                    break;
                case 2:
                    if(textfield1.getText().compareTo("") == 0 && options.getSelectedIndex() == 1)
                        createDialog("Input Error: IP Address", "Please enter an IP Address.");
                    else if(textfield1.getText().compareTo("") == 0 && options.getSelectedIndex() == 2)
                        createDialog("Input Error: Host Name", "Please enter a Host Name.");
                    else
                        j1();
                    break;
                case 3:
                    if(textfield1.getText().compareTo("") == 0 && textfield2.getText().compareTo("") == 0)
                        createDialog("Input Error: Port & IP Address", "Please enter a Port and an IP Address");
                    else if(textfield1.getText().compareTo("") == 0)
                        createDialog("Input Error: Port ", "Please enter a Port.");
                    else if(textfield2.getText().compareTo("") == 0)
                        createDialog("Input Error: IP Address", "Please enter an IP Address.");
                    else
                        j2();
                    break;
                case 5:
                    if(textfield1.getText().compareTo("") == 0 && options.getSelectedIndex() == 1)
                        createDialog("Input Error: IP Address", "Please enter an IP Address.");
                    else if(textfield1.getText().compareTo("") == 0 && options.getSelectedIndex() == 2)
                        createDialog("Input Error: Subnet", "Please enter a Subnet.");
                    else {
                        j3();
                    }
                    break;
                case 6:
                    if(textfield1.getText().compareTo("") == 0 && textfield2.getText().compareTo("") == 0)
                        createDialog("Input Error: Port & IP Address", "Please enter a Port and an IP Address");
                    else if(textfield1.getText().compareTo("") == 0)
                        createDialog("Input Error: Port ", "Please enter a Port.");
                    else if(textfield2.getText().compareTo("") == 0)
                        createDialog("Input Error: IP Address", "Please enter an IP Address.");
                    else
                        j4();
                    break;
            }
        }
    }

    class okListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            panel2.removeAll();
            panel2.revalidate();

            res.setVisible(false);
            ok.setVisible(false);
            panel3.revalidate();

            setInvisible();
            clearInputs();
            actOptions = false;
            jobOptions.setSelectedIndex(0);
        }
    }
}