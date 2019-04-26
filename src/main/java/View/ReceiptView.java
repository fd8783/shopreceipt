package View;

import Helper.StringHelper;

import javax.swing.*;
import java.awt.*;

public class ReceiptView extends JFrame {

    private JPanel receiptPanel;

    private JTextArea taReceipt;

    private JScrollPane spReceipt;

    public ReceiptView(String receiptContent) {
        this.pack();
        this.setSize(450,300);
        this.setLocationRelativeTo(null);

        this.add(initReceiptPanel(receiptContent));
    }

    public JPanel initReceiptPanel(String receiptContent){
        receiptPanel = new JPanel();

        taReceipt = new JTextArea(receiptContent);
        taReceipt.setLineWrap(true);
        taReceipt.setWrapStyleWord(true);
        taReceipt.setFont(new Font(StringHelper.getString("receiptFont"), Font.PLAIN, 12));
        taReceipt.setEditable(false);

        spReceipt = new JScrollPane(taReceipt);
        spReceipt.setVerticalScrollBarPolicy(
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        spReceipt.setPreferredSize(new Dimension(430, 250));
        spReceipt.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createCompoundBorder(
                                BorderFactory.createTitledBorder(StringHelper.getString("receipt")),
                                BorderFactory.createEmptyBorder(5,5,5,5)),
                        spReceipt.getBorder()));
        receiptPanel.add(spReceipt);

        return receiptPanel;
    }
}
