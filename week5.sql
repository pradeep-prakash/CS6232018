/*Write the following queries in SQL.

1. What are the #prods whose name begins with a 'p' and are less than
$300.00?*/

SELECT prod FROM Products
WHERE  price < 300 AND LEFT (pname,1) = 'p';


/*2. Names of the products stocked in "d2".
(a) without in/not in*/

SELECT DISTINT pname FROM Product INNER JOIN Stock
WHERE product.prod = stock.prod
AND stock.dep = 'd2';

/*(b) with in/not in*/

SELECT DISTINT pname FROM Product INNER JOIN Stock
WHERE product.prod = stock.prod
AND stock.dep IN ('d2');


/*3. #prod and names of the products that are out of stock.
(a) without in/not in*/

SELECT DISTINCT prod, pname FROM Product INNER JOIN Stock
WHERE product.prod = stock.prod
AND stock.quantity<0;

/*(b) with in/not in*/	

SELECT DISTINCT prod, pname FROM Product INNER JOIN Stock
WHERE product.prod = stock.prod
AND stock.quantity<0 AND product.prod IN ('p1','p2','p3'); 


/*4. Addresses of the depots where the product "p1" is stocked.
(a) without exists/not exists and without in/not in*/

SELECT addr from Depot INNER JOIN Stock
WHERE depot.dep = stock.dep AND stock.prod = 'p1'
AND stock.quantity>0;

/*(b) with in/not in*/

SELECT addr from Depot INNER JOIN Stock
WHERE depot.dep = stock.dep AND stock.prod = 'p1'
AND stock.quantity>0 AND addr IN ('New York', 'Sycracus', 'chicago');

/*(c) with exists/not exists*/

SELECT addr from Depot INNER JOIN Stock
WHERE EXISTS
(SELECT stock.dep FROM Stock
WHERE depot.dep = stock.dep AND stock.quantity>0 AND stock.prod = 'p1');


/*5. #prods whose price is between $250.00 and $400.00.
(a) using intersect.*/

SELECT prod FROM Product
INTERSECT prod FROM Stock
WHERE product.price >250 and product.price <400;

/*(b) without intersect.*/

SELECT prod FROM Product
WHERE product.price >250 and product.price <400;


/*6. How many products are out of stock?*/

SELECT COUNT * FROM Stock
quantity <=0;


/*7. Average of the prices of the products stocked in the "d2" depot.*/

SELECT AVG (price) FROM Product INNER JOIN Stock
WHERE product.prod = stock.prod AND stock.dep = 'd2';


/*8. #deps of the depot(s) with the largest capacity (volume).*/

SELECT dep from Depot
WHERE depot.volume = MAX(depot.vol);


/*9. Sum of the stocked quantity of each product.*/

SELECT SUM(quantity), prod FROM Stock
GROUP BY prod; 

/*10. Products names stocked in at least 3 depots.
(a) using count*/

SELECT pname FROM Product
WHERE prod IN 
(SELECT prod FROM Stock GROUP BY prod HAVING COUNT(dep)>=3);

/*(b) without using count*/

SELECT pname FROM Product WHERE prod IN
(SELECT S1.prod FROM Stock S1, Stock S2, Stock S3 WHERE
S1.dep <> S2.dep AND S1.dep <> S3.dep AND S2.dep <> S3.dep AND S1.prod = S2.prod AND S2.prod = S3.prod);


/*11. #prod stocked in all depots.
(a) using count*/

SELECT prod FROM Stock GROUP BY prod HAVING COUNT(dep)= (SELECT COUNT(*) FROM Depot);

/*(b) using exists/not exists*/
SELECT prod FROM Product P WHERE EXISTS 
(SELECT prod FROM Stock S WHERE P.prod = S.prod GROUP BY prod 
HAVING COUNT(dep) = (SELECT COUNT(*) FROM Depot ));
