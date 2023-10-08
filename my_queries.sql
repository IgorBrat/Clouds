USE boklach;

-- Task 1: Get animators with min and max salary

SELECT surname, name, city_name, region_name, salary_per_hour, "min" as salary_edge FROM animator 
WHERE salary_per_hour IN (SELECT MIN(salary_per_hour) FROM animator)
UNION
SELECT surname, name, city_name, region_name, salary_per_hour, "max" FROM animator 
WHERE salary_per_hour IN (SELECT MAX(salary_per_hour) FROM animator);

-- Task 2: Get animators by agencies

SELECT agency.name as agency_name, animator.surname, animator.name FROM animator
JOIN agency_animator ON animator_id = animator.id
JOIN agency ON agency.id = agency_id
ORDER BY agency_id, surname, name;

-- Task 3: Get animators by order

SELECT `order`.id as order_id, animator.surname, animator.name, animator.email FROM animator
JOIN order_agency_animator ON animator.id = animator_id
JOIN `order` ON `order`.id = order_id
ORDER BY `order`.id, surname, name;

-- Task 4: Get agencies from the west of Ukraine

SELECT name, owner, city_name, region_name, hq_address, phone, email FROM agency
WHERE region_name IN ("Chernivetska", "Lvivska", "Rivnenska", "Ivano-Frankivska", "Volynska");

-- Task 5: Get events which requires both microphone and earphone
SELECT event.name FROM event
JOIN event_equipment on event.id = event_id
JOIN equipment on equipment.id = equipment_id
WHERE equipment_id = 1 AND event.name IN
(SELECT event.name FROM event
JOIN event_equipment on event.id = event_id
JOIN equipment on equipment.id = equipment_id
WHERE equipment_id = 2)
ORDER BY event.name; 

-- Task 6: Get list of animators which has no order currently

SELECT * FROM animator
WHERE animator.id NOT IN (SELECT animator.id FROM animator
JOIN order_agency_animator ON animator.id = animator_id
JOIN `order` ON `order`.id = order_id
GROUP BY animator.id);

-- Task 7: Get orders which will are held in the first half of 2022

SELECT event.name as event, client.surname as client_surname, client.name as client_name, datetime, duration, `order`.city_name, `order`.region_name, total_price FROM `order`
JOIN event ON event_id = event.id
JOIN client ON client_id = client.id
WHERE DATE_FORMAT(DATE(datetime), "%Y") = 2022 AND DATE_FORMAT(DATE(datetime), "%m") < 7;

-- Task 8: Count each animators income for each order

SELECT `order`.id as order_id, animator.surname, animator.name, animator.email, 
CAST(animator.salary_per_hour*CAST(TIME_TO_SEC(`order`.duration)/3600 as decimal(10,2)) as decimal(8,2)) as income FROM animator
JOIN order_agency_animator ON animator.id = animator_id
JOIN `order` ON `order`.id = order_id
ORDER BY order_id, surname, name;

-- Task 9: Count how much money each agency will gain besides paying their animators (both money for preparing and an income)

SELECT `order`.id as order_id, agency.name as agency_name, 
total_price - SUM(CAST(animator.salary_per_hour*CAST(TIME_TO_SEC(`order`.duration)/3600 as decimal(10,2)) as decimal(8,2))) as agency_income FROM animator
JOIN order_agency_animator ON animator.id = animator_id
JOIN `order` ON `order`.id = order_id
JOIN agency ON agency_id = agency.id
GROUP BY order_id
ORDER BY order_id;

-- Task 10: Count each animators income for all already existing orders

SELECT animator.surname, animator.name, animator.email, 
SUM(CAST(animator.salary_per_hour*CAST(TIME_TO_SEC(`order`.duration)/3600 as decimal(10,2)) as decimal(8,2))) as total_income FROM animator
JOIN order_agency_animator ON animator.id = animator_id
JOIN `order` ON `order`.id = order_id
GROUP BY email
ORDER BY surname, name;

