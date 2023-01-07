-- Используйте эти данные для аутентификации
-- username: Alexis password: 12345

-- В самой базе пароль храним в зашифрованном виде
INSERT INTO USERS (ID, USERNAME, EMAIL, PASSWORD) VALUES (1, 'Alexis', 'al@gmail.com', '$2a$12$lP1HdpYa4yT./wnilPzf0OoxaVHJunEkkxw4SwkYL5obPjq.fG8Xy');
