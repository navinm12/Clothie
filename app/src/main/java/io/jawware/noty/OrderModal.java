package io.jawware.noty;

public class OrderModal {

    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProductdiscount() {
        return productdiscount;
    }

    public void setProductdiscount(String productdiscount) {
        this.productdiscount = productdiscount;
    }

    public String getProducid() {
        return producid;
    }

    public void setProducid(String producid) {
        this.producid = producid;
    }

    public String getProductname() {
        return productname;
    }

    public void setProductname(String productname) {
        this.productname = productname;
    }

    public String getProductprize() {
        return productprize;
    }

    public void setProductprize(String productprize) {
        this.productprize = productprize;
    }

    private String productdiscount;
    private String producid;
    private String productname;
    private String productprize;

    public String getProductquan() {
        return productquan;
    }

    public void setProductquan(String productquan) {
        this.productquan = productquan;
    }

    private String productquan;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    private String status;
}
