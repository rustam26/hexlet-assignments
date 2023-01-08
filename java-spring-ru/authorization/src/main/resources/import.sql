-- Используйте эти данные для аутентификации

-- В самой базе пароль храним в зашифрованном виде

-- USER
-- username: al@gmail.com password: 12345
INSERT INTO USERS (ID, USERNAME, EMAIL, PASSWORD, ROLE) VALUES (1, 'Alexis', 'al@gmail.com', '$2a$12$lP1HdpYa4yT./wnilPzf0OoxaVHJunEkkxw4SwkYL5obPjq.fG8Xy', 1);

-- ADMIN
-- username: admin@gmail.com password: admin_pass
INSERT INTO USERS (ID, USERNAME, EMAIL, PASSWORD, ROLE) VALUES (2, 'John_admin', 'admin@gmail.com', '$2a$12$rbmC6cJOZqDMXY1oQxupzOliJ2.v9J1EXYiUY2a52qSTpBJVd3vyq', 0);
