insert into players (name)
values ('Petr'),
       ('Alex'),
       ('Ivan'),
       ('Sergey'),
       ('Vlad'),
       ('Michael'),
       ('Max')
on conflict (name) do nothing;
