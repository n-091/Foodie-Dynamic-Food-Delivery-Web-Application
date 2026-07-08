-- =====================================================
-- DATABASE
-- =====================================================

SHOW DATABASES;
USE instant_food;
SHOW TABLES;

-- =========================
-- USER TABLE
-- =========================
CREATE TABLE user (
    user_id INT PRIMARY KEY AUTO_INCREMENT,
    user_name VARCHAR(50) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(100) NOT NULL,
    address VARCHAR(255),
    phone_no VARCHAR(15),
    role VARCHAR(20) DEFAULT 'customer',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    login_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP);
    
ALTER TABLE user ADD COLUMN is_active BOOLEAN DEFAULT TRUE;
SELECT * FROM user WHERE is_active = TRUE;

DESC user;
SELECT * FROM instant_food.user;

SELECT * FROM cart;


-- =========================
-- RESTAURANT TABLE
-- =========================

-- Create new restaurant table
DROP TABLE IF EXISTS restaurant;

CREATE TABLE restaurant (
    RestaurantID INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    cuisineType VARCHAR(100),
    deliveryTime INT,              -- estimated delivery time in minutes
    address VARCHAR(200),
    adminUserId INT,
    rating DECIMAL(2,1),           -- e.g. 4.5
    isActive BOOLEAN DEFAULT TRUE,
    image_path VARCHAR(255)        -- optional: store restaurant banner/logo
);
-- 1. The Oberoi Bengaluru
INSERT INTO restaurant (RestaurantID, Name, cuisineType, DeliveryTime, Address, AdminUserId, Rating, isActive, image_path)
VALUES (1, 'The Oberoi Bengaluru', 'North Indian & Continental', 45, 'MG Road, Bengaluru', 1, 4.8, 1, 'images/restaurants/oberoi.jpg');

-- 2. ITC Gardenia
INSERT INTO restaurant VALUES
(2, 'ITC Gardenia', 'South Indian & Continental', 50, 'Residency Road, Bengaluru', 1, 4.7, 1, 'images/restaurants/itc-gardenia.jpg');

-- 3. Taj MG Road
INSERT INTO restaurant VALUES
(3, 'Taj MG Road', 'Indian & Italian', 40, 'MG Road, Bengaluru', 1, 4.6, 1, 'images/restaurants/taj-mg-road.jpg');

-- 4. Shangri-La Hotel Bengaluru
INSERT INTO restaurant VALUES
(4, 'Shangri-La Hotel', 'South Indian Specials', 40, 'Palace Road, Bengaluru', 1, 4.7, 1, 'images/restaurants/shangri-la.jpg');

-- 5. JW Marriott Bengaluru
INSERT INTO restaurant VALUES
(5, 'JW Marriott', 'North Indian Specials', 45, 'Vittal Mallya Road, Bengaluru', 1, 4.8, 1, 'images/restaurants/jw-marriott.jpg');

-- 6. The Leela Palace Bengaluru
INSERT INTO restaurant VALUES
(6, 'The Leela Palace ', 'Indian + Asian + Fusion', 35, 'Old Airport Road, Bengaluru', 1, 4.5, 1, 'images/restaurants/leela-palace.jpg');

-- 7. Radisson Blu Atria
INSERT INTO restaurant VALUES
(7, 'Radisson Blu Atria', 'North Indian + Continental', 35, 'Palace Road, Bengaluru', 1, 4.4, 1, 'images/restaurants/radisson-atria.jpg');

-- 8. Hyatt Centric MG Road
INSERT INTO restaurant VALUES
(8, 'Hyatt Centric MG Road', 'Modern Indian + Asian', 40, 'MG Road, Bengaluru', 1, 4.5, 1, 'images/restaurants/hyatt-centric.jpg');

-- 9. Conrad Bengaluru
INSERT INTO restaurant VALUES
(9, 'Conrad Bengaluru', 'Global + Indian + Asian', 50, 'Ulsoor, Bengaluru', 1, 4.7, 1, 'images/restaurants/conrad.jpg');

-- 10. Fairfield by Marriott
INSERT INTO restaurant VALUES
(10, 'Fairfield by Marriott', 'Mangalorean Specials', 30, 'Rajajinagar, Bengaluru', 1, 4.3, 1, 'images/restaurants/fairfield.jpg');

-- 11. MTR (Mavalli Tiffin Rooms)
INSERT INTO restaurant VALUES
(11, 'MTR - Mavalli Tiffin Rooms', 'South Indian - Davangere', 30, 'Lalbagh Road, Bengaluru', 1, 4.6, 1, 'images/restaurants/mtr.jpg');

-- 12. The Oberoi Hubli Dharwad Specials
INSERT INTO restaurant VALUES
(12, 'The Oberoi Hubli Dharwad Specials', 'South Indian - Hubli', 30, 'Rajajinagar, Bengaluru', 1, 4.3, 1, 'images/restaurants/hubli-dharwad.jpg');

-- 13. Sattvam Restaurant
INSERT INTO restaurant VALUES
(13, 'Sattvam Restaurant', 'Pure Veg Sattvic', 45, 'Sadashivanagar, Bengaluru', 1, 4.5, 1, 'images/restaurants/sattvam.jpg');

-- 14. Corner House Icecreams
INSERT INTO restaurant VALUES
(14, 'Corner House Icecreams', 'Ice Cream & Gourmet Desserts', 35, 'Koramangala, Bengaluru', 1, 4.4, 1, 'images/restaurants/corner-house.jpg');

-- 15. Vidyarthi Bhavan
INSERT INTO restaurant VALUES
(15, 'Vidyarthi Bhavan', 'South Indian Breakfast', 25, 'Basavanagudi, Bengaluru', 1, 4.6, 1, 'images/restaurants/vidyarthi-bhavan.jpg');

SELECT * FROM restaurant WHERE isActive = true;

SELECT * FROM menu WHERE restaurantId = 4;

-- =========================
-- MENU TABLE
-- =========================

CREATE TABLE menu (
    MenuID INT PRIMARY KEY AUTO_INCREMENT,
    restaurantId INT NOT NULL,
    itemName VARCHAR(100) NOT NULL,
    description VARCHAR(255),
    price DECIMAL(10,2) NOT NULL,
    isAvailable BOOLEAN DEFAULT TRUE,
    image_path VARCHAR(255),   -- dish image
    FOREIGN KEY (restaurantId) REFERENCES restaurant(RestaurantID)
);
-- Restaurant 1: The Oberoi Bengaluru
INSERT INTO menu (restaurantId, itemName, description, price, isAvailable, image_path) VALUES
(1, 'Butter Chicken', 'Classic North Indian curry with tender chicken', 450.00, 1, 'images/menu/butter-chicken.jpg'),
(1, 'Paneer Tikka', 'Grilled paneer cubes marinated in spices', 380.00, 1, 'images/menu/paneer-tikka.jpg'),
(1, 'Dal Makhani', 'Creamy black lentils slow cooked overnight', 320.00, 1, 'images/menu/dal-makhani.jpg'),
(1, 'Caesar Salad', 'Fresh lettuce with parmesan and croutons', 300.00, 1, 'images/menu/caesar-salad.jpg'),
(1, 'Grilled Salmon', 'Salmon fillet served with lemon butter sauce', 600.00, 1, 'images/menu/grilled-salmon.jpg'),
(1, 'Chocolate Lava Cake', 'Warm chocolate cake with molten center', 280.00, 1, 'images/menu/chocolate-lava-cake.jpg'),
(1, 'Masala Chai', 'Traditional Indian spiced tea', 150.00, 1, 'images/menu/masala-chai.jpg');

-- Restaurant 2: ITC Gardenia
INSERT INTO menu (restaurantId, itemName, description, price, isAvailable, image_path) VALUES
(2, 'Hyderabadi Biryani', 'Fragrant rice with marinated chicken and spices', 350.00, 1, 'images/menu/hyderabadi-biryani.jpg'),
(2, 'Chettinad Chicken Curry', 'Spicy South Indian chicken curry with aromatic masala', 320.00, 1, 'images/menu/chettinad-chicken.jpg'),
(2, 'Appam with Stew', 'Soft appam served with vegetable coconut stew', 280.00, 1, 'images/menu/appam-stew.jpg'),
(2, 'Dal Tadka', 'Yellow lentils tempered with ghee and spices', 220.00, 1, 'images/menu/dal-tadka.jpg'),
(2, 'Paneer Butter Masala', 'Paneer cubes in creamy tomato gravy', 300.00, 1, 'images/menu/paneer-butter-masala.jpg'),
(2, 'Continental Grilled Chicken', 'Grilled chicken breast served with mashed potatoes', 400.00, 1, 'images/menu/grilled-chicken.jpg'),
(2, 'Rasam with Rice', 'Tangy South Indian soup served with steamed rice', 180.00, 1, 'images/menu/rasam-rice.jpg');

-- Restaurant 3: Taj MG Road
INSERT INTO menu (restaurantId, itemName, description, price, isAvailable, image_path) VALUES
(3, 'Coastal Fish Curry', 'Traditional South Indian coastal curry with fresh fish', 420.00, 1, 'images/menu/fish-curry.jpg'),
(3, 'Prawn Ghee Roast', 'Spicy prawn dish cooked in ghee and red masala', 480.00, 1, 'images/menu/prawn-ghee-roast.jpg'),
(3, 'Vegetable Lasagna', 'Layered pasta with vegetables, cheese, and tomato sauce', 350.00, 1, 'images/menu/veg-lasagna.jpg'),
(3, 'Grilled Lamb Chops', 'Tender lamb chops served with mashed potatoes', 650.00, 1, 'images/menu/lamb-chops.jpg'),
(3, 'Caesar Salad', 'Crisp lettuce with parmesan, croutons, and Caesar dressing', 300.00, 1, 'images/menu/caesar-salad.jpg'),
(3, 'Tiramisu', 'Classic Italian dessert with coffee and mascarpone', 280.00, 1, 'images/menu/tiramisu.jpg'),
(3, 'Mango Panna Cotta', 'Creamy Italian panna cotta infused with mango', 250.00, 1, 'images/menu/mango-panna-cotta.jpg');

-- Restaurant 4: Shangri-La Hotel Bengaluru
INSERT INTO menu (restaurantId, itemName, description, price, isAvailable, image_path) VALUES
(4, 'Masala Dosa', 'Crispy dosa stuffed with spiced potato filling', 90.00, 1, 'images/menu/masala-dosa.jpg'),
(4, 'Idli Sambar', 'Steamed rice cakes served with hot sambar', 70.00, 1, 'images/menu/idli-sambar.jpg'),
(4, 'Medu Vada', 'Crispy fried lentil doughnuts served with chutney', 80.00, 1, 'images/menu/medu-vada.jpg'),
(4, 'Rava Kesari', 'Sweet semolina dessert with ghee and nuts', 100.00, 1, 'images/menu/rava-kesari.jpg'),
(4, 'Upma', 'Savory semolina porridge with vegetables', 95.00, 1, 'images/menu/upma.jpg'),
(4, 'Filter Coffee', 'Authentic South Indian filter coffee', 60.00, 1, 'images/menu/filter-coffee.jpg'),
(4, 'Bisibele Bath', 'Spiced lentil rice dish with tamarind and spices', 120.00, 1, 'images/menu/bisibele-bath.jpg');

INSERT INTO menu (restaurantId, itemName, description, price, isAvailable, image_path) VALUES
(5, 'Butter Chicken', 'Tender chicken cooked in creamy tomato gravy', 420.00, 1, 'images/menu/butter-chicken.jpg'),
(5, 'Paneer Butter Masala', 'Paneer cubes simmered in rich butter gravy', 380.00, 1, 'images/menu/paneer-butter-masala.jpg'),
(5, 'Dal Makhani', 'Slow-cooked black lentils with cream and butter', 320.00, 1, 'images/menu/dal-makhani.jpg'),
(5, 'Tandoori Chicken', 'Chicken marinated in yogurt and spices, roasted in tandoor', 450.00, 1, 'images/menu/tandoori-chicken.jpg'),
(5, 'Aloo Gobi', 'Potato and cauliflower curry with spices', 280.00, 1, 'images/menu/aloo-gobi.jpg'),
(5, 'Naan', 'Soft leavened bread baked in tandoor', 80.00, 1, 'images/menu/naan.jpg'),
(5, 'Gulab Jamun', 'Sweet fried dumplings soaked in sugar syrup', 150.00, 1, 'images/menu/gulab-jamun.jpg');
-- Restaurant 6: The Leela Palace Bengaluru
INSERT INTO menu (restaurantId, itemName, description, price, isAvailable, image_path) VALUES
(6, 'Vegetable Samosa', 'Crispy pastry stuffed with spiced potatoes and peas', 120.00, 1, 'images/menu/samosa.jpg'),
(6, 'Paneer Pakora', 'Paneer cubes dipped in gram flour batter and fried', 150.00, 1, 'images/menu/paneer-pakora.jpg'),
(6, 'Spring Rolls', 'Crispy rolls stuffed with vegetables and served with dip', 180.00, 1, 'images/menu/spring-rolls.jpg'),
(6, 'Edamame Beans', 'Steamed Japanese soybeans sprinkled with sea salt', 200.00, 1, 'images/menu/edamame.jpg'),
(6, 'Thai Satay Skewers', 'Grilled chicken skewers served with peanut sauce', 250.00, 1, 'images/menu/thai-satay.jpg'),
(6, 'Mini Sushi Rolls', 'Assorted vegetarian sushi rolls with wasabi and soy', 280.00, 1, 'images/menu/sushi-rolls.jpg'),
(6, 'Pani Puri Shots', 'Crispy puris filled with tangy flavored water', 100.00, 1, 'images/menu/pani-puri.jpg');

-- Restaurant 7: Radisson Blu Atria
INSERT INTO menu (restaurantId, itemName, description, price, isAvailable, image_path) VALUES
(7, 'Bruschetta', 'Grilled bread topped with tomato, basil, and olive oil', 220.00, 1, 'images/menu/bruschetta.jpg'),
(7, 'French Fries', 'Golden crispy potato fries served with ketchup', 180.00, 1, 'images/menu/french-fries.jpg'),
(7, 'Chicken Wings', 'Spicy grilled chicken wings with dip', 280.00, 1, 'images/menu/chicken-wings.jpg'),
(7, 'Veggie Nachos', 'Corn chips topped with cheese, beans, and salsa', 250.00, 1, 'images/menu/nachos.jpg'),
(7, 'Mini Sliders', 'Small beef or chicken burgers with cheese', 300.00, 1, 'images/menu/sliders.jpg'),
(7, 'Garlic Bread', 'Toasted bread with garlic butter and herbs', 200.00, 1, 'images/menu/garlic-bread.jpg'),
(7, 'Cheese Platter', 'Assorted cheeses served with crackers and fruit', 350.00, 1, 'images/menu/cheese-platter.jpg');

-- Restaurant 8: Hyatt Centric MG Road
INSERT INTO menu (restaurantId, itemName, description, price, isAvailable, image_path) VALUES
(8, 'Ragi Mudde', 'Traditional Karnataka ragi balls served with sambar or curry', 150.00, 1, 'images/menu/ragi-mudde.jpg'),
(8, 'Chicken Curry', 'Spicy Indian chicken curry with rich gravy', 320.00, 1, 'images/menu/chicken-curry.jpg'),
(8, 'Tandoori Roti', 'Whole wheat flatbread baked in tandoor', 80.00, 1, 'images/menu/tandoori-roti.jpg'),
(8, 'Paneer Tikka Masala', 'Paneer cubes cooked in spiced tomato gravy', 300.00, 1, 'images/menu/paneer-tikka-masala.jpg'),
(8, 'Kerala Parota', 'Flaky layered flatbread served with curry', 90.00, 1, 'images/menu/parota.jpg'),
(8, 'Dal Fry', 'Yellow lentils tempered with ghee and spices', 220.00, 1, 'images/menu/dal-fry.jpg'),
(8, 'Chicken Biryani', 'Fragrant basmati rice cooked with marinated chicken', 350.00, 1, 'images/menu/chicken-biryani.jpg');

-- Restaurant 9: Conrad Bengaluru
INSERT INTO menu (restaurantId, itemName, description, price, isAvailable, image_path) VALUES
(9, 'Chicken Satay', 'Grilled chicken skewers served with peanut sauce', 320.00, 1, 'images/menu/chicken-satay.jpg'),
(9, 'Vegetable Spring Rolls', 'Crispy rolls stuffed with vegetables and served with dip', 220.00, 1, 'images/menu/spring-rolls.jpg'),
(9, 'Paneer Butter Masala', 'Paneer cubes simmered in creamy tomato gravy', 350.00, 1, 'images/menu/paneer-butter-masala.jpg'),
(9, 'Sushi Platter', 'Assorted sushi rolls with wasabi and soy sauce', 480.00, 1, 'images/menu/sushi-platter.jpg'),
(9, 'Thai Green Curry', 'Spicy coconut curry with vegetables and herbs', 400.00, 1, 'images/menu/thai-green-curry.jpg'),
(9, 'Dal Tadka', 'Yellow lentils tempered with ghee and spices', 250.00, 1, 'images/menu/dal-tadka.jpg'),
(9, 'Vegetable Fried Rice', 'Asian-style fried rice with mixed vegetables', 300.00, 1, 'images/menu/fried-rice.jpg');

-- Restaurant 10: Fairfield by Marriott
INSERT INTO menu (restaurantId, itemName, description, price, isAvailable, image_path) VALUES
(10, 'Chicken Ghee Roast', 'Signature Mangalorean dish with spicy red masala and ghee', 350.00, 1, 'images/menu/chicken-ghee-roast.jpg'),
(10, 'Neer Dosa', 'Soft, thin rice crepes served with chutney or curry', 120.00, 1, 'images/menu/neer-dosa.jpg'),
(10, 'Kori Rotti', 'Crispy rotti served with spicy chicken curry', 300.00, 1, 'images/menu/kori-rotti.jpg'),
(10, 'Fish Curry (Mangalorean Style)', 'Tangy coconut-based curry with fresh fish', 400.00, 1, 'images/menu/fish-curry.jpg'),
(10, 'Prawn Sukka', 'Dry prawn dish cooked with grated coconut and spices', 380.00, 1, 'images/menu/prawn-sukka.jpg'),
(10, 'Pathrode', 'Colocasia leaves rolled with spiced rice paste and steamed', 200.00, 1, 'images/menu/pathrode.jpg'),
(10, 'Mangalorean Biryani', 'Fragrant rice cooked with coastal spices and chicken', 350.00, 1, 'images/menu/mangalorean-biryani.jpg');

-- Restaurant 11: MTR (Mavalli Tiffin Rooms)
INSERT INTO menu (restaurantId, itemName, description, price, isAvailable, image_path) VALUES
(11, 'Davangere Benne Dosa', 'Crispy butter dosa, a Davangere signature dish', 100.00, 1, 'images/menu/benne-dosa.jpg'),
(11, 'Idli with Chutney', 'Soft steamed rice cakes served with coconut chutney', 70.00, 1, 'images/menu/idli.jpg'),
(11, 'Vada Sambar', 'Crispy lentil fritters served with hot sambar', 80.00, 1, 'images/menu/vada-sambar.jpg'),
(11, 'Poori Kurma', 'Fluffy pooris served with spicy vegetable kurma', 120.00, 1, 'images/menu/poori-kurma.jpg'),
(11, 'Kesari Bath', 'Sweet semolina dessert cooked with ghee and saffron', 90.00, 1, 'images/menu/kesari-bath.jpg'),
(11, 'Upma', 'Savory semolina porridge with vegetables and spices', 85.00, 1, 'images/menu/upma.jpg'),
(11, 'Filter Coffee', 'Authentic South Indian filter coffee', 60.00, 1, 'images/menu/filter-coffee.jpg');

-- Restaurant 12: Hubli Dharwad Specials
INSERT INTO menu (restaurantId, itemName, description, price, isAvailable, image_path) VALUES
(12, 'Hubli Dharwad Pedha', 'Famous milk-based sweet from Dharwad region', 120.00, 1, 'images/menu/pedha.jpg'),
(12, 'Girmit', 'Spicy puffed rice snack with onion and sev', 80.00, 1, 'images/menu/girmit.jpg'),
(12, 'Mirchi Bajji', 'Green chili fritters stuffed with masala', 90.00, 1, 'images/menu/mirchi-bajji.jpg'),
(12, 'Chapati with Ennegayi', 'Jowar chapati served with stuffed brinjal curry', 150.00, 1, 'images/menu/ennegayi.jpg'),
(12, 'Sajjige Rotti', 'Traditional Hubli rotti made with semolina', 100.00, 1, 'images/menu/sajjige-rotti.jpg'),
(12, 'Karadantu', 'Healthy jaggery and edible gum sweet from Gokak', 130.00, 1, 'images/menu/karadantu.jpg'),
(12, 'Jolada Rotti', 'Authentic North Karnataka jowar rotti served with chutney', 110.00, 1, 'images/menu/jolada-rotti.jpg');
-- Restaurant 13: Sattvam Restaurant
INSERT INTO menu (restaurantId, itemName, description, price, isAvailable, image_path) VALUES
(13, 'Sattvic Thali', 'Balanced vegetarian platter with rice, dal, sabzi, roti, and dessert', 350.00, 1, 'images/menu/sattvic-thali.jpg'),
(13, 'Paneer Shahi Kurma', 'Paneer cubes cooked in mild sattvic gravy without onion or garlic', 300.00, 1, 'images/menu/paneer-shahi-kurma.jpg'),
(13, 'Vegetable Pulao', 'Fragrant rice cooked with seasonal vegetables and mild spices', 280.00, 1, 'images/menu/veg-pulao.jpg'),
(13, 'Moong Dal Tadka', 'Light yellow lentils tempered with ghee and cumin', 220.00, 1, 'images/menu/moong-dal.jpg'),
(13, 'Aloo Jeera', 'Potatoes sautéed with cumin seeds and ghee', 200.00, 1, 'images/menu/aloo-jeera.jpg'),
(13, 'Phulka Roti', 'Soft whole wheat rotis served with ghee', 80.00, 1, 'images/menu/phulka.jpg'),
(13, 'Fruit Custard', 'Seasonal fruits mixed with creamy custard', 180.00, 1, 'images/menu/fruit-custard.jpg');

-- Restaurant 14: Corner House Icecreams
INSERT INTO menu (restaurantId, itemName, description, price, isAvailable, image_path) VALUES
(14, 'Death by Chocolate', 'Signature sundae with brownies, ice cream, and fudge sauce', 250.00, 1, 'images/menu/death-by-chocolate.jpg'),
(14, 'Hot Chocolate Fudge', 'Vanilla ice cream topped with hot fudge and nuts', 200.00, 1, 'images/menu/hot-chocolate-fudge.jpg'),
(14, 'Fruit Salad with Ice Cream', 'Seasonal fruits served with a scoop of vanilla ice cream', 180.00, 1, 'images/menu/fruit-salad.jpg'),
(14, 'Brownie Sundae', 'Warm brownie topped with ice cream and chocolate sauce', 220.00, 1, 'images/menu/brownie-sundae.jpg'),
(14, 'Strawberry Delight', 'Strawberry ice cream sundae with fresh strawberries', 190.00, 1, 'images/menu/strawberry-delight.jpg'),
(14, 'Caramel Crunch', 'Ice cream topped with caramel sauce and crunchy nuts', 210.00, 1, 'images/menu/caramel-crunch.jpg'),
(14, 'Banana Split', 'Classic dessert with banana, ice cream scoops, and toppings', 230.00, 1, 'images/menu/banana-split.jpg');

-- Restaurant 15: Vidyarthi Bhavan
INSERT INTO menu (restaurantId, itemName, description, price, isAvailable, image_path) VALUES
(15, 'Crispy Masala Dosa', 'Iconic Vidyarthi Bhavan dosa with potato filling', 90.00, 1, 'images/menu/masala-dosa.jpg'),
(15, 'Plain Dosa', 'Golden crispy dosa served with chutney and sambar', 80.00, 1, 'images/menu/plain-dosa.jpg'),
(15, 'Idli Sambar', 'Soft idlis served with hot sambar and chutney', 70.00, 1, 'images/menu/idli-sambar.jpg'),
(15, 'Medu Vada', 'Crispy lentil fritters served with chutney', 75.00, 1, 'images/menu/medu-vada.jpg'),
(15, 'Kesari Bath', 'Sweet semolina dessert cooked with ghee and saffron', 85.00, 1, 'images/menu/kesari-bath.jpg'),
(15, 'Upma', 'Savory semolina porridge with vegetables and spices', 80.00, 1, 'images/menu/upma.jpg'),
(15, 'Filter Coffee', 'Strong aromatic South Indian filter coffee', 50.00, 1, 'images/menu/filter-coffee.jpg');

SELECT * FROM menu;
DESC menu;

-- =========================
-- ORDER TABLE
-- =========================
CREATE TABLE IF NOT EXISTS orders (
    order_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    restaurant_id INT NOT NULL,
    order_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    total_amount DECIMAL(10,2) NOT NULL,
    status VARCHAR(50) DEFAULT 'Pending',
    payment_method VARCHAR(50),
    FOREIGN KEY (user_id) REFERENCES user(user_id),
    FOREIGN KEY (restaurant_id) REFERENCES restaurant(RestaurantID)
);


SELECT * FROM orders;

DESC orders;

-- =========================
-- ORDER ITEM TABLE
-- =========================

CREATE TABLE IF NOT EXISTS orderitem (
    OrderItemID INT PRIMARY KEY AUTO_INCREMENT,
    OrderID INT NOT NULL,
    MenuID INT NOT NULL,
    Quantity INT NOT NULL,
    ItemTotal DECIMAL(10,2) NOT NULL,
    FOREIGN KEY (OrderID) REFERENCES orders(order_id),
    FOREIGN KEY (MenuID) REFERENCES menu(MenuID)
);


SELECT * FROM orderitem;

-- ========================================================
-- Cart 
-- ========================================================

CREATE TABLE cart (
    cartId INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT NOT NULL,
    createdAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_cart_user
    FOREIGN KEY (user_id)
    REFERENCES user(user_id)
);

SELECT * FROM cart;
DESC cart;

-- ========================================================
-- Cart Item
-- ========================================================

CREATE TABLE cartitem (
    cartItemId INT PRIMARY KEY AUTO_INCREMENT,
    cartId INT NOT NULL,
    menuId INT NOT NULL,
    quantity INT NOT NULL,

    CONSTRAINT fk_cartitem_cart
    FOREIGN KEY (cartId)
    REFERENCES cart(cartId),

    CONSTRAINT fk_cartitem_menu
    FOREIGN KEY (menuId)
    REFERENCES menu(MenuID)
);

SELECT * FROM orders;
SELECT * FROM menu;


SHOW TABLES;

DESC user;
DESC restaurant;
DESC menu;
DESC orders;
DESC orderitem;
DESC cart;
DESC cartitem;

SELECT COUNT(*) FROM restaurant;
SELECT COUNT(*) FROM menu;


SELECT * FROM restaurant WHERE isActive=1;

CREATE TABLE categories (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50),
    image_path VARCHAR(255)
);
SET SQL_SAFE_UPDATES = 0;


INSERT INTO categories (id, name, image_path) VALUES
(1, 'Pizza', 'images/specials/pizza-hut.jpg'),
(2, 'Biryani', 'images/specials/biriyani.jpg'),
(3, 'Desserts', 'images/specials/dessert.jpg'),
(4, 'Drinks', 'images/specials/drinks.jpg');

ALTER TABLE menu ADD COLUMN category VARCHAR(50);


INSERT INTO menu (restaurantId, itemName, description, price, isAvailable, image_path, category)
VALUES 
(1, 'Margherita Pizza', 'Classic cheese pizza', 250, true, 'images/menu/margherita.jpg', 'Pizza'),
(1, 'Veggie Pizza', 'Loaded with fresh veggies', 300, true, 'images/menu/veggie.jpg', 'Pizza'),
(2, 'Chicken Biryani', 'Spicy Hyderabadi biryani', 350, true, 'images/menu/biryani.jpg', 'Biryani');


-- Desserts
INSERT INTO menu (restaurantId, itemName, description, price, isAvailable, image_path, category)
VALUES
(3, 'Chocolate Cake', 'Rich chocolate layered cake', 200, true, 'images/menu/chocolate-cake.jpg', 'Desserts'),
(3, 'Ice Cream Sundae', 'Vanilla ice cream with toppings', 150, true, 'images/menu/sundae.jpg', 'Desserts');

-- Drinks
INSERT INTO menu (restaurantId, itemName, description, price, isAvailable, image_path, category)
VALUES
(4, 'Mango Lassi', 'Sweet mango yogurt drink', 120, true, 'images/menu/mango-lassi.jpg', 'Drinks'),
(4, 'Cold Coffee', 'Chilled coffee with cream', 100, true, 'images/menu/cold-coffee.jpg', 'Drinks');


CREATE TABLE events (
    id INT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(100) NOT NULL,
    description VARCHAR(255),
    image_path VARCHAR(255),
    isActive TINYINT(1) DEFAULT 1
);

DESCRIBE orders;

select * from orderitem;
DELETE FROM orderitem;
SET SQL_SAFE_UPDATES = 0;

INSERT INTO events (title, description, image_path, isActive) VALUES
('Food Carnival', 'Join us for a weekend of flavors and fun.', 'food-carnival.jpg', 1),
('Chef’s Special', 'Exclusive dishes crafted by top chefs.', 'chef-special.jpg', 1),
('Discount Day', 'Enjoy massive discounts on your favorites.', 'discount-day.jpg', 1);


SELECT * FROM user;

INSERT INTO orders (user_id, restaurant_id, total_amount, status, payment_method)
VALUES (7, 1, 250.00, 'Confirmed', 'Cash');
INSERT INTO orderitem (OrderID, MenuID, Quantity, ItemTotal)
VALUES (1, 134, 1, 450.00);

ALTER TABLE cart
DROP FOREIGN KEY fk_cart_user,
ADD CONSTRAINT fk_cart_user
FOREIGN KEY (user_id) REFERENCES user(user_id)
ON DELETE CASCADE;

ALTER TABLE orders
DROP FOREIGN KEY fk_orders_user,
ADD CONSTRAINT fk_orders_user
FOREIGN KEY (user_id) REFERENCES user(user_id)
ON DELETE CASCADE;

SELECT ci.cartItemId, ci.cartId, ci.menuId, ci.quantity,
       m.itemName, m.price, m.image_path, m.restaurantId
FROM cartitem ci
JOIN menu m ON ci.menuId = m.MenuID
WHERE ci.cartId = 1;
