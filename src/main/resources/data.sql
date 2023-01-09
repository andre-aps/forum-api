-- credenciais para teste -> email: user@email.com / senha: 123
INSERT INTO usuario (id, nome, email, senha) VALUES (1, 'User', 'user@email.com', '$2a$04$Jtm2ekM0y.JO8gaMRCUbkOdK9HgxKwMfHhLdeM/QGy/JMiuT9GJbK');
INSERT INTO perfil (id, nome) VALUES (1, 'ROLE_ADMIN');
INSERT INTO curso (id, nome, categoria) VALUES (1, 'Java', 'Programação');
INSERT INTO usuario_perfis (usuario_id, perfis_id) VALUES (1, 1);