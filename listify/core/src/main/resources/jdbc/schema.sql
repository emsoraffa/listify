CREATE TABLE IF NOT EXISTS users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS listify_lists (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    list_name VARCHAR(255) NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(id)
);


CREATE TABLE IF NOT EXISTS list_collaborators (
    user_id BIGINT NOT NULL,
    listify_list_id BIGINT NOT NULL,
    PRIMARY KEY (user_id, listify_list_id),
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (listify_list_id) REFERENCES listify_lists(id)
);



CREATE TABLE IF NOT EXISTS list_items (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    state BOOLEAN NOT NULL DEFAULT FALSE,
    position INT NOT NULL,
    listify_list_id BIGINT NOT NULL,
    FOREIGN KEY (listify_list_id) REFERENCES listify_lists(id)
);
