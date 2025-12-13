INSERT INTO author (id, name) VALUES
(1, 'Jane Doe'),
(2, 'John Smith');
ALTER TABLE author ALTER COLUMN id RESTART WITH 3;

INSERT INTO book (id, title, read, owned, genre) VALUES
(1, 'The title', false, true, 'Fiction'),
(2, 'A title again', true, false, 'Non-Fiction');
ALTER TABLE book ALTER COLUMN id RESTART WITH 3;

INSERT INTO book_author (fk_book, fk_author) VALUES
(1, 1),
(2, 2);
