package com.polibuda.gimbus.android_lab_final;

class Product {
    private String name, unit, imagePath;
    private float amount;

    Product(String name, String amount, String unit) {
        this.name = name;
        this.unit = unit;
        this.amount = Float.valueOf(amount);
        this.imagePath = null;
    }

    Product(String name, String amount, String unit, String imagePath) {
        this.imagePath = imagePath;
        this.name = name;
        this.unit = unit;
        this.amount = Float.valueOf(amount);
    }

    public String getName() {
        return name;
    }

    public String getUnit() {
        return unit;
    }

    public float getAmount() {
        return amount;
    }

    public String getImagePath() {
        return imagePath;
    }

    @Override
    public String toString() {
        String result = name + ";" + String.valueOf(amount) + ";" + unit + ";";
        if (imagePath != null) {
            result += imagePath + ";";
        } else {
            result += "none;";
        }
        return result;
    }
}
