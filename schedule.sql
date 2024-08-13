create table Member
(
    memberId bigint auto_increment,
    memberName varchar(100),
    password varchar(100),
    email varchar(100),
    primary key (memberId)
);

create table Todo
(
    todoId bigint auto_increment,
    memberId bigint,
    todo varchar(500),
    createdAt DATE,
    updatedAt DATE,
    primary key (todoId),
    foreign key (memberId) references Member(memberId) ON DELETE CASCADE ON UPDATE CASCADE
);




