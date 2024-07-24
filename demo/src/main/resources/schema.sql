
create table if not exists
    users (
        id LONG NOT NULL AUTO_INCREMENT PRIMARY KEY,
        username VARCHAR(20) NOT NULL,
        email VARCHAR(50) UNIQUE NOT NULL,
        password VARCHAR(255) NOT NULL,
        deleted BOOL NOT NULL
    );

create table if not exists
    role (
              id LONG NOT NULL AUTO_INCREMENT PRIMARY KEY,
              name VARCHAR(20) NOT NULL,
              deleted BOOL NOT NULL
);

create table if not exists
    user_role (
             role_id LONG NOT NULL ,
             users_id LONG NOT NULL,
             CONSTRAINT user_role_fk FOREIGN KEY (users_id) REFERENCES users(id),
             CONSTRAINT user_role_fk1 FOREIGN KEY (role_id) REFERENCES role(id)
);

