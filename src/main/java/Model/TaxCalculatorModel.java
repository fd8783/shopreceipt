package Model;

import Helper.HibernateHelper;
import Model.Entity.ProductEntity;
import Model.Entity.TaxExemptEntity;
import Model.Entity.TaxRateEntity;
import org.hibernate.Session;
import org.hibernate.exception.GenericJDBCException;
import org.hibernate.exception.JDBCConnectionException;

import java.math.BigDecimal;
import java.util.List;

public class TaxCalculatorModel {

    private Session dbSession;

    public TaxCalculatorModel() {
    }

    public BigDecimal getSubTotal(ItemModel[] items){
        BigDecimal sum = new BigDecimal(0);
        for (int i = 0; i < items.length; i++){
            sum = sum.add(items[i].getPrice().multiply(new BigDecimal(items[i].getQuantity())));
        }
        return sum;
    }

    public BigDecimal calculateTax(String location, ItemModel[] items){
        if (dbSession == null || !dbSession.isOpen()){
            System.out.println("Opening session to database");
            dbSession = HibernateHelper.getSessionFactory().openSession();
        }

        //check Location exist or not
        try {
            TaxRateEntity taxRateEntity = dbSession.get(TaxRateEntity.class, location);
            if (taxRateEntity == null) return new BigDecimal(-1); //-1 = location not exist

            //get Tax Rate and exempt tpye  of Location
            BigDecimal taxSum = new BigDecimal(0);
            final BigDecimal taxRate = taxRateEntity.getTaxRate();
            final List<TaxExemptEntity> taxExemptList = dbSession.createQuery("From TaxExemptEntity t Where t.city = ?1", TaxExemptEntity.class).setParameter(1, location).getResultList();

            //calculate Tax
            for (int i = 0; i < items.length; i++) {
                List<ProductEntity> product = dbSession.createQuery("From ProductEntity p Where p.productName = ?1", ProductEntity.class).setParameter(1, items[i].getName()).getResultList();
                final int productType = product.size() > 0 ? product.get(0).getProductType() : 0;
                if (isExempted(taxExemptList, productType)) {
                    continue;
                }
                BigDecimal curTax = items[i].getPrice().multiply(taxRate).multiply(new BigDecimal(items[i].getQuantity()));
                //round up tax to 0.05
                curTax = curTax.multiply(new BigDecimal(20)).setScale(0, BigDecimal.ROUND_UP).divide(new BigDecimal(20));

                taxSum = taxSum.add(curTax);
            }

            dbSession.close();

            return taxSum;
        }
        catch (GenericJDBCException e){
            dbSession.close();

            System.err.println(e.getMessage());
            return new BigDecimal(-2);
        }
        catch (JDBCConnectionException e){
            dbSession.close();
            System.err.println(e.getMessage());

            //timeout, retry
            System.out.println("Trying to reconnect to database");
            return calculateTax(location, items);
        }
    }

    private boolean isExempted(List<TaxExemptEntity> taxExemptList, int productType){
        for (int i = 0; i < taxExemptList.size(); i++){
            if (taxExemptList.get(i).getProductType() == productType)
                return true;
        }
        return false;
    }
}
