create table if not exists players (
    id bigint generated always as identity primary key,
    name varchar(20) not null unique
);

create table if not exists matches (
    id bigint generated always as identity primary key,
    player1 bigint not null,
    player2 bigint not null,
    winner bigint,
    foreign key (player1) references players(id),
    foreign key (player2) references players(id),
    foreign key (winner) references players(id)
);