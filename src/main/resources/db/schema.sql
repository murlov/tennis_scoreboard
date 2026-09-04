create table if not exists players (
    id integer generated always as identity primary key,
    name varchar(20) not null unique
);

create table if not exists matches (
    id integer generated always as identity primary key,
    player1 integer not null,
    player2 integer not null,
    winner integer,
    foreign key (player1) references players(id),
    foreign key (player2) references players(id),
    foreign key (winner) references players(id)
);