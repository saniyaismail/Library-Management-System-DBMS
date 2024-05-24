alter table book
        add constraint fk_genre_no FOREIGN KEY (genre_no) REFERENCES genre(genre_no);

alter table student_book
        add constraint fk_student FOREIGN KEY (student_id) REFERENCES student(student_id) ON DELETE CASCADE;

alter table student_book
        add constraint fk_book FOREIGN KEY(book_id) REFERENCES book(book_id) ON DELETE CASCADE;