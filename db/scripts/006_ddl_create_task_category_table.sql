CREATE TABLE task_category (
    task_id INT NOT NULL REFERENCES tasks(id),
    category_id INT NOT NULL REFERENCES categories(id),
    PRIMARY KEY (task_id, category_id)
);
