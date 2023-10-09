package net.nvsoftware.springlive.error;

public class ProductNotFoundException extends RuntimeException {

    public ProductNotFoundException(String msg) {
        super(msg);
    }
}
