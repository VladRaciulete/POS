package com.pos.pos.common;

public class ProductStatisticsDto implements Comparable<ProductStatisticsDto> {

    private Long product_id;
    private String product_name;
    private int quantity;

    public ProductStatisticsDto(Long product_id, String product_name, int quantity) {
        this.product_id = product_id;
        this.product_name = product_name;
        this.quantity = quantity;
    }

    public Long getProduct_id() {
        return product_id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public int getQuantity() {
        return quantity;
    }

    @Override
    public int compareTo(ProductStatisticsDto o) {
        if(getQuantity() > o.getQuantity()){
            return -1;
        } else {
            return 1;
        }
    }
}
