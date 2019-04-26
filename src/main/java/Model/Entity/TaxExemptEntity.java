package Model.Entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "tax_exempt", schema = "u292891910_shop", catalog = "")
public class TaxExemptEntity {
    private int exemptId;
    private String city;
    private int productType;

    @Id
    @Column(name = "exemptID")
    public int getExemptId() {
        return exemptId;
    }

    public void setExemptId(int exemptId) {
        this.exemptId = exemptId;
    }

    @Basic
    @Column(name = "city")
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Basic
    @Column(name = "productType")
    public int getProductType() {
        return productType;
    }

    public void setProductType(int productType) {
        this.productType = productType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TaxExemptEntity that = (TaxExemptEntity) o;
        return exemptId == that.exemptId &&
                productType == that.productType &&
                Objects.equals(city, that.city);
    }

    @Override
    public int hashCode() {
        return Objects.hash(exemptId, city, productType);
    }
}
