-- Temporary schema for testing. Some considerations: 
-- many to many relationship with user and list?
-- 
-- Create table for User
CREATE TABLE IF NOT EXISTS users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE
);

-- Create table for ShoppingList
CREATE TABLE IF NOT EXISTS shopping_lists (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(id)
);

-- Create table for ListItem
CREATE TABLE IF NOT EXISTS list_items (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    shopping_list_id BIGINT NOT NULL,
    FOREIGN KEY (shopping_list_id) REFERENCES shopping_lists(id)
);
