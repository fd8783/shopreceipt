package View;

import Helper.StringHelper;
import Model.ItemModel;
import sun.plugin.javascript.navig.Array;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ShoppingCartView extends JFrame {

    private JPanel shoppingCartPanel;

    private JLabel labelLocation, labelItems;

    private JTextField tfLocation, tfItems;

    private JButton btnPrintReceipt;

    public ShoppingCartView() {
        this(StringHelper.getString("shoppingCart"));
    }

    public ShoppingCartView(String title) {
        super(title);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setSize(550,105);
        this.setLocationRelativeTo(null);

        this.add(initShoppingCartPanel());
    }

    private JPanel initShoppingCartPanel(){
        shoppingCartPanel = new JPanel();

        labelLocation = new JLabel(StringHelper.getString("location"));
        tfLocation = new JTextField(2);
        labelItems = new JLabel(StringHelper.getString("items"));
        tfItems = new JTextField(30);
        btnPrintReceipt = new JButton(StringHelper.getString("printReceipt"));

        shoppingCartPanel.add(labelLocation);
        shoppingCartPanel.add(tfLocation);
        shoppingCartPanel.add(labelItems);
        shoppingCartPanel.add(tfItems);
        shoppingCartPanel.add(btnPrintReceipt);

        return shoppingCartPanel;
    }

    public String getTfLocation() {
        return tfLocation.getText();
    }

    public ItemModel[] getTfItems() {
        String[] itemListStr = tfItems.getText().replaceAll("( at )", " ").split(",");
        List<ItemModel> itemModels = new ArrayList<>(itemListStr.length);

        //transform the str into item model
        for (int i = 0; i < itemListStr.length; i++){
            String[] itemStr = itemListStr[i].trim().split("\\s+");
            if (itemStr.length < 3) return null;    // format error

            //check duplicate
            String itemName = String.join(" ", Arrays.asList(itemStr).subList(1, itemStr.length - 1));
            boolean duplicate = false;
            for (int j = 0;  j< itemModels.size(); j++){
                if (itemModels.get(j).getName().equals(itemName) && (itemModels.get(j).getPrice().compareTo(new BigDecimal(itemStr[itemStr.length-1]))) == 0) {
                    itemModels.get(j).addQuantity(Integer.parseInt(itemStr[0]));
                    duplicate = true;
                    break;
                }
            }

            if (!duplicate)
                itemModels.add(new ItemModel(itemName, Integer.parseInt(itemStr[0]), new BigDecimal(itemStr[itemStr.length-1])));
        }
        return itemModels.stream().toArray(ItemModel[]::new);
    }

    public void addPrintReceiptBtnListener(ActionListener btnPrintReceiptListener){
        btnPrintReceipt.addActionListener(btnPrintReceiptListener);
    }

    public void loadingMessage(String message){
        Runnable popUp = () -> JOptionPane.showOptionDialog(null, message,"",
                                JOptionPane.DEFAULT_OPTION,JOptionPane.INFORMATION_MESSAGE, null, new Object[]{}, null);
        SwingUtilities.invokeLater(popUp);
    }

    public void closePopUp(){
        JOptionPane.getRootFrame().dispose();
    }
}
