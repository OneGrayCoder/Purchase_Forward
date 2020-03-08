package com.aki.purchaseforward.pojo;

/**
 * @AUTUOR QXW
 * @CREATE 2020/3/1 14:51
 */
public class Good {
    /*
* create table good(
id int(10) not null primary key auto_increment,
goodname varchar(20) not null,
goodtype varchar(20) not null,
price decimal(10,2) not null,
pic varchar(20)
)    * */
    private int id;
    private String goodname;
    private String goodtype;
    private double price;
    //pic 是啥
    //pic是上传的商品文件的名称
    private String pic;
    public Good(){
    }

    public Good(int id, String goodname, String goodtype, Double price, String pic) {
        this.id = id;
        this.goodname = goodname;
        this.goodtype = goodtype;
        this.price = price;
        this.pic = pic;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGoodname() {
        return goodname;
    }

    public void setGoodname(String goodname) {
        this.goodname = goodname;
    }

    public String getGoodtype() {
        return goodtype;
    }

    public void setGoodtype(String goodtype) {
        this.goodtype = goodtype;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }
}
