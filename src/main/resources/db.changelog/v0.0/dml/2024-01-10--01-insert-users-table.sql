--liquibase formatted sql
--changeset Minich:2
INSERT INTO users(uuid, name, surname, login, password, phone, create_date)
VALUES ('0699cfd2-9fb7-4483-bcdf-194a2c6b7fe6', 'Виктор', 'Строганов', 'victor', 'stroganov', 375297684569, '2024-01-24T12:00:00.000'),
       ('9724b9b8-216d-4ab9-92eb-e6e06029580d', 'Олег', 'Кашева', 'oleg', 'kasheva', 375297684568, '2024-01-24T12:00:00.000'),
       ('c0eebc99-9c0b-4ef8-bb6d-6bb9bd380a13', 'Вася', 'Дабро', 'vasja', 'dabro', 375297684567, '2024-01-24T12:00:00.000'),
       ('d0eebc99-9c0b-4ef8-bb6d-6bb9bd380a14', 'Женя', 'Полоса', 'shenja', 'polosa', 375297684566, '2024-01-24T12:00:00.000'),
       ('e0eebc99-9c0b-4ef8-bb6d-6bb9bd380a15', 'Катя', 'Мотуга', 'katja', 'motuga', 375297684565, '2024-01-24T12:00:00.000');