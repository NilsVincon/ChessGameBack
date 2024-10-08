create table ChessUser
(
    id SERIAL PRIMARY KEY,
    username TEXT not null,
    password TEXT not null
);

CREATE TABLE Game
(
    id SERIAL PRIMARY KEY,
    game_date DATE,
    game_time TIME,
    winner BIGINT,
    CONSTRAINT fk_winner FOREIGN KEY (winner) REFERENCES ChessUser(id)
);

CREATE TABLE Play
(
    id SERIAL PRIMARY KEY,
    id_game BIGINT,
    user1 BIGINT,
    user2 BIGINT,
    CONSTRAINT fk_game FOREIGN KEY (id_game) REFERENCES Game(id),
    CONSTRAINT fk_user1 FOREIGN KEY (user1) REFERENCES ChessUser(id),
    CONSTRAINT fk_user2 FOREIGN KEY (user2) REFERENCES ChessUser(id)
);

CREATE TABLE Friendship (
                            id SERIAL PRIMARY KEY,
                            friend1 BIGINT,
                            friend2 BIGINT,
                            FOREIGN KEY (friend1) REFERENCES ChessUser(id) ON DELETE CASCADE,
                            FOREIGN KEY (friend2) REFERENCES ChessUser(id) ON DELETE CASCADE,
                            UNIQUE (friend1, friend2)
);
