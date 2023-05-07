# create database & table
DROP DATABASE IF EXISTS supermarket;
CREATE DATABASE IF NOT EXISTS supermarket;
USE supermarket;
DROP TABLE IF EXISTS `user`;
CREATE TABLE IF NOT EXISTS `user` (
                                      id BIGINT PRIMARY KEY AUTO_INCREMENT,
                                      username VARCHAR(20) NOT NULL,
                                      password VARCHAR(50) NOT NULL
);

DROP TABLE IF EXISTS `category`;
CREATE TABLE IF NOT EXISTS `category` (
                                          id BIGINT PRIMARY KEY AUTO_INCREMENT,
                                          name VARCHAR(20) UNIQUE,
                                          recommend VARCHAR(20) NOT NULL COMMENT "推荐指数",
                                          products int NOT NULL
);

DROP TABLE IF EXISTS `product`;
CREATE TABLE IF NOT EXISTS `product` (
                                         id BIGINT PRIMARY KEY AUTO_INCREMENT,
                                         cid BIGINT,
                                         name VARCHAR(20) NOT NULL,
                                         yieldDate DATE NOT NULL COMMENT "生产日期",
                                         manufacturers VARCHAR(20) NOT NULL COMMENT "产家",
                                         price DECIMAL NOT NULL,
                                         createDate DATE NOT NULL COMMENT "进货日期",
                                         stock INT NOT NULL CHECK(stock>0),
                                         nowPrice DECIMAL NOT NULL COMMENT "售价",
                                         saleCount INT NOT NULL,
                                         category VARCHAR(20) NOT NULL,
                                         FOREIGN KEY(cid) REFERENCES category(id) ON DELETE CASCADE ON UPDATE CASCADE
);

DROP TABLE IF EXISTS `sale`;
CREATE TABLE IF NOT EXISTS `sale` (
                                      id BIGINT PRIMARY KEY AUTO_INCREMENT,
                                      pid BIGINT,
                                      createDate DATE NOT NULL,
                                      saleCount INT NOT NULL,
                                      FOREIGN KEY(pid) REFERENCES product(id) ON DELETE CASCADE ON UPDATE CASCADE
);

# create trigger
DELIMITER $
DROP TRIGGER IF EXISTS insert_before_product$
CREATE TRIGGER insert_before_product BEFORE INSERT
    ON product FOR EACH ROW
BEGIN
    DECLARE cName VARCHAR(20);
    DECLARE cCount INT;
    set cName = (SELECT name FROM category WHERE id = new.cid);
    set cCount = (SELECT products FROM category where id = new.cid);
    set new.category = cName;
    UPDATE category set products = cCount+1 WHERE id = new.cid;
END$
-- sale表插入数据时,product的库存相应减少
DROP TRIGGER IF EXISTS insert_after_sale$
CREATE TRIGGER insert_after_sale AFTER INSERT
    ON sale FOR EACH ROW
BEGIN
    UPDATE product set stock = stock - new.saleCount WHERE id = new.pid;
    UPDATE product set saleCount = saleCount + new.saleCount WHERE id = new.pid;
END$
-- category表更改时,product的分类名称也修改
DELIMITER $
DROP TRIGGER IF EXISTS update_after_category$
CREATE TRIGGER update_after_category AFTER UPDATE
    ON category FOR EACH ROW
BEGIN
    IF old.name != new.name THEN
        UPDATE product set category = new.name WHERE cid = new.id;
    END IF;
END$

USE supermarket;
INSERT `user` VALUES
                  (null,"admin","123456"),
                  (null,"perzch","perzch"),
                  (null,"test","123456");

INSERT `category` VALUES
                      (null,"食品","97",0),
                      (null,"日用品","90",0),
                      (null,"饮品","100",0),
                      (null,"化妆品","97",0);

INSERT `product`(cid,name,yieldDate,manufacturers,price,createDate,stock,nowPrice,saleCount) VALUES
                                                                                                 (1,"饼干","2020-07-03","奥利奥",5.00,"2020-07-13",989,7.00,0),
                                                                                                 (2,"牙刷","2020-07-02","舒克",3.00,"2020-07-13",260,6.00,0),
                                                                                                 (2,"牙膏","2020-07-02","舒克",8.00,"2020-07-13",500,16.00,0),
                                                                                                 (3,"绿茶","2020-07-02","统一",1.50,"2020-07-13",3797,3.00,0),
                                                                                                 (4,"口红","2020-02-05","迪奥",200.00,"2020-07-13",300,300.00,0);

INSERT `sale`(pid,createDate,saleCount) VALUES
                                            (1,"2020-07-14",11),
                                            (2,"2020-07-14",40),
                                            (3,"2020-07-14",410),
                                            (4,"2020-07-14",203)