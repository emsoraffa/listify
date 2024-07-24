
-- Insert real user
INSERT INTO users (id, name, email) VALUES (1, 'Emil Rafael Solberg', 'emil.rafael.solberg@gmail.com');

-- Insert initial list for the real user
INSERT INTO listify_lists (id, user_id, list_name) VALUES (1, 1, 'Emils handleliste');

-- Insert initial items for the real user's list
INSERT INTO list_items (id, name, state, listify_list_id) VALUES (1, 'Melk', FALSE, 1);
INSERT INTO list_items (id, name, state, listify_list_id) VALUES (2, 'Ultraprossesert', TRUE, 1);
INSERT INTO list_items (id, name, state, listify_list_id) VALUES (3, 'Pepsi Max', FALSE, 1);
INSERT INTO list_items (id, name, state, listify_list_id) VALUES (4, 'Fisk', FALSE, 1);

-- Insert initial collaborator for the real user's list (assuming user with id 1)
INSERT INTO list_collaborators (user_id, listify_list_id) VALUES (1, 1);

-- Insert mock user for development
INSERT INTO users (id, name, email) VALUES (2, 'Developer', 'dev@example.com');

-- Insert initial list for the mock user
INSERT INTO listify_lists (id, user_id, list_name) VALUES (2, 2, 'Min handleliste');

-- Insert initial items for the mock user's list
INSERT INTO list_items (id, name, state, listify_list_id) VALUES (5, 'Batterier', FALSE, 2);
INSERT INTO list_items (id, name, state, listify_list_id) VALUES (6, 'Fisk', TRUE, 2);
INSERT INTO list_items (id, name, state, listify_list_id) VALUES (7, 'Pepsi', FALSE, 2);
INSERT INTO list_items (id, name, state, listify_list_id) VALUES (8, 'Yoghurt x2', FALSE, 2);

-- Insert initial collaborator for the mock user's list (assuming user with id 2)
INSERT INTO list_collaborators (user_id, listify_list_id) VALUES (2, 2);

