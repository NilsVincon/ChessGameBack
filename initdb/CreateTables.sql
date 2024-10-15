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
    status VARCHAR(20),
    CONSTRAINT fk_winner FOREIGN KEY (winner) REFERENCES ChessUser(id) ON DELETE CASCADE
);

CREATE TABLE Play
(
    id SERIAL PRIMARY KEY,
    id_game BIGINT,
    sender BIGINT,
    receiver BIGINT,
    status VARCHAR(20),

    CONSTRAINT fk_game FOREIGN KEY (id_game) REFERENCES Game(id),
    CONSTRAINT fk_sender FOREIGN KEY (sender) REFERENCES ChessUser(id) ON DELETE CASCADE,
    CONSTRAINT fk_receiver FOREIGN KEY (receiver) REFERENCES ChessUser(id) ON DELETE CASCADE

);

CREATE TABLE Move (
                      id SERIAL PRIMARY KEY,
                      initial_row INT NOT NULL,  -- Coordonnée X de la position initiale
                      initial_column INT NOT NULL,  -- Coordonnée Y de la position initiale
                      final_row INT NOT NULL,    -- Coordonnée X de la position finale
                      final_column INT NOT NULL      -- Coordonnée Y de la position finale
);

CREATE TABLE Friendship (
                            id SERIAL PRIMARY KEY,
                            friend1 BIGINT,
                            friend2 BIGINT,
                            FOREIGN KEY (friend1) REFERENCES ChessUser(id) ON DELETE CASCADE,
                            FOREIGN KEY (friend2) REFERENCES ChessUser(id) ON DELETE CASCADE,
                            UNIQUE (friend1, friend2)
);
