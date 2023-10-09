package net.nvsoftware.springlive.model;

public class Product {
    private String productId;
    private String title;
    private String description;

    public String getProductId() {
        return productId;
    }

    public void setProductId(String id) {
        this.productId = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
