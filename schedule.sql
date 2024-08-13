CREATE TABLE calendar
(
    id BIGINT AUTO_INCREMENT,
    author varchar(100) NOT NULL,
    todo varchar(500) NOT NULL,
    password varchar(100) NOT NULL,
    createAt DATE NOT NULL,
    updateAt DATE NOT NULL,
    PRIMARY KEY (id)
)

