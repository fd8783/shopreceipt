package Controller;

import Helper.HibernateHelper;
import Helper.StringHelper;
import Model.ItemModel;
import Model.TaxCalculatorModel;
import View.ReceiptView;
import View.ShoppingCartView;
import org.apache.commons.lang3.StringUtils;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.math.BigDecimal;

public class ShoppingCartController {

    private ShoppingCartView shoppingCartView;
    private TaxCalculatorModel taxCalculatorModel;

    public ShoppingCartController(ShoppingCartView shoppingCartView, TaxCalculatorModel taxCalculatorModel){
        this.taxCalculatorModel = taxCalculatorModel;

        this.shoppingCartView = shoppingCartView;
        this.shoppingCartView.addPrintReceiptBtnListener(new btnPrintReceiptListener());
        this.shoppingCartView.addWindowListener(new viewWindowAdapter());

    }

    class btnPrintReceiptListener implements ActionListener{
        public void actionPerformed(ActionEvent e) {
            shoppingCartView.loadingMessage("Connecting To Database");

            new Thread(() -> {
                ReceiptView receiptView = new ReceiptView(generateReceiptContent());
                receiptView.setVisible(true);
            }).start();
        }
    }

    private String generateReceiptContent(){
        String receiptContent;

        ItemModel[] itemModels =  shoppingCartView.getTfItems();
        if (itemModels == null){
            //getTFItems return null when items' format is not match
            receiptContent = StringHelper.getString("itemFormatError");
        }
        else{
            //Calculate Tax
            BigDecimal tax = taxCalculatorModel.calculateTax(shoppingCartView.getTfLocation(),itemModels);
            BigDecimal subTotal = taxCalculatorModel.getSubTotal(itemModels);

            shoppingCartView.closePopUp();

            int errorCheck = tax.compareTo(new BigDecimal(-1));
            if (errorCheck <= 0){
                //alculateTax return -1 if location not exist, -2 if  GenericJDBCException occur
                receiptContent = errorCheck == -2 ? "GenericJDBCException catched" : StringHelper.getString("locationNotExist");
            }
            else {
                //Print receipt
                String receiptHeader = StringUtils.rightPad(StringHelper.getString("item"), 15, ' ')
                        + StringUtils.center( '$' + StringHelper.getString("price"), 15, ' ')
                        + StringUtils.leftPad(StringHelper.getString("quantity"), 15, ' ');
                receiptContent = receiptHeader + "\n\n";

                for (int i = 0; i < itemModels.length; i++){
                    receiptContent += StringUtils.rightPad(itemModels[i].getName(), 15, ' ')
                            + StringUtils.center( '$' + itemModels[i].getPrice().toString(), 15, ' ')
                            + StringUtils.leftPad(Integer.toString(itemModels[i].getQuantity()), 15, ' ') + "\n";
                }

                receiptContent += StringUtils.rightPad(StringHelper.getString("subTotal"), 30, ' ')
                        + StringUtils.leftPad( '$' + subTotal.toString(), 15, ' ') + "\n";
                receiptContent += StringUtils.rightPad(StringHelper.getString("tax"), 30, ' ')
                        + StringUtils.leftPad( '$' + tax.toString(), 15, ' ') + "\n";
                receiptContent += StringUtils.rightPad(StringHelper.getString("total"), 30, ' ')
                        + StringUtils.leftPad( '$' + (subTotal.add(tax)).toString(), 15, ' ') + "\n";
            }
        }
        System.out.println(receiptContent);
        return receiptContent;
    }

    class viewWindowAdapter extends WindowAdapter{
        @Override
        public void windowClosing(WindowEvent e) {
            shoppingCartView.closePopUp();
            HibernateHelper.shutdown();
            super.windowClosing(e);
        }
    }

}
