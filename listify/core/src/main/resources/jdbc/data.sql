
-- Insert mock user for development
INSERT INTO users (id, name, email) VALUES (2, 'Developer', 'dev@example.com');

-- Insert initial list for the mock user
INSERT INTO listify_lists (id, user_id, list_name) VALUES (2, 2, 'Min handleliste');

-- Insert initial items for the mock user's list with position
INSERT INTO list_items (id, name, state, position, listify_list_id) VALUES (5, 'Batterier', FALSE, 1, 2);
INSERT INTO list_items (id, name, state, position, listify_list_id) VALUES (6, 'Fisk', TRUE, 2, 2);
INSERT INTO list_items (id, name, state, position, listify_list_id) VALUES (7, 'Pepsi', FALSE, 3, 2);
INSERT INTO list_items (id, name, state, position, listify_list_id) VALUES (8, 'Yoghurt x2', FALSE, 4, 2);

-- Insert initial collaborator for the mock user's list (assuming user with id 2)
INSERT INTO list_collaborators (user_id, listify_list_id) VALUES (2, 2);

