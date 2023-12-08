package com.yasinatagun.shoestore.model;

import android.graphics.Color;

import com.yasinatagun.shoestore.model.enums.Gender;
import com.yasinatagun.shoestore.model.enums.ShoeType;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

public class Shoe implements Serializable {
    Random r = new Random();
    private int productId;
    private String documentId;
    private String productName;
    private ShoeType productType;
    private int productPrice;
    private String productDescription;
    private int productStock;
    private int productDeliveryTime;
    private Gender productGender;
    public String downloadUrl;
    private ArrayList<Integer> productAvailableSizes = new ArrayList<>();

    public Shoe(String productName, ShoeType productType, int productPrice, String productDescription, int productStock, int productDeliveryTime, Gender gender, String downloadUrl, String documentId) {
        this.productId = r.nextInt(10000)+100000;
        this.productName = productName;
        this.productType = productType;
        this.productPrice = productPrice;
        this.productDescription = productDescription;
        this.productStock = productStock;
        this.productDeliveryTime = productDeliveryTime;
        enterSizes(this.productAvailableSizes);
        this.productGender = gender;
        this.downloadUrl = downloadUrl;
        this.documentId = documentId;
    }



    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public ArrayList<Integer> enterSizes(ArrayList<Integer> sizeList){
        sizeList.add(42);
        sizeList.add(45);
        sizeList.add(44);
        sizeList.add(37);
        sizeList.add(41);
        return sizeList;
    }


    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public ShoeType getProductType() {
        return productType;
    }

    public void setProductType(ShoeType productType) {
        this.productType = productType;
    }

    public int getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(int productPrice) {
        this.productPrice = productPrice;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public int getProductStock() {
        return productStock;
    }

    public void setProductStock(int productStock) {
        this.productStock = productStock;
    }

    public int getProductDeliveryTime() {
        return productDeliveryTime;
    }

    public void setProductDeliveryTime(int productDeliveryTime) {
        this.productDeliveryTime = productDeliveryTime;
    }

    public ArrayList<Integer> getProductAvailableSizes() {
        return productAvailableSizes;
    }

    public void setProductAvailableSizes(ArrayList<Integer> productAvailableSizes) {
        this.productAvailableSizes = productAvailableSizes;
    }

    public Gender getProductGender() {
        return productGender;
    }

    public void setProductGender(Gender productGender) {
        this.productGender = productGender;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }
}
