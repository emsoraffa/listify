-- Insert initial user
INSERT INTO users (name, email) VALUES ('Emil Rafael Solberg', 'emil.rafael.solberg@gmail.com');

-- Insert initial list for the user
INSERT INTO listify_lists (user_id, list_name) VALUES (1, 'Emils handleliste');

-- Insert initial items for the list
INSERT INTO list_items (name, state, listify_list_id) VALUES ('Melk', FALSE, 1);
INSERT INTO list_items (name, state, listify_list_id) VALUES ('Ultraprossesert', TRUE, 1);
INSERT INTO list_items (name, state, listify_list_id) VALUES ('Pepsi Max', FALSE, 1);
INSERT INTO list_items (name, state, listify_list_id) VALUES ('Fisk', FALSE, 1);


-- Insert initial collaborator for the list (assuming user with id 1)
INSERT INTO list_collaborators (user_id, listify_list_id) VALUES (1, 1);
