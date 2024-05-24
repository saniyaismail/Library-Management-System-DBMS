insert into student(student_id, student_name)
VALUES(1001, 'Alice');

insert into genre(genre_no, genre_name)
VALUES(1, 'Mystery');

insert into genre(genre_no, genre_name)
VALUES(2, 'Romance');

insert into genre(genre_no, genre_name)
VALUES(3, 'Thriller');

insert into genre(genre_no, genre_name)
VALUES(4, 'Science Fiction');

insert into genre(genre_no, genre_name)
VALUES(5, 'Fantasy');

insert into book(book_id, book_name, book_author, quantity, genre_no)
VALUES(101, 'The Name of the Wind', 'Patrick Rothfuss', 5, 5);

insert into librarian(librarian_id, librarian_name)
VALUES(201, 'Jenna');

insert into student_book(student_id, book_id)
VALUES(1001, 101);