# create trigger
DELIMITER $
-- product表更新数据前,category_name禁止更新(会导致修改cid时category_name不同步)
# DROP TRIGGER IF EXISTS update_before_product$
# CREATE TRIGGER update_before_product BEFORE UPDATE
#     ON product FOR EACH ROW
# BEGIN
#     set new.category_name = old.category_name;
# end $
-- product表插入数据前,自动填入category_name
DROP TRIGGER IF EXISTS insert_before_product$
CREATE TRIGGER insert_before_product BEFORE INSERT
    ON product FOR EACH ROW
BEGIN
    DECLARE cId BIGINT;
    set cID = (SELECT id FROM category WHERE name = new.category_name);
    set new.cid = cID;
END$
-- sale表插入数据后,product的库存相应减少
# DROP TRIGGER IF EXISTS insert_after_sale_product$
# CREATE TRIGGER insert_after_sale_product AFTER INSERT
#     ON sale_product FOR EACH ROW
# BEGIN
#     IF new.count > 0 THEN
#     UPDATE product set stock = stock - new.count WHERE id = new.product_id;
#     UPDATE product set sale_count = sale_count + new.count WHERE id = new.product_id;
#     END IF;
# END$
-- category表更改后,product的分类名称也修改
DELIMITER $
DROP TRIGGER IF EXISTS update_after_category$
CREATE TRIGGER update_after_category AFTER UPDATE
    ON category FOR EACH ROW
BEGIN
    IF old.name != new.name THEN
        UPDATE product set category_name = new.name WHERE cid = new.id;
    END IF;
END$

USE supermarket;
INSERT `user` VALUES
                  (1,"admin","123456"),
                  (2,"perzch","perzch"),
                  (3,"test","123456");

INSERT `category` VALUES
                      (null,"食品","97"),
                      (null,"日用品","90"),
                      (null,"饮品","100"),
                      (null,"化妆品","97");

INSERT `product`(name,yield_date,manufacturers,price,create_date,stock,now_price,sale_count,category_name) VALUES
                                                                                                 ("饼干","2020-07-03","奥利奥",5.00,"2020-07-13",989,7.00,0,"食品"),
                                                                                                 ("牙刷","2020-07-02","舒克",3.00,"2020-07-13",260,6.00,0,"日用品"),
                                                                                                 ("牙膏","2020-07-02","舒克",8.00,"2020-07-13",500,16.00,0,"日用品"),
                                                                                                 ("绿茶","2020-07-02","统一",1.50,"2020-07-13",3797,3.00,0,"饮品"),
                                                                                                 ("口红","2020-02-05","迪奥",200.00,"2020-07-13",300,300.00,0,"化妆品"),
                                                                                                 ("可口可乐","2020-02-07","可口可乐",2.73,"2020-07-13",3000,3,0,"饮品");