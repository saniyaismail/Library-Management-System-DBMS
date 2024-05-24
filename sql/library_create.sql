create table student(
    student_id smallint NOT NULL,
    student_name varchar(30),
    constraint pk_student PRIMARY KEY (student_id)
);

create table book(
    book_id smallint NOT NULL,
    book_name varchar(50),
    book_author varchar(50),
    quantity smallint,
    genre_no smallint,
    constraint pk_book PRIMARY KEY (book_id)
);

create table genre(
    genre_no smallint NOT NULL,
    genre_name varchar(30),
    constraint pk_genre PRIMARY KEY (genre_no)
);

create table librarian(
    librarian_id smallint NOT NULL,
    librarian_name varchar(30),
    constraint pk_librarian PRIMARY KEY (librarian_id)
);

create table student_book(
    student_id smallint NOT NULL,
    book_id smallint NOT NULL,
    constraint pk_student_book PRIMARY KEY (student_id, book_id)
);