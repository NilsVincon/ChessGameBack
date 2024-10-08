INSERT INTO ChessUser (username, password) VALUES ('Paul', 'Harrohide');
INSERT INTO ChessUser (username, password) VALUES ('Harry', 'Covert');
INSERT INTO ChessUser (username, password) VALUES ('Alain', 'Posteur');
INSERT INTO ChessUser (username, password) VALUES ('Elvire', 'Debord');
INSERT INTO ChessUser (username, password) VALUES ('Laurent', 'Barre');

INSERT INTO Game ( game_date, game_time, winner) VALUES ('2024-10-08', '14:00:00', 1);
INSERT INTO Game ( game_date, game_time, winner) VALUES ('2024-10-08', '15:00:00', 2);
INSERT INTO Game ( game_date, game_time, winner) VALUES ('2024-10-08', '16:00:00', 3);

INSERT INTO Play ( id_game, user1, user2) VALUES ( 1, 1, 2);
INSERT INTO Play ( id_game, user1, user2) VALUES ( 2, 3, 4);
INSERT INTO Play ( id_game, user1, user2) VALUES ( 3, 5, 1);