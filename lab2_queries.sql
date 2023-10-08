USE labor_sql;

-- Task 1 

SELECT * FROM printer
WHERE price<300 AND type<>"Matrix"
ORDER BY type DESC;

-- Task 2 

SELECT name, launched FROM ships
WHERE name NOT LIKE "%a";

-- Task 3 

SELECT product.maker, product.type, pc.speed, pc.hd FROM labor_sql.pc
JOIN product ON product.model = pc.model
WHERE hd <= 8;

-- Task 4 

SELECT DISTINCT maker FROM product
WHERE type = "pc" and maker IN
(SELECT maker FROM product
WHERE type = "laptop");

-- Task 5

SELECT class FROM ships
WHERE name in (SELECT ship FROM outcomes WHERE result = "sunk")
UNION DISTINCT
SELECT class FROM classes
WHERE class in (SELECT ship FROM outcomes WHERE result = "sunk");

-- Task 6

SELECT DATE_FORMAT(DATE(date), "%Y.%m.%d") as date FROM battles;

-- Task 7

SELECT product.maker, MIN(printer.price) as price FROM printer
JOIN product on product.model = printer.model
WHERE color = "y";

-- Task 8

SELECT maker, AVG(pc.hd) as avg_hd FROM product
JOIN pc on product.model = pc.model
WHERE product.type = "pc" and product.maker IN
(SELECT maker FROM product
WHERE type = "printer")
GROUP BY product.maker;

-- Task 9

SELECT name, numGuns, bore, displacement, type, country, launched, ships.class
FROM ships
JOIN classes ON ships.class = classes.class
WHERE IF(numGuns=12, 1, 0) + IF(bore=16, 1, 0) + IF(displacement=46000, 1, 0) + IF(type="bc", 1, 0) + IF(country="Gt.Britain", 1, 0) + IF(launched=1941, 1, 0) + IF(ships.class="North Carolina", 1, 0) >= 4;

-- Task 10 

SELECT name FROM ships WHERE launched<1942
UNION DISTINCT
SELECT ship FROM outcomes
JOIN battles ON battles.name = outcomes.battle 
WHERE DATE_FORMAT(DATE(date), "%Y") < 1942
ORDER BY name;

