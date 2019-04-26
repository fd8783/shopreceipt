package Model.Entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "product", schema = "u292891910_shop", catalog = "")
public class ProductEntity {
    private int productId;
    private String productName;
    private int productType;

    @Id
    @Column(name = "product_id")
    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    @Basic
    @Column(name = "product_name")
    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    @Basic
    @Column(name = "product_type")
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
        ProductEntity that = (ProductEntity) o;
        return productId == that.productId &&
                productType == that.productType &&
                Objects.equals(productName, that.productName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId, productName, productType);
    }
}
