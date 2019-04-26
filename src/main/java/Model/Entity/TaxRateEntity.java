package Model.Entity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "tax", schema = "u292891910_shop", catalog = "")
public class TaxRateEntity {
    private String city;
    private BigDecimal taxRate;

    @Id
    @Column(name = "city")
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Basic
    @Column(name = "tax_rate")
    public BigDecimal getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(BigDecimal taxRate) {
        this.taxRate = taxRate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TaxRateEntity that = (TaxRateEntity) o;
        return Objects.equals(city, that.city) &&
                Objects.equals(taxRate, that.taxRate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(city, taxRate);
    }
}
